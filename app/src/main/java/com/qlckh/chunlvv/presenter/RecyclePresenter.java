package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.RecycleDao;

/**
 * @author Andy
 * @date 2018/9/4 14:20
 * Desc:
 */
public interface RecyclePresenter extends BasePresenter<RecyclePresenter.RecycleView>{

    void getRecycle(int status,String whatareaid,String starttime,String endtime,int page);


    interface RecycleView extends CommView{
        void onRecycleViewSuccess(RecycleDao dao);
    }
}
