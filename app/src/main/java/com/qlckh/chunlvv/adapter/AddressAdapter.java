package com.qlckh.chunlvv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.AddressActivity;
import com.qlckh.chunlvv.dao.AddressDao;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/9/5 14:54
 * Desc:
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.VH> {
    private final Context mContext;
    private final List<AddressDao.AddressBean> mDatas;
    private OnItemClickListener mListener;

    public AddressAdapter(AddressActivity addressActivity, List<AddressDao.AddressBean> addressBeans) {
        this.mContext = addressActivity;
        this.mDatas = addressBeans;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_address_list, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        AddressDao.AddressBean addressBean = mDatas.get(position);
        holder.address.setText(MessageFormat.format("{0}{1}", addressBean.getShippingaddress(), addressBean.getAddress()));
        holder.name.setText(addressBean.getUsername());
        holder.phone.setText(addressBean.getPhone());
        if ("1".equals(addressBean.getIsdefault())){
            holder.defaultt.setChecked(true);
        }else {
            holder.defaultt.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

     class VH extends RecyclerView.ViewHolder{
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.defaultt)
        CheckBox defaultt;
        @BindView(R.id.defaulttClick)
        FrameLayout defaulttClick;
        @BindView(R.id.delete)
        TextView delete;
        @BindView(R.id.edit)
        TextView edit;
        @BindView(R.id.clickView)
        LinearLayout clickView;

         VH(View view) {
            super(view);
            ButterKnife.bind(this, view);
            clickView.setOnClickListener(v -> {

                if (mListener!=null){
                    mListener.onItemClick(VH.this.getAdapterPosition(),v,mDatas.get(VH.this.getAdapterPosition()));
                }
            });

             defaulttClick.setOnClickListener(v -> {

                 if (mListener!=null){
                     mListener.onSelClick(VH.this.getAdapterPosition(),v,mDatas.get(VH.this.getAdapterPosition()));
                 }
             });

             delete.setOnClickListener(v -> {
                 if (mListener!=null){
                     mListener.onDelClick(VH.this.getAdapterPosition(),v,mDatas.get(VH.this.getAdapterPosition()));
                 }
             });

             edit.setOnClickListener(v -> {
                 if (mListener!=null){
                     mListener.onEditClick(VH.this.getAdapterPosition(),v,mDatas.get(VH.this.getAdapterPosition()));
                 }
             });
        }
    }

    public interface OnItemClickListener{

        void onItemClick(int position,View view,AddressDao.AddressBean bean);
        void onSelClick(int position,View view,AddressDao.AddressBean bean);
        void onEditClick(int position,View view,AddressDao.AddressBean bean);
        void onDelClick(int position,View view,AddressDao.AddressBean bean);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }
}
