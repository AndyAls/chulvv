package com.qlckh.chunlvv.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Andy
 * @date 2018/8/20 21:46
 * Desc:
 */
public class UserRespDao  {

    /**
     * status : 1
     * msg : 成功
     * row : {"id":"108","name":null,"company":"204","addtime":"1534403148","phone":"18972968666","cardid":"","img":null,"fullname":"朱建红","topflag":"4","adduser":"15355031875","items":"1,7,8,9,10","sex":"男","pwd":"e10adc3949ba59abbe56e057f20f883e","jifen":"0","huiid":"7","jicode":"0","dangid":"0","erimg":"Uploads/QRcode/204.jpg","com":"","card":null,"weight":null,"addresss":"湖北省鄂州市梧桐湖新区梧桐湖社区一期"}
     */

    private int status;
    private String msg;
    private UserResp row;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserResp getRow() {
        return row;
    }

    public void setRow(UserResp row) {
        this.row = row;
    }

    public static class UserResp implements Parcelable {
        /**
         * id : 108
         * name : null
         * company : 204
         * addtime : 1534403148
         * phone : 18972968666
         * cardid :
         * img : null
         * fullname : 朱建红
         * topflag : 4
         * adduser : 15355031875
         * items : 1,7,8,9,10
         * sex : 男
         * pwd : e10adc3949ba59abbe56e057f20f883e
         * jifen : 0
         * huiid : 7
         * jicode : 0
         * dangid : 0
         * erimg : Uploads/QRcode/204.jpg
         * com :
         * card : null
         * weight : null
         * addresss : 湖北省鄂州市梧桐湖新区梧桐湖社区一期
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
        private String com;
        private String card;
        private String weight;
        private String addresss;

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

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getAddresss() {
            return addresss;
        }

        public void setAddresss(String addresss) {
            this.addresss = addresss;
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
            dest.writeString(this.com);
            dest.writeString(this.card);
            dest.writeString(this.weight);
            dest.writeString(this.addresss);
        }

        public UserResp() {
        }

        protected UserResp(Parcel in) {
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
            this.com = in.readString();
            this.card = in.readString();
            this.weight = in.readString();
            this.addresss = in.readString();
        }

        public static final Parcelable.Creator<UserResp> CREATOR = new Parcelable.Creator<UserResp>() {
            @Override
            public UserResp createFromParcel(Parcel source) {
                return new UserResp(source);
            }

            @Override
            public UserResp[] newArray(int size) {
                return new UserResp[size];
            }
        };
    }
}
