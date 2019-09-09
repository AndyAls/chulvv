package com.qlckh.chunlvv.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.PointAdapter;
import com.qlckh.chunlvv.base.BaseMvpFragment;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.PointDao;
import com.qlckh.chunlvv.impl.PointExchangePresenterImpl;
import com.qlckh.chunlvv.presenter.PointExchangePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Andy
 * @date 2018/9/10 16:51
 * Desc:
 */
public class PointExchangeFragment extends BaseMvpFragment<PointExchangePresenter> implements PointExchangePresenter.PointExchangeView {
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private int flag=0;
    private boolean isRefrsh=false;

    @Override
    protected PointExchangePresenter initPresenter() {
        return new PointExchangePresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_order;
    }
    /**
     * 加载数据
     */
    private void refresh() {
        mPresenter.getPoints(flag+"");
    }
    @Override
    public void onPointSuccess(PointDao dao) {
        refresh.setNoMoreData(true);
        refresh.finishRefresh();
        refresh.finishLoadMore();
        if (dao!=null&&dao.getRow()!=null&&dao.getRow().size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            setAdapter(dao.getRow());
        }else {
            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setAdapter(List<PointDao.PointBean> row) {
        PointAdapter adapter = new PointAdapter(mContext,row);
        recyclerView.setAdapter(adapter);
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
        initRecyclerView();
        initRefresh();
    }
    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    private void initRefresh() {

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                refresh();
            }
        });



    }
    @Override
    public void initDate() {

        Bundle arguments = getArguments();
        assert arguments != null;
        flag= arguments.getInt("flag");
        refresh();
        isRefrsh=true;
        XLog.e("-----","initDate");
    }

    @Override
    public void showError(String msg) {
        refresh.finishRefresh();
        refresh.finishLoadMore();
        showShort(msg);
    }

    @Override
    public void release() {

    }

    public static Fragment getInstance(int i) {
        PointExchangeFragment fragment = new PointExchangeFragment();
        Bundle budle = new Bundle();
        budle.putInt("flag", i);
        fragment.setArguments(budle);
        return fragment;
    }

    public void onReshow(int position) {
        if (isRefrsh){
            flag=position;
            refresh();
        }

    }
}
