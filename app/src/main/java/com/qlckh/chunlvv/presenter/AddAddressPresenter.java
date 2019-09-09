package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;

import java.util.Map;

/**
 * @author Andy
 * @date 2018/9/5 15:19
 * Desc:
 */
public interface AddAddressPresenter extends BasePresenter<AddAddressPresenter.AddAddressView> {

    void addAddress(Map<String, String> map);

    void editAddress(Map<String, String> map);

    interface AddAddressView extends CommView {

        void onAddAddressSuccess();

        void onEditAddressSuccess();
    }
}
