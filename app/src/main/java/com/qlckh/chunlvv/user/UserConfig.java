package com.qlckh.chunlvv.user;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Andy
 * @date 2018/5/15 14:5/2
 * Desc:
 */
public class UserConfig {

    private static final String USER_SP_NAME = "USER_SP_NAME";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PWD = "USER_PWD";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String USER_ID = "USERUSER_ID";
    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String IS_AUTH = "IS_AUTH";
    public static final String SERVICE_URL = "SERVICE_URL";
    public static final String SAVA_FULLNAME = "sava_fullname";
    public static final String SAVA_USERNAME = "sava_username";
    public static final String SAVA_PHONE = "sava_phone";
    public static final String SAVA_CODE= "sava_code";
    private static SharedPreferences sp;

    public static UserInfo userInfo;

    public static UserRespDao.UserResp userResp;

    public static UserRespDao.UserResp getUserResp() {
        return userResp;
    }

    public static void setUserResp(UserRespDao.UserResp userResp) {
        UserConfig.userResp = userResp;
    }

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        UserConfig.userInfo = userInfo;
    }

    public static void init(Context context) {

        if (sp == null) {
            sp = context.getSharedPreferences(USER_SP_NAME, Context.MODE_PRIVATE);
        }

    }

    public static void savaUserName(String userName) {
        sp.edit().putString(USER_NAME, userName).apply();
    }

    public static void savaPwd(String pwd) {
        sp.edit().putString(USER_PWD, pwd).apply();
    }

    public static void savaType(int type) {
        sp.edit().putInt(USER_TYPE, type).apply();
    }

    public static void savaUserid(String id) {
        sp.edit().putString(USER_ID, id).apply();
    }


    public static String getUserName() {
        return sp.getString(USER_NAME, "");
    }

    public static String getPwd() {
        return sp.getString(USER_PWD, "");
    }

    public static int getType() {
        return sp.getInt(USER_TYPE, -1);
    }

    public static String getUserid() {
        return sp.getString(USER_ID, "");
    }

    public static void savaLogin(boolean isLogin) {
        sp.edit().putBoolean(IS_LOGIN, isLogin).apply();
    }

    public static boolean isLogin() {
        return sp.getBoolean(IS_LOGIN, false);
    }

    public static void savaAuth(boolean isAuth){
        sp.edit().putBoolean(IS_AUTH,isAuth).apply();
    }
    public static boolean isAuth(){
        return sp.getBoolean(IS_AUTH,false);
    }

    public static void savaServiceUrl(String url){

        sp.edit().putString(SERVICE_URL,"http://"+url+"/").apply();
    }

    public static String getServiceUrl(){
        return sp.getString(SERVICE_URL,"");
    }
    public static void savaFullname(String fullName){

        sp.edit().putString(SAVA_FULLNAME,fullName).apply();
    }
    public static String getSavaFullname(){

        return sp.getString(SAVA_FULLNAME,"");
    }

    public static void savaUsername(String userName){

        sp.edit().putString(SAVA_USERNAME,userName).apply();
    }
    public static String getSavaUsername(){

        return sp.getString(SAVA_USERNAME,"");
    }

    public static void savaPhone(String phone){

        sp.edit().putString(SAVA_PHONE,phone).apply();
    }
    public static String getSavaPhone(){

        return sp.getString(SAVA_PHONE,"");
    }

    public static void savaCode(String code){

        sp.edit().putString(SAVA_CODE,code).apply();
    }
    public static String getSavaCode(){

        return sp.getString(SAVA_CODE,"");
    }


    public static void reset() {
        savaLogin(false);
        savaPwd("");
        savaType(-1);
        savaUserName("");
        savaAuth(false);
        savaUserid("");
    }

}
