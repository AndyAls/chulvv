package com.qlckh.chunlvv.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Andy
 * @date 2018/8/17 17:28
 * Desc:
 */
public class MallDao {
    /**
     * status : 1
     * msg : 成功
     * row : [{"id":"25","prdname":"打算","flag":"1","jifen":"1","imgpath":"2018-07-06/5b3f1d7c00469.jpg","about":"","prdnum":"4546","finishprdnum":"0","addtime":"1530861782","classid":"0"},{"id":"43","prdname":"啊实打实","flag":"1","jifen":"445","imgpath":"","about":"","prdnum":"4546","finishprdnum":"0","addtime":"1530864025","classid":"2"}]
     */

    private int status;
    private String msg;
    private List<MallBean> row;

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

    public List<MallBean> getRow() {
        return row;
    }

    public void setRow(List<MallBean> row) {
        this.row = row;
    }

    public static class MallBean implements Parcelable {
        /**
         * id : 25
         * prdname : 打算
         * flag : 1
         * jifen : 1
         * imgpath : 2018-07-06/5b3f1d7c00469.jpg
         * about :
         * prdnum : 4546
         * finishprdnum : 0
         * addtime : 1530861782
         * classid : 0
         */

        private String id;
        private String prdname;
        private String flag;
        private String jifen;
        private String imgpath;
        private String about;
        private String prdnum;
        private String finishprdnum;
        private String addtime;
        private String classid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrdname() {
            return prdname;
        }

        public void setPrdname(String prdname) {
            this.prdname = prdname;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getPrdnum() {
            return prdnum;
        }

        public void setPrdnum(String prdnum) {
            this.prdnum = prdnum;
        }

        public String getFinishprdnum() {
            return finishprdnum;
        }

        public void setFinishprdnum(String finishprdnum) {
            this.finishprdnum = finishprdnum;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.prdname);
            dest.writeString(this.flag);
            dest.writeString(this.jifen);
            dest.writeString(this.imgpath);
            dest.writeString(this.about);
            dest.writeString(this.prdnum);
            dest.writeString(this.finishprdnum);
            dest.writeString(this.addtime);
            dest.writeString(this.classid);
        }

        public MallBean() {
        }

        protected MallBean(Parcel in) {
            this.id = in.readString();
            this.prdname = in.readString();
            this.flag = in.readString();
            this.jifen = in.readString();
            this.imgpath = in.readString();
            this.about = in.readString();
            this.prdnum = in.readString();
            this.finishprdnum = in.readString();
            this.addtime = in.readString();
            this.classid = in.readString();
        }

        public static final Parcelable.Creator<MallBean> CREATOR = new Parcelable.Creator<MallBean>() {
            @Override
            public MallBean createFromParcel(Parcel source) {
                return new MallBean(source);
            }

            @Override
            public MallBean[] newArray(int size) {
                return new MallBean[size];
            }
        };
    }
}
