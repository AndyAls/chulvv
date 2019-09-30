package com.qlckh.chunlvv;

import android.support.test.runner.AndroidJUnit4;

import com.qlckh.chunlvv.dao.ScoreBean;
import com.qlckh.chunlvv.dao.ScoreDao;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.user.UserConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import retrofit2.http.Body;

import static junit.framework.Assert.assertEquals;

/**
 * @author Andy
 * @date 2018/8/23 13:53
 * Desc:
 */

@RunWith(AndroidJUnit4.class)
public class DbTest {


    @Before
    public void insert(){
        ScoreBean bean = new ScoreBean();
        bean.setAddress("地址");
        bean.setBucketScore(10);
        bean.setCategoryScore(10);
        bean.setFullId("1");
        bean.setFullname("李帅");
        bean.setImgs(null);
        bean.setFullPhone("1825813");
        bean.setUserId(UserConfig.getUserid());
        bean.setUserName(UserConfig.getUserName());
        bean.setPutScore(10);
        bean.setWeight(1000+"");
        bean.setFullItems("items");
        bean.setTotalScore(30);
        bean.setWhatType("0");
        bean.setTime(TimeUtil.formatTime(System.currentTimeMillis(), TimeUtil.Y_M_D_H_M_24));
        ScoreDB.getInstance().getScoreDao().insert(bean);
        ScoreDB.getInstance().getScoreDao().delect(bean);
    }

    @Test
    public void test(){

        List<ScoreBean> scoreBeanList = ScoreDB.getInstance().getScoreDao().queryList();
        for (int i = 0; i < scoreBeanList.size(); i++) {
            ScoreDB.getInstance().getScoreDao().delect(scoreBeanList.get(i));
        }
        assertEquals(0,scoreBeanList.size());

    }

}
