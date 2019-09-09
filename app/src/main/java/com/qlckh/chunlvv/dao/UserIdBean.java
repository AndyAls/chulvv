package com.qlckh.chunlvv.dao;

import android.arch.persistence.room.ColumnInfo;

/**
 * @author Andy
 * @date 2018/8/23 15:44
 * Desc:
 */
public class UserIdBean {

    @ColumnInfo(name = "userId")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
