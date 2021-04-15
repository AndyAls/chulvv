package com.qlckh.chunlvv.activity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.EvaluationDetailAdapter;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.EvaluationDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.EvaluationDetailPresenterImpl;
import com.qlckh.chunlvv.presenter.EvaluationDetailPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/9/6 17:24
 * Desc: 我的评价
 */
public class EvaluationDetailActivity extends BaseMvpActivity<EvaluationDetailPresenter> implements EvaluationDetailPresenter.EvaluationDetailView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private EvaluationDetailAdapter adapter;

    @Override
    protected EvaluationDetailPresenter initPresenter() {
        return new EvaluationDetailPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_evaluation_detail;
    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }

    @Override
    public void onEvaluationDetailSuccess(EvaluationDao dao) {

        refresh.finishRefresh(true);
        if (dao!=null&&dao.getRow()!=null&&dao.getRow().size()>0){
            recyclerView.setVisibility(View.VISIBLE);

            setAdapter(dao.getRow());
        }else {
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void setAdapter(List<EvaluationDao.EvaBean> row) {

        if (adapter==null){
            adapter = new EvaluationDetailAdapter(this,row);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }

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

        setTitle("我的评价");
        goBack();
        initRecyclerView();
        initRefresh();

    }

    private void initRecyclerView() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

    }

    private void initRefresh() {

        refresh.setEnableLoadMore(false);
        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getEvaluationDetail();

            }
        });
    }

    @Override
    public void initDate() {

        mPresenter.getEvaluationDetail();
    }

    @Override
    public void showError(String msg) {

        refresh.finishRefresh(true);
        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
    }

}
