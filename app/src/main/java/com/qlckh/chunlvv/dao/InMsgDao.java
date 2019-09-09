package com.qlckh.chunlvv.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/22 21:04
 * Desc:
 */
public class InMsgDao {


    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"1","userid":"5","title":"多福多寿","content":"东方饭店","num":"0","addtime":"1529942400","readtime":"","isread":"0","residid":null}]
     */

    private int status;
    private String msg;
    private List<InMsg> data;

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

    public List<InMsg> getData() {
        return data;
    }

    public void setData(List<InMsg> data) {
        this.data = data;
    }

    public static class InMsg implements Parcelable {
        /**
         * id : 1
         * userid : 5
         * title : 多福多寿
         * content : 东方饭店
         * num : 0
         * addtime : 1529942400
         * readtime :
         * isread : 0
         * residid : null
         */

        private String id;
        private String userid;
        private String title;
        private String content;
        private String num;
        private String addtime;
        private String readtime;
        private String isread;
        private String residid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getReadtime() {
            return readtime;
        }

        public void setReadtime(String readtime) {
            this.readtime = readtime;
        }

        public String getIsread() {
            return isread;
        }

        public void setIsread(String isread) {
            this.isread = isread;
        }

        public String getResidid() {
            return residid;
        }

        public void setResidid(String residid) {
            this.residid = residid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.userid);
            dest.writeString(this.title);
            dest.writeString(this.content);
            dest.writeString(this.num);
            dest.writeString(this.addtime);
            dest.writeString(this.readtime);
            dest.writeString(this.isread);
            dest.writeString(this.residid);
        }

        public InMsg() {
        }

        protected InMsg(Parcel in) {
            this.id = in.readString();
            this.userid = in.readString();
            this.title = in.readString();
            this.content = in.readString();
            this.num = in.readString();
            this.addtime = in.readString();
            this.readtime = in.readString();
            this.isread = in.readString();
            this.residid = in.readString();
        }

        public static final Parcelable.Creator<InMsg> CREATOR = new Parcelable.Creator<InMsg>() {
            @Override
            public InMsg createFromParcel(Parcel source) {
                return new InMsg(source);
            }

            @Override
            public InMsg[] newArray(int size) {
                return new InMsg[size];
            }
        };
    }
}
