package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.OrderDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.CommView;
import com.qlckh.chunlvv.presenter.EvaluationPresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/9/6 16:43
 * Desc:
 */
public class EvaluationPresenterImpl implements EvaluationPresenter {

    private CommView mView;

    @Override
    public void evaluation(OrderDao.OrderBean bean,String xing, String content) {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .evaluation(UserConfig.getUserid(),xing,bean.getUsername(),content,bean.getId(),"")
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(CommonDao commonDao) {

                        mView.dissmissLoading();
                        mView.onSuccess(commonDao);
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
