package com.qlckh.chunlvv.usercase;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.user.UserConfig;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author Andy
 * @date 2018/5/17 11:19
 * Desc:
 */
public class MainUserCase {

    public Observable<BannerDao> getBannerImg(){
        return RxHttpUtils.createApi(ApiService.class)
                .getBanner(UserConfig.getType(),UserConfig.getUserid())
                .compose(Transformer.<BannerDao>switchSchedulers());
    }
}
