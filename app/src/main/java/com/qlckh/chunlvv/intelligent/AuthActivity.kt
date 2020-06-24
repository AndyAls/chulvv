package com.qlckh.chunlvv.intelligent

import android.content.Intent
import android.os.Handler
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.activity.LoginActivity
import com.qlckh.chunlvv.api.ApiService
import com.qlckh.chunlvv.api.NetCostant
import com.qlckh.chunlvv.base.BaseActivity
import com.qlckh.chunlvv.http.RxHttpUtils
import com.qlckh.chunlvv.http.interceptor.Transformer
import com.qlckh.chunlvv.http.observer.CommonObserver
import com.qlckh.chunlvv.qidian.StoreDao
import com.qlckh.chunlvv.user.UserConfig
import com.qlckh.chunlvv.utils.PhoneUtil
import kotlinx.android.synthetic.main.activity_auth.*

/**
 * @author Andy
 * @date   2019/12/17 15:14
 * Desc: 授权界面
 *
 */
class AuthActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_auth
    }

    override fun isSetFondSize(): Boolean {
        return false
    }

    override fun initDate() {
        login_button.setOnClickListener {
            if (checkData()) {

                RxHttpUtils.createApi(ApiService::class.java)
                        .auth(tv_http.text.toString().trim(), PhoneUtil.getIMEI(this),
                                et_fullname.text.toString().trim(), tv_username.text.toString().trim(),
                                tv_phone.text.toString().trim(), tv_code.text.toString().trim())
                        .compose(Transformer.switchSchedulers())
                        .subscribe(object : CommonObserver<StoreDao>() {
                            override fun onError(errorMsg: String?) {
                                showShort(errorMsg)
                                UserConfig.savaAuth(false)
                            }

                            override fun onSuccess(t: StoreDao?) {
                                //授权成功
                                if (t != null && t.status == 1) {
                                    UserConfig.savaAuth(true)
                                    UserConfig.savaServiceUrl(tv_http.text.toString().trim())
                                    UserConfig.savaCode(tv_code.text.toString().trim())
                                    UserConfig.savaFullname(et_fullname.text.toString().trim())
                                    UserConfig.savaUserName(tv_username.text.toString().trim())
                                    UserConfig.savaPhone(tv_phone.text.toString().trim())
                                    NetCostant.BASE_URL = UserConfig.getServiceUrl()
                                    initHttp()
                                    if (UserConfig.isLogin()) {
                                        if (UserConfig.getType() == 0) {
                                            toMian()
                                        }
                                    } else {
                                        toLogin()
                                    }

                                } else {
                                    showShort(t?.data ?: "授权失败")
                                    UserConfig.savaAuth(false)
                                }
                            }


                        })
            }
        }
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

    fun initHttp() {
        RxHttpUtils
                .getInstance()
                .config()
                .setBaseUrl(NetCostant.BASE_URL)
                .setCookie(false)
                .setSslSocketFactory()
                .setReadTimeout(ApiService.DEFAULT_TIME) //全局超时配置
                .setWriteTimeout(ApiService.DEFAULT_TIME) //全局超时配置
                .setConnectTimeout(ApiService.DEFAULT_TIME) //全局是否打开请求log日志
                .setLog(true)
    }

    private fun checkData(): Boolean {

        if (isEmpty(tv_http.text.toString().trim())) {
            tv_http.error = "请输入正确的域名"
            showShort("请输入正确的域名")
            return false
        }
        if (isEmpty(et_fullname.text.toString().trim())) {
            et_fullname.error = "请输入正确的使用人姓名"
            showShort("请输入正确的使用人姓名")
            return false
        }
        if (isEmpty(tv_username.text.toString().trim())) {
            tv_username.error = "请输入正确的公司名称"
            showShort("请输入正确的公司名称")
            return false
        }
        if (tv_phone.text.toString().trim().length != 11) {
            tv_phone.error = "请输入正确的手机号"
            showShort("请输入正确的手机号")
            return false
        }
        if (isEmpty(tv_code.text.toString().trim())) {
            tv_code.error = "请输入正确的验证码"
            showShort("请输入正确的验证码")
            return false
        }

        return true
    }

    override fun initView() {
        header.visibility = View.GONE
        ImmersionBar.with(this).transparentBar()
                .keyboardEnable(true)
                .init()

    }

    override fun showError(msg: String?) {
    }

    override fun release() {
    }
}