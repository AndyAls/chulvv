package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.IBaseView;
import com.qlckh.chunlvv.dao.RecycleDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.RecyclePresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/9/4 15:05
 * Desc:
 */
public class RecyclePresenterImpl implements RecyclePresenter {

    private RecycleView mView;

    @Override
    public void getRecycle(int status, String whatareaid, String starttime, String endtime, int page) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getRecycle(status,whatareaid,starttime,endtime,page,15)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<RecycleDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();

                    }

                    @Override
                    protected void onSuccess(RecycleDao dao) {

                        mView.dissmissLoading();
                        mView.onRecycleViewSuccess(dao);
                    }
                });
    }

    @Override
    public void register(RecycleView view) {
        mView = view;
    }

    @Override
    public void unregister(RecycleView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
