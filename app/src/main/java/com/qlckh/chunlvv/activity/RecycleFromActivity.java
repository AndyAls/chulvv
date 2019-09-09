package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.ProcuteDao;
import com.qlckh.chunlvv.dao.RecycleDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.RecycleFromPresenterImpl;
import com.qlckh.chunlvv.presenter.CommView;
import com.qlckh.chunlvv.presenter.RecycleFromPresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.user.UserRespDao;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/9/5 11:15
 * Desc:
 */
public class RecycleFromActivity extends BaseMvpActivity<RecycleFromPresenter> implements CommView {

    public static final String RECYLE_DAO = "RECYLE_DAO";
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.leave_message_et)
    EditText leaveMessageEt;
    @BindView(R.id.sub_but)
    Button subBut;

    @Override
    protected RecycleFromPresenter initPresenter() {
        return new RecycleFromPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recycle_from;
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

        setTitle("订单回收");
        goBack();
        UserRespDao.UserResp userResp = UserConfig.getUserResp();
        etName.setText(userResp.getFullname());
        etPhone.setText(userResp.getPhone());
        tvAddress.setText(userResp.getAddresss());

    }

    @Override
    public void initDate() {

        Intent intent = getIntent();
        ProcuteDao.ProcuteBean bean=intent.getParcelableExtra(RECYLE_DAO);
        tvTitle.setText(bean.getTitle());
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
    }

    @OnClick(R.id.sub_but)
    public void onViewClicked() {

        mPresenter.recycleSumbit(tvTitle.getText().toString(),leaveMessageEt.getText().toString().trim());
    }
}
