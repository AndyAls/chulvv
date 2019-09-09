package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;

/**
 * @author Andy
 * @date 2018/5/29 16:29
 * Desc:
 */
public interface MessageDetailPresenter extends BasePresenter<CommView> {

    void sure(String id,int state);
}
