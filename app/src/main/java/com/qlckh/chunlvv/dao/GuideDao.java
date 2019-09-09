package com.qlckh.chunlvv.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/9/7 16:31
 * Desc:
 */
public class GuideDao {
    /**
     * status : 1
     * msg : 成功
     * row : [{"id":"1","title":"使用指南","content":"<p>\t\t\t\t\t\t\t<\/p><p>大声说撒啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊是大撒所多所大所撒所按时打算的撒<br/><\/p><p><br/><\/p><p><br/><\/p><p>\t\t\t\t\t\t<\/p>","addtime":"1536283271"}]
     */

    private int status;
    private String msg;
    private List<GuideBean> row;

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

    public List<GuideBean> getRow() {
        return row;
    }

    public void setRow(List<GuideBean> row) {
        this.row = row;
    }

    public static class GuideBean {
        /**
         * id : 1
         * title : 使用指南
         * content : <p>							</p><p>大声说撒啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊是大撒所多所大所撒所按时打算的撒<br/></p><p><br/></p><p><br/></p><p>						</p>
         * addtime : 1536283271
         */

        private String id;
        private String title;
        private String content;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
    }
}
