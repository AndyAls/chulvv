package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.CunListDao;
import com.qlckh.chunlvv.dao.ScanListDao;

/**
 * @author Andy
 * @date 2018/6/12 10:52
 * Desc:
 */
public interface PurifierManagerPersenter extends BasePresenter<PurifierManagerPersenter.PurifierView> {

    void getCunList();
    void scanList(String streetId,String startTime,String cunId);

    interface PurifierView extends CommView{

        void onCunSuccess(CunGuanDao dao);
        void onScanSuccess(ScanListDao dao);
    }

}
