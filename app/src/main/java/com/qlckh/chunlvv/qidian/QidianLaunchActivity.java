package com.qlckh.chunlvv.qidian;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.LoginActivity;
import com.qlckh.chunlvv.activity.ScanMarkActivity;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.utils.PhoneUtil;

/**
 * @author Andy
 * @date 2019/5/31 17:31
 * Desc:
 */
public class QidianLaunchActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.qidian_launch;
    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }

    @Override
    public void initView() {
        header.setVisibility(View.GONE);

    }

    @Override
    public void initDate() {

        //评分
        if (UserConfig.isLogin()) {
            if (UserConfig.getType() == 0) {
                new Handler().postDelayed(this::toMian,1000);

            }
        }else {
            toLogin();
        }

    }

    private void toMian() {
        //入库
        if (PhoneUtil.isN5s()){
            startActivity(new Intent(this,N5ScanActivity.class));
        }else {
            startActivity(new Intent(this,PhoneScanActivity.class));
        }
        finish();
        overridePendingTransition(0, 0);

    }

    private void toLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void release() {

    }
}
