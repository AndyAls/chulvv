package com.qlckh.chunlvv.qidian

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.*
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.api.ApiService
import com.qlckh.chunlvv.base.BaseActivity
import com.qlckh.chunlvv.carpaly.ConvertUtils
import com.qlckh.chunlvv.common.MediaPlayerHelper
import com.qlckh.chunlvv.common.XLog
import com.qlckh.chunlvv.dao.HomeInfo
import com.qlckh.chunlvv.dao.PostImgDao
import com.qlckh.chunlvv.http.RxHttpUtils
import com.qlckh.chunlvv.http.interceptor.Transformer
import com.qlckh.chunlvv.http.observer.CommonObserver
import com.qlckh.chunlvv.http.utils.IntentUtil
import com.qlckh.chunlvv.http.utils.ScreenUtil
import com.qlckh.chunlvv.http.utils.ToastUtils
import com.qlckh.chunlvv.intelligent.CheckMappingActivity
import com.qlckh.chunlvv.intelligent.IntenlligentMarkActivity
import com.qlckh.chunlvv.manager.OnSerialPortDataListener
import com.qlckh.chunlvv.preview.ImgInfo
import com.qlckh.chunlvv.preview.PrePictureActivity
import com.qlckh.chunlvv.user.UserConfig
import com.qlckh.chunlvv.utils.Base64Util
import com.qlckh.chunlvv.utils.GlideUtil
import com.qlckh.chunlvv.utils.ImgUtil
import com.qlckh.chunlvv.utils.ScreenUtils
import kotlinx.android.synthetic.main.activity_home_mark.*
import kotlinx.android.synthetic.main.activity_test1.*
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.util.*

/**
 * @author Andy
 * @date 2019/6/4 16:58
 * Desc:
 */
class HomeMarkActivity : BaseActivity() {
    var status = 1
    var homeInfo: HomeInfo? = null
    private val REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA = 1000
    private val REQUEST_CODE_SELECT_PIC_FROM_CAMERA = 1001
    private lateinit var mediaPlayerHelper: MediaPlayerHelper
    private var isDone = true
    private var imgPath = ""
    private val BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private val NAME = "BT_DEMO"
    var bluetoothAdapter: BluetoothAdapter? = null
    var listenerThread: ListenerThread? = null
    var connectThread: ConnectThread? = null
    private var bluetoothDevice: BluetoothDevice? = null
    private val BUFFER_SIZE = 1024
    var threadAq: Thread? = null
    var score = 10
    var score1 = 10
    var score2 = 10
    var score3 = 10
    var score4 = 10
    var flag_status = "1"
    var flag_status1 = "1"
    var flag_status2 = "1"
    var flag_status3 = "1"
    private var imagesId = ""
    override fun initView() {

        setTitle("易腐垃圾")
        ibRight.visibility = View.GONE
        ibRight.setText("提交评价")
        setPic()
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//        searchDevices();
        listenerThread = ListenerThread()
        setScore()
        setView()
//        listenerThread!!.start()
    }

    private fun setView() {

        tvTong1.isSelected = true
        tvTing1.isSelected = true
        tvSheng1.isSelected = true
        tvLa1.isSelected = true
        tvPing1.isSelected = true
    }

    /**
     * 搜索蓝牙设备
     */
    private fun searchDevices() {
        if (bluetoothAdapter!!.isDiscovering) {
            bluetoothAdapter!!.cancelDiscovery()
        }
        bluetoothAdapter!!.startDiscovery()
        getBoundedDevices()

    }

