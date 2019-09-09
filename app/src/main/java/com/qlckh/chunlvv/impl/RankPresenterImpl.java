package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.RankDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.RankPresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/9/7 17:15
 * Desc:
 */
public class RankPresenterImpl implements RankPresenter {

    private RankView mView;

    @Override
    public void getRank(String cunId,String data) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getRank(cunId,UserConfig.getUserResp().getTopflag(),data)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<RankDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(RankDao rankDao) {

                        mView.dissmissLoading();
                        mView.onRankSuccess(rankDao);
                    }
                });
    }

    @Override
    public void getCountryList() {

        RxHttpUtils.createApi(ApiService.class)
                .getCuntryList(UserConfig.getUserid(),UserConfig.getType())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CunGuanDao>() {
                    @Override
                    protected void onError(String errorMsg) {

                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(CunGuanDao cuntryDao) {

                        mView.onCuntrySuccess(cuntryDao);
                    }
                });
    }

    @Override
    public void register(RankView view) {

        mView = view;
    }

    @Override
    public void unregister(RankView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
