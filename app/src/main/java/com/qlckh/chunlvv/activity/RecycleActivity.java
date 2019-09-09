package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.HandAdapter;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.HandDao;
import com.qlckh.chunlvv.dao.HandRecyEvent;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.RecycleHandPresenterImpl;
import com.qlckh.chunlvv.presenter.RecycleHandPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/8/9 12:58
 * Desc:回收订单处理
 */
public class RecycleActivity extends BaseMvpActivity<RecycleHandPresenter>implements RecycleHandPresenter.RecycleHandView {
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @Override
    protected int getContentView() {
        return R.layout.activity_recycle;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {

        setTitle("回收订单");
        goBack();
        initRecyclerView();
        initRefresh();
    }

    @Override
    public void initDate() {
        refresh.autoRefresh();

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

                refresh();
            }
        });



    }

    private void refresh() {
        mPresenter.getHand();
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

    @Override
    protected RecycleHandPresenter initPresenter() {
        return new RecycleHandPresenterImpl();
    }

    @Override
    public void onHandSuccess(HandDao dao) {

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

    @Override
    public void toHandSuccess() {
        refresh();
        showShort("确认成功");
    }

    private void setAdapter(List<HandDao.HandBean> row) {
        HandAdapter adapter = new HandAdapter(this,row);
        recyclerView.setAdapter(adapter);

        adapter.setonItemClickListener(new HandAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position, HandDao.HandBean bean) {
                String fstatus = bean.getFstatus();
                switch (fstatus){
                    //确定
                    case "0":
                        mPresenter.toHand(bean.getId());
                        break;
                        //查看
                    case "1":
                        startActivity(new Intent(RecycleActivity.this,RecyclerHandActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHand(HandRecyEvent event){
        refresh();
    }
}
