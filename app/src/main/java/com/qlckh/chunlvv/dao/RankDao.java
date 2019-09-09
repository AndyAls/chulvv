package com.qlckh.chunlvv.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/9/7 17:13
 * Desc:
 */
public class RankDao {
    /**
     * msg : 成功
     * row : [{"addtime":"1534471456","adduser":"15355031875","cardid":"","com":"","company":"201","dangid":"0","erimg":"./Uploads/QRcode/1535773751.jpg","fullname":"尹青云","huiid":"7","id":"102","items":"1,7,8,9,10","jicode":"0","jifen":"30","phone":"18671100966","pwd":"e10adc3949ba59abbe56e057f20f883e","sex":"男","topflag":"4"}]
     * status : 1
     */

    private String msg;
    private int status;
    private List<RankBean> row;

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

    public List<RankBean> getRow() {
        return row;
    }

    public void setRow(List<RankBean> row) {
        this.row = row;
    }

    public static class RankBean {
        /**
         * addtime : 1534471456
         * adduser : 15355031875
         * cardid :
         * com :
         * company : 201
         * dangid : 0
         * erimg : ./Uploads/QRcode/1535773751.jpg
         * fullname : 尹青云
         * huiid : 7
         * id : 102
         * items : 1,7,8,9,10
         * jicode : 0
         * jifen : 30
         * phone : 18671100966
         * pwd : e10adc3949ba59abbe56e057f20f883e
         * sex : 男
         * topflag : 4
         */

        private String addtime;
        private String adduser;
        private String cardid;
        private String com;
        private String company;
        private String dangid;
        private String erimg;
        private String fullname;
        private String huiid;
        private String id;
        private String items;
        private String jicode;
        private String jifen;
        private String phone;
        private String pwd;
        private String sex;
        private String topflag;
        private String weight;
        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
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

        public String getCardid() {
            return cardid;
        }

        public void setCardid(String cardid) {
            this.cardid = cardid;
        }

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDangid() {
            return dangid;
        }

        public void setDangid(String dangid) {
            this.dangid = dangid;
        }

        public String getErimg() {
            return erimg;
        }

        public void setErimg(String erimg) {
            this.erimg = erimg;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getHuiid() {
            return huiid;
        }

        public void setHuiid(String huiid) {
            this.huiid = huiid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getItems() {
            return items;
        }

        public void setItems(String items) {
            this.items = items;
        }

        public String getJicode() {
            return jicode;
        }

        public void setJicode(String jicode) {
            this.jicode = jicode;
        }

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getTopflag() {
            return topflag;
        }

        public void setTopflag(String topflag) {
            this.topflag = topflag;
        }
    }
}
