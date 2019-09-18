package com.qlckh.chunlvv.activity

import android.app.AlertDialog
import com.jakewharton.rxbinding2.view.RxView
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_evaluation.*
import kotlinx.android.synthetic.main.activity_text.*
import uhf.MultiLableCallBack
import uhf.Reader

/**
 * @author Andy
 * @date   2019/9/9 11:37
 * Desc:
 */
class TextActivity : BaseActivity(), MultiLableCallBack {
    val create = PublishSubject.create<String>()
    override fun method(p0: String?) {
        val subscribe = Observable.just(p0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it == null) {
                        tvSource.text = "没有接收到数据"
                        return@subscribe
                    }
                    tvSource.text = it
                    val replace = it.split(",")[5].replace("-", "")
                    tvSplit.text = replace

                }


    }

    lateinit var ReaderController: Reader

    override fun initView() {
        ReaderController = Reader(this)
        val aBoolean = ReaderController.OpenSerialPort_Android("/dev/ttyS3")
        val socketState = ReaderController.GetClientInfo()[0]
        ReaderController.StartMultiEPC(socketState)
        val alertDialog = AlertDialog.Builder(this)
                .setMessage(aBoolean.toString())
                .create()
        alertDialog.show()
    }

    override fun showError(msg: String?) {
    }

    override fun getContentView(): Int {

        return R.layout.activity_text
    }

    override fun isSetFondSize(): Boolean {
        return false
    }

    override fun initDate() {


    }

    override fun release() {
    }
}