package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.CategoryDao;
import com.qlckh.chunlvv.dao.ProcuteDao;

/**
 * @author Andy
 * @date 2018/9/4 21:56
 * Desc:
 */
public interface RecycleProcutePresenter extends BasePresenter<RecycleProcutePresenter.RecycleProcuteView>{

    void getCategory();
    void getProcute(String id);
   interface RecycleProcuteView extends CommView{
       void onCategorySuccess(CategoryDao dao);
       void onProcuteSuccess(ProcuteDao procuteDao);
   }
}
