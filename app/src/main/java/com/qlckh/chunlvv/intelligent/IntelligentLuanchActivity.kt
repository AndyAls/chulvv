package com.qlckh.chunlvv.intelligent

import android.content.Intent
import android.os.Handler
import android.view.View
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.activity.LoginActivity
import com.qlckh.chunlvv.api.ApiService
import com.qlckh.chunlvv.api.NetCostant
import com.qlckh.chunlvv.base.BaseActivity
import com.qlckh.chunlvv.carpaly.ConvertUtils
import com.qlckh.chunlvv.common.XLog
import com.qlckh.chunlvv.http.RxHttpUtils
import com.qlckh.chunlvv.http.interceptor.Transformer
import com.qlckh.chunlvv.http.observer.CommonObserver
import com.qlckh.chunlvv.qidian.StoreDao
import com.qlckh.chunlvv.user.UserConfig
import com.qlckh.chunlvv.utils.PhoneUtil

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

        mWeightManager.sendBytes(ConvertUtils.hexString2Bytes("55000001207403"))
        if (intent.action == "restart" || intent.type == "restart") {
            startActivity(Intent(this, IntenlligentMarkActivity::class.java))
            finish()
            overridePendingTransition(0, 0)
        } else {
            if (UserConfig.isLogin()) {
                if (UserConfig.getType() == 0) {
                    toMian()
                }
            } else {
                toLogin()
            }
            // TODO: 2020/6/29 需要验证把上面去掉  把下面注释放掉即可

            /*  if (UserConfig.isAuth()) {
                  NetCostant.BASE_URL = ApiService.BASE_URL
                  initHttp()
                  auth()
              } else {
                  toAuth()
              }*/

        }

    }

    private fun auth() {

        RxHttpUtils.createApi(ApiService::class.java)
                .auth(UserConfig.getServiceUrl(), PhoneUtil.getIMEI(this),
                        UserConfig.getSavaFullname(), UserConfig.getSavaUsername(),
                        UserConfig.getSavaPhone(), UserConfig.getSavaCode())
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
                            toAuth()
                        }
                    }


                })
    }

    private fun initHttp() {
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

    private fun toAuth() {

        startActivity(Intent(this, AuthActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
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