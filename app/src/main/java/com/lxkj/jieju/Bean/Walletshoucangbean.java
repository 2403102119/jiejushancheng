package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/3/13 0013.
 * Describe :
 */

public class Walletshoucangbean extends ResultBean {

    /**
     * totalPage : 2
     * dataList : [{"skuList":[{"skuName1":"个","skuPrice":"6666.00","skuStock":9996,"skuName2":"","skuId":"23f2a486fa2d417090b08191ab62d908"}],"productid":"8b64e02aec8040fd97e26c70a73d38cf","price":"9999.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200306135054xgwG82.jpg","title":"网红欧美同款浴缸"},{"skuList":[{"skuName1":"青","skuPrice":"50.00","skuStock":999,"skuName2":"","skuId":"d2fb47011dab449a90f82dcc0912c86f"},{"skuName1":"蓝","skuPrice":"50.00","skuStock":1000,"skuName2":"","skuId":"df31550921684ded9a32a6ec9c909320"},{"skuName1":"黄","skuPrice":"50.00","skuStock":1000,"skuName2":"","skuId":"e8a504e55db64d11b139981018193139"}],"productid":"66eb96b0f9574e0a96448ccc883892b7","price":"80.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200310084808Rwe47h.jpg","title":"新款淋浴头"},{"skuList":[{"skuName1":"个","skuPrice":"50.00","skuStock":1099,"skuName2":"","skuId":"f8d342b0de4846aca302c387c95562c2"}],"productid":"a2c6d9c11f344a41a85d846e1c289beb","price":"60.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200306141445eaDN6O.jpg","title":"水龙头"},{"skuList":[{"skuName1":"个","skuPrice":"1000.00","skuStock":96,"skuName2":"","skuId":"ef6a880cec6b4e64a0c067cec440d0a3"}],"productid":"920a909501fe4d2a977f7a1dcfa56d69","price":"2000.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200306141740VINhFs.jpg","title":"腾云牌马桶"},{"skuList":[{"skuName1":"个","skuPrice":"8888.00","skuStock":999,"skuName2":"","skuId":"2d2cbb5986a64e3cb96119a71fda3a38"}],"productid":"65d1d7b984934b6cb19490be65d81290","price":"10000.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200306141858cnA5nq.jpg","title":"飞龙牌浴缸"},{"skuList":[{"skuName1":"个","skuPrice":"100.00","skuStock":2000,"skuName2":"","skuId":"1f0b98c8b63147699cfd8067297a7af4"}],"productid":"c47771828ada4e149e1c63e37e367247","price":"200.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200310085120QMyCJQ.jpg","title":"惊喜牌淋浴头"},{"skuList":[{"skuName1":"个","skuPrice":"200.00","skuStock":1000,"skuName2":"","skuId":"458d6a03cfc145c9ac64258499bad70e"}],"productid":"6b361ef9e02341859790eb918533e5eb","price":"1000.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200310085731CmGtEj.jpg","title":"网红拖把(拖把中的战斗机)"},{"skuList":[{"skuName1":"个","skuPrice":"100.00","skuStock":2000,"skuName2":"","skuId":"028ffa9229134b60a655662723434015"}],"productid":"50d1c4a9ce9d46588857fb9131630a86","price":"1000.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200310085322Or9ulK.jpg","title":"厨卫打扫工具一件套"},{"skuList":[{"skuName1":"个","skuPrice":"999.00","skuStock":1200,"skuName2":"","skuId":"33c00bb043da477a8b2c633f933f8aae"}],"productid":"dd1b692b094149f684560e1959529eca","price":"10000.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200306135736PT4XAb.jpg","title":"网红同款马桶(销量过千)"},{"skuList":[{"skuName1":"个","skuPrice":"8848.00","skuStock":998,"skuName2":"","skuId":"ce4bf071493d4a84855b998db7df731f"}],"productid":"13d7f64d2fc642cda3630e74cc7639f7","price":"8848.0","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200306114737HNcN92.jpeg","title":"网红憨憨同款浴缸"}]
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
         * skuList : [{"skuName1":"个","skuPrice":"6666.00","skuStock":9996,"skuName2":"","skuId":"23f2a486fa2d417090b08191ab62d908"}]
         * productid : 8b64e02aec8040fd97e26c70a73d38cf
         * price : 9999.0
         * logo : http://huayihc.oss-cn-beijing.aliyuncs.com/20200306135054xgwG82.jpg
         * title : 网红欧美同款浴缸
         */

        private String productid;
        private String price;
        private String logo;
        private String title;
        private List<SkuListBean> skuList;

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

        public List<SkuListBean> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<SkuListBean> skuList) {
            this.skuList = skuList;
        }

        public static class SkuListBean {
            /**
             * skuName1 : 个
             * skuPrice : 6666.00
             * skuStock : 9996
             * skuName2 :
             * skuId : 23f2a486fa2d417090b08191ab62d908
             */

            private String skuName1;
            private String skuPrice;
            private int skuStock;
            private String skuName2;
            private String skuId;

            public String getSkuName1() {
                return skuName1;
            }

            public void setSkuName1(String skuName1) {
                this.skuName1 = skuName1;
            }

            public String getSkuPrice() {
                return skuPrice;
            }

            public void setSkuPrice(String skuPrice) {
                this.skuPrice = skuPrice;
            }

            public int getSkuStock() {
                return skuStock;
            }

            public void setSkuStock(int skuStock) {
                this.skuStock = skuStock;
            }

            public String getSkuName2() {
                return skuName2;
            }

            public void setSkuName2(String skuName2) {
                this.skuName2 = skuName2;
            }

            public String getSkuId() {
                return skuId;
            }

            public void setSkuId(String skuId) {
                this.skuId = skuId;
            }
        }
    }
}
