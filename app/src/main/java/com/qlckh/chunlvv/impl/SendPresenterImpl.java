package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.CuntryDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.SendPresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.usercase.CuntryListModel;

/**
 * @author Andy
 * @date 2018/5/31 9:38
 * Desc:
 */
public class SendPresenterImpl implements SendPresenter {

    private SendView mView;
    private CuntryListModel model;

    @Override
    public void sendSumbit(String gotoId, String title, String content) {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .sendSumbit(UserConfig.getUserid(),gotoId,title,content)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);

                    }

                    @Override
                    protected void onSuccess(CommonDao dao) {
                        mView.dissmissLoading();
                        mView.onSuccess(dao);

                    }
                });
    }

    @Override
    public void getCuntryList() {
        mView.showLoading();
        if (model==null){
            model = new CuntryListModel();
        }
        model.getCuntryList().subscribe(new CommonObserver<CunGuanDao>() {
            @Override
            protected void onError(String errorMsg) {
                mView.showError(errorMsg);
                mView.dissmissLoading();
            }

            @Override
            protected void onSuccess(CunGuanDao dao) {
                mView.onCuntrySuccess(dao);
                mView.dissmissLoading();

            }
        });

    }

    @Override
    public void register(SendView view) {

        mView = view;
    }

    @Override
    public void unregister(SendView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
