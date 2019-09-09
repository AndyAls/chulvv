package com.qlckh.chunlvv.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/6/12 9:53
 * Desc:
 */
public class ScanCount {


    /**
     * status : 1
     * msg : 成功
     * row : [{"yisao":"1","zongsu":"1","weisao":0,"fullname":"蒙多"}]
     */

    private int status;
    private String msg;
    private List<ScanDao> row;

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

    public List<ScanDao> getRow() {
        return row;
    }

    public void setRow(List<ScanDao> row) {
        this.row = row;
    }

    public static class ScanDao {
        /**
         * yisao : 1
         * zongsu : 1
         * weisao : 0
         * fullname : 蒙多
         */

        private String yisao;
        private String zongsu;
        private int weisao;
        private String fullname;

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

        public int getWeisao() {
            return weisao;
        }

        public void setWeisao(int weisao) {
            this.weisao = weisao;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }
    }
}
