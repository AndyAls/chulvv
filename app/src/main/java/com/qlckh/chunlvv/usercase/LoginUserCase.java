package com.qlckh.chunlvv.usercase;

import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.user.UseDo;

import io.reactivex.Observable;

/**
 * @author Andy
 * @date 2018/5/15 20:22
 * Desc:
 */
public class LoginUserCase {

    public Observable<UseDo> login(String name, String pwd, int type){
        return RxHttpUtils.createApi(ApiService.class)
                .login(name, pwd)
                .compose(Transformer.<UseDo>switchSchedulers());
    }
}
