package com.qlckh.chunlvv.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.FastAdapter;
import com.qlckh.chunlvv.adapter.RecycleAdapter;
import com.qlckh.chunlvv.base.BaseMvpFragment;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.RecycleDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.RecyclePresenterImpl;
import com.qlckh.chunlvv.presenter.RecyclePresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Andy
 * @date 2018/9/4 14:19
 * Desc:
 */
public class RecycleFragment extends BaseMvpFragment<RecyclePresenter> implements RecyclePresenter.RecycleView {
    private static final String CUN_ID = "CUN_ID";
    private static final String START_TIME = "START_TIME";
    private static final String END_TIME = "END_TIME";
    private static final String STATUS = "STATUS";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    Unbinder unbinder;
    private String cunId;
    private int status;
    private String startTime;
    private String endTime;
    private int page = 1;
    private RecycleAdapter adapter;

    public static RecycleFragment getInstance(String cunId, String startTime, String endTime,
                                              int status) {
        RecycleFragment fragment = new RecycleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CUN_ID, cunId);
        bundle.putString(START_TIME, startTime);
        bundle.putString(END_TIME, endTime);
        bundle.putInt(STATUS, status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected RecyclePresenter initPresenter() {
        return new RecyclePresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_recycle;
    }

    @Override
    public void initView() {

        initRecyclerView();
        initRefresh();
    }

    @Override
    public void initDate() {

        Bundle arguments = getArguments();
        if (arguments != null) {
            cunId = arguments.getString(CUN_ID);
            status = arguments.getInt(STATUS);
            startTime = arguments.getString(START_TIME);
            endTime = arguments.getString(END_TIME);
        }
        mPresenter.getRecycle(status, cunId, startTime, endTime, page);
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

                page++;
                mPresenter.getRecycle(status, cunId, startTime, endTime, page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getRecycle(status, cunId, startTime, endTime, page);
            }
        });


    }

    @Override
    public void showError(String msg) {
        showShort(msg);
    }

    @Override
    public void release() {
    }

    private List<RecycleDao.RecycleBean> mDatas = new ArrayList<>();

    @Override
    public void onRecycleViewSuccess(RecycleDao dao) {
        refresh.finishRefresh();
        if (dao == null || dao.getRow() == null) {
            showError("暂无数据");
            return;
        }
        List<RecycleDao.RecycleBean> row = dao.getRow();
        if (page == 1) {
            mDatas.clear();
            mDatas.addAll(dao.getRow());
        } else {
            mDatas.addAll(dao.getRow());
        }

        if (mDatas.size() == 0) {
            refresh.setEnableLoadMore(false);
        } else {
            if (Long.parseLong(dao.getTotal()) > mDatas.size()) {
                refresh.setEnableLoadMore(true);
                refresh.setNoMoreData(false);
                refresh.finishLoadMore();
            } else {
                refresh.finishLoadMoreWithNoMoreData();
            }

        }
        setAdapter();

    }

    private void setAdapter() {

        RecycleDao.RecycleBean bean = new RecycleDao.RecycleBean();
        bean.setUsername("户主");
        bean.setWhattype("垃圾");
        bean.setJi_duinum("重量(kg)");
        bean.setZong("积分");
        bean.setAddtime("日期");
        if ("日期".equals(mDatas.get(0).getAddtime())){
            mDatas.remove(0);
        }
        mDatas.add(0,bean);
        if (adapter==null){
            adapter = new RecycleAdapter(mContext,mDatas);
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
}
