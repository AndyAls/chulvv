package com.qlckh.chunlvv.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Andy
 * @date 2019/6/4 16:54
 * Desc:
 */
public class HomeInfo implements Parcelable {


    /**
     * status : 1
     * msg : 成功
     * data : {"id":17040,"fullname":"姚利英","company":"罗兰小镇绿城经营用房->8幢->中泰路->2-38号-"}
     */

    private int status;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 17040
         * fullname : 姚利英
         * company : 罗兰小镇绿城经营用房->8幢->中泰路->2-38号-
         */

        private int id;
        private String fullname;
        private String company;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.fullname);
            dest.writeString(this.company);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.fullname = in.readString();
            this.company = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
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
        dest.writeParcelable(this.data, flags);
    }

    public HomeInfo() {
    }

    protected HomeInfo(Parcel in) {
        this.status = in.readInt();
        this.msg = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<HomeInfo> CREATOR = new Parcelable.Creator<HomeInfo>() {
        @Override
        public HomeInfo createFromParcel(Parcel source) {
            return new HomeInfo(source);
        }

        @Override
        public HomeInfo[] newArray(int size) {
            return new HomeInfo[size];
        }
    };
}
