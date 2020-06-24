package com.qlckh.chunlvv.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager

/**
 * @author Andy
 * @date 2018/5/15 11:40
 * Desc: 判断是手机还是其他
 */
object PhoneUtil {
    fun isPhone(activity: Activity): Boolean {
        val telephony = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephony != null && telephony.phoneType != TelephonyManager.PHONE_TYPE_NONE
    }

    @JvmStatic
    val isN5s: Boolean
        get() = "N5S" == Build.MODEL


    /**
     * 获取手机IMEI唯一标识
     *
     * @return
     */
    @JvmStatic
    fun getIMEI(context: Context): String {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.deviceId
    }
}