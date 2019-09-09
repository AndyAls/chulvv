package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.AddressDao;

/**
 * @author Andy
 * @date 2018/9/5 20:40
 * Desc:
 */
public interface ExchangePresenter extends BasePresenter<ExchangePresenter.ExChangeView>{

    void getAddress();
    void order(String userName,String prdname,String jifen,String num,String addressid,String address,String phone,String id);
    interface ExChangeView extends CommView{
        void onAddressSuccess(AddressDao dao);
    }

}
