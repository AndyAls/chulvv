package com.qlckh.chunlvv.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/9/4 21:59
 * Desc:
 */
public class CategoryDao {

    /**
     * status : 1
     * msg : 成功
     * total : null
     * row : [{"id":"1","classname":"电视机","flag":"2"},{"id":"2","classname":"可回收","flag":"1"},{"id":"3","classname":"有毒有害","flag":"0"},{"id":"4","classname":"冰箱","flag":"0"},{"id":"5","classname":"大件","flag":"0"}]
     */

    private int status;
    private String msg;
    private Object total;
    private List<CategoryBean> row;

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

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public List<CategoryBean> getRow() {
        return row;
    }

    public void setRow(List<CategoryBean> row) {
        this.row = row;
    }

    public static class CategoryBean {
        /**
         * id : 1
         * classname : 电视机
         * flag : 2
         */

        private String id;
        private String classname;
        private String flag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}
