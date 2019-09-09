package com.qlckh.chunlvv.presenter;

import com.qlckh.chunlvv.base.BasePresenter;
import com.qlckh.chunlvv.dao.DeviceDao;

/**
 * @author Andy
 * @date 2018/9/7 16:40
 * Desc:
 */
public interface DevicePresenter extends BasePresenter<DevicePresenter.DeviceView>{


    void getDevice();
    interface DeviceView extends CommView{

        void onDeviceSuccess(DeviceDao deviceDao);
    }

}
