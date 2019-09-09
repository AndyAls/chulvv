package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.IBaseView;
import com.qlckh.chunlvv.dao.CommonDao;

/**
 * @author Andy
 * @date 2018/5/22 11:47
 * Desc:
 */
public interface CommView extends IBaseView {
    void onSuccess(CommonDao dao);
    void showLoading();
    void dissmissLoading();
}
