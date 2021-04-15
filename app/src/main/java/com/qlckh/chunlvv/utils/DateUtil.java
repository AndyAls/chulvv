package com.qlckh.chunlvv.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间、日期转换工具
 * Created by Dream on 2018/4/23.
 */

public class DateUtil {
    /**
     * 获取当前时间
     *
     * @return 年月日时分秒字符串
     */
    public static String getCurrentTime() {
        Time time = new Time("GMT+8");
        time.setToNow();
        String year = "" + time.year;
        int imonth = time.month + 1;
        String month = imonth > 9 ? "" + imonth : "0" + imonth;
        String day = time.monthDay > 9 ? "" + time.monthDay : "0"
                + time.monthDay;
        String hour = (time.hour + 8) > 9 ? "" + (time.hour + 8) : "0"
                + (time.hour + 8);
        String minute = time.minute > 9 ? "" + time.minute : "0" + time.minute;
        String sec = time.second > 9 ? "" + time.second : "0" + time.second;
        String currentTime = year + month + day + hour + minute + sec;
        return currentTime;
    }

    //获取 日期/时/分/秒
    @SuppressLint("SimpleDateFormat")
    public static String getDateTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取 日期/时/分
    @SuppressLint("SimpleDateFormat")
    public static String getHourMinute(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取 日期年月日
    @SuppressLint("SimpleDateFormat")
    public static String getYearMonthDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    //获取日期年月
    public static String getYearMonth(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取日期年月
    public static String getYearMonth2(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取日期的年
    public static String getYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String date = sdf.format(new Date());
        return date;
    }
    //获取月日期的年
    public static String getMonthDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        String date = sdf.format(new Date());
        return date;
    }
    //获取  时/分
    public static String getTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取当前时间过去一个星期的时间
    public static String sevenTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - 7);
        Date d = c.getTime();
        String day = format.format(d);
        return day;
    }

    //判断是否为同一年的日期
    public static  String showTime(String data){
        String dateTime = data.substring(0,4);
        String nowTime = getYear().substring(0,4);
        if (dateTime.equals(nowTime)){
            return data.substring(5,16);
        }else{
            return data.substring(0,16);
        }
    }
    //判断是否为同一年的日期
    public static  String showDayTime(String data){
        String dateTime = data.substring(0,4);
        String nowTime = getYear().substring(0,4);
        if (dateTime.equals(nowTime)){
            String jinTime = data.substring(5,10);
            if (getMonthDay().equals(jinTime)){
                return data.substring(11,16);
            }else{
                return data.substring(5,16);
            }
        }else{
            return data.substring(0,16);
        }
    }
}
