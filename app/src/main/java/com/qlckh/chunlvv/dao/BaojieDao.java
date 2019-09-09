package com.qlckh.chunlvv.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/28 14:15
 * Desc:
 */
public class BaojieDao {

    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"8","username":"131111111114","addtime":"1534394728","address":"1,7,8,9,14","phone":"131111111114","card":"","img":null,"fullname":"章","adduser":"18888741678","sex":"男","pwd":"e10adc3949ba59abbe56e057f20f883e","user_type":"0","district":"","pingallscore":"0","pingusercount":"0"}]
     * res : [{"id":"32","username":"18888741678","pass":"e10adc3949ba59abbe56e057f20f883e","addtime":"1534394619","items":"1,7,8,9,14","topflag":"3","fullname":"章","phone":"18888741678","card":"","address":""}]
     */

    private int status;
    private String msg;
    private List<BaojieBean> data;
    private List<ResBean> res;

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

    public List<BaojieBean> getData() {
        return data;
    }

    public void setData(List<BaojieBean> data) {
        this.data = data;
    }

    public List<ResBean> getRes() {
        return res;
    }

    public void setRes(List<ResBean> res) {
        this.res = res;
    }

    public static class BaojieBean {
        /**
         * id : 8
         * username : 131111111114
         * addtime : 1534394728
         * address : 1,7,8,9,14
         * phone : 131111111114
         * card :
         * img : null
         * fullname : 章
         * adduser : 18888741678
         * sex : 男
         * pwd : e10adc3949ba59abbe56e057f20f883e
         * user_type : 0
         * district :
         * pingallscore : 0
         * pingusercount : 0
         */

        private String id;
        private String username;
        private String addtime;
        private String address;
        private String phone;
        private String card;
        private Object img;
        private String fullname;
        private String adduser;
        private String sex;
        private String pwd;
        private String user_type;
        private String district;
        private String pingallscore;
        private String pingusercount;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public Object getImg() {
            return img;
        }

        public void setImg(Object img) {
            this.img = img;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getAdduser() {
            return adduser;
        }

        public void setAdduser(String adduser) {
            this.adduser = adduser;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getPingallscore() {
            return pingallscore;
        }

        public void setPingallscore(String pingallscore) {
            this.pingallscore = pingallscore;
        }

        public String getPingusercount() {
            return pingusercount;
        }

        public void setPingusercount(String pingusercount) {
            this.pingusercount = pingusercount;
        }
    }

    public static class ResBean {
        /**
         * id : 32
         * username : 18888741678
         * pass : e10adc3949ba59abbe56e057f20f883e
         * addtime : 1534394619
         * items : 1,7,8,9,14
         * topflag : 3
         * fullname : 章
         * phone : 18888741678
         * card :
         * address :
         */

        private String id;
        private String username;
        private String pass;
        private String addtime;
        private String items;
        private String topflag;
        private String fullname;
        private String phone;
        private String card;
        private String address;

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

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getItems() {
            return items;
        }

        public void setItems(String items) {
            this.items = items;
        }

        public String getTopflag() {
            return topflag;
        }

        public void setTopflag(String topflag) {
            this.topflag = topflag;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
