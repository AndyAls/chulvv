package com.qlckh.chunlvv.intelligent

import android.content.Intent
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_itelligent_delay.*
import java.util.concurrent.TimeUnit

/**
 * @author Andy
 * @date   2019/9/12 16:41
 * Desc:
 */
class IntelligentDelayActivity : BaseActivity() {

    val Duration = 10
    override fun getContentView(): Int {

        return R.layout.activity_itelligent_delay
    }

    override fun isSetFondSize(): Boolean {

        return false
    }

    lateinit var subscribe: Disposable
    override fun initView() {

        subscribe = Observable.interval(1, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (Duration - it == 0L) {
                        startActivity(Intent(this, IntenlligentMarkActivity::class.java))
                        finish()
                        overridePendingTransition(0,0)
                    }
                    tvSource.text = (Duration - it).toString()
                }

    }


    override fun initDate() {
    }

    override fun showError(msg: String?) {

    }

    override fun release() {
        if (!subscribe.isDisposed) {
            subscribe.dispose()
        }
    }
}