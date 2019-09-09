package com.qlckh.chunlvv.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.ScoreDB;
import com.qlckh.chunlvv.adapter.CollectAdapter;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.common.MyTask;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.ScaleBean;
import com.qlckh.chunlvv.dao.ScoreBean;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.utils.GsonUtils;
import com.qlckh.chunlvv.impl.CollectPresenterImpl;
import com.qlckh.chunlvv.presenter.CollectPresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.utils.Base64Util;
import com.qlckh.chunlvv.utils.ImgUtil;
import com.qlckh.chunlvv.utils.JsonUtil;
import com.qlckh.chunlvv.utils.NetworkUtils;
import com.qlckh.chunlvv.utils.SpUtils;
import com.qlckh.chunlvv.view.LoadingView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Andy
 * @date 2018/8/18 23:52
 * Desc:
 */
public class CollectListActivity extends BaseMvpActivity<CollectPresenter> implements CollectPresenter.CollectView {
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Flowable<List<ScoreBean>> listFlowable;
    private List<ScoreBean> scoreBeanList;
    private Disposable disposable;
    private String ip = "";

    private String imgPath = "";
    private String photoPath = "";
    private boolean isDone = true;
    private List<String> imgs;

    @Override
    protected CollectPresenter initPresenter() {
        return new CollectPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_collect;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void onSuccess(CommonDao dao) {
        i=0;
        if (this.scoreBeanList != null && this.scoreBeanList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }

        showShort("提交成功");
    }

    @Override
    public void onAddScanedSuccess() {

    }

    @Override
    public void showLoading() {

        LoadingView.showLoading(this, "请稍等", false);
    }

    @Override
    public void dissmissLoading() {

        LoadingView.cancelLoading();
    }

    @Override
    public void initView() {

        setTitle("待上传采集");
        goBack();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    public void initDate() {
        String param = SpUtils.getStringParam(this, ScaleActivity.SCALE_INFO, "");
        ScaleBean info = JsonUtil.json2Object2(param, ScaleBean.class);
        if (info != null) {
            ip = info.getIp();
        }
        scoreBeanList = ScoreDB.getInstance().getScoreDao().queryList();
//        disposable = listFlowable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(scoreBeans -> scoreBeanList = scoreBeans);
        if (this.scoreBeanList != null && this.scoreBeanList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            if (!UserConfig.getUserid().equals(this.scoreBeanList.get(0).getUserId())) {
                this.scoreBeanList.clear();
            }
        } else {
            recyclerView.setVisibility(View.GONE);
        }

        CollectAdapter adapter = new CollectAdapter(this, this.scoreBeanList);
        recyclerView.setAdapter(adapter);

        adapter.setonItemClickListener((view, position, bean) -> {

            if (NetworkUtils.isNetWorkAvailable()) {
                imgs = bean.getImgs();
                String path= "";
                for (int i = 0; i < imgs.size(); i++) {
                    File compress = ImgUtil.compress(new File(imgs.get(i)), 50, 2100000);
                    String s = Base64Util.ioEncode(compress);
                    String concat = "data:image/png;base64,".concat(s).concat("分");
                    path=concat +  path;

                }
                mPresenter.sumbit(bean,path);
                this.scoreBeanList.remove(bean);
                ScoreDB.getInstance().getScoreDao().delect(bean);
                adapter.notifyDataSetChanged();
            } else {
                showShort("请联网再提交");
            }


        });

    }

    private int i=0;
    @Override
    public void showError(String msg) {
        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();

        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
