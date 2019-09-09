package com.qlckh.chunlvv.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/9/6 17:31
 * Desc:
 */
public class EvaluationDao {
    /**
     * status : 1
     * msg : 成功
     * row : [{"id":"3","senduserid":"11","senduser":"","imgpath":null,"p_xing":"5","orderid":"2","content":"打撒所多所多所多所","addtime":"1532061252","huiuser":"","huicode":"0"}]
     */

    private int status;
    private String msg;
    private List<EvaBean> row;

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

    public List<EvaBean> getRow() {
        return row;
    }

    public void setRow(List<EvaBean> row) {
        this.row = row;
    }

    public static class EvaBean {
        /**
         * id : 3
         * senduserid : 11
         * senduser :
         * imgpath : null
         * p_xing : 5
         * orderid : 2
         * content : 打撒所多所多所多所
         * addtime : 1532061252
         * huiuser :
         * huicode : 0
         */

        private String id;
        private String senduserid;
        private String senduser;
        private Object imgpath;
        private String p_xing;
        private String orderid;
        private String content;
        private String addtime;
        private String huiuser;
        private String huicode;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSenduserid() {
            return senduserid;
        }

        public void setSenduserid(String senduserid) {
            this.senduserid = senduserid;
        }

        public String getSenduser() {
            return senduser;
        }

        public void setSenduser(String senduser) {
            this.senduser = senduser;
        }

        public Object getImgpath() {
            return imgpath;
        }

        public void setImgpath(Object imgpath) {
            this.imgpath = imgpath;
        }

        public String getP_xing() {
            return p_xing;
        }

        public void setP_xing(String p_xing) {
            this.p_xing = p_xing;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getHuiuser() {
            return huiuser;
        }

        public void setHuiuser(String huiuser) {
            this.huiuser = huiuser;
        }

        public String getHuicode() {
            return huicode;
        }

        public void setHuicode(String huicode) {
            this.huicode = huicode;
        }
    }
}
