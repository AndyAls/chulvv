package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.base.BaseView;
import com.qlckh.chunlvv.dao.AddressDao;

/**
 * @author Andy
 * @date 2018/8/24 21:23
 * Desc:
 */
public interface AddressPresenter extends BasePresenter<AddressPresenter.AddressView>{

    void getAddress();
    void selectAddress(String id);
    void delAddress(String id);

    interface AddressView extends CommView{

        void onAddressSuccess(AddressDao dao);
        void onSelectAddressSuccess();
        void onDelAddressSuccess();
    }
}
