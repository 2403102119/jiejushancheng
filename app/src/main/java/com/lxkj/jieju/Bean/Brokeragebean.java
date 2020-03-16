package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/25 0025.
 * Describe :
 */

public class Brokeragebean extends ResultBean {

    /**
     * totalPage :
     * inviteCode : xxx
     * inviteNum : 2
     * commission : 66.66
     * friendList : [{"icon":"xxx","name":"xxx","amount":"100.00","adtime":"xxx"}]
     */

    private String totalPage;
    private String inviteCode;
    private String inviteNum;
    private String commission;
    private List<FriendListBean> friendList;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteNum() {
        return inviteNum;
    }

    public void setInviteNum(String inviteNum) {
        this.inviteNum = inviteNum;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public List<FriendListBean> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<FriendListBean> friendList) {
        this.friendList = friendList;
    }

    public static class FriendListBean {
        /**
         * icon : xxx
         * name : xxx
         * amount : 100.00
         * adtime : xxx
         */

        private String icon;
        private String name;
        private String amount;
        private String adtime;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAdtime() {
            return adtime;
        }

        public void setAdtime(String adtime) {
            this.adtime = adtime;
        }
    }
}
