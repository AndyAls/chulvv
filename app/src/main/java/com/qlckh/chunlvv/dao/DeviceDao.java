package com.qlckh.chunlvv.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Andy
 * @date 2018/9/7 16:45
 * Desc:
 */
public class DeviceDao {
    /**
     * status : 1
     * msg : 成功
     * row : [{"id":"2","title":"使用指南","content":"<p>\t\t\t\t\t\t\t<\/p><p>的撒大所大所大所大所大所大所大撒所大所大所大所大所大所多所<\/p><p>\t\t\t\t\t\t<\/p>","img":"2018-09-07/5b91dcc25c5e4.jpg","addtime":"1536285890"},{"id":"3","title":"大师的三大","content":"<p>啊实打实大师大所大所<br/><\/p>","img":"2018-09-07/5b91dd3658ced.jpg","addtime":"1536286006"}]
     */

    private int status;
    private String msg;
    private List<DeviceBean> row;

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

    public List<DeviceBean> getRow() {
        return row;
    }

    public void setRow(List<DeviceBean> row) {
        this.row = row;
    }

    public static class DeviceBean implements Parcelable {
        /**
         * id : 2
         * title : 使用指南
         * content : <p>							</p><p>的撒大所大所大所大所大所大所大撒所大所大所大所大所大所多所</p><p>						</p>
         * img : 2018-09-07/5b91dcc25c5e4.jpg
         * addtime : 1536285890
         */

        private String id;
        private String title;
        private String content;
        private String img;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.title);
            dest.writeString(this.content);
            dest.writeString(this.img);
            dest.writeString(this.addtime);
        }

        public DeviceBean() {
        }

        protected DeviceBean(Parcel in) {
            this.id = in.readString();
            this.title = in.readString();
            this.content = in.readString();
            this.img = in.readString();
            this.addtime = in.readString();
        }

        public static final Parcelable.Creator<DeviceBean> CREATOR = new Parcelable.Creator<DeviceBean>() {
            @Override
            public DeviceBean createFromParcel(Parcel source) {
                return new DeviceBean(source);
            }

            @Override
            public DeviceBean[] newArray(int size) {
                return new DeviceBean[size];
            }
        };
    }
}
