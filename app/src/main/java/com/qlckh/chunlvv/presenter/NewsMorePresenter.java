package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.base.IBaseView;
import com.qlckh.chunlvv.dao.BannerDao;

/**
 * @author Andy
 * @date 2018/8/17 10:30
 * Desc:
 */
public interface NewsMorePresenter extends BasePresenter<NewsMorePresenter.NewsMoreView>{

    void getNewsList();

    interface NewsMoreView extends IBaseView{

        void onNewsSuccess(BannerDao dao);
    }
}
