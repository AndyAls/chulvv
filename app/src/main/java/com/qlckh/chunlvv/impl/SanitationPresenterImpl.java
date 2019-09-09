package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.dao.Comm2Dao;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.HomeDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.CompositeView;
import com.qlckh.chunlvv.presenter.SanitationPresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/5/20 1:17
 * Desc:
 */
public class SanitationPresenterImpl implements SanitationPresenter {

    private CompositeView mView;
    private static final String TAG = "test";

    @Override
    public void sanitationSubmit(HomeDao dao, int envScore, String address, String tel, String imgs,String weight) {
        XLog.e(TAG,imgs);
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .sanitationSubmit(dao.getFullname(),dao.getId(),UserConfig.getUserName(),UserConfig.getUserid(),envScore,address,envScore,
                        "1",dao.getPhone(),imgs,weight)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(CommonDao dao) {
                        mView.dissmissLoading();
                        mView.onSuccess(dao);

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
