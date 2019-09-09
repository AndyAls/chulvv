package com.qlckh.chunlvv.activity;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.qlckh.chunlvv.App;
import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.common.GlideImageLoader;
import com.qlckh.chunlvv.common.LocationService;
import com.qlckh.chunlvv.common.ThreadPool;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.SignDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.impl.AttendancePresenterImpl;
import com.qlckh.chunlvv.presenter.AttendancePresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.utils.SpUtils;
import com.qlckh.chunlvv.utils.TimeUtils;
import com.qlckh.chunlvv.view.LoadingView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/18 10:57
 * Desc: 签到界面
 */
public class AttendanceActivity extends BaseMvpActivity<AttendancePresenter> implements AttendancePresenter.AttendanceView {
    private static final String TAG = "AttendanceActivity";
    private static final String WORK_TIME = "WORK_TIME";
    private static final String OFFLINE_TIME = "OFFLINE_TIME";
    private static final String WORK_LOCATION = "WORK_LOCATION";
    private static final String OFFLINE_LOCATION = "OFFLINE_LOCATION";
    private static final String WUSERID = "WUSERID";
    private static final String OUSERID = "OUSERID";
    @BindView(R.id.braner)
    Banner braner;
    @BindView(R.id.tv_working_location)
    TextView tvWorkingLocation;
    @BindView(R.id.tv_working_time)
    TextView tvWorkingTime;
    @BindView(R.id.tv_working_sign)
    TextView tvWorkingSign;
    @BindView(R.id.tv_offline_location)
    TextView tvOfflineLocation;
    @BindView(R.id.tv_offline_time)
    TextView tvOfflineTime;
    @BindView(R.id.tv_offline_sign)
    TextView tvOfflineSign;
    private List<BannerDao.ImgBean> imgs;
    private static int type = -1;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
    private LocationService locationService;
    private boolean worked = false;
    private SignDao.SignBean signBean;
    private List<SignDao.SignBean> row;
    private SignDao.SignBean worksignBean;

    @Override
    protected AttendancePresenter initPresenter() {
        return new AttendancePresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_attendance;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {
        setTitle("签到");
        goBack();
        tvWorkingSign.setClickable(true);
        tvOfflineSign.setClickable(true);

    }

    @Override
    public void initDate() {
        mPresenter.getSign(UserConfig.getUserid(), System.currentTimeMillis() / 1000 + "");
        mPresenter.getBanner();
    }

    private Handler mHandler = new Handler(msg -> {
        if (msg.what == 0) {
            if (tvWorkingTime != null) {
                tvWorkingTime.setText(format.format(System.currentTimeMillis()));
            }
        } else if (msg.what == 1) {
            if (tvOfflineTime != null) {
                tvOfflineTime.setText(format.format(System.currentTimeMillis()));
            }
        }
        return false;
    });

    @Override
    public void showError(String msg) {
        showShort(msg);
        showErrorBranner();
    }

    @Override
    public void release() {

        ThreadPool.getInstance().release();
        if (mHandler.hasMessages(0)) {
            mHandler.removeMessages(0);
        }
        if (mHandler.hasMessages(1)) {
            mHandler.removeMessages(1);
        }
        RxHttpUtils.cancelAllRequest();

    }

    private void showErrorBranner() {
        braner.setImageLoader(new GlideImageLoader());
        List<Integer> mList = new ArrayList<>();
        mList.add(R.drawable.error);
        mList.add(R.drawable.error);
        braner.setImages(mList);
        braner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        braner.isAutoPlay(true);
        braner.setIndicatorGravity(BannerConfig.RIGHT);
        braner.start();
    }


    @OnClick({R.id.tv_working_sign, R.id.tv_offline_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_working_sign:
                locationService.start();
                type = 0;
                String workTime = this.format.format(System.currentTimeMillis());
                tvWorkingTime.setText(workTime);
                ThreadPool.getInstance().release();
                mHandler.removeMessages(0);

                break;
            case R.id.tv_offline_sign:
               if (row!=null&&"0".equals(row.get(0).getStatus())){
                   locationService.start();
                   type = 1;
                   String offLineTime = this.format.format(System.currentTimeMillis());
                   tvOfflineTime.setText(offLineTime);
                   ThreadPool.getInstance().release();
                   mHandler.removeMessages(1);
               }else {
                   showShort("必须上班签到之后才能下班签到");
               }

                break;
            default:
        }
    }

    @Override
    public void onSuccess(BannerDao dao) {
        if (dao == null) {
            showError("轮播图获取失败");
            return;
        }
        if (dao.getStatus() == 1) {
            imgs = dao.getRow();
            Collections.sort(imgs, (o1, o2) -> Integer.compare(Integer.parseInt(o2.getFlag()), Integer.parseInt(o1.getFlag())));
            mPresenter.showBanner(this, imgs, braner);

        } else {
            showError(dao.getMsg());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        braner.startAutoPlay();
        locationService = ((App) getApplication()).locationService;
        locationService.registerListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        braner.stopAutoPlay();
        locationService.unregisterListener(listener);
    }

    private BDAbstractLocationListener listener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            String address = "无法定位到位置";
            if (bdLocation != null) {
                String addrStr = bdLocation.getAddrStr();
                String locationDescribe = bdLocation.getLocationDescribe();
                if (locationDescribe != null) {
                    address = addrStr.concat(locationDescribe.substring(1, locationDescribe.length() - 2));
                } else {
                    address = addrStr;
                }
            }

            if (isEmpty(address)) {
                showShort("无法获取到位置,请确定是否开启定位");
                locationService.stop();
                return;
            }
            if (type == 0) {
                tvWorkingLocation.setText(address);
            }
            if (type == 1) {
                tvOfflineLocation.setText(address);
            }
            mPresenter.sign(Integer.parseInt(UserConfig.getUserid()), type, address);
            locationService.stop();
        }
    };

