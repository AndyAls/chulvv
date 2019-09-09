package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.AddressAdapter;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.AddressDao;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.AddressPresenterImpl;
import com.qlckh.chunlvv.presenter.AddressPresenter;
import com.qlckh.chunlvv.usercase.AddressEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/8/24 21:22
 * Desc:
 */
public class AddressActivity extends BaseMvpActivity<AddressPresenter> implements AddressPresenter.AddressView {
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bt_address)
    Button btAddress;
    private List<AddressDao.AddressBean> addressBeans;
    private AddressAdapter adapter;

    public static final String CHOOSE_ADDRESS_ACTION="choose_address_action";

    @Override
    protected AddressPresenter initPresenter() {
        return new AddressPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_address;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {
        setTitle("地址管理");
        goBack();
        initRecyclerView();
        EventBus.getDefault().register(this);
    }


    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddressSuccess(AddressEvent event) {
        mPresenter.getAddress();
    }

    @Override
    public void initDate() {

        mPresenter.getAddress();
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {
        RxHttpUtils.cancelAllRequest();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.bt_address)
    public void onViewClicked() {

        Intent intent = new Intent(this, AddAddressActivity.class);
        intent.setAction(AddAddressActivity.ADD_ACTION);
        startActivity(intent);
    }

    @Override
    public void onAddressSuccess(AddressDao dao) {

        if (dao != null && dao.getRow() != null && dao.getRow().size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            addressBeans = dao.getRow();
            setAdapter(addressBeans);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void setAdapter(List<AddressDao.AddressBean> addressBeans) {
        adapter = new AddressAdapter(this, addressBeans);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(int position, View view, AddressDao.AddressBean bean) {
                if (CHOOSE_ADDRESS_ACTION.equals(getIntent().getAction())){
                    Intent intent = getIntent();
                    intent.putExtra("bean",bean);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }

            @Override
            public void onSelClick(int position, View view, AddressDao.AddressBean bean) {
                mPresenter.selectAddress(bean.getId());
            }

            @Override
            public void onEditClick(int position, View view, AddressDao.AddressBean bean) {

                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                intent.setAction(AddAddressActivity.EDIT_ACTION);
                intent.putExtra(AddAddressActivity.ADDRESS_BEAN, bean);
                startActivity(intent);
            }

            @Override
            public void onDelClick(int position, View view, AddressDao.AddressBean bean) {
                mPresenter.delAddress(bean.getId());
            }
        });
    }

    @Override
    public void onSelectAddressSuccess() {
        mPresenter.getAddress();
    }

    @Override
    public void onDelAddressSuccess() {
        mPresenter.getAddress();
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
}
