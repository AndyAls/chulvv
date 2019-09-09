package com.qlckh.chunlvv.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Andy
 * @date 2018/5/19 19:18
 * Desc:
 */
public class HomeDao implements Parcelable {


    /**
     * id : 5
     * name :
     * company : 3-105
     * addtime : 1533892131
     * phone : 13130724171
     * cardid : 445454564545
     * img : 2018-08-10/5b6d562318d6d.png
     * fullname : 杨打定
     * topflag : 4
     * adduser : admin
     * items : 11,,32,33,37
     * sex : 男
     * pwd : e10adc3949ba59abbe56e057f20f883e
     * jifen : 88
     * huiid : 3
     * jicode : 0
     * dangid : 0
     * erimg : Uploads/QRcode/3-105.jpg
     * names : 124
     * address : 浙江省新昌县羽林街道孟家塘
     */

    private String id;
    private String name;
    private String company;
    private String addtime;
    private String phone;
    private String cardid;
    private String img;
    private String fullname;
    private String topflag;
    private String adduser;
    private String items;
    private String sex;
    private String pwd;
    private String jifen;
    private String huiid;
    private String jicode;
    private String dangid;
    private String erimg;
    private String names;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
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

    public String getTopflag() {
        return topflag;
    }

    public void setTopflag(String topflag) {
        this.topflag = topflag;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
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

    public String getJifen() {
        return jifen;
    }

    public void setJifen(String jifen) {
        this.jifen = jifen;
    }

    public String getHuiid() {
        return huiid;
    }

    public void setHuiid(String huiid) {
        this.huiid = huiid;
    }

    public String getJicode() {
        return jicode;
    }

    public void setJicode(String jicode) {
        this.jicode = jicode;
    }

    public String getDangid() {
        return dangid;
    }

    public void setDangid(String dangid) {
        this.dangid = dangid;
    }

    public String getErimg() {
        return erimg;
    }

    public void setErimg(String erimg) {
        this.erimg = erimg;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.company);
        dest.writeString(this.addtime);
        dest.writeString(this.phone);
        dest.writeString(this.cardid);
        dest.writeString(this.img);
        dest.writeString(this.fullname);
        dest.writeString(this.topflag);
        dest.writeString(this.adduser);
        dest.writeString(this.items);
        dest.writeString(this.sex);
        dest.writeString(this.pwd);
        dest.writeString(this.jifen);
        dest.writeString(this.huiid);
        dest.writeString(this.jicode);
        dest.writeString(this.dangid);
        dest.writeString(this.erimg);
        dest.writeString(this.names);
        dest.writeString(this.address);
    }

    public HomeDao() {
    }

    protected HomeDao(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.company = in.readString();
        this.addtime = in.readString();
        this.phone = in.readString();
        this.cardid = in.readString();
        this.img = in.readString();
        this.fullname = in.readString();
        this.topflag = in.readString();
        this.adduser = in.readString();
        this.items = in.readString();
        this.sex = in.readString();
        this.pwd = in.readString();
        this.jifen = in.readString();
        this.huiid = in.readString();
        this.jicode = in.readString();
        this.dangid = in.readString();
        this.erimg = in.readString();
        this.names = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<HomeDao> CREATOR = new Parcelable.Creator<HomeDao>() {
        @Override
        public HomeDao createFromParcel(Parcel source) {
            return new HomeDao(source);
        }

        @Override
        public HomeDao[] newArray(int size) {
            return new HomeDao[size];
        }
    };
}
