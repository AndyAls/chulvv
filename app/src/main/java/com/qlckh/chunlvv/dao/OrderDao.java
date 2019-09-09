package com.qlckh.chunlvv.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Andy
 * @date 2018/9/6 13:43
 * Desc:
 */
public class OrderDao {
    /**
     * msg : 成功
     * row : [{"address":"浙江省绍兴市  新昌县羽林街道南苑","addtime":1532058161,"belongareaid":"0","content":"打撒所多所多所多所","dizhi":"","fstatus":"1","huiid":"5","huiprdid":"0","huiuser":"","id":"37","items":"123","jifen":"0","num":"1","ordercode":15320581612222,"phone":"13816221788","prdclass":"0","price":"0.00","sendid":"0","status":"2","title":"电视","username":"13816221788"}]
     * status : 1
     */

    private String msg;
    private int status;
    private List<OrderBean> row;

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

    public List<OrderBean> getRow() {
        return row;
    }

    public void setRow(List<OrderBean> row) {
        this.row = row;
    }

    public static class OrderBean implements Parcelable {
        /**
         * address : 浙江省绍兴市  新昌县羽林街道南苑
         * addtime : 1532058161
         * belongareaid : 0
         * content : 打撒所多所多所多所
         * dizhi :
         * fstatus : 1
         * huiid : 5
         * huiprdid : 0
         * huiuser :
         * id : 37
         * items : 123
         * jifen : 0
         * num : 1
         * ordercode : 15320581612222
         * phone : 13816221788
         * prdclass : 0
         * price : 0.00
         * sendid : 0
         * status : 2
         * title : 电视
         * username : 13816221788
         */

        private String address;
        private long addtime;
        private String belongareaid;
        private String content;
        private String dizhi;
        private String fstatus;
        private String huiid;
        private String huiprdid;
        private String huiuser;
        private String id;
        private String items;
        private String jifen;
        private String num;
        private long ordercode;
        private String phone;
        private String prdclass;
        private String price;
        private String sendid;
        private String status;
        private String title;
        private String username;
        private String zhong;
        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getZhong() {
            return zhong;
        }

        public void setZhong(String zhong) {
            this.zhong = zhong;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
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

        public String getHuiuser() {
            return huiuser;
        }

        public void setHuiuser(String huiuser) {
            this.huiuser = huiuser;
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

        public long getOrdercode() {
            return ordercode;
        }

        public void setOrdercode(long ordercode) {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.address);
            dest.writeLong(this.addtime);
            dest.writeString(this.belongareaid);
            dest.writeString(this.content);
            dest.writeString(this.dizhi);
            dest.writeString(this.fstatus);
            dest.writeString(this.huiid);
            dest.writeString(this.huiprdid);
            dest.writeString(this.huiuser);
            dest.writeString(this.id);
            dest.writeString(this.items);
            dest.writeString(this.jifen);
            dest.writeString(this.num);
            dest.writeLong(this.ordercode);
            dest.writeString(this.phone);
            dest.writeString(this.prdclass);
            dest.writeString(this.price);
            dest.writeString(this.sendid);
            dest.writeString(this.status);
            dest.writeString(this.title);
            dest.writeString(this.username);
            dest.writeString(this.zhong);
            dest.writeString(this.img);
        }

        public OrderBean() {
        }

        protected OrderBean(Parcel in) {
            this.address = in.readString();
            this.addtime = in.readLong();
            this.belongareaid = in.readString();
            this.content = in.readString();
            this.dizhi = in.readString();
            this.fstatus = in.readString();
            this.huiid = in.readString();
            this.huiprdid = in.readString();
            this.huiuser = in.readString();
            this.id = in.readString();
            this.items = in.readString();
            this.jifen = in.readString();
            this.num = in.readString();
            this.ordercode = in.readLong();
            this.phone = in.readString();
            this.prdclass = in.readString();
            this.price = in.readString();
            this.sendid = in.readString();
            this.status = in.readString();
            this.title = in.readString();
            this.username = in.readString();
            this.zhong = in.readString();
            this.img=in.readString();
        }

        public static final Parcelable.Creator<OrderBean> CREATOR = new Parcelable.Creator<OrderBean>() {
            @Override
            public OrderBean createFromParcel(Parcel source) {
                return new OrderBean(source);
            }

            @Override
            public OrderBean[] newArray(int size) {
                return new OrderBean[size];
            }
        };
    }
}
