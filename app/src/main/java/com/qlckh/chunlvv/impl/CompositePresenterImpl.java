package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.Comm2Dao;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.HomeDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.CompositePresenter;
import com.qlckh.chunlvv.presenter.CompositeView;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.usercase.ScanEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Andy
 * @date 2018/5/19 22:22
 * Desc:
 */
public class CompositePresenterImpl implements CompositePresenter {

    private CompositeView mView;

    @Override
    public void sumbit(HomeDao dao, int categoryScore, int bucketScore, int putScore, int totalScore, String address, String tel, String imgs
    ,String weight) {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .compositeSubmit(dao.getFullname(),dao.getId(),UserConfig.getUserName(),UserConfig.getUserid(),
                        categoryScore,bucketScore,putScore,address,totalScore,"0",dao.getPhone(),imgs,weight)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(CommonDao comm2Dao) {
                        mView.onSuccess(comm2Dao);
                        mView.dissmissLoading();

                    }
                });
    }

    @Override
    public void addScan(String id) {
        RxHttpUtils.createApi(ApiService.class)
                .addScan(id,UserConfig.getUserid())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(CommonDao dao) {
                        EventBus.getDefault().post(new ScanEvent());
                    mView.onAddScanedSuccess();
                    }
                });
    }

    @Override
    public void register(CompositeView view) {
        mView = view;

    }

    @Override
    public void unregister(CompositeView view) {
        if (mView!=null){
            mView=null;
        }

    }
}
