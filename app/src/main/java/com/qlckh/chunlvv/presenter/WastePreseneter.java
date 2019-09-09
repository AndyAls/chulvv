package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.WasteDao;
import com.qlckh.chunlvv.view.MainView;

/**
 * @author Andy
 * @date 2018/8/21 11:35
 * Desc:
 */
public interface WastePreseneter extends MainPresenter{

    void getRecyWaste(String whatareaid,String starttime,String endtime,int page);
    void getUnRecyWaste(String whatareaid,String starttime,String endtime,int page);
    void getCuntryList(String id);

    interface WasteView extends MainView{

        void onRecyWasteSuccess(WasteDao wasteDao);
        void onUnRecyWasteSuccess(WasteDao dao);
        void onCuntrySuccess(CunGuanDao dao);
    }
}
