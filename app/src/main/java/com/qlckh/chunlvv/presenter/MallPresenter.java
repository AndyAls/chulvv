package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.base.BaseView;
import com.qlckh.chunlvv.base.IBaseView;
import com.qlckh.chunlvv.dao.MallCatgrayDao;
import com.qlckh.chunlvv.dao.MallDao;

/**
 * @author Andy
 * @date 2018/8/17 17:09
 * Desc:
 */
public interface MallPresenter extends BasePresenter<MallPresenter.MallView>{


    void getMallCatgray();
    void getMall(String id);

    interface MallView extends BaseView{
        void onMallSuccess(MallDao dao);
        void onMallCatgray(MallCatgrayDao catgrayDao);

    }
}
