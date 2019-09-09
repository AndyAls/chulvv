package com.qlckh.chunlvv.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/22 21:07
 * Desc:
 */
public class OutMessageDao {


    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"67","content":"<p>爱上大飒飒大师<\/p>","newsid":"7","addtime":"","isread":"0","readtime":"","title":"大声道","newsadduser":"admin","status":"0","newstype":"0","userid":"3"},{"id":"73","content":"<p>大叔大婶所<br/><\/p>","newsid":"8","addtime":"","isread":"0","readtime":"","title":"大声道","newsadduser":"admin","status":"0","newstype":"0","userid":"3"},{"id":"79","content":"<p>大叔大婶大所多<br/><\/p>","newsid":"9","addtime":"1531370800","isread":"0","readtime":"","title":"打扫打扫的","newsadduser":"admin","status":"0","newstype":"0","userid":"3"}]
     */

    private int status;
    private String msg;
    private List<OutMessage> data;

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

    public List<OutMessage> getData() {
        return data;
    }

    public void setData(List<OutMessage> data) {
        this.data = data;
    }

    public static class OutMessage implements Parcelable {
        /**
         * id : 67
         * content : <p>爱上大飒飒大师</p>
         * newsid : 7
         * addtime :
         * isread : 0
         * readtime :
         * title : 大声道
         * newsadduser : admin
         * status : 0
         * newstype : 0
         * userid : 3
         */

        private String id;
        private String content;
        private String newsid;
        private String addtime;
        private String isread;
        private String readtime;
        private String title;
        private String newsadduser;
        private String status;
        private String newstype;
        private String userid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNewsid() {
            return newsid;
        }

        public void setNewsid(String newsid) {
            this.newsid = newsid;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getIsread() {
            return isread;
        }

        public void setIsread(String isread) {
            this.isread = isread;
        }

        public String getReadtime() {
            return readtime;
        }

        public void setReadtime(String readtime) {
            this.readtime = readtime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNewsadduser() {
            return newsadduser;
        }

        public void setNewsadduser(String newsadduser) {
            this.newsadduser = newsadduser;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNewstype() {
            return newstype;
        }

        public void setNewstype(String newstype) {
            this.newstype = newstype;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.content);
            dest.writeString(this.newsid);
            dest.writeString(this.addtime);
            dest.writeString(this.isread);
            dest.writeString(this.readtime);
            dest.writeString(this.title);
            dest.writeString(this.newsadduser);
            dest.writeString(this.status);
            dest.writeString(this.newstype);
            dest.writeString(this.userid);
        }

        public OutMessage() {
        }

        protected OutMessage(Parcel in) {
            this.id = in.readString();
            this.content = in.readString();
            this.newsid = in.readString();
            this.addtime = in.readString();
            this.isread = in.readString();
            this.readtime = in.readString();
            this.title = in.readString();
            this.newsadduser = in.readString();
            this.status = in.readString();
            this.newstype = in.readString();
            this.userid = in.readString();
        }

        public static final Parcelable.Creator<OutMessage> CREATOR = new Parcelable.Creator<OutMessage>() {
            @Override
            public OutMessage createFromParcel(Parcel source) {
                return new OutMessage(source);
            }

            @Override
            public OutMessage[] newArray(int size) {
                return new OutMessage[size];
            }
        };
    }
}
