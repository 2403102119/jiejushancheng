package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/23 0023.
 * Describe :
 */

public class Messagebean extends ResultBean {

    /**
     * totalPage : 1
     * dataList : [{"msgid":"42fb57de2f2f4c1b92d5cea220959660","title":"111","adtime":"2019-11-21 14:41:30","content":"1111"},{"msgid":"f9c7aae0ef54471189472410f12bdb34","title":"测试数据","adtime":"2019-11-21 11:34:45","content":"系统消息测试"}]
     */

    private int totalPage;
    private List<DataListBean> dataList;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * msgid : 42fb57de2f2f4c1b92d5cea220959660
         * title : 111
         * adtime : 2019-11-21 14:41:30
         * content : 1111
         */

        private String msgid;
        private String title;
        private String adtime;
        private String content;

        public String getMsgid() {
            return msgid;
        }

        public void setMsgid(String msgid) {
            this.msgid = msgid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAdtime() {
            return adtime;
        }

        public void setAdtime(String adtime) {
            this.adtime = adtime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
