package com.qlckh.chunlvv.dao;

import com.google.gson.annotations.SerializedName;

/**
 * @author Andy
 * @date 2019/1/24 15:15
 * Desc:
 */
public class TsDo {


    /**
     * msg : 成功
     * row : {"0":{"address":"1,7,8,9,10","addtime":"1548214513","belongareaid":"0","content":"测试","dizhi":"","fstatus":"0","huiid":"102","huiprdid":"0","id":"28","img":"","items":"湖北省鄂州市梧桐湖新区梧桐湖社区一期","jifen":"0","num":"1","ordercode":"15482145138052","phone":"18671100966","prdclass":"0","price":"0.00","sendid":"0","status":"2","title":"纸张","username":"尹青云","zhong":"0"},"1":{"address":"1,7,8,9,10","addtime":"1548221044","belongareaid":"0","content":"","dizhi":"","fstatus":"0","huiid":"102","huiprdid":"0","id":"29","img":"","items":"湖北省鄂州市梧桐湖新区梧桐湖社区一期","jifen":"0","num":"1","ordercode":"15482210445120","phone":"18671100966","prdclass":"0","price":"0.00","sendid":"0","status":"2","title":"纸张","username":"尹青云","zhong":"0"}}
     * status : 1
     */

    private String msg;
    private RowBean row;
    private int status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RowBean getRow() {
        return row;
    }

    public void setRow(RowBean row) {
        this.row = row;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class RowBean {
        /**
         * 0 : {"address":"1,7,8,9,10","addtime":"1548214513","belongareaid":"0","content":"测试","dizhi":"","fstatus":"0","huiid":"102","huiprdid":"0","id":"28","img":"","items":"湖北省鄂州市梧桐湖新区梧桐湖社区一期","jifen":"0","num":"1","ordercode":"15482145138052","phone":"18671100966","prdclass":"0","price":"0.00","sendid":"0","status":"2","title":"纸张","username":"尹青云","zhong":"0"}
         * 1 : {"address":"1,7,8,9,10","addtime":"1548221044","belongareaid":"0","content":"","dizhi":"","fstatus":"0","huiid":"102","huiprdid":"0","id":"29","img":"","items":"湖北省鄂州市梧桐湖新区梧桐湖社区一期","jifen":"0","num":"1","ordercode":"15482210445120","phone":"18671100966","prdclass":"0","price":"0.00","sendid":"0","status":"2","title":"纸张","username":"尹青云","zhong":"0"}
         */

        @SerializedName("0")
        private _$0Bean _$0;
        @SerializedName("1")
        private _$1Bean _$1;

        public _$0Bean get_$0() {
            return _$0;
        }

        public void set_$0(_$0Bean _$0) {
            this._$0 = _$0;
        }

        public _$1Bean get_$1() {
            return _$1;
        }

        public void set_$1(_$1Bean _$1) {
            this._$1 = _$1;
        }

        public static class _$0Bean {
            /**
             * address : 1,7,8,9,10
             * addtime : 1548214513
             * belongareaid : 0
             * content : 测试
             * dizhi :
             * fstatus : 0
             * huiid : 102
             * huiprdid : 0
             * id : 28
             * img :
             * items : 湖北省鄂州市梧桐湖新区梧桐湖社区一期
             * jifen : 0
             * num : 1
             * ordercode : 15482145138052
             * phone : 18671100966
             * prdclass : 0
             * price : 0.00
             * sendid : 0
             * status : 2
             * title : 纸张
             * username : 尹青云
             * zhong : 0
             */

            private String address;
            private String addtime;
            private String belongareaid;
            private String content;
            private String dizhi;
            private String fstatus;
            private String huiid;
            private String huiprdid;
            private String id;
            private String img;
            private String items;
            private String jifen;
            private String num;
            private String ordercode;
            private String phone;
            private String prdclass;
            private String price;
            private String sendid;
            private String status;
            private String title;
            private String username;
            private String zhong;

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

            public String getBelongareaid() {
                return belongareaid;
            }

            public void setBelongareaid(String belongareaid) {
                this.belongareaid = belongareaid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getDizhi() {
                return dizhi;
            }

            public void setDizhi(String dizhi) {
                this.dizhi = dizhi;
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

            public String getHuiprdid() {
                return huiprdid;
            }

            public void setHuiprdid(String huiprdid) {
                this.huiprdid = huiprdid;
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

            public String getItems() {
                return items;
            }

            public void setItems(String items) {
                this.items = items;
            }

            public String getJifen() {
                return jifen;
            }

            public void setJifen(String jifen) {
                this.jifen = jifen;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getOrdercode() {
                return ordercode;
            }

            public void setOrdercode(String ordercode) {
                this.ordercode = ordercode;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPrdclass() {
                return prdclass;
            }

            public void setPrdclass(String prdclass) {
                this.prdclass = prdclass;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSendid() {
                return sendid;
            }

            public void setSendid(String sendid) {
                this.sendid = sendid;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getZhong() {
                return zhong;
            }

            public void setZhong(String zhong) {
                this.zhong = zhong;
            }
        }

        public static class _$1Bean {
            /**
             * address : 1,7,8,9,10
             * addtime : 1548221044
             * belongareaid : 0
             * content :
             * dizhi :
             * fstatus : 0
             * huiid : 102
             * huiprdid : 0
             * id : 29
             * img :
             * items : 湖北省鄂州市梧桐湖新区梧桐湖社区一期
             * jifen : 0
             * num : 1
             * ordercode : 15482210445120
             * phone : 18671100966
             * prdclass : 0
             * price : 0.00
             * sendid : 0
             * status : 2
             * title : 纸张
             * username : 尹青云
             * zhong : 0
             */

            private String address;
            private String addtime;
            private String belongareaid;
            private String content;
            private String dizhi;
            private String fstatus;
            private String huiid;
            private String huiprdid;
            private String id;
            private String img;
            private String items;
            private String jifen;
            private String num;
            private String ordercode;
            private String phone;
            private String prdclass;
            private String price;
            private String sendid;
            private String status;
            private String title;
            private String username;
            private String zhong;

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

            public String getBelongareaid() {
                return belongareaid;
            }

            public void setBelongareaid(String belongareaid) {
                this.belongareaid = belongareaid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getDizhi() {
                return dizhi;
            }

            public void setDizhi(String dizhi) {
                this.dizhi = dizhi;
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

            public String getHuiprdid() {
                return huiprdid;
            }

            public void setHuiprdid(String huiprdid) {
                this.huiprdid = huiprdid;
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

            public String getItems() {
                return items;
            }

            public void setItems(String items) {
                this.items = items;
            }

            public String getJifen() {
                return jifen;
            }

            public void setJifen(String jifen) {
                this.jifen = jifen;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getOrdercode() {
                return ordercode;
            }

            public void setOrdercode(String ordercode) {
                this.ordercode = ordercode;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPrdclass() {
                return prdclass;
            }

            public void setPrdclass(String prdclass) {
                this.prdclass = prdclass;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSendid() {
                return sendid;
            }

            public void setSendid(String sendid) {
                this.sendid = sendid;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getZhong() {
                return zhong;
            }

            public void setZhong(String zhong) {
                this.zhong = zhong;
            }
        }
    }
}
