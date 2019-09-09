package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.HandDao;

/**
 * @author Andy
 * @date 2018/9/12 16:27
 * Desc:
 */
public interface RecycleHandPresenter extends BasePresenter<RecycleHandPresenter.RecycleHandView>{


    void getHand();
    void toHand(String id);

    interface RecycleHandView extends CommView{

        void onHandSuccess(HandDao dao);
        void toHandSuccess();
    }
}
