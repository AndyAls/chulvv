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
import com.qlckh.chunlvv.adapter.OrderAdapter;
import com.qlckh.chunlvv.base.BaseMvpFragment;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.OrderDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.ConfirmOrderPresenterImpl;
import com.qlckh.chunlvv.presenter.ConfirmOrderPresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

/**
 * @author Andy
 * @date 2018/9/6 13:40
 * Desc: 回收订单
 */
public class OrderFragment extends BaseMvpFragment<ConfirmOrderPresenter> implements ConfirmOrderPresenter.ConfirmOrderView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    private int flag=0;
    private boolean isRefrsh=false;

    public static OrderFragment getInstance(int flag){
        OrderFragment fragment = new OrderFragment();
        Bundle budle = new Bundle();
        budle.putInt("flag",flag);
        fragment.setArguments(budle);
        return fragment;
    }
    @Override
    protected ConfirmOrderPresenter initPresenter() {
        return new ConfirmOrderPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_order;
    }

    @Override
    public void onOrdersSuccess(OrderDao dao) {
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

    private void setAdapter(List<OrderDao.OrderBean> row) {
        OrderAdapter adapter = new OrderAdapter(mContext,row);
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

    public void onReshow(int pos){
        if (isRefrsh){
            flag=pos;
            refresh();
        }

        XLog.e("-----","onReshow");
    }
    /**
     * 加载数据
     */
    private void refresh() {
        mPresenter.getOrders(flag);
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
}
