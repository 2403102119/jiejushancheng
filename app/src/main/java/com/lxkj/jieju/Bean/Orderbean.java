package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/27 0027.
 * Describe :
 */

public class Orderbean extends ResultBean {

    /**
     * totalPage : 1
     * dataList : [{"emscode":"","orderid":"2019112717350001","orderItem":[{"itemId":"671050e290594405beca338110261383","productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126214958UtuW2u.jpg","productId":"8a03f3389449412095624acce4660883","productSkuName2":"","productSkuName1":"304","productCount":2,"productName":"刚博士弧形复底优雅304材质16cm304材质奶锅 HX116","productPrice":"63.00"}],"orderPrice":"126.00","emsname":"","adtime":"2019-11-27 17:35:15","emsno":"","status":"0"}]
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
         * emscode :
         * orderid : 2019112717350001
         * orderItem : [{"itemId":"671050e290594405beca338110261383","productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126214958UtuW2u.jpg","productId":"8a03f3389449412095624acce4660883","productSkuName2":"","productSkuName1":"304","productCount":2,"productName":"刚博士弧形复底优雅304材质16cm304材质奶锅 HX116","productPrice":"63.00"}]
         * orderPrice : 126.00
         * emsname :
         * adtime : 2019-11-27 17:35:15
         * emsno :
         * status : 0
         */

        private String emscode;
        private String orderid;
        private String orderPrice;
        private String emsname;
        private String adtime;
        private String emsno;
        private String status;
        private List<OrderItemBean> orderItem;

        public String getEmscode() {
            return emscode;
        }

        public void setEmscode(String emscode) {
            this.emscode = emscode;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(String orderPrice) {
            this.orderPrice = orderPrice;
        }

        public String getEmsname() {
            return emsname;
        }

        public void setEmsname(String emsname) {
            this.emsname = emsname;
        }

        public String getAdtime() {
            return adtime;
        }

        public void setAdtime(String adtime) {
            this.adtime = adtime;
        }

        public String getEmsno() {
            return emsno;
        }

        public void setEmsno(String emsno) {
            this.emsno = emsno;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<OrderItemBean> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(List<OrderItemBean> orderItem) {
            this.orderItem = orderItem;
        }

        public static class OrderItemBean {
            /**
             * itemId : 671050e290594405beca338110261383
             * productImage : http://huayihc.oss-cn-beijing.aliyuncs.com/20191126214958UtuW2u.jpg
             * productId : 8a03f3389449412095624acce4660883
             * productSkuName2 :
             * productSkuName1 : 304
             * productCount : 2
             * productName : 刚博士弧形复底优雅304材质16cm304材质奶锅 HX116
             * productPrice : 63.00
             */

            private String itemId;
            private String productImage;
            private String productId;
            private String productSkuName2;
            private String productSkuName1;
            private int productCount;
            private String productName;
            private String productPrice;

            public String getOrderPrice1() {
                return orderPrice1;
            }

            public void setOrderPrice1(String orderPrice1) {
                this.orderPrice1 = orderPrice1;
            }

            private String orderPrice1;

            public String getItemId() {
                return itemId;
            }

            public void setItemId(String itemId) {
                this.itemId = itemId;
            }

            public String getProductImage() {
                return productImage;
            }

            public void setProductImage(String productImage) {
                this.productImage = productImage;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductSkuName2() {
                return productSkuName2;
            }

            public void setProductSkuName2(String productSkuName2) {
                this.productSkuName2 = productSkuName2;
            }

            public String getProductSkuName1() {
                return productSkuName1;
            }

            public void setProductSkuName1(String productSkuName1) {
                this.productSkuName1 = productSkuName1;
            }

            public int getProductCount() {
                return productCount;
            }

            public void setProductCount(int productCount) {
                this.productCount = productCount;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(String productPrice) {
                this.productPrice = productPrice;
            }
        }
    }
}
