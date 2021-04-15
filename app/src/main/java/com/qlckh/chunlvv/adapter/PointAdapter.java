package com.qlckh.chunlvv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.dao.PointDao;
import com.qlckh.chunlvv.http.utils.TimeUtil;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/9/10 17:02
 * Desc:
 */
public class PointAdapter extends RecyclerView.Adapter<PointAdapter.VH> {
    private final Context mContext;
    private final List<PointDao.PointBean> mDatas;

    public PointAdapter(Context context, List<PointDao.PointBean> row) {
        this.mContext = context;
        this.mDatas = row;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.point_list_item, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        PointDao.PointBean pointBean = mDatas.get(position);
        holder.goodsNameTv.setText(pointBean.getPrdname());
        String flag = pointBean.getFlag();
        switch (flag) {
            case "0":
                holder.tvState.setText("未处理");
                break;
            case "1":
                holder.tvState.setText("已发货");
                break;
            case "2":
                holder.tvState.setText("已完成");
                break;
            default:
                holder.tvState.setText("");
                break;
        }
        holder.tvUseName.setText(MessageFormat.format("积分: {0}", pointBean.getJifen()));
        holder.exchangeTv.setText(pointBean.getPhone());
        holder.tvAddress.setText(pointBean.getXaddress());
        holder.tvTime.setText(pointBean.getUcode());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class VH extends RecyclerView.ViewHolder {
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

        VH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
