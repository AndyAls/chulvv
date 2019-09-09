package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.CommView;
import com.qlckh.chunlvv.presenter.FeedPresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/5/22 11:50
 * Desc:
 */
public class FeedPresenterImpl implements FeedPresenter {

    private CommView mView;

    @Override
    public void sumbit(String content,String img) {

        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .feedSubmit(UserConfig.getUserid(), UserConfig.getType(),content,img)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(CommonDao dao) {

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
