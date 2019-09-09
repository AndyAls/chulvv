package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.IBaseView;
import com.qlckh.chunlvv.dao.Comm2Dao;
import com.qlckh.chunlvv.dao.CommonDao;

/**
 * @author Andy
 * @date 2018/5/20 1:19
 * Desc:
 */
public interface CompositeView extends IBaseView {
    void showLoading();
    void dissmissLoading();
    void onSuccess(CommonDao dao);
    void onAddScanedSuccess();
}
