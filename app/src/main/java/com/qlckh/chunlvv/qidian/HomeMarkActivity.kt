package com.qlckh.chunlvv.qidian

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.AsyncTask
import android.os.Build
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import com.bumptech.glide.Glide
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.api.ApiService
import com.qlckh.chunlvv.base.BaseActivity
import com.qlckh.chunlvv.common.XLog
import com.qlckh.chunlvv.dao.HomeInfo
import com.qlckh.chunlvv.http.RxHttpUtils
import com.qlckh.chunlvv.http.interceptor.Transformer
import com.qlckh.chunlvv.http.observer.CommonObserver
import com.qlckh.chunlvv.http.utils.IntentUtil
import com.qlckh.chunlvv.preview.ImgInfo
import com.qlckh.chunlvv.preview.PrePictureActivity
import com.qlckh.chunlvv.user.UserConfig
import com.qlckh.chunlvv.utils.Base64Util
import com.qlckh.chunlvv.utils.ImgUtil
import kotlinx.android.synthetic.main.activity_home_mark.*
import java.io.File
import java.lang.ref.WeakReference
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
    private var isDone = true
    private var imgPath = ""
    override fun initView() {
        setTitle("评分")
        goBack()
        initlistener()
        setPic()
    }

    private fun initlistener() {

        submit.setOnClickListener {
            if (isDone) {
                postData()
            } else {
                showShort("等待图片上传,请稍后重试")
            }
        }
    }

    private fun postData() {
        loading()
        RxHttpUtils.createApi(ApiService::class.java)
                .mark(intent.getStringExtra("ncode"), status, UserConfig.getUserid(),imgPath,tv_score.text.toString())
                .compose(Transformer.switchSchedulers())
                .subscribe(object : CommonObserver<Any>() {
                    override fun onError(errorMsg: String) {
                        cancelLoading()
                        showLong(errorMsg)
                    }

                    override fun onSuccess(homeInfo: Any) {
                        cancelLoading()
                        showShort("评价成功~~")
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
        tvName.text = homeInfo!!.data.fullname
        tvAddress.text = homeInfo!!.data.company
        rg.setOnCheckedChangeListener { group, checkedId ->
            val findViewById = group.findViewById<RadioButton>(checkedId)
            if (!findViewById.isPressed) {
                return@setOnCheckedChangeListener
            }
            if (checkedId == rb1.id) {
                status = 1
                tv_score.text="5"
            } else if (checkedId == rb2.id) {
                status = 2
                tv_score.text="3"
            } else {
                status = 0
                tv_score.text="1"

            }
        }
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
            val params = LinearLayout.LayoutParams(40, 40)
            iv.scaleType = ImageView.ScaleType.FIT_XY
            iv.layoutParams = params
            picModify.addView(iv)
            val info = ImgInfo()
            info.url = filePath
            imgInfos.add(info)
            Glide.with(this).load(filePath).into(iv)
            info.url = filePath
            imgInfos.add(info)
            Glide.with(this).load(filePath).into(iv)
            iv.setOnClickListener {
                startPre(it)
            }
            i++
        }

        val iv = ImageView(this)
        val params = LinearLayout.LayoutParams(40, 40)
        iv.layoutParams = params
        if (picModify.getChildCount() < 4) {
            picModify.addView(iv)
        }
        iv.setImageResource(R.drawable.task_iv_default)
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
