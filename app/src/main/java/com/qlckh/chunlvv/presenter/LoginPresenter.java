package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.base.IBaseView;
import com.qlckh.chunlvv.user.UseDo;

/**
 * @author Andy
 * @date 2018/5/15 20:14
 * Desc:
 */
public interface LoginPresenter extends BasePresenter<LoginPresenter.LoginView> {

    void login(String name,String pwd,int type);

    interface LoginView extends IBaseView {

        void showLoading();
        void dissmissLoading();
        void getUser(UseDo info);
    }
}
