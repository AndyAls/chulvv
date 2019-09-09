package com.qlckh.chunlvv.impl;

import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.NewsMorePresenter;
import com.qlckh.chunlvv.usercase.MainUserCase;

import io.reactivex.Observable;

/**
 * @author Andy
 * @date 2018/8/17 10:45
 * Desc:
 */
public class NewsMorePresenterImpl implements NewsMorePresenter {

    private NewsMoreView mView;
    private MainUserCase mainUserCase;

    @Override
    public void getNewsList() {
        XLog.e("--");
        if (mainUserCase == null) {
            mainUserCase = new MainUserCase();
        }
        Observable<BannerDao> bannerImg = mainUserCase.getBannerImg();
        bannerImg.subscribe(new CommonObserver<BannerDao>() {
            @Override
            protected void onError(String errorMsg) {

                mView.showError(errorMsg);
            }

            @Override
            protected void onSuccess(BannerDao bannerDao) {
                    mView.onNewsSuccess(bannerDao);
            }

        });

    }

    @Override
    public void register(NewsMoreView view) {

        mView = view;

    }

    @Override
    public void unregister(NewsMoreView view) {
        if (mView != null) {
            mView = null;
        }
    }
}
