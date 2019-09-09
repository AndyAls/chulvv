package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.HandDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.RecycleHandPresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/9/12 16:30
 * Desc:
 */
public class RecycleHandPresenterImpl implements RecycleHandPresenter {

    private RecycleHandView mView;

    @Override
    public void getHand() {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getHand(UserConfig.getUserid())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<HandDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(HandDao handDao) {

                        mView.dissmissLoading();
                        mView.onHandSuccess(handDao);
                    }
                });
    }

    @Override
    public void toHand(String id) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .toHand(id)
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
                        mView.toHandSuccess();
                    }
                });
    }

    @Override
    public void register(RecycleHandView view) {

        mView = view;
    }

    @Override
    public void unregister(RecycleHandView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
