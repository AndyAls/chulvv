package com.qlckh.chunlvv.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andy
 * @date 2018/9/4 14:25
 * Desc:
 */
public class RecycleDao implements Parcelable {

    /**
     * status : 1
     * msg : 成功
     * total : 848
     * row : [{"id":"1526","username":"","addtime":"1537924548","usercode":"416","imgpath":null,"ji_class":"2","ji_she":"2","ji_qing":"2","ji_bai":"0","ji_dui":"0","ji_duinum":"0.00","address":"1,7,8,9,10","tel":"空房","adduser":"13111111111","adduserid":"7","belongareaid":"0","whattype":"0","zong":"6","dianzicheng":null,"addresss":"湖北省鄂州市梧桐湖新区梧桐湖社区一期"},{"id":"1525","username":"尹章运","addtime":"1537924519","usercode":"104","imgpath":null,"ji_class":"2","ji_she":"2","ji_qing":"2","ji_bai":"0","ji_dui":"0","ji_duinum":"0.00","address":"1,7,8,9,10","tel":"15972556655","adduser":"13111111111","adduserid":"7","belongareaid":"0","whattype":"0","zong":"6","dianzicheng":null,"addresss":"湖北省鄂州市梧桐湖新区梧桐湖社区一期"},{"id":"1524","username":"管委会","addtime":"1537924503","usercode":"413","imgpath":null,"ji_class":"2","ji_she":"2","ji_qing":"2","ji_bai":"0","ji_dui":"0","ji_duinum":"0.00","address":"1,7,8,9,10","tel":"","adduser":"13111111111","adduserid":"7","belongareaid":"0","whattype":"0","zong":"6","dianzicheng":null,"addresss":"湖北省鄂州市梧桐湖新区梧桐湖社区一期"},{"id":"1523","username":"管委会","addtime":"1537924475","usercode":"412","imgpath":null,"ji_class":"2","ji_she":"2","ji_qing":"2","ji_bai":"0","ji_dui":"0","ji_duinum":"0.00","address":"1,7,8,9,10","tel":"","adduser":"13111111111","adduserid":"7","belongareaid":"0","whattype":"0","zong":"6","dianzicheng":null,"addresss":"湖北省鄂州市梧桐湖新区梧桐湖社区一期"}]
     */

    private int status;
    private String msg;
    private String total;
    private List<RecycleBean> row;

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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<RecycleBean> getRow() {
        return row;
    }

    public void setRow(List<RecycleBean> row) {
        this.row = row;
    }

    public static class RecycleBean implements Parcelable {
        /**
         * id : 1526
         * username :
         * addtime : 1537924548
         * usercode : 416
         * imgpath : null
         * ji_class : 2
         * ji_she : 2
         * ji_qing : 2
         * ji_bai : 0
         * ji_dui : 0
         * ji_duinum : 0.00
         * address : 1,7,8,9,10
         * tel : 空房
         * adduser : 13111111111
         * adduserid : 7
         * belongareaid : 0
         * whattype : 0
         * zong : 6
         * dianzicheng : null
         * addresss : 湖北省鄂州市梧桐湖新区梧桐湖社区一期
         */

        private String id;
        private String username;
        private String addtime;
        private String usercode;
        private String imgpath;
        private String ji_class;
        private String ji_she;
        private String ji_qing;
        private String ji_bai;
        private String ji_dui;
        private String ji_duinum;
        private String address;
        private String tel;
        private String adduser;
        private String adduserid;
        private String belongareaid;
        private String whattype;
        private String zong;
        private String dianzicheng;
        private String addresss;

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

        public String getUsercode() {
            return usercode;
        }

        public void setUsercode(String usercode) {
            this.usercode = usercode;
        }

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }

        public String getJi_class() {
            return ji_class;
        }

        public void setJi_class(String ji_class) {
            this.ji_class = ji_class;
        }

        public String getJi_she() {
            return ji_she;
        }

        public void setJi_she(String ji_she) {
            this.ji_she = ji_she;
        }

