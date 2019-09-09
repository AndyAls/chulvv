package com.qlckh.chunlvv.activity;

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
 * @date 2018/9/10 13:39
 * Desc:
 */
public class MotifyPhoneActivity extends BaseActivity {
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    @Override
    protected int getContentView() {
        return R.layout.activitiy_motify_phone;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {
        setTitle("更换手机号");
        goBack();
    }

    @Override
    public void initDate() {

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

        if (isEmpty(name.getText().toString().trim())||name.getText().toString().trim().length()!=11){
            showShort("请输入正确的手机号码");
            return;
        }
        loading();
        RxHttpUtils.createApi(ApiService.class)
                .motifyPhone(UserConfig.getUserid(),UserConfig.getType()+"",name.getText().toString().trim())
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
                        showError("手机号码修改成功");
                        finish();


                    }
                });
    }
}