    @Override
    public void onSiginSuccess(CommonDao dao, String msg) {
        mPresenter.getSign(UserConfig.getUserid(), System.currentTimeMillis() / 1000 + "");
        showShort(msg);
    }

    @Override
    public void onGetSignSuccess(SignDao dao) {

        row = dao.getRow();

        if (row != null && row.size() == 1 && "0".equals(row.get(0).getStatus())) {
            tvWorkingSign.setEnabled(false);
            tvWorkingSign.setText("已签到");
            tvWorkingTime.setText(TimeUtil.formatTime(Long.parseLong(row.get(0).getCreatetime()) * 1000, TimeUtil.Y_M_D_H_M_S_24));
            tvWorkingLocation.setText(row.get(0).getAddress());

            tvOfflineSign.setEnabled(true);
            ThreadPool.getInstance().executeTimer(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(1);
                    XLog.e(TAG, "executeTimer");
                }
            }, 0, 1000);

        }
        if (row == null || row.size() < 1) {
            tvWorkingSign.setEnabled(true);
            ThreadPool.getInstance().executeTimer(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(0);
                    XLog.e(TAG, "executeTimer");
                }
            }, 0, 1000);

        }
        if (row != null && row.size() == 1 && "1".equals(row.get(0).getStatus())) {
            tvOfflineSign.setEnabled(false);
            tvWorkingSign.setEnabled(false);
            tvOfflineSign.setText("已签到");
            tvWorkingSign.setText("已签到");
            SignDao.SignBean signBean = row.get(0);
            tvOfflineTime.setText(TimeUtil.formatTime(Long.parseLong(signBean.getAddtime()) * 1000, TimeUtil.Y_M_D_H_M_S_24));
            tvOfflineLocation.setText(signBean.getXaddress());
            tvWorkingTime.setText(TimeUtil.formatTime(Long.parseLong(signBean.getCreatetime()) * 1000, TimeUtil.Y_M_D_H_M_S_24));
            tvWorkingLocation.setText(signBean.getAddress());
        }
    }

    @Override
    public void showloading() {
        LoadingView.showLoading(this, "", false);
    }
}