        public String getJi_qing() {
            return ji_qing;
        }

        public void setJi_qing(String ji_qing) {
            this.ji_qing = ji_qing;
        }

        public String getJi_bai() {
            return ji_bai;
        }

        public void setJi_bai(String ji_bai) {
            this.ji_bai = ji_bai;
        }

        public String getJi_dui() {
            return ji_dui;
        }

        public void setJi_dui(String ji_dui) {
            this.ji_dui = ji_dui;
        }

        public String getJi_duinum() {
            return ji_duinum;
        }

        public void setJi_duinum(String ji_duinum) {
            this.ji_duinum = ji_duinum;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getAdduser() {
            return adduser;
        }

        public void setAdduser(String adduser) {
            this.adduser = adduser;
        }

        public String getAdduserid() {
            return adduserid;
        }

        public void setAdduserid(String adduserid) {
            this.adduserid = adduserid;
        }

        public String getBelongareaid() {
            return belongareaid;
        }

        public void setBelongareaid(String belongareaid) {
            this.belongareaid = belongareaid;
        }

        public String getWhattype() {
            return whattype;
        }

        public void setWhattype(String whattype) {
            this.whattype = whattype;
        }

        public String getZong() {
            return zong;
        }

        public void setZong(String zong) {
            this.zong = zong;
        }

        public String getDianzicheng() {
            return dianzicheng;
        }

        public void setDianzicheng(String dianzicheng) {
            this.dianzicheng = dianzicheng;
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
            dest.writeString(this.username);
            dest.writeString(this.addtime);
            dest.writeString(this.usercode);
            dest.writeString(this.imgpath);
            dest.writeString(this.ji_class);
            dest.writeString(this.ji_she);
            dest.writeString(this.ji_qing);
            dest.writeString(this.ji_bai);
            dest.writeString(this.ji_dui);
            dest.writeString(this.ji_duinum);
            dest.writeString(this.address);
            dest.writeString(this.tel);
            dest.writeString(this.adduser);
            dest.writeString(this.adduserid);
            dest.writeString(this.belongareaid);
            dest.writeString(this.whattype);
            dest.writeString(this.zong);
            dest.writeString(this.dianzicheng);
            dest.writeString(this.addresss);
        }

        public RecycleBean() {
        }

        protected RecycleBean(Parcel in) {
            this.id = in.readString();
            this.username = in.readString();
            this.addtime = in.readString();
            this.usercode = in.readString();
            this.imgpath = in.readString();
            this.ji_class = in.readString();
            this.ji_she = in.readString();
            this.ji_qing = in.readString();
            this.ji_bai = in.readString();
            this.ji_dui = in.readString();
            this.ji_duinum = in.readString();
            this.address = in.readString();
            this.tel = in.readString();
            this.adduser = in.readString();
            this.adduserid = in.readString();
            this.belongareaid = in.readString();
            this.whattype = in.readString();
            this.zong = in.readString();
            this.dianzicheng = in.readString();
            this.addresss = in.readString();
        }

        public static final Creator<RecycleBean> CREATOR = new Creator<RecycleBean>() {
            @Override
            public RecycleBean createFromParcel(Parcel source) {
                return new RecycleBean(source);
            }

            @Override
            public RecycleBean[] newArray(int size) {
                return new RecycleBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.msg);
        dest.writeString(this.total);
        dest.writeList(this.row);
    }

    public RecycleDao() {
    }

    protected RecycleDao(Parcel in) {
        this.status = in.readInt();
        this.msg = in.readString();
        this.total = in.readString();
        this.row = new ArrayList<RecycleBean>();
        in.readList(this.row, RecycleBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<RecycleDao> CREATOR = new Parcelable.Creator<RecycleDao>() {
        @Override
        public RecycleDao createFromParcel(Parcel source) {
            return new RecycleDao(source);
        }

        @Override
        public RecycleDao[] newArray(int size) {
            return new RecycleDao[size];
        }
    };
}
