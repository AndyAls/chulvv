package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.AddressDao;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.ExchangePresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/9/5 21:35
 * Desc:
 */
public class ExchangePresenterImpl implements ExchangePresenter {

    private ExChangeView mView;

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
    public void order(String userName, String prdname, String jifen, String num, String addressid, String address, String phone, String id) {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .goodsOrder(userName,prdname,jifen,num,addressid,address,phone,UserConfig.getUserid())
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
                        mView.onSuccess(commonDao);
                    }
                });
    }

    @Override
    public void register(ExChangeView view) {

        mView = view;
    }

    @Override
    public void unregister(ExChangeView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
