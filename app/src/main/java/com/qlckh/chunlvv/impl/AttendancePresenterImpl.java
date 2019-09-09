package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.SignDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.AttendancePresenter;
import com.qlckh.chunlvv.view.LoadingView;
import com.qlckh.chunlvv.view.MainView;

import io.reactivex.functions.Consumer;

/**
 * @author Andy
 * @date 2018/5/18 11:55
 * Desc:
 */
public class AttendancePresenterImpl extends MainPresenterImpl implements AttendancePresenter {

    private AttendanceView mView;

    @Override
    public void sign(int userId, int type, String address) {
        RxHttpUtils.createApi(ApiService.class)
                .signin(userId,type,address)
                .compose(Transformer.<CommonDao>switchSchedulers())
                .subscribe(new CommonObserver<CommonDao>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(CommonDao commonDao) {

                        try {
                            if (type==0){
                                mView.onSiginSuccess(commonDao,"上班签到成功");
                            }
                            if (type==1){
                                mView.onSiginSuccess(commonDao,"下班签到成功");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public void getSign(String id, String addTime) {
        mView.showloading();
        RxHttpUtils.createApi(ApiService.class)
                .getSigin(id,addTime)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<SignDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        LoadingView.cancelLoading();
                    }

                    @Override
                    protected void onSuccess(SignDao signDao) {
                        LoadingView.cancelLoading();
                        mView.onGetSignSuccess(signDao);

                    }
                });
    }

    @Override
    public void register(MainView view) {

        super.register(view);
         mView = (AttendanceView) view;
    }

    @Override
    public void unregister(MainView view) {

        super.unregister(view);
        if (mView!=null){
            mView=null;
        }
    }
}
