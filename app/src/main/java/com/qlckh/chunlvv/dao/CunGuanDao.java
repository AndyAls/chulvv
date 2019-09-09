package com.qlckh.chunlvv.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andy
 * @date 2018/8/24 21:42
 * Desc:
 */
public class CunGuanDao implements Parcelable {


    /**
     * status : 1
     * msg : 成功
     * row : [{"id":"10","name":"一期","classid":"9","sort":"226","class":"5"},{"id":"11","name":"二期","classid":"9","sort":"226","class":"5"},{"id":"12","name":"三期","classid":"9","sort":"226","class":"5"},{"id":"14","name":"严山村","classid":"9","sort":"226","class":"5"}]
     */

    private int status;
    private String msg;
    private List<CunGuanBean> row;

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

    public List<CunGuanBean> getRow() {
        return row;
    }

    public void setRow(List<CunGuanBean> row) {
        this.row = row;
    }

    public static class CunGuanBean implements Parcelable {
        /**
         * id : 10
         * name : 一期
         * classid : 9
         * sort : 226
         * class : 5
         */

        private String id;
        private String name;
        private String classid;
        private int sort;
        @SerializedName("class")
        private String classX;

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

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.name);
            dest.writeString(this.classid);
            dest.writeInt(this.sort);
            dest.writeString(this.classX);
        }

        public CunGuanBean() {
        }

        protected CunGuanBean(Parcel in) {
            this.id = in.readString();
            this.name = in.readString();
            this.classid = in.readString();
            this.sort = in.readInt();
            this.classX = in.readString();
        }

        public static final Creator<CunGuanBean> CREATOR = new Creator<CunGuanBean>() {
            @Override
            public CunGuanBean createFromParcel(Parcel source) {
                return new CunGuanBean(source);
            }

            @Override
            public CunGuanBean[] newArray(int size) {
                return new CunGuanBean[size];
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
        dest.writeList(this.row);
    }

    public CunGuanDao() {
    }

    protected CunGuanDao(Parcel in) {
        this.status = in.readInt();
        this.msg = in.readString();
        this.row = new ArrayList<CunGuanBean>();
        in.readList(this.row, CunGuanBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CunGuanDao> CREATOR = new Parcelable.Creator<CunGuanDao>() {
        @Override
        public CunGuanDao createFromParcel(Parcel source) {
            return new CunGuanDao(source);
        }

        @Override
        public CunGuanDao[] newArray(int size) {
            return new CunGuanDao[size];
        }
    };
}
