package com.qlckh.chunlvv.fragment;

import android.content.Intent;
import android.graphics.Color;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.RecycleFromActivity;
import com.qlckh.chunlvv.adapter.FastAdapter;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseMvpFragment;
import com.qlckh.chunlvv.dao.CategoryDao;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.ProcuteDao;
import com.qlckh.chunlvv.impl.RecycleProcutePresenterImpl;
import com.qlckh.chunlvv.presenter.RecycleProcutePresenter;
import com.qlckh.chunlvv.utils.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author Andy
 * @date 2018/9/4 21:55
 * Desc:呼叫等待
 */
public class RecycleProcuteFragment extends BaseMvpFragment<RecycleProcutePresenter> implements RecycleProcutePresenter.RecycleProcuteView {
    @BindView(R.id.category_list)
    ListView categoryList;
    @BindView(R.id.procute_list)
    ListView procuteList;
    Unbinder unbinder;
    private int guanPos = 0;
    private FastAdapter guanAdapter;
    private List<CategoryDao.CategoryBean> categoryBeans;
    private List<ProcuteDao.ProcuteBean> procuteBeans;
    private FastAdapter procuteAdapter;

    @Override
    protected RecycleProcutePresenter initPresenter() {
        return new RecycleProcutePresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_recycle_procute;
    }

    @Override
    public void onCategorySuccess(CategoryDao dao) {

        if (dao != null && dao.getRow() != null) {
            categoryBeans = dao.getRow();
            setGuanAdapter(categoryBeans);
            mPresenter.getProcute(dao.getRow().get(0).getId());
        }
    }

    private void setGuanAdapter(List<CategoryDao.CategoryBean> guans) {
        guanAdapter = new FastAdapter() {
            @Override
            public int getCount() {
                return guans.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.select_list_item, parent, false);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                CategoryDao.CategoryBean guan = guans.get(position);
                holder.ivCheck.setVisibility(View.GONE);
                holder.tvName.setText(guan.getClassname());
                if (guanPos != -1 && guanPos == position) {
                    holder.llPrent.setBackgroundColor(Color.WHITE);
                    holder.tvName.setTextColor(Color.RED);
                } else {
                    holder.llPrent.setBackgroundColor(Color.TRANSPARENT);
                    holder.tvName.setTextColor(getResources().getColor(R.color.text_color_normal));
                }

                return convertView;
            }
        };
        categoryList.setAdapter(guanAdapter);
    }

    @Override
    public void onProcuteSuccess(ProcuteDao procuteDao) {

        procuteBeans = procuteDao.getRow();
        setProcuteAdapter(procuteBeans);
    }

    private void setProcuteAdapter(List<ProcuteDao.ProcuteBean> procuteBeans) {
        procuteAdapter = new FastAdapter() {
            @Override
            public int getCount() {
                return procuteBeans == null ? 0 : procuteBeans.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                VH holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.product_list_item, parent, false);
                    holder = new VH(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (VH) convertView.getTag();
                }
                ProcuteDao.ProcuteBean procuteBean = procuteBeans.get(position);
                GlideUtil.displayRoundConnerImg(mContext, ApiService.IMG_URL + procuteBean.getImg(), holder.ivProcute);
                holder.tvPrice.setText(procuteBean.getPrice());
                holder.tvProcute.setText(procuteBean.getTitle());
                return convertView;
            }
        };
        procuteList.setAdapter(procuteAdapter);
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

        categoryList.setOnItemClickListener((parent, view, position, id) -> {
            guanPos = position;
            guanAdapter.notifyDataSetChanged();
            mPresenter.getProcute(categoryBeans.get(position).getId());
        });

        procuteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(mContext, RecycleFromActivity.class);
                intent.putExtra(RecycleFromActivity.RECYLE_DAO, procuteBeans.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void initDate() {
        mPresenter.getCategory();

    }

    @Override
    public void showError(String msg) {
        showShort(msg);
    }

    @Override
    public void release() {
    }

    public static Fragment getInstance() {
        RecycleProcuteFragment fragment = new RecycleProcuteFragment();
        return fragment;
    }

    private class ViewHolder {
        TextView tvName;
        ImageView ivCheck;
        LinearLayout llPrent;

        ViewHolder(View view) {
            tvName = view.findViewById(R.id.tv_name);
            ivCheck = view.findViewById(R.id.iv_check);
            llPrent = view.findViewById(R.id.ll_prent);
        }
    }

    private class VH {
        TextView tvPrice, tvProcute;
        ImageView ivProcute;

        VH(View view) {
            tvPrice = view.findViewById(R.id.tv_price);
            tvProcute = view.findViewById(R.id.tv_procute);
            ivProcute = view.findViewById(R.id.iv_procute);
        }
    }
}
