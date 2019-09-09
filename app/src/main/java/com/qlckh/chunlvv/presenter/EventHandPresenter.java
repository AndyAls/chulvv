package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;

/**
 * @author Andy
 * @date 2018/5/29 16:57
 * Desc:
 */
public interface EventHandPresenter extends BasePresenter<CommView> {

    void handEvent(int id,int state,String content,String imgPath);
}
