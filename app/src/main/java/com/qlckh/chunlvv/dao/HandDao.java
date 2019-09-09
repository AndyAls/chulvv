package com.qlckh.chunlvv.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/9/12 16:28
 * Desc:
 */
public class HandDao {


    /**
     * status : 1
     * msg : 成功
     * row : [{"id":"36","fstatus":"0","huiid":"5","huiuser":null,"title":"电视","addtime":null,"ordercode":null,"sendid":"0","num":"1","price":"0.00","prdclass":"0","content":"打撒所多所多所多所","username":"13816221788","address":"11,31,32,33,35","phone":"13816221788","huiprdid":"0","jifen":"0","dizhi":"","belongareaid":"0","status":"2","items":"123","addresss":"浙江省绍兴市  新昌县羽林街道南苑"},{"id":"37","fstatus":"1","huiid":"5","huiuser":null,"title":"电视","addtime":null,"ordercode":null,"sendid":"0","num":"1","price":"0.00","prdclass":"0","content":"打撒所多所多所多所","username":"13816221788","address":"11,31,32,33,35","phone":"13816221788","huiprdid":"0","jifen":"0","dizhi":"","belongareaid":"0","status":"2","items":"123","addresss":"浙江省绍兴市  新昌县羽林街道南苑"},{"id":"39","fstatus":"0","huiid":"5","huiuser":null,"title":"电视","addtime":"1532058161","ordercode":"15320581612222","sendid":"0","num":"1","price":"0.00","prdclass":"0","content":"打撒所多所多所多所","username":"13816221788","address":"11,31,32,33,35","phone":"13816221788","huiprdid":"0","jifen":"0","dizhi":"","belongareaid":"0","status":"2","items":"123","addresss":"浙江省绍兴市  新昌县羽林街道南苑"}]
     */

    private int status;
    private String msg;
    private List<HandBean> row;

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

    public List<HandBean> getRow() {
        return row;
    }

    public void setRow(List<HandBean> row) {
        this.row = row;
    }

    public static class HandBean {
        /**
         * id : 36
         * fstatus : 0
         * huiid : 5
         * huiuser : null
         * title : 电视
         * addtime : null
         * ordercode : null
         * sendid : 0
         * num : 1
         * price : 0.00
         * prdclass : 0
         * content : 打撒所多所多所多所
         * username : 13816221788
         * address : 11,31,32,33,35
         * phone : 13816221788
         * huiprdid : 0
         * jifen : 0
         * dizhi :
         * belongareaid : 0
         * status : 2
         * items : 123
         * addresss : 浙江省绍兴市  新昌县羽林街道南苑
         */

        private String id;
        private String fstatus;
        private String huiid;
        private Object huiuser;
        private String title;
        private Object addtime;
        private Object ordercode;
        private String sendid;
        private String num;
        private String price;
        private String prdclass;
        private String content;
        private String username;
        private String address;
        private String phone;
        private String huiprdid;
        private String jifen;
        private String dizhi;
        private String belongareaid;
        private String status;
        private String items;
        private String addresss;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFstatus() {
            return fstatus;
        }

        public void setFstatus(String fstatus) {
            this.fstatus = fstatus;
        }

        public String getHuiid() {
            return huiid;
        }

        public void setHuiid(String huiid) {
            this.huiid = huiid;
        }

        public Object getHuiuser() {
            return huiuser;
        }

        public void setHuiuser(Object huiuser) {
            this.huiuser = huiuser;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getAddtime() {
            return addtime;
        }

        public void setAddtime(Object addtime) {
            this.addtime = addtime;
        }

        public Object getOrdercode() {
            return ordercode;
        }

        public void setOrdercode(Object ordercode) {
            this.ordercode = ordercode;
        }

        public String getSendid() {
            return sendid;
        }

        public void setSendid(String sendid) {
            this.sendid = sendid;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPrdclass() {
            return prdclass;
        }

        public void setPrdclass(String prdclass) {
            this.prdclass = prdclass;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getHuiprdid() {
            return huiprdid;
        }

        public void setHuiprdid(String huiprdid) {
            this.huiprdid = huiprdid;
        }

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }

        public String getDizhi() {
            return dizhi;
        }

        public void setDizhi(String dizhi) {
            this.dizhi = dizhi;
        }

        public String getBelongareaid() {
            return belongareaid;
        }

        public void setBelongareaid(String belongareaid) {
            this.belongareaid = belongareaid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getItems() {
            return items;
        }

        public void setItems(String items) {
            this.items = items;
        }

        public String getAddresss() {
            return addresss;
        }

        public void setAddresss(String addresss) {
            this.addresss = addresss;
        }
    }
}
