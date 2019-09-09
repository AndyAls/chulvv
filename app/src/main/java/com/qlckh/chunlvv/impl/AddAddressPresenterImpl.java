package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.AddAddressPresenter;

import java.util.Map;

/**
 * @author Andy
 * @date 2018/9/5 15:23
 * Desc:
 */
public class AddAddressPresenterImpl implements AddAddressPresenter {

    private AddAddressView mView;

    @Override
    public void addAddress(Map<String, String> map) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .addAddress(map)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(CommonDao commonDao) {

                        mView.dissmissLoading();
                        mView.onAddAddressSuccess();
                    }
                });
    }

    @Override
    public void editAddress(Map<String, String> map) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .editAddress(map)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(CommonDao commonDao) {

                        mView.dissmissLoading();
                        mView.onEditAddressSuccess();
                    }
                });
    }

    @Override
    public void register(AddAddressView view) {

        mView = view;
    }

    @Override
    public void unregister(AddAddressView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
