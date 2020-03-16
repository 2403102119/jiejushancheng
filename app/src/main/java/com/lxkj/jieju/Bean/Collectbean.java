package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/25 0025.
 * Describe :
 */

public class Collectbean extends ResultBean {

    /**
     * totalPage : 1
     * dataList : [{"productid":"b860f43ac8864b3fbbc1ea5cc1cfd989","oldPrice":"8.00","price":"5.61","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165755UBsqRY.jpg","title":"小甜瓜"}]
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
         * productid : b860f43ac8864b3fbbc1ea5cc1cfd989
         * oldPrice : 8.00
         * price : 5.61
         * logo : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165755UBsqRY.jpg
         * title : 小甜瓜
         */

        private String productid;
        private String oldPrice;
        private String price;
        private String logo;
        private String title;

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(String oldPrice) {
            this.oldPrice = oldPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
