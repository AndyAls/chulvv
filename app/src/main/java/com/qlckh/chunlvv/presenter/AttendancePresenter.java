package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.SignDao;
import com.qlckh.chunlvv.view.MainView;

/**
 * @author Andy
 * @date 2018/5/18 11:47
 * Desc:
 */
public interface AttendancePresenter extends MainPresenter{

    void sign(int userId,int  address,String type);
    void getSign(String id,String addTime);

     interface AttendanceView extends MainView {

         void onSiginSuccess(CommonDao dao, String msg);
         void onGetSignSuccess(SignDao dao);
         void showloading();

    }
}
