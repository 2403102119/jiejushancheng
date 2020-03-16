package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/22 0022.
 * Describe :个人中心
 */

public class UserInfobean extends ResultBean {


    /**
     * dataObject : {"phone":"17600167028","agentState":"0","name":"海绵宝宝","icon":"null","isagent":"0","newMsg":"0"}
     * canList : [{"productid":"","title":"","logo":"","price":"","sales":""}]
     */

    private DataObjectBean dataObject;
    private List<CanListBean> canList;

    public DataObjectBean getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObjectBean dataObject) {
        this.dataObject = dataObject;
    }

    public List<CanListBean> getCanList() {
        return canList;
    }

    public void setCanList(List<CanListBean> canList) {
        this.canList = canList;
    }

    public static class DataObjectBean {
        /**
         * phone : 17600167028
         * agentState : 0
         * name : 海绵宝宝
         * icon : null
         * isagent : 0
         * newMsg : 0
         */

        private String phone;
        private String agentState;
        private String name;
        private String icon;
        private String isagent;
        private String newMsg;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAgentState() {
            return agentState;
        }

        public void setAgentState(String agentState) {
            this.agentState = agentState;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIsagent() {
            return isagent;
        }

        public void setIsagent(String isagent) {
            this.isagent = isagent;
        }

        public String getNewMsg() {
            return newMsg;
        }

        public void setNewMsg(String newMsg) {
            this.newMsg = newMsg;
        }
    }

    public static class CanListBean {
        /**
         * productid :
         * title :
         * logo :
         * price :
         * sales :
         */

        private String productid;
        private String title;
        private String logo;
        private String price;
        private String sales;

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }
    }
}
