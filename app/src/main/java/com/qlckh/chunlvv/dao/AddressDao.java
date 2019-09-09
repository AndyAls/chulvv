package com.qlckh.chunlvv.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Andy
 * @date 2018/9/5 14:19
 * Desc:
 */
public class AddressDao {
    /**
     * status : 1
     * msg : 成功
     * row : [{"id":"1","userid":"5","shippingaddress":"11,31,32,33,36","address":"试试","username":"杨打定","phone":"15555555555","isdefault":"0","createtime":null,"updatetime":null}]
     */

    private int status;
    private String msg;
    private List<AddressBean> row;

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

    public List<AddressBean> getRow() {
        return row;
    }

    public void setRow(List<AddressBean> row) {
        this.row = row;
    }

    public static class AddressBean implements Parcelable {
        /**
         * id : 1
         * userid : 5
         * shippingaddress : 11,31,32,33,36
         * address : 试试
         * username : 杨打定
         * phone : 15555555555
         * isdefault : 0
         * createtime : null
         * updatetime : null
         */

        private String id;
        private String userid;
        private String shippingaddress;
        private String address;
        private String username;
        private String phone;
        private String isdefault;
        private String createtime;
        private String updatetime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getShippingaddress() {
            return shippingaddress;
        }

        public void setShippingaddress(String shippingaddress) {
            this.shippingaddress = shippingaddress;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIsdefault() {
            return isdefault;
        }

        public void setIsdefault(String isdefault) {
            this.isdefault = isdefault;
        }

        public Object getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public Object getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.userid);
            dest.writeString(this.shippingaddress);
            dest.writeString(this.address);
            dest.writeString(this.username);
            dest.writeString(this.phone);
            dest.writeString(this.isdefault);
            dest.writeString(this.createtime);
            dest.writeString(this.updatetime);
        }

        public AddressBean() {
        }

        protected AddressBean(Parcel in) {
            this.id = in.readString();
            this.userid = in.readString();
            this.shippingaddress = in.readString();
            this.address = in.readString();
            this.username = in.readString();
            this.phone = in.readString();
            this.isdefault = in.readString();
            this.createtime = in.readString();
            this.updatetime = in.readString();
        }

        public static final Parcelable.Creator<AddressBean> CREATOR = new Parcelable.Creator<AddressBean>() {
            @Override
            public AddressBean createFromParcel(Parcel source) {
                return new AddressBean(source);
            }

            @Override
            public AddressBean[] newArray(int size) {
                return new AddressBean[size];
            }
        };
    }
}
