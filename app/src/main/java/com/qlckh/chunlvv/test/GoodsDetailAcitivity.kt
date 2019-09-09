package com.qlckh.chunlvv.test

import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.base.BaseActivity
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import org.greenrobot.eventbus.EventBus
import kotlinx.android.synthetic.main.activity_goods_detail.*

/**
 * @author Andy
 * @date 2018/10/18 13:57
 * Desc:
 */
class GoodsDetailAcitivity : BaseActivity(),CustomWebView.OnScrollChangeListener {
    var bridge = AndroidBridge()
    override fun onScrollChange(y: Int) {
    }

    override fun getContentView(): Int {
        return R.layout.activity_goods_detail
    }

    override fun isSetFondSize(): Boolean {
        return false
    }

    override fun initView() {

        initWebView()
    }

    private fun initWebView() {
        webView.setOnScrollChangeListener(this)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(bridge, "android")
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                Log.e("webview", url)
                if (url!!.contains("html/views/about.html?Id")) {
                    return true
                } else
                    return super.shouldOverrideUrlLoading(view, url)
            }
        })
        webView.loadUrl("http://t_old.yunfanfamily.com/html/views/product_info.html?Id=152")
    }

    inner class AndroidBridge //这个类中提供各种js可调用的方法。
    {
        @JavascriptInterface
        fun callOpenOrClose(arg: String) {

        }
    }

    override fun initDate() {

    }

    override fun showError(msg: String) {

    }

    override fun release() {

        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);//加载空地址
            webView.clearCache(true);
            webView.clearHistory();
            val viewGroup: ViewGroup = webView.parent as ViewGroup
            viewGroup.removeView(webView)
            webView.destroy();
        }
        super.onDestroy()
        EventBus.getDefault().unregister(this)

    }
}
