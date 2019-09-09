package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.common.AddressPickTask;
import com.qlckh.chunlvv.dao.AddressDao;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.impl.AddAddressPresenterImpl;
import com.qlckh.chunlvv.presenter.AddAddressPresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.usercase.AddressEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

/**
 * @author Andy
 * @date 2018/9/5 15:19
 * Desc:
 */
public class AddAddressActivity extends BaseMvpActivity<AddAddressPresenter> implements AddAddressPresenter.AddAddressView {


    public static final String ADD_ACTION = "ADD_ACTION";
    public static final String EDIT_ACTION = "EDIT_ACTION";
    public static final String ADDRESS_BEAN = "address_bean";
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.cityV)
    TextView cityV;
    @BindView(R.id.cityClick)
    LinearLayout cityClick;
    @BindView(R.id.addressD)
    EditText addressD;
    @BindView(R.id.bt_address)
    Button btAddress;

    private boolean isAdd=true;
    private AddressDao.AddressBean bean;

    @Override
    protected AddAddressPresenter initPresenter() {
        return new AddAddressPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_address;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void onAddAddressSuccess() {

      addressSuccess();
    }

    private void addressSuccess() {

        EventBus.getDefault().post(new AddressEvent());
        finish();
    }

    @Override
    public void onEditAddressSuccess() {
        addressSuccess();
    }

    @Override
    public void onSuccess(CommonDao dao) {

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
        goBack();
    }

    private boolean checkData(){

        if (isEmpty(name.getText().toString().trim())){
            showError("请输入姓名");
            return false;
        }

        if (isEmpty(phone.getText().toString().trim())&&phone.getText().toString().trim().length()!=11){
            showError("请输入正确的手机号");
            return false;
        }
        if (isEmpty(cityV.getText().toString().trim())){
            showError("请选择地址");
            return false;
        }
        if (isEmpty(addressD.getText().toString().trim())){
            showError("请输入详细地址");
            return false;
        }
        return true;

    }
    @Override
    public void initDate() {

        Intent intent = getIntent();
        if (ADD_ACTION.equals(intent.getAction())){
            setTitle("新增地址");
            isAdd=true;
        }else {
            setTitle("修改地址");
            isAdd=false;
            bean = getIntent().getParcelableExtra(ADDRESS_BEAN);
            setEditBean(bean);
        }
    }

    private void setEditBean(AddressDao.AddressBean bean) {
        name.setText(bean.getUsername());
        name.setSelection(name.getText().toString().length());
        phone.setText(bean.getPhone());
        cityV.setText(bean.getShippingaddress());
        addressD.setText(bean.getAddress());
    }

    @Override
    public void showError(String msg) {
        showShort(msg);
    }

    @Override
    public void release() {

    }

    @OnClick({R.id.cityClick, R.id.bt_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cityClick:
                chooseAddress();
                break;
            case R.id.bt_address:
                if (checkData()){
                    if (isAdd){
                        addAddress();
                    }else {
                        editAddress();
                    }
                }
                break;
                default:
        }
    }

    private void chooseAddress() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideCounty(false);
        task.setHideProvince(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                showError("无法获取地址");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                cityV.setText(MessageFormat.format("{0},{1},{2}", province.getAreaName(), city.getAreaName(), county.getAreaName()));
            }
        });
        task.execute("北京", "北京", "东城");
    }

    private void editAddress() {
        Map<String,String> map =new HashMap<>();
        map.put("username",name.getText().toString().trim());
        map.put("id", UserConfig.getUserid());
        map.put("aid", bean.getId());
        map.put("phone",phone.getText().toString().trim());
        map.put("address",addressD.getText().toString().trim());
        map.put("ship",cityV.getText().toString().trim());
        map.put("status",UserConfig.getType()+"");
        mPresenter.editAddress(map);
    }

    private void addAddress() {
        Map<String,String> map =new HashMap<>();
        map.put("username",name.getText().toString().trim());
        map.put("id", UserConfig.getUserid());
        map.put("phone",phone.getText().toString().trim());
        map.put("address",addressD.getText().toString().trim());
        map.put("ship",cityV.getText().toString().trim());
        map.put("status",UserConfig.getType()+"");
        mPresenter.addAddress(map);
    }
}
