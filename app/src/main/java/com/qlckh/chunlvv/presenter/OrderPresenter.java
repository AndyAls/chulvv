package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.CuntryDao;

/**
 * @author Andy
 * @date 2018/5/26 11:39
 * Desc:
 */
public interface OrderPresenter extends BasePresenter<OrderPresenter.OrderView> {

        void getCuntryList();
        void orderSumbit(String title,String content,String imgpath,String guanId,String baojieId);
    interface OrderView extends CommView{

        void onCuntrySuccess(CunGuanDao dao);

    }
}
