package com.qlckh.chunlvv.intelligent

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Handler
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.api.ApiService
import com.qlckh.chunlvv.base.BaseActivity
import com.qlckh.chunlvv.common.XLog
import com.qlckh.chunlvv.dao.BannerDao
import com.qlckh.chunlvv.dao.PostImgDao
import com.qlckh.chunlvv.http.RxHttpUtils
import com.qlckh.chunlvv.http.interceptor.Transformer
import com.qlckh.chunlvv.http.observer.CommonObserver
import com.qlckh.chunlvv.http.utils.ToastUtils.showToast
import com.qlckh.chunlvv.qidian.HomeMarkActivity
import com.qlckh.chunlvv.user.UserConfig
import com.qlckh.chunlvv.utils.Base64Util
import com.qlckh.chunlvv.utils.GlideUtil
import com.qlckh.chunlvv.utils.ImgUtil
import kotlinx.android.synthetic.main.activity_home_mark.*
import kotlinx.android.synthetic.main.activity_test1.*
import java.io.File
import java.lang.ref.WeakReference

/**
 * @author Andy
 * @date   2020/6/23 17:43
 * Desc:
 */
class TextActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_test1
    }

    override fun isSetFondSize(): Boolean {
        return false
    }

    override fun initDate() {

        val intent = Intent(this, CheckMappingActivity::class.java)
        startActivityForResult(intent, 1000)
    }

    override fun initView() {
    }

    override fun showError(msg: String?) {
    }

    override fun release() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (requestCode == 1000) {
                val filePath = data.getStringExtra("filePath")
                GlideUtil.displayImage(this, filePath, ivm)
                val compress = ImgUtil.compress(File(filePath), 45, 2100000)
                doTask(compress)
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
            val activity = reference.get() as TextActivity?
            if (activity == null || activity.isFinishing) {
                return
            }
            RxHttpUtils.createApi(ApiService::class.java)
                    .postImg("21164", s)
                    .compose(Transformer.switchSchedulers())
                    .subscribe(object : CommonObserver<PostImgDao>() {
                        override fun onError(errorMsg: String?) {
                        }

                        override fun onSuccess(t: PostImgDao?) {
                            showToast(t?.data)
                        }
                    })

        }

        override fun onCancelled() {
            super.onCancelled()
            val activity = reference.get() as TextActivity?
            if (activity == null || activity.isFinishing) {
                return
            }
        }

        override fun onPreExecute() {
            super.onPreExecute()
            val activity = reference.get() as TextActivity?
            if (activity == null || activity.isFinishing) {
                return
            }
        }
    }
}