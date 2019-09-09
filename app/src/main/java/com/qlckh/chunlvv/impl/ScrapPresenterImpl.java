package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.Comm2Dao;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.HomeDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.CommView;
import com.qlckh.chunlvv.presenter.ScrapPresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/5/22 13:52
 * Desc:
 */
public class ScrapPresenterImpl implements ScrapPresenter {

    private CommView mView;

    @Override
    public void scrapSubmit(HomeDao dao, String content, String address, String img) {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .scrapSubmit(UserConfig.getUserid(),dao.getFullname(),img,content,address)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {

                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(CommonDao dao) {

                        mView.dissmissLoading();
                        mView.onSuccess(dao);
                    }
                });

    }

    @Override
    public void register(CommView view) {

        mView = view;
    }

    @Override
    public void unregister(CommView view) {
        if (mView!=null){
            mView=null;
        }

    }
}
