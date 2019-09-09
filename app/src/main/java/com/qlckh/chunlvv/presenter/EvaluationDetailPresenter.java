package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.EvaluationDao;

/**
 * @author Andy
 * @date 2018/9/6 17:25
 * Desc:
 */
public interface EvaluationDetailPresenter extends BasePresenter<EvaluationDetailPresenter.EvaluationDetailView>{

    void getEvaluationDetail();
    interface  EvaluationDetailView extends CommView{
       void onEvaluationDetailSuccess(EvaluationDao dao);
    }
}
