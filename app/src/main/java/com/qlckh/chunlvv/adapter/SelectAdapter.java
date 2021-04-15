package com.qlckh.chunlvv.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.common.SelectActivity3;
import com.qlckh.chunlvv.dao.MixDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/5/26 13:57
 * Desc:
 */
public class SelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final List<MixDao> mDatas;
    private onItemClickListener mListener;

    public SelectAdapter(SelectActivity3 selectActivity, List<MixDao> datas) {
        this.mContext = selectActivity;
        this.mDatas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SelectActivity3.JIE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.jie_item, parent, false);
            return new Holder(view);
        } else if (viewType == SelectActivity3.GUAN_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.guan_item, parent, false);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MixDao mixDao = mDatas.get(position);
        if (holder instanceof ViewHolder) {

            ViewHolder contentHolder = (ViewHolder) holder;
            contentHolder.tvContent.setText(mixDao.getFullName());
            if (mixDao.isSelect()){
                contentHolder.tvContent.setSelected(true);
                contentHolder.tvContent.setTextColor(mContext.getResources().getColor(R.color.white));
            }else {
                contentHolder.tvContent.setSelected(false);
                contentHolder.tvContent.setTextColor(mContext.getResources().getColor(R.color.text_color_normal));
            }
        }
        if (holder instanceof Holder) {
            ((Holder) holder).tvTitle.setText(mixDao.getHeadName());
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position).isHeader()) {
            return SelectActivity3.JIE_ITEM;
        }
        return SelectActivity3.GUAN_ITEM;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            GridLayoutManager gridManager = ((GridLayoutManager) manager);

            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case SelectActivity3.JIE_ITEM:
                            return 3;
                        default:
                            return 1;
                    }
                }
            });
        }
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            tvContent.setOnClickListener(v -> {
                if (mListener!=null){
                    mListener.onItemClick(v,ViewHolder.this.getAdapterPosition(),mDatas);
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position,List<MixDao> daos);
    }

    public void setonItemClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }

}
