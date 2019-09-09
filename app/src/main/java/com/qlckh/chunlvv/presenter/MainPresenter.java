package com.qlckh.chunlvv.presenter;

import android.content.Context;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.dao.MainDao;
import com.qlckh.chunlvv.view.MainView;
import com.youth.banner.Banner;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/17 11:12
 * Desc:
 */
public interface MainPresenter extends BasePresenter<MainView> {

    void getBanner();

    List<MainDao> getDatas();
    List<MainDao> getClassifyDatas();
    List<MainDao> getMeDatas();
    void getPersonInfo(String id);


    void showBanner(Context context, List<BannerDao.ImgBean> imgs, Banner banner);

}
