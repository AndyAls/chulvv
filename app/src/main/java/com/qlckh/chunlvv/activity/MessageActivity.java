package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.MessageAdapter;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.InMsgDao;
import com.qlckh.chunlvv.dao.OutMessageDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.MessagePresenterImpl;
import com.qlckh.chunlvv.presenter.MessagePresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.usercase.MessageEvent;
import com.qlckh.chunlvv.utils.NetworkUtils;
import com.qlckh.chunlvv.utils.SpUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/22 11:43
 * Desc:
 */
public class MessageActivity extends BaseMvpActivity<MessagePresenter> implements MessagePresenter.MessageView {
    public static final String MESSAGE_ACTION = "MESSAGE_ACTION";
    public static final String MESAGE_DAO = "MESAGE_DAO";
    public static final String MESSAGE_SOURCE = "MESSAGE_SOURCE";
    private static final String TAG = "MessageActivity";
    @BindView(R.id.tv_in_notice)
    TextView tvInNotice;
    @BindView(R.id.tv_out_notice)
    TextView tvOutNotice;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private int source = 0;
    private int page = 0;
    private List<InMsgDao.InMsg> mInDatas = new ArrayList<>();
    private List<OutMessageDao.OutMessage> mOutDatas = new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.activity_message;
    }

    @Override
    protected boolean isSetFondSize() {

        return true;
    }

    @Override
    public void initView() {



        setTitle("我的消息");
        goBack();
        tvInNotice.setSelected(true);
        tvOutNotice.setSelected(false);
        initRefresh();
        initRecyclerView();
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

                page++;
                if (source == 0) {
                    mPresenter.getInMessageList(UserConfig.getUserName(), page + "");
                } else {
                    mPresenter.getOutMessageList(UserConfig.getUserName(), page + "");
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                if (source == 0) {
                    mPresenter.getInMessageList(UserConfig.getUserName(), page + "");
                } else {
                    mPresenter.getOutMessageList(UserConfig.getUserName(), page + "");
                }

            }
        });



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

        RxHttpUtils.cancelAllRequest();
    }

    @Override
    protected MessagePresenter initPresenter() {
        return new MessagePresenterImpl();
    }


    @Override
    public void onSuccess(CommonDao dao) {

        refresh.autoRefresh();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dissmissLoading() {

    }


    @OnClick({R.id.tv_in_notice, R.id.tv_out_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_in_notice:
                tvOutNotice.setSelected(false);
                tvInNotice.setSelected(true);
                tvOutNotice.setEnabled(false);
                tvInNotice.setEnabled(false);
                source = 0;
                refresh.autoRefresh();
                break;
            case R.id.tv_out_notice:
                tvOutNotice.setSelected(true);
                tvInNotice.setSelected(false);
                tvOutNotice.setEnabled(false);
                tvInNotice.setEnabled(false);
                source = 1;
                refresh.autoRefresh();
                break;
            default:
        }

    }

    @Override
    public void onInMessageSuccess(InMsgDao dao) {

        refresh.finishRefresh();
        refresh.finishLoadMore();
        List<InMsgDao.InMsg> data = dao.getData();
        if (data != null) {
            if (page == 0) {
                mInDatas.clear();
                mInDatas.addAll(data);

            } else {
                mInDatas.addAll(data);

            }

        }
        setAdapter();

        XLog.e(TAG,"0",System.currentTimeMillis());
        new Handler().postDelayed(() -> {
            XLog.e(TAG,"1",System.currentTimeMillis());
            tvInNotice.setEnabled(true);
            tvOutNotice.setEnabled(true);
        },800);

        postBadge();
    }
    private void postBadge() {
        int caiCount=0;

        if (mInDatas!=null){
            for (int i = 0; i < mInDatas.size(); i++) {

                InMsgDao.InMsg msg = mInDatas.get(i);
                if ("0".equals(msg.getIsread())){
                    caiCount++;
                }
            }
        }
        if (mOutDatas!=null){
            for (int i = 0; i < mOutDatas.size(); i++) {
                OutMessageDao.OutMessage outMessage = mOutDatas.get(i);
                if ("0".equals(outMessage.getIsread())){
                    caiCount++;
                }
            }
        }
        MessageEvent event = new MessageEvent();
        event.setUnRead(caiCount);
        EventBus.getDefault().post(event);
    }
    private void setAdapter() {


        MessageAdapter adapter = new MessageAdapter(this, source, mInDatas, mOutDatas);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setonItemClickListener(this::onItemClick);

    }

    private void toMessageDetail(int position) {
        Intent intent = new Intent(this, MessageDetailActivitiy.class);

        intent.setAction(MESSAGE_ACTION);
        if (source == 0) {
            InMsgDao.InMsg msgBean = mInDatas.get(position);
            intent.putExtra(MESAGE_DAO, msgBean);
        } else {
            OutMessageDao.OutMessage msgDao = mOutDatas.get(position);
            intent.putExtra(MESAGE_DAO, msgDao);

        }
        intent.putExtra(MESSAGE_SOURCE, source);
        startActivity(intent);

    }

    @Override
    public void onOutMessageSuccess(OutMessageDao dao) {

        refresh.finishRefresh();
        refresh.finishLoadMore();
        List<OutMessageDao.OutMessage> data = dao.getData();
        if (data != null) {
            if (page == 0) {
                mOutDatas.clear();
                mOutDatas.addAll(data);

            } else {
                mOutDatas.addAll(data);

            }

        }
        setAdapter();
        XLog.e(TAG,"0",System.currentTimeMillis());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                XLog.e(TAG,"1",System.currentTimeMillis());
                tvInNotice.setEnabled(true);
                tvOutNotice.setEnabled(true);
            }
        },800);
        postBadge();
    }

    private void onItemClick(View view, int position) {
        String id = "";
        if (source == 0) {
            id = mInDatas.get(position).getId();

        } else {
            id = mOutDatas.get(position).getId()+"";
        }
        mPresenter.updateMessageState(id, source);
        toMessageDetail(position);
    }
}
