package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/27 0027.
 * Describe :
 */

public class Cartbean extends ResultBean {


    /**
     * totalPage : 1
     * dataList : [{"image":"http://huayihc.oss-cn-beijing.aliyuncs.com/20200228121524q6EPUQ.jpg","productId":"48cd8e1d4e9e4e4aabc75eabe658d87e","price":"0.01","cartId":"c081b3e1689e42d0bf63463c8dad5539","count":"1","skuName1":"2","isagent":"0","skuName2":"3","stock":98,"productName":"ceshi"}]
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

    public static class  DataListBean  implements Serializable {
        /**
         * image : http://huayihc.oss-cn-beijing.aliyuncs.com/20200228121524q6EPUQ.jpg
         * productId : 48cd8e1d4e9e4e4aabc75eabe658d87e
         * price : 0.01
         * cartId : c081b3e1689e42d0bf63463c8dad5539
         * count : 1
         * skuName1 : 2
         * isagent : 0
         * skuName2 : 3
         * stock : 98
         * productName : ceshi
         */

        private String image;
        private String productId;
        private String price;
        private String cartId;
        private String count;
        private String skuName1;
        private String isagent;
        private String skuName2;
        private int stock;
        private String productName;

        public boolean isIscheck() {
            return ischeck;
        }

        public void setIscheck(boolean ischeck) {
            this.ischeck = ischeck;
        }

        private boolean ischeck;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getSkuName1() {
            return skuName1;
        }

        public void setSkuName1(String skuName1) {
            this.skuName1 = skuName1;
        }

        public String getIsagent() {
            return isagent;
        }

        public void setIsagent(String isagent) {
            this.isagent = isagent;
        }

        public String getSkuName2() {
            return skuName2;
        }

        public void setSkuName2(String skuName2) {
            this.skuName2 = skuName2;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
    }
}
