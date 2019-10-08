package com.qlckh.chunlvv.intelligent

import android.content.Intent
import android.os.Handler
import android.view.View
import com.qlckh.chunlvv.App
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.activity.LoginActivity
import com.qlckh.chunlvv.api.ApiService
import com.qlckh.chunlvv.base.BaseActivity
import com.qlckh.chunlvv.dao.HomeInfo
import com.qlckh.chunlvv.http.RxHttpUtils
import com.qlckh.chunlvv.http.interceptor.Transformer
import com.qlckh.chunlvv.http.observer.CommonObserver
import com.qlckh.chunlvv.qidian.HomeMarkActivity
import com.qlckh.chunlvv.user.UserConfig
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_intenlligent_mark.*
import uhf.MultiLableCallBack
import uhf.Reader
import java.util.concurrent.TimeUnit


/**
 * @author Andy
 * @date   2019/9/9 16:30
 * Desc:
 */
class IntenlligentMarkActivity : BaseActivity(), MultiLableCallBack {
    override fun showError(msg: String?) {
        showLong(msg)
    }


    var i = 0
    var j = 0
    var isStart = false
    override fun method(p0: String?) {

        runOnUiThread {
            tvSource.text = p0 + "=========" + i + "-->" + j + "==>" + (subscribe == null).toString()
        }

        var ncode = ""
        if (p0 != null) {
            if (p0.split(",").size < 6) {
                initDate()
                return
            }
            if (i == 0) {
//                i+=1
                subscribe = Flowable.just(p0)
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .onBackpressureLatest()
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter {
                            val split = it.split(",")
                            if (split.size < 6) {
                                
                                i = 0
//                                startSelf()
                                showError("识别编码有误,请重试")
                            }
                            split.size > 5
                        }
                        .map {
                            it.split(",")[5].replace("-", "")
                        }
                        .filter {
                            if (it.length != 24) {
//                                startSelf()
                                
                                i = 0
                                showLong("解析的编码不正确,请重试")
                            }
                            it.length == 24
                        }
                        .doOnNext {
                            loading()
                            ncode = it
                        }
                       .share()
                        .subscribe({
                            RxHttpUtils.createApi(ApiService::class.java)
                                    .queryInfo(it)
                                    .compose(Transformer.switchSchedulers())
                                    .subscribe(object : CommonObserver<HomeInfo>() {
                                        override fun onError(errorMsg: String?) {
                                            compositeDisposable1.clear()
                                            i = 0
                                            cancelLoading()
                                            showLong(errorMsg)
//                                            startSelf()
                                        }

                                        override fun onSuccess(homeInfo: HomeInfo?) {
                                            cancelLoading()
                                            if (homeInfo == null) {
                                                showLong("获取用户信息失败")
                                                
                                                i = 0
                                                return
                                            }
                                            if (homeInfo.status == 1) {
                                                if (!isStart) {
                                                    val intent = Intent(this@IntenlligentMarkActivity, HomeMarkActivity::class.java)
                                                    intent.putExtra("homeinfo", homeInfo)
                                                    intent.putExtra("ncode", it)
                                                    startActivity(intent)
                                                    finish()
                                                    isStart = true
                                                }

                                            } else {
                                                showShort(homeInfo.getMsg())
                                            }
                                        }


                                    })

                        }, {
                            
                            cancelLoading()
                        })
                if (subscribe != null) {
                    compositeDisposable1.add(subscribe!!)
                }
            }
            /* else {
                 runOnUiThread {
                     showLong("请重试")
                 }
                 Handler().postDelayed({
                     
                 }, 2000)
             }*/
        } else {
            runOnUiThread {
                tvSource.text = "没有接收到数据"
            }
        }


    }

    fun startSelf() {
        startActivity(Intent(this, IntenlligentMarkActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }

    override fun initView() {

        setTitle("巡检")
        goBack()
        ibRight.visibility = View.VISIBLE
        ibRight.text = "退出登录"
        ibRight.setOnClickListener {

            /*   val intent = Intent(this,IntelligentLuanchActivity::class.java)
               intent?.run {
                   type="restart"
                   action="restart"
                   addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                   addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                   startActivity(this)
               }

               Handler().postDelayed({
                   android.os.Process.killProcess(android.os.Process.myPid());
               },20)*/
            logout()
        }

        tvSource.setOnClickListener {
            //                        method("0,0,0,0,0,0080000C1050184-5000B28FC")
            tvSource1.text = "--" + ReaderController.RecvStr +
                    //            +"--\n\n--"+ReaderController.SerialResult
                    "--\n\n--" + ReaderController.SendResult + "--"
        }
    }

    private fun logout() {
        UserConfig.reset()
        UserConfig.userInfo = null
        UserConfig.userResp = null
        val intent1 = Intent(this, LoginActivity::class.java)
        intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent1)
    }

    override fun initSlidr() {

    }

    lateinit var ReaderController: Reader
    val compositeDisposable = CompositeDisposable()
    val compositeDisposable1 = CompositeDisposable()
    var subscribe: Disposable? = null
    override fun getContentView(): Int {
        return R.layout.activity_intenlligent_mark
    }

    override fun isSetFondSize(): Boolean {
        return false
    }

    override fun initDate() {

        ReaderController = Reader(this)
        val aBoolean = ReaderController.OpenSerialPort_Android("/dev/ttyS1")
        tvSource.text = if (aBoolean) "扫描开启成功" else "扫描开启失败"
        if (ReaderController.GetClientInfo() == null || ReaderController.GetClientInfo().size < 1) {
            tvSource.text = "扫描开启失败"
            return
        }
        val socketState = ReaderController.GetClientInfo()[0]
//        ReaderController.SetPower(socketState, 0xc.toByte(), 0xc.toByte())

        val subscribe1 = Observable.interval(1, 1, TimeUnit.SECONDS).subscribe {
            ReaderController.StartMultiEPC(socketState)
        }
        compositeDisposable.add(subscribe1)


    }

    private fun readSocket() {
        val app = application as App
        val reader = app.getReader(this)
        val socketState = app.socketState
        Handler().postDelayed({
            reader.StartMultiEPC(socketState)
        }, 1000)
        //        tvSource.text = if (aBoolean) "扫描开启成功" else "扫描开启失败"
        tvSource1.text = reader.RecvStr
    }

    override fun release() {
        compositeDisposable.clear()
        compositeDisposable1.clear()
//        ReaderController.CloseSerialPort_Android()
    }
}