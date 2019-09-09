package com.qlckh.chunlvv.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/17 11:47
 * Desc:
 */
public class BannerDao {


    /**
     * msg : 成功
     * row : [{"address":"","addtime":"1533287620","adduser":"","content":" ","flag":"0","id":"19","img":"2018-08-01/5b6162dfc2f39.jpg","newsid":"6","title":"白起传"}]
     * status : 1
     */

    private String msg;
    private int status;
    private List<ImgBean> row;

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

    public List<ImgBean> getRow() {
        return row;
    }

    public void setRow(List<ImgBean> row) {
        this.row = row;
    }

    public static class ImgBean {
        /**
         * address :
         * addtime : 1533287620
         * adduser :
         * content :
         * flag : 0
         * id : 19
         * img : 2018-08-01/5b6162dfc2f39.jpg
         * newsid : 6
         * title : 白起传
         */

        private String address;
        private String addtime;
        private String adduser;
        private String content;
        private String flag;
        private String id;
        private String img;
        private String newsid;
        private String title;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getNewsid() {
            return newsid;
        }

        public void setNewsid(String newsid) {
            this.newsid = newsid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
