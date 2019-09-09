package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.OrderDao;

/**
 * @author Andy
 * @date 2018/9/6 16:36
 * Desc:
 */
public interface EvaluationPresenter extends BasePresenter<CommView>{

    void evaluation(OrderDao.OrderBean bean,String xing,String content);
}
