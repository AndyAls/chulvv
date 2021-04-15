package com.qlckh.chunlvv.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.RankAdapter;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.RankDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.impl.RankPresenterImpl;
import com.qlckh.chunlvv.presenter.RankPresenter;
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
 * @date 2018/9/7 17:08
 * Desc:
 */
public class RankActivity extends BaseMvpActivity<RankPresenter> implements RankPresenter.RankView {
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
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
    private RankAdapter adapter;
    private List<CunGuanDao.CunGuanBean> cuntryList;
    private String cunId = "";
    private DatePickerView datePickerView;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日", Locale.SIMPLIFIED_CHINESE);
    private String data = "";

    @Override
    protected RankPresenter initPresenter() {
        return new RankPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_rank;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void onRankSuccess(RankDao dao) {
        refresh.finishRefresh();

        if (!isEmpty(dao) && !isEmpty(dao.getRow()) && dao.getRow().size() > 0) {

            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            adapter = new RankAdapter(this, dao.getRow());
            recyclerView.setAdapter(adapter);

        } else {

            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
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
        mPresenter.getRank(cunId, data);
    }

    @Override
    public void onSuccess(CommonDao dao) {

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
                mPresenter.getRank(cunId, data);

            }
        });


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

        setTitle("历史排行");
        goBack();
        initRecyclerView();
        initRefresh();
    }

    @Override
    public void initDate() {
        startDateTv.setText(DateUtil.getYearMonthDay());
        data = String.valueOf(TimeUtil.parseTime(startDateTv.getText().toString(), TimeUtil.Y_M_D) / 1000);
        mPresenter.getCountryList();
        datePickerView = new DatePickerView(this);
    }

    @Override
    public void showError(String msg) {
        refresh.finishRefresh();
        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
    }

    @OnClick({R.id.tv_country, R.id.start_date_tv, R.id.end_date_tv, R.id.bt_query, R.id.ll_choose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                data = String.valueOf(TimeUtil.parseTime(startDateTv.getText().toString(), TimeUtil.Y_M_D) / 1000);
                mPresenter.getRank(cunId, data);
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
