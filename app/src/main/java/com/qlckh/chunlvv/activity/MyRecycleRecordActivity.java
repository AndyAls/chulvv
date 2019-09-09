package com.qlckh.chunlvv.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.RankAdapter;
import com.qlckh.chunlvv.adapter.RecycleAdapter;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.RecordDao;
import com.qlckh.chunlvv.dao.RecycleDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.impl.RecycleRecordPresenterImpl;
import com.qlckh.chunlvv.presenter.RecycleRecordPresenter;
import com.qlckh.chunlvv.utils.DateUtil;
import com.qlckh.chunlvv.view.DatePickerView;
import com.qlckh.chunlvv.view.HintDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/9/10 11:32
 * Desc:
 */
public class MyRecycleRecordActivity extends BaseMvpActivity<RecycleRecordPresenter> implements RecycleRecordPresenter.RecycleRecordView {
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.tv_country)
    TextView tvCountry;
    @BindView(R.id.start_date_tv)
    TextView startDateTv;
    @BindView(R.id.end_date_tv)
    TextView endDateTv;
    @BindView(R.id.bt_query)
    TextView btQuery;
    @BindView(R.id.ll_choose)
    LinearLayout llChoose;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private String cunId = "";
    private List<CunGuanDao.CunGuanBean> cuntryList;
    private DatePickerView datePickerView;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日", Locale.SIMPLIFIED_CHINESE);
    private String startData = "";
    private String endData = "";
    private RecycleAdapter adapter;


    @Override
    protected RecycleRecordPresenter initPresenter() {
        return new RecycleRecordPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_recycle_record;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void onRecordSuccess(RecordDao dao) {
        refresh.finishRefresh();

        if (!isEmpty(dao) && !isEmpty(dao.getRow()) && dao.getRow().size() > 0) {

            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            setAdapter(dao);
        } else {

            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setAdapter(RecordDao dao) {
        List<RecordDao.RecordBean> rows = dao.getRow();
        List<RecycleDao.RecycleBean> datas = new ArrayList<>();

        RecycleDao.RecycleBean bean = new RecycleDao.RecycleBean();
        bean.setUsername("户主");
        bean.setWhattype("垃圾");
        bean.setJi_duinum("重量(kg)");
        bean.setZong("积分");
        bean.setAddtime("日期");
        if (datas.size() > 0) {
            if ("日期".equals(datas.get(0).getAddtime())) {
                datas.remove(0);
            }
        }

        datas.add(0, bean);
        for (int d = 0; d < rows.size(); d++) {
            RecycleDao.RecycleBean recycleBean = new RecycleDao.RecycleBean();
            recycleBean.setUsername(rows.get(d).getUsername());
            recycleBean.setWhattype(rows.get(d).getWhattype());
            recycleBean.setJi_duinum(rows.get(d).getJi_duinum());
            recycleBean.setZong(rows.get(d).getZong());
            recycleBean.setAddtime(rows.get(d).getAddtime());
            datas.add(recycleBean);
        }
        adapter = new RecycleAdapter(this, datas);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCuntrySuccess(CunGuanDao dao) {
        if (dao != null && dao.getRow() != null && dao.getRow().size() > 0) {
            this.cuntryList = dao.getRow();
            tvCountry.setText(cuntryList.get(0).getName());
            cunId = cuntryList.get(0).getId();
        } else {
            showError("没有获取到村信息");
        }
        mPresenter.getRecord(cunId, startData, endData);
    }

    @Override
    public void onSuccess(CommonDao dao) {

    }

    @Override
    public void showLoading() {

        loading();
    }

    @Override
    public void dissmissLoading() {
        cancelLoading();

    }

    @Override
    public void initView() {

        setTitle("我的数据");
        goBack();
        initRecyclerView();
        initRefresh();
    }

    @Override
    public void initDate() {
        startDateTv.setText(DateUtil.sevenTime());
        endDateTv.setText(DateUtil.getYearMonthDay());
        startData = String.valueOf(TimeUtil.parseTime(startDateTv.getText().toString(), TimeUtil.Y_M_D) / 1000);
        endData = String.valueOf(TimeUtil.parseTime(endDateTv.getText().toString(), TimeUtil.Y_M_D) / 1000);
//        mPresenter.getCountryList();
        tvCountry.setEnabled(false);
        datePickerView = new DatePickerView(this);
        mPresenter.getRecord(cunId, startData, endData);
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {
        RxHttpUtils.cancelAllRequest();
    }

    private void initRecyclerView() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    private void initRefresh() {
        refresh.setEnableLoadMore(false);
        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getRecord(cunId, startData, endData);

            }
        });


    }

    @OnClick({R.id.tv_country, R.id.start_date_tv, R.id.end_date_tv, R.id.bt_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_country:
                HintDialog.showListDialog(this, cuntryList, (position, mDatas, view1) -> {
//                    tvCountry.setText(mDatas.get(position).getName());
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
                startData = String.valueOf(TimeUtil.parseTime(startDateTv.getText().toString(), TimeUtil.Y_M_D) / 1000);
                endData = String.valueOf(TimeUtil.parseTime(endDateTv.getText().toString(), TimeUtil.Y_M_D) / 1000);
                mPresenter.getRecord(cunId, startData, endData);
                break;
            default:
        }
    }

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
