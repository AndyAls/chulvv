package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.NewsMoreAdapter;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.NewsMorePresenterImpl;
import com.qlckh.chunlvv.presenter.NewsMorePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;

/**
 * @author Andy
 * @date 2018/8/17 10:29
 * Desc: 新闻列表
 */
public class NewsMoreActivity extends BaseMvpActivity<NewsMorePresenter> implements NewsMorePresenter.NewsMoreView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private NewsMoreAdapter adapter;

    @Override
    protected NewsMorePresenter initPresenter() {
        return new NewsMorePresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_news_more;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void onNewsSuccess(BannerDao dao) {
        refresh.finishRefresh();

        if (!isEmpty(dao)&&!isEmpty(dao.getRow())){

                adapter = new NewsMoreAdapter(this,dao.getRow());
                recyclerView.setAdapter(adapter);
                setAdapterListener();
        }
    }

    @Override
    public void initView() {
        setTitle("新闻列表");
        goBack();
        initRecyclerView();
        initRefresh();
    }

    @Override
    public void initDate() {
        refresh.autoRefresh();
    }

    private void setAdapterListener() {
        adapter.setonItemClickListener(new NewsMoreAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BannerDao.ImgBean imgs) {
                //新闻详情
                Intent intent = new Intent(NewsMoreActivity.this, NewsActivity.class);
                intent.putExtra(NewsActivity.NEWS_CONTENT,imgs.getContent());
                intent.putExtra(NewsActivity.NEWS_TIME,imgs.getAddtime());
                intent.putExtra(NewsActivity.NEWS_TITLE,imgs.getTitle());
                startActivity(intent);
            }
        });
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
                mPresenter.getNewsList();

            }
        });


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

}
