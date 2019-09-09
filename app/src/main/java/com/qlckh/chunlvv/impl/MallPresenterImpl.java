package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.IBaseView;
import com.qlckh.chunlvv.dao.MallCatgrayDao;
import com.qlckh.chunlvv.dao.MallDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.MallPresenter;
import com.qlckh.chunlvv.user.UserConfig;

/**
 * @author Andy
 * @date 2018/8/17 17:30
 * Desc:
 */
public class MallPresenterImpl implements MallPresenter {

    private MallView mView;

    @Override
    public void getMallCatgray() {
        RxHttpUtils.createApi(ApiService.class)
                .getMallCatgray(UserConfig.getUserid())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<MallCatgrayDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(MallCatgrayDao catgrayDao) {
                        mView.onMallCatgray(catgrayDao);

                    }
                });
    }

    @Override
    public void getMall(String id) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getMall(id)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<MallDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                    mView.dissmissLoading();
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(MallDao dao) {
                        mView.dissmissLoading();
                        mView.onMallSuccess(dao);

                    }
                });

    }

    @Override
    public void register(MallView view) {
        mView = view;
    }

    @Override
    public void unregister(MallView view) {
        if (mView!=null){
            mView=null;
        }
    }
}
