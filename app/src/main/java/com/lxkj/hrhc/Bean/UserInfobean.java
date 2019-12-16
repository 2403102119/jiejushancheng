package com.lxkj.hrhc.Bean;

import com.lxkj.hrhc.Http.ResultBean;
import com.lxkj.hrhc.Utils.StringUtil;

/**
 * Created ：李迪迦
 * on:2019/11/22 0022.
 * Describe :个人中心
 */

public class UserInfobean extends ResultBean {

    /**
     * dataObject : {"todyIncome":0,"historyIncome":0,"isopen":"","balance":"10000.00","phone":"17600167028","sex":"","inviteCode":"","name":"用户7028","icon":"null","newMsg":"1"}
     */

    private DataObjectBean dataObject;

    public DataObjectBean getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObjectBean dataObject) {
        this.dataObject = dataObject;
    }

    public static class DataObjectBean {
        /**
         * todyIncome : 0
         * historyIncome : 0
         * isopen :
         * balance : 10000.00
         * phone : 17600167028
         * sex :
         * inviteCode :
         * name : 用户7028
         * icon : null
         * newMsg : 1
         */

        private String todyIncome;
        private String historyIncome;
        private String isopen;
        private String balance;
        private String phone;
        private String sex;
        private String inviteCode;
        private String name;
        private String icon;
        private String newMsg;

        public String getTodyIncome() {
            return todyIncome;
        }

        public void setTodyIncome(String todyIncome) {
            this.todyIncome = todyIncome;
        }

        public String getHistoryIncome() {
            return historyIncome;
        }

        public void setHistoryIncome(String historyIncome) {
            this.historyIncome = historyIncome;
        }

        public String getIsopen() {
            return isopen;
        }

        public void setIsopen(String isopen) {
            this.isopen = isopen;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
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

        public String getNewMsg() {
            return newMsg;
        }

        public void setNewMsg(String newMsg) {
            this.newMsg = newMsg;
        }
    }
}
