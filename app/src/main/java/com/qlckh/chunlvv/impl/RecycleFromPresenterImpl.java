package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.CommView;
import com.qlckh.chunlvv.presenter.RecycleFromPresenter;
import com.qlckh.chunlvv.user.UserConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andy
 * @date 2018/9/5 11:21
 * Desc:
 */
public class RecycleFromPresenterImpl implements RecycleFromPresenter {

    private CommView mView;

    @Override
    public void recycleSumbit(String title, String content) {

        mView.showLoading();
        String items = UserConfig.getUserResp().getItems();
        String[] split = items.split(",");
        if (split.length<5){
            return;
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("username", UserConfig.getUserResp().getFullname());
        map.put("phone", UserConfig.getUserResp().getPhone());
        map.put("title", title);
        map.put("cid",split[0]);
        map.put("sid",split[1]);
        map.put("xid",split[2]);
        map.put("jid",split[3]);
        map.put("cunid",split[4]);
        map.put("content", content);
        map.put("address", UserConfig.getUserResp().getAddresss());
        map.put("id", UserConfig.getUserid());
        RxHttpUtils.createApi(ApiService.class)
                .recycleSumbit(map)
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
