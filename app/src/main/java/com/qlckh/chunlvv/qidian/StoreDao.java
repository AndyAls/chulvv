package com.qlckh.chunlvv.qidian;

/**
 * @author Andy
 * @date 2019/5/31 18:32
 * Desc:
 */
public class StoreDao {

    /**
     * status : 1
     * msg : 成功
     * data : 请将袋子放入0100第2中
     */

    private int status;
    private String msg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
