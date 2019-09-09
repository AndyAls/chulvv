package com.qlckh.chunlvv.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Andy
 * @date 2018/9/4 22:00
 * Desc:
 */
public class ProcuteDao {

    /**
     * msg : 成功
     * row : [{"addtime":"1529942400","adduser":"admin","classid":"1","flag":"2","id":"3","img":"152558574381018.png","price":"60.00","title":"电视","units":"台"}]
     * status : 1
     */

    private String msg;
    private int status;
    private List<ProcuteBean> row;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ProcuteBean> getRow() {
        return row;
    }

    public void setRow(List<ProcuteBean> row) {
        this.row = row;
    }

    public static class ProcuteBean implements Parcelable {
        /**
         * addtime : 1529942400
         * adduser : admin
         * classid : 1
         * flag : 2
         * id : 3
         * img : 152558574381018.png
         * price : 60.00
         * title : 电视
         * units : 台
         */

        private String addtime;
        private String adduser;
        private String classid;
        private String flag;
        private String id;
        private String img;
        private String price;
        private String title;
        private String units;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getAdduser() {
            return adduser;
        }

        public void setAdduser(String adduser) {
            this.adduser = adduser;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.addtime);
            dest.writeString(this.adduser);
            dest.writeString(this.classid);
            dest.writeString(this.flag);
            dest.writeString(this.id);
            dest.writeString(this.img);
            dest.writeString(this.price);
            dest.writeString(this.title);
            dest.writeString(this.units);
        }

        public ProcuteBean() {
        }

        protected ProcuteBean(Parcel in) {
            this.addtime = in.readString();
            this.adduser = in.readString();
            this.classid = in.readString();
            this.flag = in.readString();
            this.id = in.readString();
            this.img = in.readString();
            this.price = in.readString();
            this.title = in.readString();
            this.units = in.readString();
        }

        public static final Parcelable.Creator<ProcuteBean> CREATOR = new Parcelable.Creator<ProcuteBean>() {
            @Override
            public ProcuteBean createFromParcel(Parcel source) {
                return new ProcuteBean(source);
            }

            @Override
            public ProcuteBean[] newArray(int size) {
                return new ProcuteBean[size];
            }
        };
    }
}
