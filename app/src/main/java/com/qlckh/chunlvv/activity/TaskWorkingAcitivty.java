package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.EventAdapter;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.EventListDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.TaskWorkingPresenterImpl;
import com.qlckh.chunlvv.presenter.TaskWorkingPresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.usercase.BadgeEvent;
import com.qlckh.chunlvv.usercase.HandEvent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/26 14:44
 * Desc: 事件处理
 */
public class TaskWorkingAcitivty extends BaseMvpActivity<TaskWorkingPresenter> implements TaskWorkingPresenter.TaskWorkingView {
    public static final String EVENT_DAO = "EVENT_DAO";
    private static final int MESSAGE_DETAIL_REQUEST_CODE = 2002;
    public static final String TASK_WORKING_ACTION = "TASK_WORKING_ACTION";
    @BindView(R.id.tv_todo)
    TextView tvTodo;
    @BindView(R.id.tv_doing)
    TextView tvDoing;
    @BindView(R.id.tv_done)
    TextView tvDone;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private int page = 0;
    private int source = 0;
    private ArrayList<EventListDao.EventDao> mDatas = new ArrayList<>();
    private EventAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_task_working;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {
        if (UserConfig.getType() == 1) {
            setTitle("任务反馈");

        } else {
            setTitle("事件处理");
        }
        goBack();
        initRefresh();
        initRecyclerView();
        tvTodo.setSelected(true);
        tvDoing.setSelected(false);
        tvDone.setSelected(false);

    }

    @Override
    public void initDate() {
        refresh.autoRefresh();
        EventBus.getDefault().register(this);

    }

    @Override
    public void showError(String msg) {

        refresh.finishRefresh();
        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
        EventBus.getDefault().unregister(this);
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

                page++;
                mPresenter.getEventList(page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                mPresenter.getEventList(page);

            }
        });
    }


    @OnClick({R.id.tv_todo, R.id.tv_doing, R.id.tv_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_todo:
                tvTodo.setSelected(true);
                tvDoing.setSelected(false);
                tvDone.setSelected(false);
                break;
            case R.id.tv_doing:
                tvTodo.setSelected(false);
                tvDoing.setSelected(true);
                tvDone.setSelected(false);
                break;
            case R.id.tv_done:
                tvTodo.setSelected(false);
                tvDoing.setSelected(false);
                tvDone.setSelected(true);
                break;
            default:
        }
    }

    @Override
    protected TaskWorkingPresenter initPresenter() {
        return new TaskWorkingPresenterImpl();
    }

    @Override
    public void onSuccess(CommonDao dao) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventHandSuccess(HandEvent event) {
        refresh.autoRefresh();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dissmissLoading() {

    }

    @Override
    public void onEventSuccess(EventListDao dao) {

        List<EventListDao.EventDao> data = dao.getData();
        refresh.finishRefresh(500);
        refresh.finishLoadMore(500);
        if (data != null) {
            if (page == 0) {
                mDatas.clear();
                mDatas.addAll(data);
            } else {
                mDatas.addAll(data);
            }
        }
        setAdapter();
        postBadge();
    }

    private void postBadge() {
        int caiCount = 0;
        int cunCount = 0;

        if (mDatas != null) {
            for (int i = 0; i < mDatas.size(); i++) {

                EventListDao.EventDao eventDao = mDatas.get(i);
                if ("0".equals(eventDao.getType())) {
                    caiCount++;
                } else if ("1".equals(eventDao.getType())) {
                    cunCount++;
                }
            }
        }
        BadgeEvent event = new BadgeEvent();
        event.setCunBadge(cunCount);
        event.setCaiBadge(caiCount);
        EventBus.getDefault().post(event);
    }


    private void setAdapter() {


        if (adapter == null) {
            adapter = new EventAdapter(this, mDatas);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        adapter.setonItemClickListener((view, position) -> toDetail(position));
    }

    private void toDetail(int position) {

        EventListDao.EventDao eventDao = mDatas.get(position);
        Intent intent = new Intent(this, MessageDetailActivitiy.class);
        intent.putExtra(EVENT_DAO, eventDao);
        intent.setAction(TASK_WORKING_ACTION);
        startActivityForResult(intent, MESSAGE_DETAIL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            refresh.autoRefresh();
        }
    }
}
