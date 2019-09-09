package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.HomeDao;

/**
 * @author Andy
 * @date 2018/5/19 22:15
 * Desc:
 */
public interface CompositePresenter extends BasePresenter<CompositeView> {

    void sumbit(HomeDao dao, int categoryScore, int bucketScore, int putScore, int totalScore, String address, String tel, String imgs,String weight);
    void addScan(String id);
}
