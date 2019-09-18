package com.qlckh.chunlvv;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.widget.ScrollView;

import com.facebook.stetho.Stetho;
import com.lsh.packagelibrary.CasePackageApp;
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
import uhf.AsyncSocketState;
import uhf.MultiLableCallBack;
import uhf.Reader;

/**
 * @author Andy
 * @date 2018/5/14 11:28
 * Desc:
 */
public class App extends CasePackageApp {

    private static final String APPKEY = "55a0674a2a";
    private static App app;
    public LocationService locationService;
    private Reader reader;
    private AsyncSocketState socketState;

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

    /**
     *
     ReaderController = Reader(this)
     val aBoolean = ReaderController.OpenSerialPort_Android("/dev/ttyS1")
     if (ReaderController.GetClientInfo() == null || ReaderController.GetClientInfo().size < 1) {
     tvSource.text = "扫描开启失败"
     return
     }
     val socketState = ReaderController.GetClientInfo()[0]
     ReaderController.SetPower(socketState, 0x12.toByte(), 0x12.toByte())
     * @return  MultiLableCallBack
     */
    public Reader getReader(MultiLableCallBack callBack){
        if (reader==null){
            reader = new Reader(callBack);
            reader.OpenSerialPort_Android("/dev/ttyS1");
            if (reader.GetClientInfo() == null || reader.GetClientInfo().size() < 1) {
                return null;
            }
            socketState = reader.GetClientInfo().get(0);
//            reader.SetPower(socketState,(byte) 0xc,(byte) 0xc);
        }
        return reader;

    }

    public AsyncSocketState getSocketState(){
        return socketState;
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
