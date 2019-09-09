package com.qlckh.chunlvv.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/9/10 11:35
 * Desc:
 */
public class RecordDao {


    /**
     * status : 1
     * msg : 成功
     * row : [{"id":"1476","username":"尹青云","addtime":"1537921445","usercode":"102","imgpath":null,"ji_class":"2","ji_she":"2","ji_qing":"2","ji_bai":"0","ji_dui":"0","ji_duinum":"0.00","address":"1,7,8,9,10","tel":"18671100966","adduser":"13111111111","adduserid":"7","belongareaid":"0","whattype":"0","zong":"6","dianzicheng":null},{"id":"1477","username":"尹青云","addtime":"1537921458","usercode":"102","imgpath":null,"ji_class":"2","ji_she":"2","ji_qing":"2","ji_bai":"0","ji_dui":"0","ji_duinum":"0.00","address":"1,7,8,9,10","tel":"18671100966","adduser":"13111111111","adduserid":"7","belongareaid":"0","whattype":"0","zong":"6","dianzicheng":null}]
     */

    private int status;
    private String msg;
    private List<RecordBean> row;

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

    public List<RecordBean> getRow() {
        return row;
    }

    public void setRow(List<RecordBean> row) {
        this.row = row;
    }

    public static class RecordBean {
        /**
         * id : 1476
         * username : 尹青云
         * addtime : 1537921445
         * usercode : 102
         * imgpath : null
         * ji_class : 2
         * ji_she : 2
         * ji_qing : 2
         * ji_bai : 0
         * ji_dui : 0
         * ji_duinum : 0.00
         * address : 1,7,8,9,10
         * tel : 18671100966
         * adduser : 13111111111
         * adduserid : 7
         * belongareaid : 0
         * whattype : 0
         * zong : 6
         * dianzicheng : null
         */

        private String id;
        private String username;
        private String addtime;
        private String usercode;
        private Object imgpath;
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
        private Object dianzicheng;

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

        public Object getImgpath() {
            return imgpath;
        }

        public void setImgpath(Object imgpath) {
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

        public Object getDianzicheng() {
            return dianzicheng;
        }

        public void setDianzicheng(Object dianzicheng) {
            this.dianzicheng = dianzicheng;
        }
    }
}
