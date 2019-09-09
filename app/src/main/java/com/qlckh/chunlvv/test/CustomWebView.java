package com.qlckh.chunlvv.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.http.SslError;
import android.util.AttributeSet;

import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class CustomWebView extends WebView {
    private OnScrollChangeListener onScrollChangeListener;

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.onScrollChangeListener = onScrollChangeListener;
    }

    public CustomWebView(Context context) {
        super(context);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    @SuppressLint("NewApi")
    private void init(){
        setHorizontalScrollBarEnabled(false);//水平不显示  
        setVerticalScrollBarEnabled(false); //垂直不显示
        getSettings().setBuiltInZoomControls(false);
        getSettings().setSupportZoom(false);
        getSettings().setDisplayZoomControls(false);
        getSettings().setTextZoom(100);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAppCacheEnabled(true);
        getSettings().setDatabaseEnabled(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        getSettings().setAllowFileAccess(true);
        getSettings().setBlockNetworkImage(false);
        getSettings().setMixedContentMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        this.setWebViewClient(new WebViewClient(){

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
            sslErrorHandler.proceed();
            }
        });
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        onScrollChangeListener.onScrollChange(t);
    }

    public interface OnScrollChangeListener{
        void onScrollChange(int y);
    }
}
