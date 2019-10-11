package com.qlckh.chunlvv.qidian;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseScanActivity;
import com.qlckh.chunlvv.common.XLog;
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
 * @date 2019/5/31 17:41
 * Desc:
 */
public class N5ScanActivity extends BaseScanActivity {

    private static final String TAG = "Scrap1Activity";
    @BindView(R.id.bt_scan)
    Button btScan;
    @BindView(R.id.tvTong)
    TextView tvTong;
    @BindView(R.id.tvTongHint)
    TextView tvTongHint;
    @BindView(R.id.tvCode)
    TextView tvCode;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.bt_huan)
    Button btHuan;
    private int scanMode;
    private boolean inContinuousShoot = false;
    private final static String ANDY = "andy";

    private String andy;

    @Override
    protected int getContentView() {
        return R.layout.activity_qidiann5;
    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }

    @Override
    public void initView() {
        ibRight.setVisibility(View.VISIBLE);
        setTitle("扫一扫");
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
        setTong();
    }

    private boolean setTong() {
        andy = SpUtils.getStringParam(this, ANDY, "");
        if (isEmpty(andy)) {
            tvTong.setText("");
            tvTongHint.setText("请先扫描捅上的二维码再扫描垃圾袋");
            btHuan.setVisibility(View.GONE);
            return true;
        } else {
            tvTong.setText(andy.split("IMEI:")[1]);
            tvTongHint.setText("请确定桶的编号是否正确");
            btHuan.setVisibility(View.VISIBLE);
            return false;
        }
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

                    //还没扫垃圾桶
                    if (isEmpty(andy)) {
                        SpUtils.putParam(N5ScanActivity.this, ANDY, json);
                        setTong();
                    } else {
                        //扫描的是垃圾袋
                        chooseTong(json);
                    }
                }
            });

        } else {
            runOnUiThread(() -> showShort("没有扫描结果,请对准二维码"));
        }
        XLog.e(TAG, json);

    }

    private void chooseTong(String json) {
        loading();
        RxHttpUtils.createApi(ApiService.class)
                .putStore(andy.split("IMEI:")[1], json, UserConfig.getUserid())
                .compose(Transformer.<StoreDao>switchSchedulers())
                .subscribe(new CommonObserver<StoreDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        cancelLoading();
                        showLong(errorMsg);
                    }

                    @Override
                    protected void onSuccess(StoreDao responeDao) {
                        cancelLoading();
                        tvCode.setText(responeDao.getData());
                    }
                });

    }

    @OnClick({R.id.bt_scan, R.id.bt_huan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_scan:
                scanMode();
                break;
            case R.id.bt_huan:
                SpUtils.putParam(this, ANDY, "");
                setTong();
                break;

            default:
        }
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
