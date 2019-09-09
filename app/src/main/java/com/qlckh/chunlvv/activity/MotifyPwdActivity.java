package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.user.UserConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/9/10 14:00
 * Desc:
 */
public class MotifyPwdActivity extends BaseActivity {
    @BindView(R.id.et_old)
    EditText etOld;
    @BindView(R.id.et_new)
    EditText etNew;
    @BindView(R.id.et_sure)
    EditText etSure;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    private String pwd;

    @Override
    protected int getContentView() {
        return R.layout.activity_motify_pwd;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {

        setTitle("修改密码");
        goBack();
    }

    @Override
    public void initDate() {

        pwd = UserConfig.getPwd();
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
    }

    @OnClick(R.id.bt_submit)
    public void onViewClicked() {
        if (!checkData()){
            return;
        }
        loading();
        RxHttpUtils.createApi(ApiService.class)
                .motifyPwd(UserConfig.getUserid(),UserConfig.getType()+"",etOld.getText().toString().trim(),
                        etNew.getText().toString().trim(),etSure.getText().toString().trim())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showError(errorMsg);
                        cancelLoading();
                    }

                    @Override
                    protected void onSuccess(CommonDao commonDao) {
                        cancelLoading();
                        showError("密码修改成功,请重新登录");
                        UserConfig.reset();
                        UserConfig.userInfo = null;
                        UserConfig.userResp=null;
                        Intent intent = new Intent(MotifyPwdActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private boolean checkData(){

        if (!pwd.equals(etOld.getText().toString().trim())){
            showShort("旧密码输入错误");
            return false;
        }

        if (isEmpty(etNew.getText().toString().trim())||isEmpty(etSure.getText().toString().trim())||!etNew.getText().toString().trim().equals(etSure.getText().toString().trim())){

            showError("两次输入的密码不一致,请确定");
            return false;
        }
        return true;
    }
}
