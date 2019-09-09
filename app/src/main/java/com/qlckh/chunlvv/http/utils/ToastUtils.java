package com.qlckh.chunlvv.http.utils;

import android.widget.Toast;

import com.qlckh.chunlvv.http.RxHttpUtils;

/**
 * @author Andy
 * @date   2018/5/15 18:52
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    ToastUtils.java
 */

public class ToastUtils {

    private static Toast mToast;

    /**
     * Toast提示
     *
     * @param msg 提示内容
     */
    public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(RxHttpUtils.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
