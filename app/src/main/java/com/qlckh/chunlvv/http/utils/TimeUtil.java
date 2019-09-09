package com.qlckh.chunlvv.http.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * CreateTime: 2016-07-23上午10:39
 * Author: wjkjinke00@126.com
 * Description:
 */
public class TimeUtil {
    public static final String M_D = "MM-dd";
    public static final String Y_M_D = "yyyy-MM-dd";
    public static final String M_D_H = "MM-dd HH:mm";
    public static final String Y_M_D_2 = "yyyy.MM.dd";
    public static final String Y_M_D_3 = "yyyy年MM月dd日";
    public static final String Y_M_D_W = "yyyy-MM-dd EEEE";
    //24小时制
    public static final String Y_M_D_H_M_S_24 = "yyyy-MM-dd HH:mm:ss";
    public static final String Y_M_D_H_M_24_W = "yyyy-MM-dd HH:mm EEEE";
    public static final String Y_M_D_H_M_24 = "yyyy-MM-dd HH:mm";

    //12小时制
    public static final String Y_M_D_H_M_S_12 = "yyyy-MM-dd hh:mm:ss";

    private TimeUtil() {
    }

    public static String formatTime(String millis, String type) {
        try {
            long l = Long.parseLong(millis);
            return formatTime(l, type);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatTime(long millis, String type) {
        if (millis < 10000000000L) {
            millis = millis * 1000;
        }
        String format = formatTime(new Date(millis), type);
        return format;
    }

    public static String formatTime(Date date, String type) {
        SimpleDateFormat formater = new SimpleDateFormat(type);
        String format = formater.format(date);
        return format;
    }

    public static long parseTime(String time, String type) {
        SimpleDateFormat formater = new SimpleDateFormat(type);
        try {
            Date parse = formater.parse(time);
            long millis = parse.getTime();
            return millis;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monday = calendar.get(Calendar.MONTH);
        calendar.set(year, monday, 1, 0, 0, 0);
        long time = calendar.getTimeInMillis();
        return time;
    }

    public static long getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monday = calendar.get(Calendar.MONTH);
        calendar.set(year, monday + 1, 0, 23, 59, 59);//日期设为0就是当月的最后一天
        long time = calendar.getTimeInMillis();
        return time;
    }

    public static boolean isSameDay(long time1, long time2) {
        String format1 = formatTime(time1, Y_M_D);
        String format2 = formatTime(time2, Y_M_D);
        return format1.equals(format2);
    }

    //根据日期取得星期几  
    public static String getWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(date);
        return week;
    }


}
