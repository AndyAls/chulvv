package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.EventListDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.TaskWorkingPresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/5/29 9:37
 * Desc:
 */
public class TaskWorkingPresenterImpl implements TaskWorkingPresenter {

    private TaskWorkingView mView;

    @Override
    public void getEventList(int page) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getEventList(UserConfig.getUserid(),UserConfig.getType())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<EventListDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();

                    }

                    @Override
                    protected void onSuccess(EventListDao dao) {

                        mView.dissmissLoading();
                        mView.onEventSuccess(dao);
                    }
                });


    }

    @Override
    public void register(TaskWorkingView view) {

        mView = view;
    }

    @Override
    public void unregister(TaskWorkingView view) {

        if (mView!=null){
            mView=null;
        }
    }
}
