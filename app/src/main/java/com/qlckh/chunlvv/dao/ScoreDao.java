package com.qlckh.chunlvv.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author Andy
 * @date 2018/8/23 13:21
 * Desc:
 */

@Dao
public interface ScoreDao {

    @Query("Select * from scorebean order by id desc")
    List<ScoreBean> queryList();
    @Query("select userId from scorebean")
    List<UserIdBean> queryUserId();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ScoreBean bean);
    @Delete
    void delect(ScoreBean bean);
}
