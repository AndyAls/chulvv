package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.EventListDao;

/**
 * @author Andy
 * @date 2018/5/29 9:29
 * Desc:
 */
public interface TaskWorkingPresenter extends BasePresenter<TaskWorkingPresenter.TaskWorkingView> {

    void getEventList(int page);

    interface TaskWorkingView extends CommView{

       void onEventSuccess(EventListDao dao);
    }
}
