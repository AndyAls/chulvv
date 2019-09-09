package com.qlckh.chunlvv.dao;

/**
 * @author Andy
 * @date 2018/10/18 14:20
 * Desc:
 */
public class TitleDao {
    /**
     * status : 1
     * msg : 成功
     * row : {"id":"30","username":"13677119889","pass":"e10adc3949ba59abbe56e057f20f883e","addtime":"1539410814","items":"1,7,8,9,","topflag":"2","fullname":"孟斌","phone":"13677119889","card":"","address":"?????","full":"春绿环保"}
     */

    private int status;
    private String msg;
    private TitleBean row;

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

    public TitleBean getRow() {
        return row;
    }

    public void setRow(TitleBean row) {
        this.row = row;
    }

    public static class TitleBean {
        /**
         * id : 30
         * username : 13677119889
         * pass : e10adc3949ba59abbe56e057f20f883e
         * addtime : 1539410814
         * items : 1,7,8,9,
         * topflag : 2
         * fullname : 孟斌
         * phone : 13677119889
         * card :
         * address : ?????
         * full : 春绿环保
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
        private String full;

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

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }
    }
}
