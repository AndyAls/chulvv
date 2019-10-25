package com.qlckh.chunlvv.intelligent

import android.content.Intent
import android.os.Handler
import android.view.View
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.activity.LoginActivity
import com.qlckh.chunlvv.base.BaseActivity
import com.qlckh.chunlvv.common.XLog
import com.qlckh.chunlvv.user.UserConfig

/**
 * @author Andy
 * @date   2019/9/9 16:24
 * Desc:
 */
class IntelligentLuanchActivity : BaseActivity() {
    override fun initView() {
        header.visibility = View.GONE

        val action = intent.action
        val dataString = intent.dataString
        val flags = intent.flags
        val scheme = intent.scheme
        val type = intent.type
        XLog.e("IntelligentLuanchActivity", "action--" + action
                , "dataString--" + dataString,
                "flags--" + flags,
                "scheme--" + scheme,
                "type--" + type)

    }

    override fun showError(msg: String?) {
    }

    override fun getContentView(): Int {

        return R.layout.qidian_launch;
    }

    override fun isSetFondSize(): Boolean {
        return false
    }

    override fun initDate() {
        if (intent.action=="restart"||intent.type=="restart"){
            startActivity(Intent(this, IntelligentDelayActivity::class.java))
            finish()
            overridePendingTransition(0, 0)
        }else{

            Handler().postDelayed({
                if (UserConfig.isLogin()) {
                    if (UserConfig.getType() == 0) {
                        Handler().postDelayed({ this.toMian() }, 1000)
                    }
                } else {
                    toLogin()
                }
            },1000)
            //评分

        }

    }

    override fun release() {
    }

    private fun toMian() {
        startActivity(Intent(this, IntenlligentMarkActivity::class.java))
        finish()
        overridePendingTransition(0, 0)

    }

    private fun toLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }
}