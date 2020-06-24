package com.qlckh.chunlvv.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.api.NetCostant;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.utils.GlideUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/9/10 15:13
 * Desc:
 */
public class QrCodeActicvity extends BaseActivity {
    @BindView(R.id.iv_qrCode)
    ImageView ivQrCode;
    @BindView(R.id.bt_scan)
    TextView btScan;

    @Override
    protected int getContentView() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {
        setTitle("我的二维码");
        goBack();
        GlideUtil.displayRoundConnerImg(this, NetCostant.BASE_URL+UserConfig.getUserResp().getErimg(),ivQrCode);
    }

    @Override
    public void initDate() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void release() {

    }

}
