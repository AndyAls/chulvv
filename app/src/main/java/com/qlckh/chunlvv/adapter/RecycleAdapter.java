package com.qlckh.chunlvv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.dao.RecycleDao;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.utils.TimeUtils;

import java.sql.Time;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/9/4 16:04
 * Desc:
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.VH> {
    private final Context mContext;
    private final List<RecycleDao.RecycleBean> mDatas;

    public RecycleAdapter(Context context, List<RecycleDao.RecycleBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.recy_list_item, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        RecycleDao.RecycleBean recycleBean = mDatas.get(position);
        holder.usernameTv.setText(recycleBean.getUsername());
        if ("0".equals(recycleBean.getWhattype())){
            holder.compostTv.setText("可回收");
        }else if ("1".equals(recycleBean.getWhattype())){
            holder.compostTv.setText("不可回收");
        }else {
            holder.compostTv.setText("可回收");
        }
        holder.weightTv.setText(recycleBean.getJi_duinum());
        holder.integralTv.setText(recycleBean.getZong());
        if (position==0){
            holder.dateTv.setText(recycleBean.getAddtime());
        }else {
            holder.dateTv.setText(TimeUtil.formatTime(Long.parseLong(recycleBean.getAddtime())*1000,TimeUtil.M_D_H));
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

     class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.username_tv)
        TextView usernameTv;
        @BindView(R.id.compost_tv)
        TextView compostTv;
        @BindView(R.id.weight_tv)
        TextView weightTv;
        @BindView(R.id.integral_tv)
        TextView integralTv;
        @BindView(R.id.date_tv)
        TextView dateTv;

         VH(View view) {
             super(view);
            ButterKnife.bind(this, view);
        }
    }
}
