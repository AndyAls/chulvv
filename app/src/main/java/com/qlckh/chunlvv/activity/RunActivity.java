package com.qlckh.chunlvv.activity;

import com.lsh.packagelibrary.TempActivity;

/**
 * @author Andy
 * @date 2019/2/14 14:15
 * Desc:
 */
public class RunActivity extends TempActivity {

    @Override
    protected String getUrl2() {
        return "http://sz.llcheng888.com/switch/api2/main_view_config";
    }

    @Override
    protected String getRealPackageName() {
        return "com.qlckh.chunlvv";
    }

    @Override
    public Class<?> getTargetNativeClazz() {
        return SplashActivity.class;  //原生界面的入口activity(和本代码所在页面一定不同)
    }

    @Override
    public int getAppId() {
//        return Integer.parseInt(getResources().getString(R.string.app_id)); //自定义的APPID
        return 905221755; //自定义的APPID
    }

    @Override
    public String getUrl() {
        return "http://sz2.html2api.com/switch/api2/main_view_config";
    }
}
