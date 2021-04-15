package com.qlckh.chunlvv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.MessageActivity;
import com.qlckh.chunlvv.dao.InMsgDao;
import com.qlckh.chunlvv.dao.OutMessageDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/5/23 14:10
 * Desc:
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.VH> {
    private MessageActivity mContext;
    private boolean isIn;
    private List<InMsgDao.InMsg> mInDatas;
    private List<OutMessageDao.OutMessage> mOutDatas;
    private onItemClickListener mListener;

    public MessageAdapter(MessageActivity context, int source, List<InMsgDao.InMsg> inDatas, List<OutMessageDao.OutMessage> outDatas) {
        this.mContext = context;
        this.isIn = source == 0;
        this.mInDatas = inDatas;
        this.mOutDatas = outDatas;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.message_list_item, parent, false);

        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        if (isIn){
            InMsgDao.InMsg msgBean = mInDatas.get(position);
            holder.tvMessage.setText(msgBean.getTitle());
            String isread = msgBean.getIsread();
            if ("0".equals(isread)){
                holder.tvMessage.setTextColor(mContext.getResources().getColor(R.color.text_color_normal));
                holder.ivNotice.setImageDrawable(mContext.getResources().getDrawable(R.drawable.unread_notice));
            }else {
                holder.tvMessage.setTextColor(mContext.getResources().getColor(R.color.text_color_hint));
                holder.ivNotice.setImageDrawable(mContext.getResources().getDrawable(R.drawable.read_notice));
            }
        }else {
            OutMessageDao.OutMessage msgDao = mOutDatas.get(position);
            holder.tvMessage.setText(msgDao.getTitle());
            String isnew = msgDao.getIsread();
            if ("0".equals(isnew)){
                holder.tvMessage.setTextColor(mContext.getResources().getColor(R.color.text_color_normal));
                holder.ivNotice.setImageDrawable(mContext.getResources().getDrawable(R.drawable.unread_notice));
            }else {
                holder.tvMessage.setTextColor(mContext.getResources().getColor(R.color.text_color_hint));
                holder.ivNotice.setImageDrawable(mContext.getResources().getDrawable(R.drawable.read_notice));
            }
        }

    }

    @Override
    public int getItemCount() {
        return isIn?(mInDatas==null?0:mInDatas.size()):(mOutDatas==null?0:mOutDatas.size());
    }

    class VH extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_notice)
        ImageView ivNotice;
        @BindView(R.id.tv_message)
        TextView tvMessage;

        VH(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> {

                if (mListener!=null){
                    mListener.onItemClick(view,VH.this.getAdapterPosition());
                }

            });
        }
    }
    public interface onItemClickListener{
        void onItemClick(View view,int position);
    }
    public void setonItemClickListener(onItemClickListener listener){
        this.mListener=listener;
    }


}
