package com.qlckh.chunlvv.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.HandRecyEvent;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/9/12 17:17
 * Desc:
 */
public class RecyclerHandActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_title)
    EditText tvTitle;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.et_zhong)
    EditText etZhong;
    @BindView(R.id.et_jifen)
    EditText etJifen;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.sub_but)
    Button subBut;

    @Override
    protected int getContentView() {
        return R.layout.activitiy_recy_hand;
    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }

    @Override
    public void initView() {

        setTitle("回收数据");
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

    private boolean checkData(){

        if (isEmpty(etName.getText().toString().trim())){
            showShort("请输入姓名");
            return false;
        }

        if (isEmpty(etPhone.getText().toString().trim())){
            showShort("请输入手机号码");
            return false;
        }

        if (isEmpty(tvTitle.getText().toString().trim())){
            showShort("请输入回收物品");
            return false;
        }

        if (isEmpty(etAddress.getText().toString().trim())){
            showShort("请输入地址");
            return false;
        }

        if (isEmpty(etNum.getText().toString().trim())){
            showShort("请输入数量");
            return false;
        }

        if (isEmpty(etNum.getText().toString().trim())){
            showShort("请输入数量");
            return false;
        }

        if (isEmpty(etJifen.getText().toString().trim())){
            showShort("请输入积分");
            return false;
        }


        if (isEmpty(etPrice.getText().toString().trim())){
            showShort("请输入价格");
            return false;
        }


        return true;
    }
    @OnClick(R.id.sub_but)
    public void onViewClicked() {

        if (!checkData()){
            return;
        }

        loading();
        HashMap<String, String> map = new HashMap<>();
        map.put("username",etName.getText().toString().trim());
        map.put("phone",etPhone.getText().toString().trim());
        map.put("address",etAddress.getText().toString().trim());
        map.put("title",tvTitle.getText().toString().trim());
        map.put("zhong",etZhong.getText().toString().trim());
        map.put("jifen",etJifen.getText().toString().trim());
        RxHttpUtils.createApi(ApiService.class)
                .handRecy(map)
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
                EventBus.getDefault().post(new HandRecyEvent());
                finish();
            }
        });

    }
}
