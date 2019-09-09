package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.ScoreBean;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.CollectPresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.usercase.ScanEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Andy
 * @date 2018/8/19 0:29
 * Desc:
 */
public class CollectPresenterImpl implements CollectPresenter {

    private CollectView mView;

    @Override
    public void sumbit(ScoreBean bean,String img) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .compositeSubmit(bean.getFullname(),bean.getFullId(), bean.getUserName(),bean.getUserId(),
                        bean.getCategoryScore(),bean.getBucketScore(),bean.getPutScore(),bean.getFullItems(),bean.getTotalScore(),bean.getWhatType()
                        ,bean.getFullPhone(),img,bean.getWeight())
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
    public void addScan(String id,ScoreBean bean) {
        RxHttpUtils.createApi(ApiService.class)
                .addScan(id,bean.getUserId())
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
    public void register(CollectView view) {

        mView = view;
    }

    @Override
    public void unregister(CollectView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
