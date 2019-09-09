package com.qlckh.chunlvv.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/8/13 17:11
 * Desc:
 */
public class SignDao {

    /**
     * status : 1
     * msg : 成功
     * row : [{"id":"7","caijiuserid":"5","address":"中国浙江省杭州市江干区四季路70号UDC·时代大厦","createtime":"1534139832","status":"0","xaddress":null,"addtime":null},{"id":"8","caijiuserid":"5","address":"中国浙江省杭州市江干区四季路70号UDC·时代大厦","createtime":"1534139943","status":"0","xaddress":null,"addtime":null}]
     */

    private int status;
    private String msg;
    private List<SignBean> row;

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

    public List<SignBean> getRow() {
        return row;
    }

    public void setRow(List<SignBean> row) {
        this.row = row;
    }

    public static class SignBean {
        /**
         * id : 7
         * caijiuserid : 5
         * address : 中国浙江省杭州市江干区四季路70号UDC·时代大厦
         * createtime : 1534139832
         * status : 0
         * xaddress : null
         * addtime : null
         */

        private String id;
        private String caijiuserid;
        private String address;
        private String createtime;
        private String status;
        private String xaddress;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCaijiuserid() {
            return caijiuserid;
        }

        public void setCaijiuserid(String caijiuserid) {
            this.caijiuserid = caijiuserid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getXaddress() {
            return xaddress;
        }

        public void setXaddress(String xaddress) {
            this.xaddress = xaddress;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
