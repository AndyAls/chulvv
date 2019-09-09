package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.PointDao;

/**
 * @author Andy
 * @date 2018/9/10 16:26
 * Desc:
 */
public interface PointExchangePresenter extends BasePresenter<PointExchangePresenter.PointExchangeView>{

    void getPoints(String flag);

    interface PointExchangeView extends CommView{

        void onPointSuccess(PointDao pointDao);
    }
}
