package com.qlckh.chunlvv.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.EntrantAdapter;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.RankDao;
import com.qlckh.chunlvv.impl.RankPresenterImpl;
import com.qlckh.chunlvv.presenter.RankPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/9/10 13:03
 * Desc:
 */
public class EntrantActivity extends BaseMvpActivity<RankPresenter>implements RankPresenter.RankView {
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @Override
    protected int getContentView() {
        return R.layout.activity_evaluation_detail;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {
        setTitle("成功入驻");
        goBack();
        initRecyclerView();
        initRefresh();
    }

    @Override
    public void initDate() {

       refresh.autoRefresh();
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
        refresh.finishRefresh();
    }

    @Override
    public void release() {

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

                mPresenter.getCountryList();
            }
        });
    }

    @Override
    protected RankPresenter initPresenter() {
        return new RankPresenterImpl();
    }

    @Override
    public void onRankSuccess(RankDao dao) {

    }

    @Override
    public void onCuntrySuccess(CunGuanDao dao) {
        refresh.finishRefresh();
        if (dao != null && dao.getRow() != null && dao.getRow().size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            setAdapter(dao.getRow());
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            showError("没有获取到村信息");
        }
    }

    private void setAdapter(List<CunGuanDao.CunGuanBean> row) {

        EntrantAdapter adapter = new EntrantAdapter(this,row);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSuccess(CommonDao dao) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dissmissLoading() {

    }
}
