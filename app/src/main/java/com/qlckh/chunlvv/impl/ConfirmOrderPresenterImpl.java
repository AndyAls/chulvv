package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.OrderDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.ConfirmOrderPresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/9/6 13:47
 * Desc:
 */
public class ConfirmOrderPresenterImpl implements ConfirmOrderPresenter {

    private ConfirmOrderView mView;

    @Override
    public void getOrders(int flag) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getOrders(UserConfig.getUserid(),flag,UserConfig.getType())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<OrderDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(OrderDao orderDao) {
                        mView.dissmissLoading();
                        mView.onOrdersSuccess(orderDao);
                    }
                });
    }

    @Override
    public void register(ConfirmOrderView view) {
        mView = view;
    }

    @Override
    public void unregister(ConfirmOrderView view) {
        if (mView!=null){
            mView=null;
        }

    }
}
