package com.qlckh.chunlvv.usercase;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.CuntryDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.user.UserConfig;

import io.reactivex.Observable;

/**
 * @author Andy
 * @date 2018/5/31 10:16
 * Desc:
 */
public class CuntryListModel {

    public Observable<CunGuanDao> getCuntryList(){

        return RxHttpUtils.createApi(ApiService.class)
                .getCuntryList(UserConfig.getUserid(),UserConfig.getType())
                .compose(Transformer.switchSchedulers());
    }
}
