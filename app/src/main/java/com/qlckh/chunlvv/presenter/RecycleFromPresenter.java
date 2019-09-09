package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;

/**
 * @author Andy
 * @date 2018/9/5 11:18
 * Desc:
 */
public interface RecycleFromPresenter extends BasePresenter<CommView>{

    void recycleSumbit(String title,String content);
}
