package com.qlckh.chunlvv.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Andy
 * @date 2018/5/15 14:45
 * Desc: 登录用户信息
 */
public class UserInfo implements Parcelable {


    /**
     * id : 5
     * username : 时间
     * addtime : 1533537849
     * address : 11,31,32,33,35
     * phone : 13819560726
     * card : 21138219950308104x
     * img : 2018-08-06/5b67ee391d11f.jpg
     * fullname : 蒙多
     * adduser : admin
     * sex : 女
     * pwd : e10adc3949ba59abbe56e057f20f883e
     * user_type : 1
     * district : 新闫村
     * pingallscore : 0
     * pingusercount : 0
     */

    private String id;
    private String username;
    private String addtime;
    private String address;
    private String phone;
    private String card;
    private String img;
    private String fullname;
    private String adduser;
    private String sex;
    private String pwd;
    private String user_type;
    private String district;
    private String pingallscore;
    private String pingusercount;
    private String full;
    private String member;

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPingallscore() {
        return pingallscore;
    }

    public void setPingallscore(String pingallscore) {
        this.pingallscore = pingallscore;
    }

    public String getPingusercount() {
        return pingusercount;
    }

    public void setPingusercount(String pingusercount) {
        this.pingusercount = pingusercount;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.addtime);
        dest.writeString(this.address);
        dest.writeString(this.phone);
        dest.writeString(this.card);
        dest.writeString(this.img);
        dest.writeString(this.fullname);
        dest.writeString(this.adduser);
        dest.writeString(this.sex);
        dest.writeString(this.pwd);
        dest.writeString(this.user_type);
        dest.writeString(this.district);
        dest.writeString(this.pingallscore);
        dest.writeString(this.pingusercount);
        dest.writeString(this.full);
        dest.writeString(this.member);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.addtime = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
        this.card = in.readString();
        this.img = in.readString();
        this.fullname = in.readString();
        this.adduser = in.readString();
        this.sex = in.readString();
        this.pwd = in.readString();
        this.user_type = in.readString();
        this.district = in.readString();
        this.pingallscore = in.readString();
        this.pingusercount = in.readString();
        this.full = in.readString();
        this.member = in.readString();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
