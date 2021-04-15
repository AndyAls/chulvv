package com.qlckh.chunlvv;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.qlckh.chunlvv.dao.ScoreBean;
import com.qlckh.chunlvv.dao.ScoreDao;

/**
 * @author Andy
 * @date 2018/8/23 13:25
 * Desc:
 */

@Database(entities = {ScoreBean.class},version = 1,exportSchema = false)
@TypeConverters(TagsConver.class)
public abstract class ScoreDB extends RoomDatabase {
    private final static String SCORE_DB_NAME="ScoreDB.db";
    private static volatile ScoreDB instance;

    public static ScoreDB getInstance(){

        if (instance==null){
            synchronized (ScoreDB.class){
                if (instance==null){
                    instance= Room.databaseBuilder(App.getAppContext(),ScoreDB.class,SCORE_DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract ScoreDao getScoreDao();
}
