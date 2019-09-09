package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.EvaluationDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.EvaluationDetailPresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/9/6 17:32
 * Desc:
 */
public class EvaluationDetailPresenterImpl implements EvaluationDetailPresenter {

    private EvaluationDetailView mView;

    @Override
    public void getEvaluationDetail() {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getEvaDetail(UserConfig.getUserid(),UserConfig.getType()+"",UserConfig.getUserResp().getFullname())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<EvaluationDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(EvaluationDao evaluationDao) {

                        mView.dissmissLoading();
                        mView.onEvaluationDetailSuccess(evaluationDao);
                    }
                });

    }

    @Override
    public void register(EvaluationDetailView view) {
        mView = view;
    }

    @Override
    public void unregister(EvaluationDetailView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
