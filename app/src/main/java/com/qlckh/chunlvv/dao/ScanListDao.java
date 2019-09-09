package com.qlckh.chunlvv.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/6/12 13:07
 * Desc:
 */
public class ScanListDao {

    private String msg;
    private int status;
    private List<ScanList> row;

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

    public List<ScanList> getData() {
        return row;
    }

    public void setData(List<ScanList> data) {
        this.row = data;
    }

    public static class ScanList {
//
//            "yisao": "8",
//            "weisao": 722,
//            "zongsu": "730",
//            "fullname": "02"
        private String fullname;
        private String weisao;
        private String yisao;
        private String zongsu;

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getWeisao() {
            return weisao;
        }

        public void setWeisao(String weisao) {
            this.weisao = weisao;
        }

        public String getYisao() {
            return yisao;
        }

        public void setYisao(String yisao) {
            this.yisao = yisao;
        }

        public String getZongsu() {
            return zongsu;
        }

        public void setZongsu(String zongsu) {
            this.zongsu = zongsu;
        }
    }
}
