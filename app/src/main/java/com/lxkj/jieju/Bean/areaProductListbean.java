package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/3/2 0002.
 * Describe :
 */

public class areaProductListbean extends ResultBean {


    /**
     * totalPage : 1
     * dataList : [{"productid":"48cd8e1d4e9e4e4aabc75eabe658d87e","price":"0.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200228121524q6EPUQ.jpg","title":"ceshi","sales":1},{"productid":"77d52932fb274760945ea2010ee87f54","price":"0.1","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/202002281206006td314.jpg","title":"新款推荐测试","sales":10},{"productid":"7a23908448ba425f9031ab130ac25d78","price":"2.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200228121101jS16b8.jpg","title":"1","sales":3}]
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
         * productid : 48cd8e1d4e9e4e4aabc75eabe658d87e
         * price : 0.0
         * logo : http://huayihc.oss-cn-beijing.aliyuncs.com/20200228121524q6EPUQ.jpg
         * title : ceshi
         * sales : 1
         */

        private String productid;
        private String price;
        private String logo;
        private String title;
        private int sales;

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
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

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }
    }
}
