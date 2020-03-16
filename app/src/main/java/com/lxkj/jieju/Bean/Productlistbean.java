package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/26 0026.
 * Describe :
 */

public class Productlistbean extends ResultBean {

    /**
     * totalPage : 1
     * dataList : [{"skuList":[{"skuName1":"500g","skuPrice":"1000.00","skuStock":195,"skuName2":"","skuId":"77d342b2943f4d8bafb25fcc97ad44c5"}],"productid":"97e75f9474204982974df5cd0b7ccdf4","price":"17.00","oldPrice":"22.00","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911181619152J5t13.jpg","title":"猕猴桃","sales":5},{"skuList":[{"skuName1":"500g","skuPrice":"4.18","skuStock":198,"skuName2":"","skuId":"835c42fc3db14cd5b4e3a52d7e1680d2"}],"productid":"b860f43ac8864b3fbbc1ea5cc1cfd989","price":"5.61","oldPrice":"8.00","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165755UBsqRY.jpg","title":"小甜瓜","sales":2},{"skuList":[{"skuName1":"1斤","skuPrice":"5.50","skuStock":198,"skuName2":"","skuId":"52b73a960a734fdb898ed5de23275799"}],"productid":"b22eecb4b22e4e2db34d7156077087bf","price":"6.60","oldPrice":"8.80","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165413tTJ4K2.jpg","title":"青苹果","sales":2},{"skuList":[{"skuName1":"1斤","skuPrice":"5.00","skuStock":196,"skuName2":"","skuId":"1ecf1decf67f47f2aaacd6acee23ce70"}],"productid":"9ce24d2fa06b458e8d053e4ac0ab0df9","price":"6.00","oldPrice":"12.00","logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118164036wF1r9L.jpg","title":"香蕉","sales":4}]
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
         * skuList : [{"skuName1":"500g","skuPrice":"1000.00","skuStock":195,"skuName2":"","skuId":"77d342b2943f4d8bafb25fcc97ad44c5"}]
         * productid : 97e75f9474204982974df5cd0b7ccdf4
         * price : 17.00
         * oldPrice : 22.00
         * logo : http://huayihc.oss-cn-beijing.aliyuncs.com/201911181619152J5t13.jpg
         * title : 猕猴桃
         * sales : 5
         */

        private String productid;
        private String price;
        private String oldPrice;
        private String logo;
        private String title;
        private int sales;
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

        public String getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(String oldPrice) {
            this.oldPrice = oldPrice;
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

        public List<SkuListBean> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<SkuListBean> skuList) {
            this.skuList = skuList;
        }

        public static class SkuListBean {
            /**
             * skuName1 : 500g
             * skuPrice : 1000.00
             * skuStock : 195
             * skuName2 :
             * skuId : 77d342b2943f4d8bafb25fcc97ad44c5
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
