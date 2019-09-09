package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.PointDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.PointExchangePresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/9/10 16:30
 * Desc:
 */
public class PointExchangePresenterImpl implements PointExchangePresenter {

    private PointExchangeView mView;

    @Override
    public void getPoints(String flag) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getPoints(UserConfig.getUserid(),flag)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<PointDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(PointDao pointDao) {

                        mView.dissmissLoading();
                        mView.onPointSuccess(pointDao);
                    }
                });
    }

    @Override
    public void register(PointExchangeView view) {
        mView = view;
    }

    @Override
    public void unregister(PointExchangeView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
