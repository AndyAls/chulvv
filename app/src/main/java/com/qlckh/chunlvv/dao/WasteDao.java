package com.qlckh.chunlvv.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andy
 * @date 2018/8/21 11:48
 * Desc:
 */
public class WasteDao implements Parcelable {
    /**
     * msg : 成功
     * row : [{"address":"11,31,32,33,37","addtime":"1509674334","belongareaid":"40","content":"内容","dizhi":"40","fstatus":"0","huiprdid":"0","huiuser":"dsads","id":"19","jifen":"0","num":"1","ordercode":"152463889348427","phone":"13265865134","prdclass":"3","price":"100.00","sendid":"3","status":"1","title":"七八九","username":"用户名"},{"address":"11,31,32,33,37","addtime":"1509674334","belongareaid":"40","content":"具体","dizhi":"40","fstatus":"0","huiprdid":"0","huiuser":"dsads","id":"20","jifen":"0","num":"1","ordercode":"152463906040928","phone":"13826223611","prdclass":"3","price":"100.00","sendid":"1","status":"1","title":"七八九","username":"村民33"},{"address":"11,31,32,33,37","addtime":"1509674334","belongareaid":"40","content":"具体","dizhi":"40","fstatus":"0","huiprdid":"0","huiuser":"关于歌剧院","id":"21","jifen":"0","num":"1","ordercode":"15246390666745","phone":"13826223611","prdclass":"3","price":"100.00","sendid":"14","status":"1","title":"七八九","username":"张学友"}]
     * status : 1
     * total : 16
     */

    private String msg;
    private int status;
    private String total;
    private List<WasteBean> row;

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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<WasteBean> getRow() {
        return row;
    }

    public void setRow(List<WasteBean> row) {
        this.row = row;
    }

    public static class WasteBean implements Parcelable {
        /**
         * address : 11,31,32,33,37
         * addtime : 1509674334
         * belongareaid : 40
         * content : 内容
         * dizhi : 40
         * fstatus : 0
         * huiprdid : 0
         * huiuser : dsads
         * id : 19
         * jifen : 0
         * num : 1
         * ordercode : 152463889348427
         * phone : 13265865134
         * prdclass : 3
         * price : 100.00
         * sendid : 3
         * status : 1
         * title : 七八九
         * username : 用户名
         */

        private String address;
        private String addtime;
        private String belongareaid;
        private String content;
        private String dizhi;
        private String fstatus;
        private String huiprdid;
        private String huiuser;
        private String id;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.address);
            dest.writeString(this.addtime);
            dest.writeString(this.belongareaid);
            dest.writeString(this.content);
            dest.writeString(this.dizhi);
            dest.writeString(this.fstatus);
            dest.writeString(this.huiprdid);
            dest.writeString(this.huiuser);
            dest.writeString(this.id);
            dest.writeString(this.jifen);
            dest.writeString(this.num);
            dest.writeString(this.ordercode);
            dest.writeString(this.phone);
            dest.writeString(this.prdclass);
            dest.writeString(this.price);
            dest.writeString(this.sendid);
            dest.writeString(this.status);
            dest.writeString(this.title);
            dest.writeString(this.username);
        }

        public WasteBean() {
        }

        protected WasteBean(Parcel in) {
            this.address = in.readString();
            this.addtime = in.readString();
            this.belongareaid = in.readString();
            this.content = in.readString();
            this.dizhi = in.readString();
            this.fstatus = in.readString();
            this.huiprdid = in.readString();
            this.huiuser = in.readString();
            this.id = in.readString();
            this.jifen = in.readString();
            this.num = in.readString();
            this.ordercode = in.readString();
            this.phone = in.readString();
            this.prdclass = in.readString();
            this.price = in.readString();
            this.sendid = in.readString();
            this.status = in.readString();
            this.title = in.readString();
            this.username = in.readString();
        }

        public static final Creator<WasteBean> CREATOR = new Creator<WasteBean>() {
            @Override
            public WasteBean createFromParcel(Parcel source) {
                return new WasteBean(source);
            }

            @Override
            public WasteBean[] newArray(int size) {
                return new WasteBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg);
        dest.writeInt(this.status);
        dest.writeString(this.total);
        dest.writeList(this.row);
    }

    public WasteDao() {
    }

    protected WasteDao(Parcel in) {
        this.msg = in.readString();
        this.status = in.readInt();
        this.total = in.readString();
        this.row = new ArrayList<WasteBean>();
        in.readList(this.row, WasteBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<WasteDao> CREATOR = new Parcelable.Creator<WasteDao>() {
        @Override
        public WasteDao createFromParcel(Parcel source) {
            return new WasteDao(source);
        }

        @Override
        public WasteDao[] newArray(int size) {
            return new WasteDao[size];
        }
    };
}
