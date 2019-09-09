package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.CunListDao;
import com.qlckh.chunlvv.dao.ScanListDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.PurifierManagerPersenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/6/12 13:14
 * Desc:
 */
public class PurifierManagerPersenterImpl implements PurifierManagerPersenter {

    private PurifierView mView;

    @Override
    public void getCunList() {

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

                       mView.onCunSuccess(cuntryDao);
                    }
                });

    }

    @Override
    public void scanList(String streetId, String startTime,String cunid) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                    .getScanList(startTime,UserConfig.getUserid(),UserConfig.getType())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<ScanListDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();

                    }

                    @Override
                    protected void onSuccess(ScanListDao dao) {
                        mView.dissmissLoading();
                        mView.onScanSuccess(dao);

                    }
                });
    }

    @Override
    public void register(PurifierView view) {

        mView = view;

    }

    @Override
    public void unregister(PurifierView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
