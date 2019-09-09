package com.qlckh.chunlvv.impl;

import android.content.Context;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.MainDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.WastePreseneter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.view.MainView;
import com.youth.banner.Banner;

import java.util.List;

/**
 * @author Andy
 * @date 2018/8/21 11:52
 * Desc:
 */
public class WastePreseneterImpl extends MainPresenterImpl implements WastePreseneter {

    private WasteView mView;

    @Override
    public void getRecyWaste(String whatareaid, String starttime, String endtime, int page) {

    }

    @Override
    public void getUnRecyWaste(String whatareaid, String starttime, String endtime, int page) {

    }

    @Override
    public void getCuntryList(String id) {
        RxHttpUtils.createApi(ApiService.class)
                .getCuntryList(UserConfig.getUserid(),UserConfig.getType())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CunGuanDao>() {
                    @Override
                    protected void onError(String errorMsg) {

                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(CunGuanDao cuntryDao) {

                        mView.onCuntrySuccess(cuntryDao);
                    }
                });
    }

    @Override
    public void register(MainView view) {
        super.register(view);
        mView = (WasteView) view;

    }

    @Override
    public void unregister(MainView view) {
        super.unregister(view);
        if (mView!=null){
            mView=null;
        }
    }
}
