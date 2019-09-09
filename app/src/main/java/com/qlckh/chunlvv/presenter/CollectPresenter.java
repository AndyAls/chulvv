package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.base.BaseView;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.HomeDao;
import com.qlckh.chunlvv.dao.ScoreBean;

/**
 * @author Andy
 * @date 2018/8/18 23:53
 * Desc:
 */
public interface CollectPresenter extends BasePresenter<CollectPresenter.CollectView>{

    void sumbit(ScoreBean bean,String imgs);
    void addScan(String id,ScoreBean bean);

    interface CollectView extends BaseView{
        void onSuccess(CommonDao dao);
        void onAddScanedSuccess();
    }
}
