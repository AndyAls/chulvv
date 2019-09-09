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
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.utils.GlideUtil;
import com.qlckh.chunlvv.view.richtextview.XRichText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/8/17 10:57
 * Desc:
 */
public class NewsMoreAdapter extends RecyclerView.Adapter<NewsMoreAdapter.VH> {

    private onItemClickListener mListener;

    private Context mContext;
    private List<BannerDao.ImgBean> mDatas;

    public NewsMoreAdapter(NewsMoreActivity newsMoreActivity, List<BannerDao.ImgBean> row) {
        this.mContext = newsMoreActivity;
        this.mDatas = row;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.fragment_news_view, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        BannerDao.ImgBean imgBean = mDatas.get(position);
        holder.newsOneContentTv.text(imgBean.getContent());
        holder.newsOneTitleTv.setText(imgBean.getTitle());
        holder.newsOneTimeTv.setText(TimeUtil.formatTime(Long.parseLong(imgBean.getAddtime()) * 1000, TimeUtil.Y_M_D_H_M_24));
        GlideUtil.displayRoundConnerImg(mContext, ApiService.IMG_URL + imgBean.getImg(), holder.newsOneImg);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.news_one_img)
        ImageView newsOneImg;
        @BindView(R.id.news_one_title_tv)
        TextView newsOneTitleTv;
        @BindView(R.id.news_one_content_tv)
        XRichText newsOneContentTv;
        @BindView(R.id.news_one_time_tv)
        TextView newsOneTimeTv;
        @BindView(R.id.news_one_ll)
        LinearLayout newsOneLl;
        @BindView(R.id.news_ll)
        LinearLayout newsLl;

        VH(View view) {
            super(view);
            ButterKnife.bind(this, view);
            newsLl.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onItemClick(view, VH.this.getAdapterPosition(), mDatas.get(VH.this.getAdapterPosition()));
                }
            });

        }
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position, BannerDao.ImgBean imgs);
    }

    public void setonItemClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }

}
