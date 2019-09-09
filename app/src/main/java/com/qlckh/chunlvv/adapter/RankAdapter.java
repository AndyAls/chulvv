package com.qlckh.chunlvv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.NewsMoreActivity;
import com.qlckh.chunlvv.activity.RankActivity;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.RankDao;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.utils.GlideUtil;
import com.qlckh.chunlvv.view.richtextview.XRichText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/9/7 17:28
 * Desc:
 */
public class RankAdapter extends RecyclerView.Adapter<RankAdapter.VH> {
    private Context mContext;
    private List<RankDao.RankBean> mDatas;

    public RankAdapter(RankActivity newsMoreActivity, List<RankDao.RankBean> row) {
        this.mContext = newsMoreActivity;
        this.mDatas = row;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.rank_list_item, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        RankDao.RankBean rankBean = mDatas.get(position);
        GlideUtil.displayCircleImg(mContext, ApiService.IMG_URL + rankBean.getErimg(), holder.ivHead);
        holder.tvUser.setText(rankBean.getFullname());
        holder.tvAddress.setText(rankBean.getWeight());
        holder.tvJifen.setText(rankBean.getJifen());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_user)
        TextView tvUser;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_jifen)
        TextView tvJifen;

        VH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
