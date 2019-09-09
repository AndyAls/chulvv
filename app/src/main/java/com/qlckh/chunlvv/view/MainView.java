package com.qlckh.chunlvv.view;

import com.qlckh.chunlvv.base.IBaseView;
import com.qlckh.chunlvv.dao.BannerDao;

/**
 * @author Andy
 * @date 2018/5/18 21:12
 * Desc:
 */
public interface MainView extends IBaseView {
    void onSuccess(BannerDao dao);
}
