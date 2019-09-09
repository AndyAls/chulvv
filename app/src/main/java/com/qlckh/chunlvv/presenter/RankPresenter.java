package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.RankDao;

/**
 * @author Andy
 * @date 2018/9/7 17:08
 * Desc:
 */
public interface RankPresenter extends BasePresenter<RankPresenter.RankView>{

    void getRank(String cunId,String data);
    void getCountryList();
    interface RankView extends CommView{
        void onRankSuccess(RankDao dao);
        void onCuntrySuccess(CunGuanDao cuntryDao);
    }
}