    private fun setScore() {
        val totalScore = score + score4 + score3 + score2 + score1
        tv_score.text = totalScore.toString()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    /**
     * 获取已经配对过的设备
     */
    @SuppressLint("NewApi")
    private fun getBoundedDevices() {
        //获取已经配对过的设备
        val pairedDevices = bluetoothAdapter!!.bondedDevices
        //将其添加到设备列表中
        if (pairedDevices.size > 0) {
            for (device in pairedDevices) {
                if ("FAYA".equals(device.name, ignoreCase = true)) {
                    bluetoothDevice = device
                    connectDevice(device)
                }
            }
        }
    }

    /**
     * 连接蓝牙设备
     */
    private fun connectDevice(device: BluetoothDevice) {
        try {
            //创建Socket
            val socket = device.createInsecureRfcommSocketToServiceRecord(BT_UUID)
            copySocket = socket

            //启动连接线程
            connectThread = ConnectThread(socket, true)
//            connectThread!!.start()

        } catch (e: IOException) {
            runOnUiThread {
                showLong("connect-->" + e.message)
            }
            e.printStackTrace()
        }

    }

    /**
     * 监听线程
     */
    inner class ListenerThread : Thread() {
        private var serverSocket: BluetoothServerSocket? = null
        private var socket: BluetoothSocket? = null

        override fun run() {
            try {
                serverSocket = bluetoothAdapter!!.listenUsingInsecureRfcommWithServiceRecord(NAME, BT_UUID)
                socket = serverSocket!!.accept()
                while (true) {
                    //线程阻塞，等待别的设备连接

                    connectThread = ConnectThread(socket!!, false)
//                    connectThread!!.start()
                }
            } catch (err: OutOfMemoryError) {
                System.gc()
            } catch (e: Exception) {

                e.printStackTrace()
                runOnUiThread {
                    showLong(" Listener-->" + e.message)
                }
            }

        }
    }

    internal lateinit var inputStream: InputStream
    var copySocket: BluetoothSocket? = null

    /**
     * 连接线程
     */
    inner class ConnectThread(var socket: BluetoothSocket, val activeConnect: Boolean) : Thread() {

        internal var outputStream: OutputStream? = null
        override fun run() {
            try {
                //如果是自动连接 则调用连接方法
                if (activeConnect) {
//                    threadAq = thread {
                    socket.connect()
//                    }

                }

                inputStream = socket.inputStream
                //                outputStream = socket.getOutputStream();

                val buffer = ByteArray(BUFFER_SIZE)//1024
                var bytes: Int
                while (true) {
                    //读取数据
                    bytes = inputStream.read(buffer)
                    if (bytes > 0) {
                        val data = ByteArray(bytes)
                        System.arraycopy(buffer, 0, data, 0, bytes)
                        if (etWeight != null) {
                            etWeight.post {
                                if (etWeight != null) {
//                                    etWeight.setText(getWeight(String(data)))
//                                    etWeight.isEnabled = false
                                }
                            }
                        }

                    }
                }

            } catch (e: Exception) {

                try {
                    runOnUiThread {
                        showLong("recive-->" + e.message)
                    }
                    release()
                    socket.close()
                    copySocket?.close()
                    Handler(Looper.getMainLooper()).postDelayed({
                        //                        restartSelf()

                    }, 0)
                } catch (e: Exception) {

                    runOnUiThread {
                        showLong("请检查蓝牙设备")
                    }
                }


                /* runOnUiThread {
                     if (listenerThread == null) {
                         listenerThread = ListenerThread();
                         listenerThread!!.start();
                     }
                     searchDevices();
                 }
 */
                /*   try {
                       val method = socket.remoteDevice!!.javaClass.getMethod("createRfcommSocket", Int.javaClass)
                       socket = method.invoke(socket.remoteDevice, 1) as BluetoothSocket
                       socket.connect()
                       copySocket = socket
                   } catch (e: Exception) {
                       goBack()
                   }*/
                e.printStackTrace()

            }

        }
    }

    private fun restartSelf() {
        val intent = Intent(this@HomeMarkActivity, HomeMarkActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("homeinfo", homeInfo)
        intent.putExtra("ncode", intent.getStringExtra("ncode"))
        overridePendingTransition(0, 0)
        startActivity(intent)
        finishAffinity()
        Handler().postDelayed({
            Process.killProcess(Process.myPid());
        }, 50)
    }

    private fun getWeight(s: String): String {
        if (isEmpty(s)) {
            return ""
        }
        val split = s.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (split.isEmpty()) {
            return "0"
        }
        val source = split[0]
        var reverse = reverse(source)
        if (reverse.isEmpty()) {
            return "0"
        }
        val c = reverse[0]
        if ("-" == reverse[0] + "") {
            reverse = if (isEmpty(reverse.substring(1))) "0" else reverse.substring(1)
        }
        return java.lang.Double.parseDouble(reverse).toString()
    }

    private fun reverse(str: String): String {
        return StringBuilder(str).reverse().toString()
    }


    private fun postData() {
        loading()
        RxHttpUtils.createApi(ApiService::class.java)
                .mark1(intent.getStringExtra("ncode"), status, UserConfig.getUserid(), imagesId,
                        tv_score.text.toString(), etWeight.text.toString(),
                        flag_status, flag_status1, flag_status2, flag_status3)
                .compose(Transformer.switchSchedulers())
                .subscribe(object : CommonObserver<Any>() {
                    override fun onError(errorMsg: String) {
                        cancelLoading()
                        showLong(errorMsg)
                    }

                    override fun onSuccess(homeInfo: Any) {
                        cancelLoading()
//                        showShort("评价成功~~")
                        if (tv_score.text.toString().trim().toDouble() >= 37) {
                            mediaPlayerHelper.startPlay(R.raw.hege)
                        } else {
                            mediaPlayerHelper.startPlay(R.raw.buhege)
                        }
                        Handler().postDelayed({
                            cancelLoading()
                            val intent = Intent(this@HomeMarkActivity, IntenlligentMarkActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 3500)


                    }
                })
    }

    override fun showError(msg: String?) {
    }

    override fun getContentView(): Int {
        return R.layout.activity_home_mark
    }

    override fun isSetFondSize(): Boolean {

        return false
    }

    override fun initDate() {

        homeInfo = intent.getParcelableExtra<HomeInfo>("homeinfo")
        if (homeInfo != null) {
            tvName.text = homeInfo!!.data.fullname
            tvAddress.text = homeInfo!!.data.company
        }
        initListener()
        setWeightListener()

        val intent = Intent(this, CheckMappingActivity::class.java)
        startActivityForResult(intent, 19999)
        mediaPlayerHelper = MediaPlayerHelper.getInstance(this)
    }

    private fun initListener() {

        submit.setOnClickListener {
            if (imagesId.isNotEmpty()) {
                postData()
            } else {
                showShort("等待图片上传,请稍后重试")
            }
        }
        button.setOnClickListener {
            mWeightManager.sendBytes(ConvertUtils.hexString2Bytes("55000001207403"))
        }
        tvPing1.setOnClickListener {
            status = 1
            score = 10
            setScore()
            tvPing1.isSelected = true
            tvPing2.isSelected = false
            tvPing3.isSelected = false
        }
        tvPing2.setOnClickListener {
            status = 2
            score = 3
            setScore()
            tvPing1.isSelected = false
            tvPing2.isSelected = true
            tvPing3.isSelected = false

        }
        tvPing3.setOnClickListener {
            status = 0
            score = 1
            setScore()
            tvPing1.isSelected = false
            tvPing2.isSelected = false
            tvPing3.isSelected = true
        }

        tvTong1.setOnClickListener {
            flag_status = "1"
            score1 = 10
            setScore()
            tvTong1.isSelected = true
            tvTong2.isSelected = false
        }
        tvTong2.setOnClickListener {
            flag_status = "0"
            score1 = 0
            setScore()
            tvTong1.isSelected = false
            tvTong2.isSelected = true
        }


        tvTing1.setOnClickListener {
            flag_status1 = "1"
            score2 = 10
            setScore()
            tvTing1.isSelected = true
            tvTing2.isSelected = false
        }

        tvTing2.setOnClickListener {
            flag_status1 = "0"
            score2 = 0
            setScore()
            tvTing1.isSelected = false
            tvTing2.isSelected = true
        }
        tvSheng1.setOnClickListener {
            score3 = 10
            flag_status2 = "1"
            setScore()
            tvSheng1.isSelected = true
            tvSheng2.isSelected = false
        }
        tvSheng2.setOnClickListener {
            score3 = 0
            flag_status2 = "0"
            setScore()
            tvSheng1.isSelected = false
            tvSheng2.isSelected = true
        }
        tvLa1.setOnClickListener {
            score4 = 10
            flag_status3 = "1"
            setScore()
            tvLa1.isSelected = true
            tvLa2.isSelected = false
        }
        tvLa2.setOnClickListener {
            score4 = 0
            flag_status3 = "0"
            setScore()
            tvLa1.isSelected = false
            tvLa2.isSelected = true
        }
    }

    private fun setWeightListener() {

        mWeightManager.setOnSerialPortDataListener(object : OnSerialPortDataListener {
            override fun onDataReceived(bytes: ByteArray?) {

                val message = Message()
                message.what = WEIGHT_WHAT
                message.obj = bytes
                mHandler.sendMessageDelayed(message, 500)

            }

            override fun onDataSent(bytes: ByteArray?) {

            }

        })
    }

    private val WEIGHT_WHAT = 10009
    private val mHandler = Handler(Handler.Callback { msg ->
        val what = msg.what
        when (what) {

            WEIGHT_WHAT -> {
                handWeight(msg.obj as ByteArray)
            }

        }
        false
    })
    internal var buffer = StringBuffer()
    private fun handWeight(bytes: ByteArray) {
        buffer.append(ConvertUtils.bytes2HexString(bytes))
        if (!buffer.toString().startsWith("55")) {
            buffer.delete(0, buffer.length)
            return
        }
        if (buffer.length == 18) {
//            tvState.text = buffer.toString()
            if (getMeight(buffer.toString()).toDouble() > 100) {
                mWeightManager.sendBytes(ConvertUtils.hexString2Bytes("55000001207403"))
                etWeight.setText("0")
            } else {
                if (etWeight != null) {
                    etWeight.setText(getMeight(buffer.toString()))
                    etWeight.isEnabled = false
                }
            }
            buffer.delete(0, buffer.length)
        }
    }

    private fun getMeight(str: String): String {

        val sequence = str.subSequence(10, 14).toString()
        val weight = sequence.toLong(16)

        tvState1.text = sequence + "  -->  " + weight

        return div(weight.toDouble(), 100.toDouble(), 10).toString()
    }

    fun div(d1: Double, d2: Double, scale: Int): Double {
        if (scale < 0) {
            throw IllegalArgumentException("The scale must be a positive integer or zero")
        }
        val b1 = BigDecimal(java.lang.Double.toString(d1))
        val b2 = BigDecimal(java.lang.Double.toString(d2))
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()

    }

    internal var imgInfos = ArrayList<ImgInfo>()
    private var photoPath: String? = null
    private val picFilePathList = ArrayList<String?>()

    /**
     * 设置照片
     */
    private fun setPic() {
        picModify.setColumNum(1)
        picModify.removeAllViews()
        imgInfos.clear()
        var i = 0
        val picFilePathListSize = picFilePathList.size
        while (i < picFilePathListSize) {
            val filePath = picFilePathList.get(i)
            val iv = ImageView(this)
            val params = LinearLayout.LayoutParams(ScreenUtils.dp2px(this, 65f), ScreenUtils.dp2px(this, 65f))
            iv.scaleType = ImageView.ScaleType.CENTER_CROP
            iv.layoutParams = params
            picModify.addView(iv)
            val info = ImgInfo()
            info.url = filePath
            imgInfos.add(info)
            Glide.with(this).load(filePath).into(iv)
            iv.setOnClickListener {
                startPre(it)
            }
            i++
        }

        val iv = ImageView(this)
        val params = LinearLayout.LayoutParams(ScreenUtils.dp2px(this, 65f), ScreenUtils.dp2px(this, 65f))
        iv.layoutParams = params
        if (picModify.getChildCount() < 1) {
            picModify.addView(iv)
        }
        iv.setImageResource(R.drawable.ic_take_photo)
        iv.setBackgroundColor(Color.WHITE)
        iv.setOnClickListener { v ->
            /* photoPath = ImgUtil.getPicSavaPath(this) + "/" + System.currentTimeMillis() + ".jpg"
             if (Build.VERSION.SDK_INT > 23) {
                 startActivityForResult(IntentUtil.getGrantPicFromCameraIntent(this@HomeMarkActivity, photoPath),
                         REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA)
             } else {
                 startActivityForResult(IntentUtil.getPicFromCameraIntent(photoPath),
                         REQUEST_CODE_SELECT_PIC_FROM_CAMERA)
             }*/
        }
    }

    private fun startPre(v: View) {
        val currentIndex = picModify.indexOfChild(v)
        for (j in imgInfos.indices) {
            val rect = Rect()
            val imageView = picModify.getChildAt(j) as ImageView
            if (imageView != null) {
                imageView.getGlobalVisibleRect(rect)
                imgInfos[j].bounds = rect
            }
        }
        PrePictureActivity.start(this, imgInfos, currentIndex)
    }

    override fun release() {

        //取消搜索
        if (bluetoothAdapter != null && bluetoothAdapter!!.isDiscovering) {
            bluetoothAdapter!!.cancelDiscovery()
        }

        try {
            if (listenerThread != null) {
                listenerThread!!.stop()
                listenerThread!!.destroy()
                listenerThread = null
            }
            if (connectThread != null) {
                connectThread!!.stop()
                connectThread!!.destroy()
                connectThread = null
            }
            if (threadAq != null) {
                threadAq!!.stop()
                threadAq!!.destroy()
                threadAq = null
            }
            inputStream.run {
                close()
            }
            copySocket?.run {
                close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        /* try {
            if (connectThread != null) {
                if (connectThread.inputStream != null) {
                    connectThread.inputStream.close();
                    connectThread.inputStream = null;
                }

                if (connectThread.socket != null) {
                    connectThread.socket.close();
                    connectThread.socket=null;

                }

                connectThread.interrupt();
                connectThread=null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        imagesId = ""
        mediaPlayerHelper.release()
        if (!filePath.isNotEmpty()) {
            val file = File(filePath)
            if (file.exists()) {
                file.delete()
            }
            filePath = ""
        }
    }

    override fun onResume() {
        super.onResume()
        mediaPlayerHelper.onResume()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayerHelper.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_SELECT_PIC_FROM_CAMERA -> {
                    picFilePathList.add(photoPath)
                    val compress = ImgUtil.compress(File(photoPath), 45, 2100000)
                    Handler().post { this.setPic() }
                    doTask(compress)
                }
                REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA -> {
                    picFilePathList.add(photoPath)
                    val compress1 = ImgUtil.compress(File(photoPath), 45, 2100000)
                    Handler().post { this.setPic() }
                    doTask(compress1)
                }
                19999 -> {
                    filePath = data.getStringExtra("filePath") ?: ""
                    picFilePathList.add(filePath)
                    val compress = ImgUtil.compress(File(filePath), 55, 2100000)
                            Handler().post { this.setPic() }
                    doTask(compress)
                }
            }
        }
    }

    private var filePath = ""
    private fun doTask(compress: File) {

        val task = MyTask(this)
        task.execute(compress)

    }

    private class MyTask internal constructor(activity: Activity) : AsyncTask<File, Void, String>() {

        private val reference: WeakReference<Activity>

        init {
            reference = WeakReference(activity)
        }

        override fun doInBackground(vararg files: File): String {
            val ioEncode = Base64Util.ioEncode(files[0])
            val s = "data:image/png;base64,$ioEncode"
            XLog.e("+++", "doInBackground", ioEncode)
            val source = s + "分"
            XLog.e("+++", "source", source)
            return source
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            val activity = reference.get() as HomeMarkActivity?
            if (activity == null || activity.isFinishing) {
                return
            }
            RxHttpUtils.createApi(ApiService::class.java)
                    .postImg(UserConfig.getUserid(), s)
                    .compose(Transformer.switchSchedulers())
                    .subscribe(object : CommonObserver<PostImgDao>() {
                        override fun onError(errorMsg: String?) {
                        }

                        override fun onSuccess(t: PostImgDao?) {
                            if (t?.status == "200") {
                                activity.imagesId = t.data
                            }

                        }
                    })
            /*  activity.imgPath += s
              XLog.e("+++", "s", s)
              XLog.e("+++", "imgPath", activity.imgPath)
              activity.isDone = true*/
        }

        override fun onCancelled() {
            super.onCancelled()
            val activity = reference.get() as HomeMarkActivity?
            if (activity == null || activity.isFinishing) {
                return
            }
            activity.isDone = true
        }

        override fun onPreExecute() {
            super.onPreExecute()
            val activity = reference.get() as HomeMarkActivity?
            if (activity == null || activity.isFinishing) {
                return
            }
            activity.isDone = false
        }
    }
}
