package com.qlckh.chunlvv.qidian;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.AttendanceActivity;
import com.qlckh.chunlvv.activity.LoginActivity;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseScanActivity;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.dao.HomeInfo;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.utils.SpUtils;
import com.qlckh.chunlvv.view.HintDialog;
import com.zltd.industry.ScannerManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2019/6/4 16:15
 * Desc: 家园系统评分首页
 */
public class HomeSysActivity extends BaseScanActivity {

    private static final String TAG = "Scrap1Activity";
    @BindView(R.id.bt_scan)
    Button btScan;
    private int scanMode;
    private boolean inContinuousShoot = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_home_sys;
    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }

    @Override
    public void initView() {
        goBack();
        setTitle("巡检");
        ibRight.setVisibility(View.VISIBLE);
        ibRight.setText("退出登录");
        ibRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        scanMode = mScannerManager.getScanMode();
        if (scanMode == ScannerManager.SCAN_KEY_HOLD_MODE) {
            btScan.setEnabled(false);
            HintDialog.showHintDialog(this, "提示", "请在扫描设置中设置单扫或连扫模式", "知道了",
                    null, true, null);
        } else {
            btScan.setEnabled(true);
        }
    }

    @Override
    public void initDate() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
    }

    @Override
    public void onScannerResultChanage(byte[] arg0) {


        String json = new String(arg0);
       /* if (json.contains("\"c\",")) {
            json = json.replaceAll("\"c\",", "");
        }*/
        mSoundUtils.success();
        if (scanMode == ScannerManager.SCAN_CONTINUOUS_MODE) {
            mScannerManager.stopContinuousScan();
        }
        if (!json.contains("Decode is interruptted or timeout")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    queryData(json);
                }
            });

        } else {
            runOnUiThread(() -> showShort("没有扫描结果,请对准二维码"));
        }
        XLog.e(TAG, json);

    }

    private void queryData(String json) {

        loading();
        RxHttpUtils.createApi(ApiService.class)
                .queryInfo(json)
                .compose(Transformer.<HomeInfo>switchSchedulers())
                .subscribe(new CommonObserver<HomeInfo>() {
                    @Override
                    protected void onError(String errorMsg) {
                        cancelLoading();
                        showLong(errorMsg);
                    }

                    @Override
                    protected void onSuccess(HomeInfo homeInfo) {
                        cancelLoading();
                        if (homeInfo.getStatus() == 1) {
                            Intent intent = new Intent(HomeSysActivity.this, HomeMarkActivity.class);
                            intent.putExtra("homeinfo", homeInfo);
                            intent.putExtra("ncode", json);
                            startActivity(intent);
                        } else {
                            showShort(homeInfo.getMsg());
                        }

                    }
                });
    }

    @OnClick({R.id.bt_scan, R.id.bt_logout, R.id.bt_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_scan:
                scanMode();
                break;
            case R.id.bt_logout:
                logout();
                break;

            case R.id.bt_sign:
                toSign();
                break;
            default:
        }
    }

    private void toSign() {
        startActivity(new Intent(this, AttendanceActivity.class));
    }

    private void logout() {
        UserConfig.reset();
        UserConfig.userInfo = null;
        UserConfig.userResp = null;
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void scanMode() {
        switch (scanMode) {
            case ScannerManager.SCAN_CONTINUOUS_MODE:
                if (!HintDialog.isReplayClick()) {
                    if (!inContinuousShoot) {
                        inContinuousShoot = true;
                        mScannerManager.startContinuousScan();
                    } else {
                        inContinuousShoot = false;
                        mScannerManager.stopContinuousScan();
                    }
                }

                break;
            case ScannerManager.SCAN_SINGLE_MODE:
                if (!HintDialog.isReplayClick()) {
                    mScannerManager.singleScan();
                }
                break;
            default:
        }
    }
}
