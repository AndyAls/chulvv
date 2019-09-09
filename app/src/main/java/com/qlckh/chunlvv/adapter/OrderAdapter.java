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
import com.qlckh.chunlvv.activity.EvaluationActivitiy;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.MallCatgrayDao;
import com.qlckh.chunlvv.dao.MallDao;
import com.qlckh.chunlvv.dao.OrderDao;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.utils.GlideUtil;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/9/6 14:06
 * Desc:
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private final Context mContext;
    private final List<OrderDao.OrderBean> mDatas;
    private onItemClickListener mListener;

    public OrderAdapter(Context context, List<OrderDao.OrderBean> row) {
        this.mContext = context;
        this.mDatas = row;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderDao.OrderBean orderBean = mDatas.get(position);

        String fstatus = orderBean.getFstatus();
        switch (fstatus) {
            case "0":
                holder.tvState.setText("待处理");
                holder.llHand.setVisibility(View.GONE);
                holder.ivEvaluation.setVisibility(View.GONE);
                holder.llHandWeight.setVisibility(View.GONE);
                break;
            case "1":
                holder.tvState.setText("处理中");
                holder.llHand.setVisibility(View.VISIBLE);
                holder.ivEvaluation.setVisibility(View.GONE);
                holder.llHandWeight.setVisibility(View.GONE);
                break;
            case "2":
                holder.llHand.setVisibility(View.VISIBLE);
                holder.ivEvaluation.setVisibility(View.VISIBLE);
                holder.llHandWeight.setVisibility(View.VISIBLE);
                holder.tvState.setText("已处理");
                break;
            case "3":
                holder.tvState.setText("已完成");
                holder.llHand.setVisibility(View.VISIBLE);
                holder.ivEvaluation.setVisibility(View.GONE);
                holder.llHandWeight.setVisibility(View.VISIBLE);
                holder.tvState.setText("已处理");
                break;
            default:
        }
        GlideUtil.displayRoundConnerImg(mContext, ApiService.IMG_URL+orderBean.getImg(), holder.goodsImg);
        holder.goodsNameTv.setText(orderBean.getTitle());
        holder.tvUseName.setText(orderBean.getUsername());
        holder.exchangeTv.setText(orderBean.getPhone());
        holder.tvAddress.setText(orderBean.getAddress());
        holder.tvTime.setText(TimeUtil.formatTime(orderBean.getAddtime() * 1000, TimeUtil.Y_M_D_H_M_S_24));
        holder.tvPrice.setText(MessageFormat.format("总价:  {0}", orderBean.getJifen()));
        holder.tvHander.setText(MessageFormat.format("处理员  {0}", orderBean.getHuiuser()));
        holder.tvJifen.setText(MessageFormat.format("积分:  {0}", orderBean.getJifen()));
        holder.tvWeight.setText(MessageFormat.format("重量:  {0}", orderBean.getZhong()));
        holder.tvHandPhone.setText(orderBean.getPhone());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvState)
        TextView tvState;
        @BindView(R.id.ll_my_store)
        LinearLayout llMyStore;
        @BindView(R.id.goods_img)
        ImageView goodsImg;
        @BindView(R.id.goods_name_tv)
        TextView goodsNameTv;
        @BindView(R.id.tv_useName)
        TextView tvUseName;
        @BindView(R.id.exchange_tv)
        TextView exchangeTv;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.iv_evaluation)
        TextView ivEvaluation;
        @BindView(R.id.tv_hander)
        TextView tvHander;
        @BindView(R.id.tv_hand_phone)
        TextView tvHandPhone;
        @BindView(R.id.ll_hand)
        LinearLayout llHand;
        @BindView(R.id.tv_weight)
        TextView tvWeight;
        @BindView(R.id.tv_jifen)
        TextView tvJifen;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.ll_hand_weight)
        LinearLayout llHandWeight;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ivEvaluation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   Intent intent =new Intent(mContext,EvaluationActivitiy.class);
                   intent.putExtra("orderbean",mDatas.get(ViewHolder.this.getAdapterPosition()));
                   mContext.startActivity(intent);
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position, OrderDao.OrderBean bean);
    }

    public void setonItemClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }

}
