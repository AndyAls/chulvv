package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.DeviceDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.DevicePresenter;

/**
 * @author Andy
 * @date 2018/9/7 16:46
 * Desc:
 */
public class DevicePresenterImpl implements DevicePresenter {

    private DeviceView mView;

    @Override
    public void getDevice() {
        mView.showLoading();
        RxHttpUtils.createApi(ApiService.class)
                .getDevice()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<DeviceDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.dissmissLoading();
                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(DeviceDao deviceDao) {

                        mView.dissmissLoading();
                        mView.onDeviceSuccess(deviceDao);
                    }
                });
    }

    @Override
    public void register(DeviceView view) {

        mView = view;
    }

    @Override
    public void unregister(DeviceView view) {
        if (mView!=null){
            mView=null;
        }

    }
}
