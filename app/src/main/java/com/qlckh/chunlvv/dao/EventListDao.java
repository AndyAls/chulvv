package com.qlckh.chunlvv.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/29 11:50
 * Desc:
 */
public class EventListDao {

    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"8","userid":"0","title":"","content":"","addtime":"","readtime":null,"isread":"0","taskid":null,"img":"","status":"0","type":"1","titles":"","shijian":"1534239385","cimg":"Uploads/public/Uploads/20180814173625_.jpeg"}]
     */

    private int status;
    private String msg;
    private List<EventDao> data;

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

    public List<EventDao> getData() {
        return data;
    }

    public void setData(List<EventDao> data) {
        this.data = data;
    }

    public static class EventDao implements Parcelable {
        /**
         * id : 8
         * userid : 0
         * title :
         * content :
         * addtime :
         * readtime : null
         * isread : 0
         * taskid : null
         * img :
         * status : 0
         * type : 1
         * titles :
         * shijian : 1534239385
         * cimg : Uploads/public/Uploads/20180814173625_.jpeg
         */

        private String id;
        private String userid;
        private String title;
        private String content;
        private String addtime;
        private String readtime;
        private String isread;
        private String taskid;
        private String img;
        private String status;
        private String type;
        private String titles;
        private String shijian;
        private String cimg;

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

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public Object getReadtime() {
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

        public Object getTaskid() {
            return taskid;
        }

        public void setTaskid(String taskid) {
            this.taskid = taskid;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitles() {
            return titles;
        }

        public void setTitles(String titles) {
            this.titles = titles;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public String getCimg() {
            return cimg;
        }

        public void setCimg(String cimg) {
            this.cimg = cimg;
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
            dest.writeString(this.addtime);
            dest.writeString(this.readtime);
            dest.writeString(this.isread);
            dest.writeString(this.taskid);
            dest.writeString(this.img);
            dest.writeString(this.status);
            dest.writeString(this.type);
            dest.writeString(this.titles);
            dest.writeString(this.shijian);
            dest.writeString(this.cimg);
        }

        public EventDao() {
        }

        protected EventDao(Parcel in) {
            this.id = in.readString();
            this.userid = in.readString();
            this.title = in.readString();
            this.content = in.readString();
            this.addtime = in.readString();
            this.readtime = in.readParcelable(Object.class.getClassLoader());
            this.isread = in.readString();
            this.taskid = in.readParcelable(Object.class.getClassLoader());
            this.img = in.readString();
            this.status = in.readString();
            this.type = in.readString();
            this.titles = in.readString();
            this.shijian = in.readString();
            this.cimg = in.readString();
        }

        public static final Parcelable.Creator<EventDao> CREATOR = new Parcelable.Creator<EventDao>() {
            @Override
            public EventDao createFromParcel(Parcel source) {
                return new EventDao(source);
            }

            @Override
            public EventDao[] newArray(int size) {
                return new EventDao[size];
            }
        };
    }
}

