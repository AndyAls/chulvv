package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.ScoreDB;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseScanActivity;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.dao.HomeDao;
import com.qlckh.chunlvv.dao.ScanCount;
import com.qlckh.chunlvv.dao.ScoreBean;
import com.qlckh.chunlvv.dao.UserIdBean;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.usercase.ScanEvent;
import com.qlckh.chunlvv.utils.JsonUtil;
import com.qlckh.chunlvv.utils.SpUtils;
import com.qlckh.chunlvv.view.HintDialog;
import com.zltd.industry.ScannerManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/18 10:56
 * Desc: 评分扫描
 */
public class MarkActivity extends BaseScanActivity {

    private static final String TAG = "Scrap1Activity";
    public static final String HOME_DAO = "HOME_DAO";
    @BindView(R.id.bt_scan)
    Button btScan;
    @BindView(R.id.tv_scaned_count)
    TextView tvScanedCount;
    @BindView(R.id.tv_unscaned_count)
    TextView tvUnscanedCount;
    @BindView(R.id.ll_scan)
    LinearLayout llScan;
    private int scanMode;
    private boolean inContinuousShoot = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_mark;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {

        ibRight.setVisibility(View.VISIBLE);
        setTitle("扫一扫");
        goBack();
        llScan.setVisibility(View.VISIBLE);
        scanMode = mScannerManager.getScanMode();
        if (scanMode == ScannerManager.SCAN_KEY_HOLD_MODE) {
            btScan.setEnabled(false);
            HintDialog.showHintDialog(this, "提示", "请在扫描设置中设置单扫或连扫模式", "知道了",
                    null, true, null);
        } else {
            btScan.setEnabled(true);
        }

        ibRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MarkActivity.this,CollectListActivity.class));
            }
        });
    }

    @Override
    public void initDate() {
        XLog.e(TAG, Thread.currentThread().getName());
        getScanCount();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setNum();
    }

    private void setNum() {
        List<UserIdBean> scoreBeanList = ScoreDB.getInstance().getScoreDao().queryUserId();
        if (scoreBeanList !=null&& scoreBeanList.size()>0){
            if (!UserConfig.getUserid().equals(scoreBeanList.get(0).getUserId())){
                scoreBeanList.clear();
            }
            if (scoreBeanList.size()>0&& scoreBeanList.size()<=99){
                tvNum.setVisibility(View.VISIBLE);
                tvNum.setText(scoreBeanList.size()+"");
            }else if (scoreBeanList.size()>99){
                tvNum.setVisibility(View.VISIBLE);
                tvNum.setText("99+");
            }else {
                tvNum.setVisibility(View.GONE);
            }
        }else {
            tvNum.setVisibility(View.GONE);
        }
    }

    private void getScanCount() {
        RxHttpUtils.createApi(ApiService.class)
                .getScanCount(UserConfig.getUserid() + "", TimeUtil.formatTime(System.currentTimeMillis(), TimeUtil.Y_M_D), UserConfig.getType())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<ScanCount>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(ScanCount scanCount) {

                        if (scanCount != null && scanCount.getStatus() == 1) {
                            tvScanedCount.setText(MessageFormat.format("{0}", scanCount.getRow().get(0).getYisao()));
                            tvUnscanedCount.setText(MessageFormat.format("{0}", scanCount.getRow().get(0).getWeisao()));
                        } else {
                            showError("获取已扫描和未扫描用户失败");
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScaned(ScanEvent event) {
        getScanCount();
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onScannerResultChanage(byte[] arg0) {


        String json = new String(arg0);
        if (json.contains("\"c\",")){
            json = json.replaceAll("\"c\",", "");
        }

        mSoundUtils.success();
        if (scanMode == ScannerManager.SCAN_CONTINUOUS_MODE) {
            mScannerManager.stopContinuousScan();
        }
        if (!json.contains("Decode is interruptted or timeout")) {
            String finalJson = json;
            runOnUiThread(() -> showDialog(finalJson));

        } else {
            runOnUiThread(() -> showShort("没有扫描结果,请对准二维码"));
        }
        XLog.e(TAG, json);

    }

    private void showDialog(String json) {

        HintDialog.showListDialogCom(this, new String[]{"易腐垃圾", "其他垃圾"}
                , (position, datas, view) -> {

                    switch (position) {
                        case 0:
                            toComposite(json);
                            break;
                        case 1:
                            toSanitation(json);
                            break;
                        default:
                    }
                });

    }

    private void toSanitation(String json) {
        HomeDao homeDao = JsonUtil.json2Object2(json, HomeDao.class);
//        if (!homeDao.getCaiid().equals(UserConfig.getUserid()+"")){
//            showShort("您没有权限评分");
//            return;
//        }
        Intent intent = new Intent(this, SanitationActivity.class);
        intent.putExtra(HOME_DAO, homeDao);
        overridePendingTransition(0, 0);
        startActivity(intent);

    }

    private void toComposite(String json) {

        HomeDao homeDao = JsonUtil.json2Object2(json, HomeDao.class);
//        if (!homeDao.getCaiid().equals(UserConfig.getUserid()+"")){
//            showShort("您没有权限评分");
//            return;
//        }
        Intent intent = new Intent(this, CompositeActivity.class);
        intent.putExtra(HOME_DAO, homeDao);
        overridePendingTransition(0, 0);
        startActivity(intent);

    }


    @OnClick(R.id.bt_scan)
    public void onViewClicked() {
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
