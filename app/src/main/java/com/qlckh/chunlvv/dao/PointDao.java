package com.qlckh.chunlvv.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/9/10 16:27
 * Desc:
 */
public class PointDao {


    /**
     * msg : 成功
     * row : [{"address":"11,31,32,33,37","addressid":"0","addtime":"1530925890","adduser":"admin","codeid":"5","flag":"1","id":"15","jifen":"1","kdh":"12345674897","kname":"sdas","num":"1","phone":"15555555555","prdid":"25","prdname":"打算","status":"1","ucode":"1530925890","username":"杨打定","xaddress":"sada"}]
     * status : 1
     */

    private String msg;
    private int status;
    private List<PointBean> row;

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

    public List<PointBean> getRow() {
        return row;
    }

    public void setRow(List<PointBean> row) {
        this.row = row;
    }

    public static class PointBean {
        /**
         * address : 11,31,32,33,37
         * addressid : 0
         * addtime : 1530925890
         * adduser : admin
         * codeid : 5
         * flag : 1
         * id : 15
         * jifen : 1
         * kdh : 12345674897
         * kname : sdas
         * num : 1
         * phone : 15555555555
         * prdid : 25
         * prdname : 打算
         * status : 1
         * ucode : 1530925890
         * username : 杨打定
         * xaddress : sada
         */

        private String address;
        private String addressid;
        private String addtime;
        private String adduser;
        private String codeid;
        private String flag;
        private String id;
        private String jifen;
        private String kdh;
        private String kname;
        private String num;
        private String phone;
        private String prdid;
        private String prdname;
        private String status;
        private String ucode;
        private String username;
        private String xaddress;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressid() {
            return addressid;
        }

        public void setAddressid(String addressid) {
            this.addressid = addressid;
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

        public String getCodeid() {
            return codeid;
        }

        public void setCodeid(String codeid) {
            this.codeid = codeid;
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

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }

        public String getKdh() {
            return kdh;
        }

        public void setKdh(String kdh) {
            this.kdh = kdh;
        }

        public String getKname() {
            return kname;
        }

        public void setKname(String kname) {
            this.kname = kname;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPrdid() {
            return prdid;
        }

        public void setPrdid(String prdid) {
            this.prdid = prdid;
        }

        public String getPrdname() {
            return prdname;
        }

        public void setPrdname(String prdname) {
            this.prdname = prdname;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUcode() {
            return ucode;
        }

        public void setUcode(String ucode) {
            this.ucode = ucode;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getXaddress() {
            return xaddress;
        }

        public void setXaddress(String xaddress) {
            this.xaddress = xaddress;
        }
    }
}
