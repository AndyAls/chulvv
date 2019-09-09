package com.qlckh.chunlvv.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.OrderDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.EvaluationPresenterImpl;
import com.qlckh.chunlvv.presenter.CommView;
import com.qlckh.chunlvv.presenter.EvaluationPresenter;
import com.qlckh.chunlvv.utils.GlideUtil;
import com.qlckh.chunlvv.view.RatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/9/6 16:36
 * Desc:
 */
public class EvaluationActivitiy extends BaseMvpActivity<EvaluationPresenter> implements CommView {
    @BindView(R.id.goods_img)
    ImageView goodsImg;
    @BindView(R.id.goods_name_tv)
    TextView goodsNameTv;
    @BindView(R.id.huishouyuan_tv)
    TextView huishouyuanTv;
    @BindView(R.id.star)
    RatingBar star;
    @BindView(R.id.context_ed)
    EditText contextEd;
    @BindView(R.id.sumit)
    Button sumit;
    public float xing = 5.0f;
    private OrderDao.OrderBean orderBean;

    @Override
    protected EvaluationPresenter initPresenter() {
        return new EvaluationPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_evaluation;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void onSuccess(CommonDao dao) {

        finish();
    }

    @Override
    public void showLoading() {

        loading();
    }

    @Override
    public void dissmissLoading() {

        cancelLoading();
    }

    @Override
    public void initView() {
        setTitle("评价");
        star.setStar(xing);
        goBack();
        star.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                EvaluationActivitiy.this.xing = ratingCount;
            }
        });
    }

    @Override
    public void initDate() {
        orderBean = getIntent().getParcelableExtra("orderbean");
        GlideUtil.displayRoundConnerImg(this, ApiService.IMG_URL+orderBean.getImg(),goodsImg);
        goodsNameTv.setText(orderBean.getTitle());
    }

    @Override
    public void showError(String msg) {
        showShort(msg);
    }

    @Override
    public void release() {
        RxHttpUtils.cancelAllRequest();
    }


    @OnClick(R.id.sumit)
    public void onViewClicked() {
        mPresenter.evaluation(orderBean,xing+"",contextEd.getText().toString().trim());
    }
}
