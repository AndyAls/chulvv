package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.HomeDao;

/**
 * @author Andy
 * @date 2018/5/20 1:13
 * Desc:
 */
public interface SanitationPresenter extends BasePresenter<CompositeView> {

    void sanitationSubmit(HomeDao dao, int envScore, String address, String tel, String imgs,String weight);
}
