package com.qlckh.chunlvv.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.NewsActivity;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.utils.GlideUtil;
import com.qlckh.chunlvv.view.richtextview.XRichText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2019/1/21 10:34
 * Desc:
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VH> {


    private final Context mContext;
    private List<BannerDao.ImgBean> mDatas;

    public NewsAdapter(Context context) {
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_news_view, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        BannerDao.ImgBean imgBean = mDatas.get(position);
        GlideUtil.displayRoundConnerImg(mContext, ApiService.IMG_URL + imgBean.getImg(),holder.newsOneImg);
        holder.newsOneTitleTv.setText(imgBean.getTitle());
        holder.newsOneContentTv.text(imgBean.getContent());
        holder.newsOneTimeTv.setText(TimeUtil.formatTime(Long.parseLong(imgBean.getAddtime()) * 1000, TimeUtil.Y_M_D_H_M_24));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, NewsActivity.class);
                intent.putExtra(NewsActivity.NEWS_CONTENT, imgBean.getContent());
                intent.putExtra(NewsActivity.NEWS_TIME, imgBean.getAddtime());
                intent.putExtra(NewsActivity.NEWS_TITLE, imgBean.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size()>3?3:mDatas.size();
    }

    public void setNewsData(List<BannerDao.ImgBean> imgBean) {
        if (mDatas.size() > 0) {
            mDatas.clear();
        }
        this.mDatas = imgBean;
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder{
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
        }
    }
}
