package com.qlckh.chunlvv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.EvaluationDetailActivity;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.EvaluationDao;
import com.qlckh.chunlvv.utils.GlideUtil;
import com.qlckh.chunlvv.view.RatingBar;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/9/6 17:48
 * Desc:
 */
public class EvaluationDetailAdapter extends RecyclerView.Adapter<EvaluationDetailAdapter.VH> {
    private final Context mContext;
    private final List<EvaluationDao.EvaBean> mDatas;

    public EvaluationDetailAdapter(EvaluationDetailActivity evaluationDetailActivity, List<EvaluationDao.EvaBean> row) {
        this.mContext = evaluationDetailActivity;
        this.mDatas = row;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_evaluation_view, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        EvaluationDao.EvaBean evaBean = mDatas.get(position);

        GlideUtil.displayRoundConnerImg(mContext, ApiService.IMG_URL+evaBean.getImgpath(),holder.goodsImg);
        holder.goodsNameTv.setText(MessageFormat.format("采集员: {0}", evaBean.getHuiuser()));
        String p_xing = evaBean.getP_xing();
        holder.evaluationStar.setStar(Float.parseFloat(p_xing));
        holder.evaluationVontentTv.setText(evaBean.getContent());


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

     class VH extends RecyclerView.ViewHolder{
        @BindView(R.id.goods_img)
        ImageView goodsImg;
        @BindView(R.id.goods_name_tv)
        TextView goodsNameTv;
        @BindView(R.id.people_tv)
        TextView peopleTv;
        @BindView(R.id.evaluation_star)
        RatingBar evaluationStar;
        @BindView(R.id.evaluation_vontent_tv)
        TextView evaluationVontentTv;

         VH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
