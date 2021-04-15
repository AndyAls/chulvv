package com.qlckh.chunlvv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.CollectListActivity;
import com.qlckh.chunlvv.dao.ScoreBean;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/8/19 0:07
 * Desc:
 */
public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.VH> {
    private final Context mContext;
    private final List<ScoreBean> mDatas;
    private onItemClickListener mListener;


    public CollectAdapter(CollectListActivity collectListActivity, List<ScoreBean> scoreBeanList) {
        this.mContext = collectListActivity;

        this.mDatas = scoreBeanList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_gathering_data_view, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        ScoreBean bean = mDatas.get(position);

        if ("0".equals(bean.getWhatType())){

            holder.tvTitle.setText("易腐垃圾");
        }else {
            holder.tvTitle.setText("其他垃圾");
        }
        holder.usernameTv.setText(MessageFormat.format("户主: {0}", bean.getFullname()));
        holder.scoreTv.setText(MessageFormat.format("所得积分: {0}", bean.getTotalScore()));
        holder.dateTv.setText(MessageFormat.format("采集时间: {0}", bean.getTime()));
        holder.useraddresTv.setText(MessageFormat.format("地址: {0}", bean.getAddress()));
        holder.submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.onItemClick(v,holder.getAdapterPosition(),mDatas.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas==null?0:mDatas.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.username_tv)
        TextView usernameTv;
        @BindView(R.id.score_tv)
        TextView scoreTv;
        @BindView(R.id.date_tv)
        TextView dateTv;
        @BindView(R.id.useraddres_tv)
        TextView useraddresTv;
        @BindView(R.id.submit_tv)
        TextView submitTv;

        VH(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position,ScoreBean bean);
    }

    public void setonItemClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }

}
