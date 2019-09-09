package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.OrderDao;

/**
 * @author Andy
 * @date 2018/9/6 13:40
 * Desc:
 */
public interface ConfirmOrderPresenter extends BasePresenter<ConfirmOrderPresenter.ConfirmOrderView>{

    void getOrders(int flag);
    interface ConfirmOrderView extends CommView{

        void onOrdersSuccess(OrderDao dao);

    }
}
