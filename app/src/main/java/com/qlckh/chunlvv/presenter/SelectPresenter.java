package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.BaojieDao;
import com.qlckh.chunlvv.dao.GuanDao;

/**
 * @author Andy
 * @date 2018/5/26 13:43
 * Desc:
 */
public interface SelectPresenter extends BasePresenter<SelectPresenter.SelectView> {

    void getGuanList(String address,int position);
    void getBaojie(String address,int position);
    interface SelectView extends CommView {

       void onGuanSuccess(GuanDao guanDao, int pos);
       void onBaojieSuccess(BaojieDao baojieDao, int pos);
    }
}
