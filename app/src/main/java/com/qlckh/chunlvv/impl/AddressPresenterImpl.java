package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.AddressDao;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.AddressPresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/9/5 14:27
 * Desc:
 */
public class AddressPresenterImpl implements AddressPresenter {

    private AddressView mView;

    @Override
    public void getAddress() {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getAddress(UserConfig.getUserid(),UserConfig.getType())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<AddressDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(AddressDao addressDao) {

                        mView.dissmissLoading();
                        mView.onAddressSuccess(addressDao);
                    }
                });
    }

    @Override
    public void selectAddress(String id) {

        RxHttpUtils.createApi(ApiService.class)
                .selAddress(id)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(CommonDao commonDao) {
                        mView.onSelectAddressSuccess();
                    }
                });
    }

    @Override
    public void delAddress(String id) {

        RxHttpUtils.createApi(ApiService.class)
                .delAddress(id)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(CommonDao commonDao) {
                        mView.onDelAddressSuccess();
                    }
                });
    }

    @Override
    public void register(AddressView view) {

        mView = view;
    }

    @Override
    public void unregister(AddressView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
