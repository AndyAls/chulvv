package com.qlckh.chunlvv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.EntrantActivity;
import com.qlckh.chunlvv.dao.CunGuanDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/9/10 13:12
 * Desc:
 */
public class EntrantAdapter extends RecyclerView.Adapter<EntrantAdapter.VH> {
    private final EntrantActivity mContext;
    private final List<CunGuanDao.CunGuanBean> mDatas;

    public EntrantAdapter(EntrantActivity entrantActivity, List<CunGuanDao.CunGuanBean> row) {
        this.mContext = entrantActivity;
        this.mDatas = row;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.entrant_list_item, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        holder.textView.setText(mDatas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

     class VH extends RecyclerView.ViewHolder{
        @BindView(R.id.textView)
        TextView textView;

         VH(View view) {
             super(view);
            ButterKnife.bind(this, view);
        }
    }
}
