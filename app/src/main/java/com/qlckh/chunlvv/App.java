package com.qlckh.chunlvv;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.api.NetCostant;
import com.qlckh.chunlvv.common.GlideApp;
import com.qlckh.chunlvv.common.LocationService;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.intelligent.IntelligentLuanchActivity;
import com.qlckh.chunlvv.manager.Constant;
import com.qlckh.chunlvv.manager.SerialPortManager;
import com.qlckh.chunlvv.preview.GlideLoader;
import com.qlckh.chunlvv.preview.ZoomMediaLoader;
import com.qlckh.chunlvv.user.UserConfig;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;
import butterknife.ButterKnife;
import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.activity.DefaultErrorActivity;
import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 * @author Andy
 * @date 2018/5/14 11:28
 * Desc:
 */
public class App extends Application {

    private static final String APPKEY = "55a0674a2a";
    private static App app;
    public LocationService locationService;

    public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    private SerialPort mSerialPort = null;

    private SerialPortManager mWeightManager = null;
    private SerialPortManager mScanManager = null;
    private SerialPortManager mPrintManager = null;
    private SerialPortManager mPanelManager = null;


    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(BuildConfig.DEBUG);
        XLog.e("--", "onCreate");
        Bugly.init(getApplicationContext(), APPKEY, true);
        Stetho.initializeWithDefaults(this);
        QbSdk.initX5Environment(this, null);
        initCrash();
    }

    private void initCrash() {

        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .enabled(true) //default: true
                .showErrorDetails(true) //default: true
                .trackActivities(true)
                .showRestartButton(true) //default: true
                .logErrorOnRestart(true) //default: true
                .trackActivities(true) //default: false
                .minTimeBetweenCrashesMs(2000) //default: 3000
                .errorDrawable(R.drawable.app_icon) //default: bug image
                .restartActivity(IntelligentLuanchActivity.class) //default: null (your app's launch activity)
                .errorActivity(DefaultErrorActivity.class) //default: null (default error activity)
                .apply();
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
// TODO: 2020/6/29 认证先去掉  需要打开注释去掉即可
       /* if (UserConfig.isAuth()) {
            NetCostant.BASE_URL = UserConfig.getServiceUrl();
        }*/
        RxHttpUtils
                .getInstance()
                //开启全局配置
                .config()
                //全局的BaseUrl
                .setBaseUrl(NetCostant.BASE_URL)
                //开启缓存策略
//                .setCache()
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
                .setWriteTimeout(ApiService.DEFAULT_TIME)
                //全局超时配置
                .setConnectTimeout(ApiService.DEFAULT_TIME)
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

    public SerialPortManager getmWeightManager() {
        if (mWeightManager == null) {
            mWeightManager = new SerialPortManager();
            SharedPreferences sp = getSharedPreferences(Constant.SP_NAME, MODE_PRIVATE);
//            String weightNode = sp.getString(Constant.WEGHT_NODE, "");
//            int weightRate = Integer.decode(sp.getString(Constant.WEGHT_RATE, "-1"));

            String weightNode = "/dev/ttyS3";
            int weightRate = 9600;
            mWeightManager.openSerialPort(new File(weightNode), weightRate);

        }
        return mWeightManager;
    }

    public SerialPortManager getmScanManager() {
        if (mScanManager == null) {
            mScanManager = new SerialPortManager();
            SharedPreferences ssp = getSharedPreferences(Constant.SP_NAME, MODE_PRIVATE);
//            String scanNode = ssp.getString(Constant.SCAN_NODE, "");
//            int scanRate = Integer.decode(ssp.getString(Constant.SCAN_RATE, "-1"));
            String scanNode = "/dev/ttyS1";
            //9600
            int scanRate = 9600;
            mScanManager.openSerialPort(new File(scanNode), scanRate);
        }
        return mScanManager;
    }

    public SerialPortManager getmPrintManager() {
        if (mPrintManager == null) {
            mPrintManager = new SerialPortManager();

            SharedPreferences prsp = getSharedPreferences(Constant.SP_NAME, MODE_PRIVATE);
            String printNode = prsp.getString(Constant.PRINT_NODE, "");
            int printRate = Integer.decode(prsp.getString(Constant.PRINT_RATE, "-1"));
            mPrintManager.openSerialPort(new File(printNode), printRate);
        }
        return mPrintManager;
    }

    public SerialPortManager getmPanelManager() {
        if (mPanelManager == null) {
            mPanelManager = new SerialPortManager();
            SharedPreferences psp = getSharedPreferences(Constant.SP_NAME, MODE_PRIVATE);
            String panelNode = psp.getString(Constant.PANEL_NODE, "");
            int panelRate = Integer.decode(psp.getString(Constant.PRINT_RATE, "-1"));
//            String panelNode ="/dev/ttyO3";
//            int panelRate = 38400;
            mPanelManager.openSerialPort(new File(panelNode), panelRate);
        }
        return mPanelManager;
    }

    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
            /* Read serial port parameters */
            SharedPreferences sp = getSharedPreferences(Constant.SP_NAME, MODE_PRIVATE);
            String path = sp.getString(Constant.WEGHT_NODE, "");
            int baudrate = Integer.decode(sp.getString(Constant.WEGHT_RATE, "-1"));

            XLog.e("---", "getSerialPort()", path, baudrate);
            /* Check parameters */
            if ((path.length() == 0) || (baudrate == -1)) {
                throw new InvalidParameterException();
            }

            /* Open the serial port */
            mSerialPort = new SerialPort(new File(path), baudrate, 0);
        }
        return mSerialPort;
    }

    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    public void closeAllSerialPort() {
        if (mPanelManager != null) {
            mPanelManager.closeSerialPort();
            mPanelManager = null;
        }

        if (mPrintManager != null) {
            mPrintManager.closeSerialPort();
            mPrintManager = null;
        }

        if (mScanManager != null) {
            mScanManager.closeSerialPort();
            mScanManager = null;
        }
        if (mWeightManager != null) {
            mWeightManager.closeSerialPort();
            mWeightManager = null;
        }
    }

}
