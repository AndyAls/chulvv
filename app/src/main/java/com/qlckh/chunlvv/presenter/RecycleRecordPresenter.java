package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.RecordDao;

/**
 * @author Andy
 * @date 2018/9/10 11:32
 * Desc:
 */
public interface RecycleRecordPresenter extends BasePresenter<RecycleRecordPresenter.RecycleRecordView>{

    void getRecord(String cunid,String startData,String endData);
    void getCountryList();


    interface RecycleRecordView extends CommView{

       void onRecordSuccess(RecordDao recordDao);
        void onCuntrySuccess(CunGuanDao cuntryDao);
    }
}
