package com.qlckh.chunlvv.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/8/17 17:27
 * Desc:
 */
public class MallCatgrayDao {


    /**
     * status : 1
     * msg : 成功
     * row : [{"id":"2","name":"房子","flg":"7","addtime":"1530842038"},{"id":"3","name":"车子","flg":"5","addtime":"1530841275"}]
     */

    private int status;
    private String msg;
    private List<MallCatgrayBean> row;

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

    public List<MallCatgrayBean> getRow() {
        return row;
    }

    public void setRow(List<MallCatgrayBean> row) {
        this.row = row;
    }

    public static class MallCatgrayBean {
        /**
         * id : 2
         * name : 房子
         * flg : 7
         * addtime : 1530842038
         */

        private String id;
        private String name;
        private String flg;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFlg() {
            return flg;
        }

        public void setFlg(String flg) {
            this.flg = flg;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
