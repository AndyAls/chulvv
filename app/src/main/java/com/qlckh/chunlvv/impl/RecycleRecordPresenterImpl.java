package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.RecordDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.RecycleRecordPresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/9/10 11:37
 * Desc:
 */
public class RecycleRecordPresenterImpl implements RecycleRecordPresenter {

    private RecycleRecordView mView;

    @Override
    public void getRecord(String cunid, String startData, String endData) {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getRecord(UserConfig.getUserid(),startData,endData)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<RecordDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(RecordDao recordDao) {

                        mView.dissmissLoading();
                        mView.onRecordSuccess(recordDao);
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
    public void register(RecycleRecordView view) {

        mView = view;
    }

    @Override
    public void unregister(RecycleRecordView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
