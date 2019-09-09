package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.common.SelectActivity3;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.CuntryDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.SendPresenterImpl;
import com.qlckh.chunlvv.presenter.SendPresenter;
import com.qlckh.chunlvv.view.LoadingView;

import java.text.MessageFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/22 18:40
 * Desc:发送消息 -->即时通讯
 */
public class SendActivity extends BaseMvpActivity<SendPresenter> implements SendPresenter.SendView {
    public static final int SELECT_REQUEST_CODE = 0x3e8;
    public static final String CUNTRY_LIST = "CUNTRY_LIST";
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    @BindView(R.id.sumit)
    Button sumit;

    private ArrayList<CunGuanDao.CunGuanBean> cuntryList = new ArrayList<>();
    private String tel="";
    private int baojieId=-1;

    @Override
    protected SendPresenter initPresenter() {
        return new SendPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_send;
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
        LoadingView.showLoading(this,"",false);

    }

    @Override
    public void dissmissLoading() {

        LoadingView.cancelLoading();
    }

    @Override
    public void initView() {
        goBack();
        setTitle("即时通讯");

    }

    @Override
    public void initDate() {
        mPresenter.getCuntryList();

    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {
        RxHttpUtils.cancelAllRequest();

    }


    @OnClick({R.id.ll_select, R.id.sumit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_select:
                toSelect();
                break;
            case R.id.sumit:
                if (checkData()){
                    mPresenter.sendSumbit(baojieId+"",etTitle.getText().toString().trim(),etContent.getText().toString().trim());
                }
                break;
            default:
        }
    }

    private void toSelect() {
        Intent intent = new Intent(this,SelectActivity.class);
        intent.putParcelableArrayListExtra(CUNTRY_LIST, cuntryList);
        startActivityForResult(intent,SELECT_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null&&resultCode==RESULT_OK){
            if (requestCode==SELECT_REQUEST_CODE){
                tel = data.getStringExtra(SelectActivity3.GUAN_ID);
                String name = data.getStringExtra(SelectActivity3.BAOJIE_NAME);
//                if (tel.length()>1){
//                    tvSelect.setVisibility(View.VISIBLE);
//                    String[] split = tel.split(",");
//                    tvSelect.setText(MessageFormat.format("已选:{0}人", split.length));
//                }

                baojieId = data.getIntExtra(SelectActivity.BAOJIE_ID, -1);
                tvSelect.setVisibility(View.VISIBLE);
                tvSelect.setText(String.format("%s",
                        data.getStringExtra(SelectActivity.BAOJIE_NAME)));
            }
        }
    }

    @Override
    public void onCuntrySuccess(CunGuanDao dao) {
        cuntryList = (ArrayList<CunGuanDao.CunGuanBean>) dao.getRow();
    }
    private boolean checkData(){

        if (isEmpty(etTitle.getText().toString().trim())){
            showShort("请输入标题");
            return false;
        }
        if (isEmpty(etContent.getText().toString().trim())){
            showShort("请输入内容");
            return false;
        }
        if (baojieId==-1){
            showShort("请选择发送人");
            return false;
        }
        return true;
    }
}
