package com.qlckh.chunlvv.dao;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import org.w3c.dom.Text;

import java.sql.Blob;
import java.util.List;

/**
 * @author Andy
 * @date 2018/8/18 23:19
 * Desc:
 */

@Entity(tableName = "scorebean",indices = {@Index(value = {"id"},unique = true)})
public class ScoreBean {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String userId;
    private String fullname;
    private String fullId;
    private String userName;
    private int categoryScore;
    private int bucketScore;
    private int putScore;
    private int totalScore;
    private String address;
    private String fullItems;
    private String whatType;
    private String fullPhone;
    private List<String> imgs;
    private String weight;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFullItems() {
        return fullItems;
    }

    public void setFullItems(String fullItems) {
        this.fullItems = fullItems;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullId() {
        return fullId;
    }

    public void setFullId(String fullId) {
        this.fullId = fullId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCategoryScore() {
        return categoryScore;
    }

    public void setCategoryScore(int categoryScore) {
        this.categoryScore = categoryScore;
    }

    public int getBucketScore() {
        return bucketScore;
    }

    public void setBucketScore(int bucketScore) {
        this.bucketScore = bucketScore;
    }

    public int getPutScore() {
        return putScore;
    }

    public void setPutScore(int putScore) {
        this.putScore = putScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWhatType() {
        return whatType;
    }

    public void setWhatType(String whatType) {
        this.whatType = whatType;
    }

    public String getFullPhone() {
        return fullPhone;
    }

    public void setFullPhone(String fullPhone) {
        this.fullPhone = fullPhone;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
