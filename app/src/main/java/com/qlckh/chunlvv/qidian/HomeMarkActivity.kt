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
import android.widget.RadioButton
import com.bumptech.glide.Glide
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.api.ApiService
import com.qlckh.chunlvv.base.BaseActivity
import com.qlckh.chunlvv.carpaly.ConvertUtils
import com.qlckh.chunlvv.common.XLog
import com.qlckh.chunlvv.dao.HomeInfo
import com.qlckh.chunlvv.http.RxHttpUtils
import com.qlckh.chunlvv.http.interceptor.Transformer
import com.qlckh.chunlvv.http.observer.CommonObserver
import com.qlckh.chunlvv.http.utils.IntentUtil
import com.qlckh.chunlvv.intelligent.IntelligentLuanchActivity
import com.qlckh.chunlvv.intelligent.IntenlligentMarkActivity
import com.qlckh.chunlvv.manager.OnSerialPortDataListener
import com.qlckh.chunlvv.preview.ImgInfo
import com.qlckh.chunlvv.preview.PrePictureActivity
import com.qlckh.chunlvv.user.UserConfig
import com.qlckh.chunlvv.utils.Base64Util
import com.qlckh.chunlvv.utils.ImgUtil
import kotlinx.android.synthetic.main.activity_home_mark.*
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.math.MathContext
import java.util.*
import kotlin.concurrent.thread

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
    override fun initView() {

        setTitle("易腐垃圾")
        ibRight.visibility = View.GONE
        ibRight.setText("提交评价")
        initlistener()
        setPic()
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//        searchDevices();
        listenerThread = ListenerThread()
//        listenerThread!!.start()
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

    private fun initlistener() {

        submit.setOnClickListener {
            if (isDone) {
                postData()
            } else {
                showShort("等待图片上传,请稍后重试")
            }
        }
        button.setOnClickListener {
            mWeightManager.sendBytes(ConvertUtils.hexString2Bytes("55000001207403"))
        }
    }

    private fun postData() {
        loading()
        RxHttpUtils.createApi(ApiService::class.java)
                .mark(intent.getStringExtra("ncode"), status, UserConfig.getUserid(), imgPath, tv_score.text.toString(), etWeight.text.toString())
                .compose(Transformer.switchSchedulers())
                .subscribe(object : CommonObserver<Any>() {
                    override fun onError(errorMsg: String) {
                        cancelLoading()
                        showLong(errorMsg)
                    }

                    override fun onSuccess(homeInfo: Any) {
                        cancelLoading()
                        showShort("评价成功~~")
                        val intent = Intent(this@HomeMarkActivity, IntenlligentMarkActivity::class.java)
                        startActivity(intent)
                        finish()

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
        rg.setOnCheckedChangeListener { group, checkedId ->
            val findViewById = group.findViewById<RadioButton>(checkedId)
            if (!findViewById.isPressed) {
                return@setOnCheckedChangeListener
            }
            if (checkedId == rb1.id) {
                status = 1
                tv_score.text = "5"
            } else if (checkedId == rb2.id) {
                status = 2
                tv_score.text = "3"
            } else {
                status = 0
                tv_score.text = "1"

            }
        }
        setWeightListener()
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
            tvState.text = buffer.toString()
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
        picModify.setColumNum(4)
        picModify.removeAllViews()
        imgInfos.clear()
        var i = 0
        val picFilePathListSize = picFilePathList.size
        while (i < picFilePathListSize) {
            val filePath = picFilePathList.get(i)
            val iv = ImageView(this)
            val params = LinearLayout.LayoutParams(35, 35)
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
        val params = LinearLayout.LayoutParams(35, 35)
        iv.layoutParams = params
        if (picModify.getChildCount() < 4) {
            picModify.addView(iv)
        }
        iv.setImageResource(R.drawable.ic_take_photo)
        iv.setBackgroundColor(Color.WHITE)
        iv.setOnClickListener { v ->
            photoPath = ImgUtil.getPicSavaPath(this) + "/" + System.currentTimeMillis() + ".jpg"
            if (Build.VERSION.SDK_INT > 23) {
                startActivityForResult(IntentUtil.getGrantPicFromCameraIntent(this@HomeMarkActivity, photoPath),
                        REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA)
            } else {
                startActivityForResult(IntentUtil.getPicFromCameraIntent(photoPath),
                        REQUEST_CODE_SELECT_PIC_FROM_CAMERA)
            }
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
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
            }
        }
    }

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
            activity.imgPath += s
            XLog.e("+++", "s", s)
            XLog.e("+++", "imgPath", activity.imgPath)
            activity.isDone = true
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
