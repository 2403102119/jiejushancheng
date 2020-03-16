package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/3/11 0011.
 * Describe :
 */

public class recommendProductbean extends ResultBean {

    /**
     * totalPage : 5
     * dataList : [{"productid":"a78735ee2f554cb88fcb6c3f8612a5c9","price":"30.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200306094729bPgb4S.jpg","title":"水龙头","sales":2},{"productid":"05aeb9cda54d43c086f1e0ab18fbf225","price":"120.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126235505zKG8fd.jpg","title":"天使之眼款耳环 E0347","sales":11},{"productid":"12660879d045475e813c91bc68d1e492","price":"30.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200306094729bPgb4S.jpg","title":"水龙头","sales":101},{"productid":"13d7f64d2fc642cda3630e74cc7639f7","price":"8848.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200306114737HNcN92.jpeg","title":"网红憨憨同款浴缸","sales":1002},{"productid":"17973344fa834efca9eef2cac4577918","price":"1000.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/2020030614130086IO53.jpg","title":"洗手台一件套","sales":1000},{"productid":"19f5705a38584509b5f7fa01a3b28864","price":"36.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126224720zvMphO.jpg","title":"喻金匠 巴西进口石榴石手链","sales":0},{"productid":"2d05b47fd02b4220b3858aab424e632a","price":"60.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126225911WgskIw.jpg","title":"喻金匠 S925银耳钉鱼遇上猫","sales":1},{"productid":"38edee1967854f9d867b69e1e1bd7752","price":"40.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/2019112622544972rI0c.jpg","title":"喻金匠 S925银耳钉樱桃","sales":0},{"productid":"48cd8e1d4e9e4e4aabc75eabe658d87e","price":"0.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200228121524q6EPUQ.jpg","title":"ceshi","sales":2},{"productid":"50d1c4a9ce9d46588857fb9131630a86","price":"1000.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200310085322Or9ulK.jpg","title":"厨卫打扫工具一件套","sales":800}]
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
         * productid : a78735ee2f554cb88fcb6c3f8612a5c9
         * price : 30.0
         * logo : http://huayihc.oss-cn-beijing.aliyuncs.com/20200306094729bPgb4S.jpg
         * title : 水龙头
         * sales : 2
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
