package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/28 0028.
 * Describe :
 */

public class Orderdetailbean extends ResultBean {


    /**
     * orderDetail : {"emscode":"","orderItem":[{"itemId":"fdbdab15d2a649e98a239ee4e58f5134","productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200306141858cnA5nq.jpg","productId":"65d1d7b984934b6cb19490be65d81290","productSkuName2":"","productSkuName1":"个","productCount":1,"productName":"飞龙牌浴缸","productPrice":"8888.00"}],"receiverName":"海绵","receiveDate":"","remark":"","emsname":"","emsno":"","receiverAddress":"湖南省岳阳市岳阳楼区2202","payType":"","receiverPhone":"17600167028","createdDate":"2020-03-11 16:54:43","waitPay":"85767","orderPrice":"8888.00","deliveryDate":"","status":"0","payDate":""}
     */

    private OrderDetailBean orderDetail;

    public OrderDetailBean getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailBean orderDetail) {
        this.orderDetail = orderDetail;
    }

    public static class OrderDetailBean {
        /**
         * emscode :
         * orderItem : [{"itemId":"fdbdab15d2a649e98a239ee4e58f5134","productImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200306141858cnA5nq.jpg","productId":"65d1d7b984934b6cb19490be65d81290","productSkuName2":"","productSkuName1":"个","productCount":1,"productName":"飞龙牌浴缸","productPrice":"8888.00"}]
         * receiverName : 海绵
         * receiveDate :
         * remark :
         * emsname :
         * emsno :
         * receiverAddress : 湖南省岳阳市岳阳楼区2202
         * payType :
         * receiverPhone : 17600167028
         * createdDate : 2020-03-11 16:54:43
         * waitPay : 85767
         * orderPrice : 8888.00
         * deliveryDate :
         * status : 0
         * payDate :
         */

        private String emscode;
        private String receiverName;
        private String receiveDate;
        private String remark;
        private String emsname;
        private String emsno;
        private String receiverAddress;
        private String payType;
        private String receiverPhone;
        private String createdDate;
        private String waitPay;
        private String orderPrice;
        private String deliveryDate;
        private String status;
        private String payDate;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        private String phone;
        private List<OrderItemBean> orderItem;

        public String getEmscode() {
            return emscode;
        }

        public void setEmscode(String emscode) {
            this.emscode = emscode;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getReceiveDate() {
            return receiveDate;
        }

        public void setReceiveDate(String receiveDate) {
            this.receiveDate = receiveDate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getEmsname() {
            return emsname;
        }

        public void setEmsname(String emsname) {
            this.emsname = emsname;
        }

        public String getEmsno() {
            return emsno;
        }

        public void setEmsno(String emsno) {
            this.emsno = emsno;
        }

        public String getReceiverAddress() {
            return receiverAddress;
        }

        public void setReceiverAddress(String receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getReceiverPhone() {
            return receiverPhone;
        }

        public void setReceiverPhone(String receiverPhone) {
            this.receiverPhone = receiverPhone;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getWaitPay() {
            return waitPay;
        }

        public void setWaitPay(String waitPay) {
            this.waitPay = waitPay;
        }

        public String getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(String orderPrice) {
            this.orderPrice = orderPrice;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPayDate() {
            return payDate;
        }

        public void setPayDate(String payDate) {
            this.payDate = payDate;
        }

        public List<OrderItemBean> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(List<OrderItemBean> orderItem) {
            this.orderItem = orderItem;
        }

        public static class OrderItemBean {
            /**
             * itemId : fdbdab15d2a649e98a239ee4e58f5134
             * productImage : http://huayihc.oss-cn-beijing.aliyuncs.com/20200306141858cnA5nq.jpg
             * productId : 65d1d7b984934b6cb19490be65d81290
             * productSkuName2 :
             * productSkuName1 : 个
             * productCount : 1
             * productName : 飞龙牌浴缸
             * productPrice : 8888.00
             */

            private String itemId;
            private String productImage;
            private String productId;
            private String productSkuName2;
            private String productSkuName1;
            private int productCount;
            private String productName;
            private String productPrice;

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
