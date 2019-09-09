package com.qlckh.chunlvv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.MallDao;
import com.qlckh.chunlvv.utils.GlideUtil;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/8/20 11:06
 * Desc:
 */
public class MallListAdapter extends RecyclerView.Adapter<MallListAdapter.VH> {
    private final Context mContext;
    private final List<MallDao.MallBean> mDatas;
    private onItemClickListener mListener;

    public MallListAdapter(Context context, List<MallDao.MallBean> row) {
        this.mContext = context;
        this.mDatas = row;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_mall, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        MallDao.MallBean mallBean = mDatas.get(position);
        GlideUtil.displayRoundConnerImg(mContext, ApiService.IMG_URL+mallBean.getImgpath(),holder.goodsImg);
        holder.goodsNameTv.setText(mallBean.getPrdname());
        holder.integralTv.setText(MessageFormat.format("积分 {0}", mallBean.getJifen()));
        holder.inventoryTv.setText(MessageFormat.format("库存 {0}", mallBean.getPrdnum()));
        holder.goodsExchangeTv.setText(MessageFormat.format("已兑换{0}件", mallBean.getFinishprdnum()));
    }

    @Override
    public int getItemCount() {
        return mDatas==null?0:mDatas.size();
    }

     class VH extends RecyclerView.ViewHolder{
        @BindView(R.id.goods_img)
        ImageView goodsImg;
        @BindView(R.id.goods_name_tv)
        TextView goodsNameTv;
        @BindView(R.id.goods_exchange_tv)
        TextView goodsExchangeTv;
        @BindView(R.id.inventory_tv)
        TextView inventoryTv;
        @BindView(R.id.integral_tv)
        TextView integralTv;
        @BindView(R.id.exchange_tv)
        TextView exchangeTv;

        VH(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> {
                if (mListener!=null){
                    mListener.onItemClick(v,VH.this.getAdapterPosition(),mDatas.get(VH.this.getAdapterPosition()));
                }
            });
        }
    }
    public interface onItemClickListener {
        void onItemClick(View view, int position, MallDao.MallBean bean);
    }

    public void setonItemClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }

}
