package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.CuntryDao;

/**
 * @author Andy
 * @date 2018/5/25 14:25
 * Desc:
 */
public interface SendPresenter extends BasePresenter<SendPresenter.SendView> {

    void sendSumbit(String gotoId,String title,String content);
    void getCuntryList();

    interface SendView extends CommView {

        void onCuntrySuccess(CunGuanDao dao);

    }
}
