package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CategoryDao;
import com.qlckh.chunlvv.dao.ProcuteDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.RecycleProcutePresenter;

/**
 * @author Andy
 * @date 2018/9/4 22:02
 * Desc:
 */
public class RecycleProcutePresenterImpl implements RecycleProcutePresenter {

    private RecycleProcuteView mView;

    @Override
    public void getCategory() {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getCategory()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CategoryDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showError(errorMsg);
                        mView.dissmissLoading();
                    }

                    @Override
                    protected void onSuccess(CategoryDao categoryDao) {

                        mView.dissmissLoading();
                        mView.onCategorySuccess(categoryDao);
                    }
                });
    }

    @Override
    public void getProcute(String id) {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getProcute(id)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<ProcuteDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(ProcuteDao categoryDao) {

                        mView.dissmissLoading();
                        mView.onProcuteSuccess(categoryDao);
                    }
                });
    }

    @Override
    public void register(RecycleProcuteView view) {

        mView = view;
    }

    @Override
    public void unregister(RecycleProcuteView view) {
        if (mView!=null){
            mView=null;
        }

    }
}
