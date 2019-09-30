package com.qlckh.chunlvv;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.common.GlideApp;
import com.qlckh.chunlvv.common.LocationService;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.preview.GlideLoader;
import com.qlckh.chunlvv.preview.ZoomMediaLoader;
import com.qlckh.chunlvv.user.UserConfig;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.QbSdk;

import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/5/14 11:28
 * Desc:
 */
public class App extends Application {

    private static final String APPKEY = "55a0674a2a";
    private static App app;
    public LocationService locationService;

    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(BuildConfig.DEBUG);
        XLog.e("--", "onCreate");
        Bugly.init(getApplicationContext(), APPKEY, true);
        Stetho.initializeWithDefaults(this);
        QbSdk.initX5Environment(this,null);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        app = this;
        XLog.e("--", "attachBaseContext");
        init(base);
        initHttp();
    }

    private void initHttp() {
        /**
         * 全局请求的统一配置
         */
        RxHttpUtils.init(this);

        RxHttpUtils
                .getInstance()
                //开启全局配置
                .config()
                //全局的BaseUrl
                .setBaseUrl(ApiService.BASE_URL)
                //开启缓存策略
                .setCache()
                //全局的请求头信息
                //.setHeaders(headerMaps)
                //全局持久话cookie,保存本地每次都会携带在header中
                .setCookie(false)
                //全局ssl证书认证
                //信任所有证书,不安全有风险
                .setSslSocketFactory()
                //使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(getAssets().open("your.cer"))
                //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(getAssets().open("your.bks"), "123456", getAssets().open("your.cer"))
                //全局超时配置
                .setReadTimeout(ApiService.DEFAULT_TIME)
                //全局超时配置
                .setWriteTimeout(10)
                //全局超时配置
                .setConnectTimeout(10)
                //全局是否打开请求log日志
                .setLog(true);
    }

    private void init(Context context) {
        MultiDex.install(context);
        UserConfig.init(context);
        locationService = new LocationService(context);
        ZoomMediaLoader.getInstance().init(new GlideLoader());
    }

    public synchronized static Context getAppContext() {
        return app.getApplicationContext();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GlideApp.get(this).onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        GlideApp.get(this).onTrimMemory(level);
    }

}
