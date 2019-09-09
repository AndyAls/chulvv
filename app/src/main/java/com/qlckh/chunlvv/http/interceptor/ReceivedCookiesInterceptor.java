package com.qlckh.chunlvv.http.interceptor;


import com.qlckh.chunlvv.http.constant.SPKeys;
import com.qlckh.chunlvv.http.utils.SPUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

import static java.util.Calendar.getInstance;

/**
 * @author Andy
 * @date   2018/5/15 18:45
 * Desc:    ReceivedCookiesInterceptor.java
 */

public class ReceivedCookiesInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            SPUtils.put(SPKeys.COOKIE, cookies);
        }
        //获取服务器相应时间--用于计算倒计时的时间差
        if (!originalResponse.header("Date").isEmpty()) {
            long date = dateToStamp(originalResponse.header("Date"));
            SPUtils.put(SPKeys.DATE, date);
        }

        return originalResponse;
    }


    /**
     * 将时间转换为时间戳
     *
     * @param s date
     * @return long
     * @throws android.net.ParseException
     */
    public static long dateToStamp(String s) throws android.net.ParseException {
        //转换为标准时间对象
        Date date = new Date(s);
        Calendar calendar = getInstance();
        calendar.setTime(date);
        long mTimeInMillis = calendar.getTimeInMillis();
        return mTimeInMillis;
    }
}
