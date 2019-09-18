package com.qlckh.chunlvv.intelligent

import android.content.Intent
import android.os.Handler
import android.view.View
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.activity.LoginActivity
import com.qlckh.chunlvv.base.BaseActivity
import com.qlckh.chunlvv.user.UserConfig

/**
 * @author Andy
 * @date   2019/9/9 16:24
 * Desc:
 */
class IntelligentLuanchActivity : BaseActivity() {
    override fun initView() {
        header.visibility = View.GONE
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

        //评分
        if (UserConfig.isLogin()) {
            if (UserConfig.getType() == 0) {
                Handler().postDelayed({ this.toMian() }, 1000)

            }
        } else {
            toLogin()
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