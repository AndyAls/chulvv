package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.CuntryDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.OrderPresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/5/26 13:12
 * Desc:
 */
public class OrderPresenterImpl implements OrderPresenter {

    private OrderView mView;

    @Override
    public void getCuntryList() {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getCuntryList(UserConfig.getUserid(),UserConfig.getType())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CunGuanDao>() {
                    @Override
                    protected void onError(String errorMsg) {

                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(CunGuanDao cuntryDao) {

                        mView.onCuntrySuccess(cuntryDao);
                        mView.dissmissLoading();
                    }
                });
    }

    @Override
    public void orderSumbit(String title, String content, String imgpath, String guanId, String baojieId) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .order(baojieId,"",UserConfig.getUserid(),title ,content,imgpath)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(CommonDao dao) {
                        mView.onSuccess(dao);
                        mView.dissmissLoading();

                    }
                });
    }

    @Override
    public void register(OrderView view) {

        mView = view;

    }

    @Override
    public void unregister(OrderView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
