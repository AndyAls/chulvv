package com.qlckh.chunlvv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.RecycleActivity;
import com.qlckh.chunlvv.dao.HandDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/9/12 16:39
 * Desc:
 */
public class HandAdapter extends RecyclerView.Adapter<HandAdapter.VH> {
    private final Context mContext;
    private final List<HandDao.HandBean> mDatas;
    private onItemClickListener mListener;

    public HandAdapter(RecycleActivity recycleActivity, List<HandDao.HandBean> row) {
        this.mContext = recycleActivity;
        this.mDatas = row;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.hand_list_item, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        HandDao.HandBean handBean = mDatas.get(position);
        holder.tvAddress.setText(handBean.getAddresss());
        holder.tvName.setText(handBean.getUsername());
        holder.tvWu.setText(handBean.getTitle());
        String fstatus = handBean.getFstatus();
        switch (fstatus){
            case "0":
                holder.tvState.setText("确定");
                holder.tvState.setBackgroundResource(R.drawable.boc_check_status2);
                holder.tvState.setTextColor(mContext.getResources().getColor(R.color.status_green));
                break;
            case "1":
                holder.tvState.setText("查看");
                holder.tvState.setBackgroundResource(R.drawable.boc_check_status3);
                holder.tvState.setTextColor(mContext.getResources().getColor(R.color.status_orange));
                break;
                default:
                    holder.tvState.setVisibility(View.INVISIBLE);
                    break;
        }
        holder.tvState.setOnClickListener(new View.OnClickListener() {
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
        return mDatas.size();
    }

     class VH extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_wu)
        TextView tvWu;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_state)
        TextView tvState;

         VH(View view) {
             super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position, HandDao.HandBean bean);
    }

    public void setonItemClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }
}
