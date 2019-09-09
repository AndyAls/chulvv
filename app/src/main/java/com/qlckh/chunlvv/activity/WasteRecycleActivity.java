package com.qlckh.chunlvv.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.WasteDao;
import com.qlckh.chunlvv.fragment.RecycleFragment;
import com.qlckh.chunlvv.fragment.RecycleProcuteFragment;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.impl.WastePreseneterImpl;
import com.qlckh.chunlvv.presenter.WastePreseneter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.utils.DateUtil;
import com.qlckh.chunlvv.view.DatePickerView;
import com.qlckh.chunlvv.view.HintDialog;
import com.youth.banner.Banner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/8/21 11:34
 * Desc:
 */
public class WasteRecycleActivity extends BaseMvpActivity<WastePreseneter> implements WastePreseneter.WasteView {
    @BindView(R.id.braner)
    Banner braner;
    @BindView(R.id.tv_recycle)
    TextView tvRecycle;
    @BindView(R.id.tv_un_recycle)
    TextView tvUnRecycle;
    @BindView(R.id.call_recycle)
    TextView callRecycle;
    @BindView(R.id.tv_country)
    TextView tvCountry;
    @BindView(R.id.start_date_tv)
    TextView startDateTv;
    @BindView(R.id.end_date_tv)
    TextView endDateTv;
    @BindView(R.id.bt_query)
    TextView btQuery;
    @BindView(R.id.fl_frag)
    FrameLayout flContainer;
    @BindView(R.id.ll_choose)
    LinearLayout llChoose;
    private List<BannerDao.ImgBean> imgs;
    private List<CunGuanDao.CunGuanBean> cuntryList;
    private String cunId = "";
    private List<String> cunName;
    private int status = 0;
    private final long ONE_DAY_MILLS = 24 * 60 * 60 * 1000L;
    private DatePickerView datePickerView;

    @Override
    protected WastePreseneter initPresenter() {
        return new WastePreseneterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_waste_recycle;
    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }

    @Override
    public void onRecyWasteSuccess(WasteDao wasteDao) {

    }

    @Override
    public void onUnRecyWasteSuccess(WasteDao dao) {

    }


    @Override
    public void onCuntrySuccess(CunGuanDao dao) {
        if (dao != null && dao.getRow() != null && dao.getRow().size() > 0) {
            this.cuntryList = dao.getRow();
            tvCountry.setText(cuntryList.get(0).getName());
            cunId = cuntryList.get(0).getId();
        } else {
            showError("没有获取到村信息");
            cuntryList=new ArrayList<>();
        }
        cunName = new ArrayList<>();
        for (int i = 0; i < cuntryList.size(); i++) {
            cunName.add(cuntryList.get(i).getName());
        }

        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_frag, RecycleFragment.getInstance(cunId, String.valueOf(TimeUtil.parseTime(startDateTv.getText().toString(), TimeUtil.Y_M_D) / 1000),
                String.valueOf(System.currentTimeMillis() / 1000), status));
        fragmentTransaction.commit();
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
    public void initView() {

        setTitle("数据回收");
        goBack();
        tvRecycle.setTextColor(getResources().getColor(R.color.comm_header_color));
        tvUnRecycle.setTextColor(getResources().getColor(R.color.text_color_normal));
        callRecycle.setTextColor(getResources().getColor(R.color.text_color_normal));
        startDateTv.setText(DateUtil.sevenTime());
        endDateTv.setText(DateUtil.getYearMonthDay());
    }

    @Override
    public void initDate() {
        datePickerView = new DatePickerView(this);
        mPresenter.getBanner();
        mPresenter.getCuntryList(UserConfig.getUserid());
    }

    @Override
    public void showError(String msg) {
        showShort(msg);
    }

    @Override
    public void release() {
        RxHttpUtils.cancelAllRequest();
    }

    @OnClick({R.id.tv_recycle, R.id.tv_un_recycle, R.id.call_recycle, R.id.tv_country, R.id.start_date_tv, R.id.end_date_tv, R.id.bt_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_recycle:
                status = 0;
                llChoose.setVisibility(View.VISIBLE);
                tvRecycle.setTextColor(getResources().getColor(R.color.comm_header_color));
                tvUnRecycle.setTextColor(getResources().getColor(R.color.text_color_normal));
                callRecycle.setTextColor(getResources().getColor(R.color.text_color_normal));
                changFragment();
                break;
            case R.id.tv_un_recycle:
                llChoose.setVisibility(View.VISIBLE);
                status = 1;
                tvRecycle.setTextColor(getResources().getColor(R.color.text_color_normal));
                tvUnRecycle.setTextColor(getResources().getColor(R.color.comm_header_color));
                callRecycle.setTextColor(getResources().getColor(R.color.text_color_normal));
                changFragment();
                break;
            case R.id.call_recycle:
                llChoose.setVisibility(View.GONE);
                tvRecycle.setTextColor(getResources().getColor(R.color.text_color_normal));
                tvUnRecycle.setTextColor(getResources().getColor(R.color.text_color_normal));
                callRecycle.setTextColor(getResources().getColor(R.color.comm_header_color));
                changRecycleProcute();
                break;
            case R.id.tv_country:
                HintDialog.showListDialog(this, cuntryList, (position, mDatas, view1) -> {
                    tvCountry.setText(mDatas.get(position).getName());
                    cunId = mDatas.get(position).getId();
                });
                break;
            case R.id.start_date_tv:
                showDataDialog(startDateTv);
                break;
            case R.id.end_date_tv:
                showDataDialog(endDateTv);
                break;
            case R.id.bt_query:
                changFragment();
                break;
            default:
        }
    }

    private void changRecycleProcute() {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_frag, RecycleProcuteFragment.getInstance());
        fragmentTransaction.commit();
    }

    private void changFragment() {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_frag, RecycleFragment.getInstance(cunId,
                String.valueOf(TimeUtil.parseTime(startDateTv.getText().toString(), TimeUtil.Y_M_D) / 1000),
                String.valueOf(TimeUtil.parseTime(endDateTv.getText().toString(), TimeUtil.Y_M_D) / 1000), status));
        fragmentTransaction.commit();
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日", Locale.SIMPLIFIED_CHINESE);

    private void showDataDialog(TextView tv) {
        String s = tv.getText().toString();
        String format = "";
        try {
            Date parse = sdf.parse(s);
            format = sdf2.format(parse);
        } catch (ParseException e) {
            format = sdf2.format(new Date());
            e.printStackTrace();
        }
        datePickerView.dateTimePicKDialog(tv, format);
    }
}
