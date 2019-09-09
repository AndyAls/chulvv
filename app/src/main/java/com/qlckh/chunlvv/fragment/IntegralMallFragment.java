package com.qlckh.chunlvv.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.ExchangeActivity;
import com.qlckh.chunlvv.adapter.FastAdapter;
import com.qlckh.chunlvv.adapter.MallListAdapter;
import com.qlckh.chunlvv.base.BaseMvpFragment;
import com.qlckh.chunlvv.dao.MallCatgrayDao;
import com.qlckh.chunlvv.dao.MallDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.MallPresenterImpl;
import com.qlckh.chunlvv.presenter.MallPresenter;
import com.qlckh.chunlvv.view.LoadingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Andy
 * @date 2018/8/17 17:08
 * Desc: 积分商城
 */
public class IntegralMallFragment extends BaseMvpFragment<MallPresenter> implements MallPresenter.MallView {
    @BindView(R.id.lv_menu)
    ListView lvMenu;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    private FastAdapter adapter;
    private List<MallCatgrayDao.MallCatgrayBean> catgrayDaoRow;

    @Override
    protected MallPresenter initPresenter() {
        return new MallPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_integral_mall;
    }

    @Override
    public void initView() {

        initRecyclerView();
        initRefresh();
    }

    @Override
    public void initDate() {

        mPresenter.getMallCatgray();
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {

    }

    @Override
    public void onMallSuccess(MallDao dao) {

        refresh.finishRefresh();
        List<MallDao.MallBean> row = dao.getRow();
        MallListAdapter adapter = new MallListAdapter(mContext, row);
        recyclerView.setAdapter(adapter);
        adapter.setonItemClickListener((view, position, bean) -> {
            Intent intent = new Intent(mContext, ExchangeActivity.class);
            intent.putExtra(ExchangeActivity.MALL_BEAN, bean);
            startActivity(intent);
        });

    }


    private int selPos = 0;

    @Override
    public void onMallCatgray(MallCatgrayDao catgrayDao) {

        if (catgrayDao != null && catgrayDao.getRow() != null) {

            catgrayDaoRow = catgrayDao.getRow();

            adapter = new FastAdapter() {
                @Override
                public int getCount() {
                    return catgrayDaoRow.size();
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    if (convertView == null) {
                        convertView = LayoutInflater.from(mContext).inflate(R.layout.category_list_item, parent, false);
                    }
                    TextView tvCategory = convertView.findViewById(R.id.tv_category);
                    MallCatgrayDao.MallCatgrayBean mallCatgrayBean = catgrayDao.getRow().get(position);
                    tvCategory.setText(mallCatgrayBean.getName());
                    if (selPos != -1 & selPos == position) {
                        tvCategory.setBackgroundColor(getResources().getColor(R.color.white));
                        tvCategory.setTextColor(getResources().getColor(R.color.comm_header_color));
                    } else {
                        tvCategory.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                        tvCategory.setTextColor(getResources().getColor(R.color.text_color_normal));
                    }
                    return convertView;
                }
            };

            lvMenu.setAdapter(adapter);
            mPresenter.getMall(catgrayDao.getRow().get(0).getId());
        }

    }

    @Override
    public void showLoading() {

        LoadingView.showLoading(mActivity, "正在加载", false);
    }

    @Override
    public void dissmissLoading() {

        LoadingView.cancelLoading();
    }


    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        lvMenu.setOnItemClickListener((parent, view, position, id) -> {
            selPos = position;
            adapter.notifyDataSetChanged();
            mPresenter.getMall(catgrayDaoRow.get(position).getId());

        });

    }

    private void initRefresh() {
        refresh.setEnableLoadMore(false);
        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                mPresenter.getMall(catgrayDaoRow.get(selPos).getId());
            }
        });


    }
}
