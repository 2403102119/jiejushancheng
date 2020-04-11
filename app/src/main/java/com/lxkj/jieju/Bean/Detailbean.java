package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/26 0026.
 * Describe :
 */

public class Detailbean extends ResultBean {


    /**
     * productDetail : {"productImages":["http://huayihc.oss-cn-beijing.aliyuncs.com/20200228121530aKryu8.jpg"],"skuList":[{"skuName1":"2","skuPrice":"0.01","skuStock":99,"skuName2":"3","skuId":"a861a3036fb84bb48809aba37400286a"}],"price":"0.0","isCollect":"0","isagent":"0","sname1":"12","type":"1","sname2":"23","productName":"ceshi","sales":1,"url":"http://122.114.49.11:8801/jiejushop/display/product?id=48cd8e1d4e9e4e4aabc75eabe658d87e","content":"&lt;p&gt;&lt;img src=&quot;http://huayihc.oss-cn-beijing.aliyuncs.com/20200228121539WAamVl.jpg&quot; style=&quot;width: 495px;&quot;&gt;\r\n                          \r\n                        &lt;/p&gt;"}
     */

    private ProductDetailBean productDetail;

    public ProductDetailBean getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetailBean productDetail) {
        this.productDetail = productDetail;
    }

    public static class ProductDetailBean {
        /**
         * productImages : ["http://huayihc.oss-cn-beijing.aliyuncs.com/20200228121530aKryu8.jpg"]
         * skuList : [{"skuName1":"2","skuPrice":"0.01","skuStock":99,"skuName2":"3","skuId":"a861a3036fb84bb48809aba37400286a"}]
         * price : 0.0
         * isCollect : 0
         * isagent : 0
         * sname1 : 12
         * type : 1
         * sname2 : 23
         * productName : ceshi
         * sales : 1
         * url : http://122.114.49.11:8801/jiejushop/display/product?id=48cd8e1d4e9e4e4aabc75eabe658d87e
         * content : &lt;p&gt;&lt;img src=&quot;http://huayihc.oss-cn-beijing.aliyuncs.com/20200228121539WAamVl.jpg&quot; style=&quot;width: 495px;&quot;&gt;

         &lt;/p&gt;
         */

        private String price;
        private String isCollect;
        private String isagent;
        private String sname1;
        private String type;
        private String sname2;
        private String productName;
        private int sales;
        private String url;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        private String phone;

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        private String video;
        private String content;
        private List<String> productImages;
        private List<SkuListBean> skuList;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(String isCollect) {
            this.isCollect = isCollect;
        }

        public String getIsagent() {
            return isagent;
        }

        public void setIsagent(String isagent) {
            this.isagent = isagent;
        }

        public String getSname1() {
            return sname1;
        }

        public void setSname1(String sname1) {
            this.sname1 = sname1;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSname2() {
            return sname2;
        }

        public void setSname2(String sname2) {
            this.sname2 = sname2;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getProductImages() {
            return productImages;
        }

        public void setProductImages(List<String> productImages) {
            this.productImages = productImages;
        }

        public List<SkuListBean> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<SkuListBean> skuList) {
            this.skuList = skuList;
        }

        public static class SkuListBean {
            /**
             * skuName1 : 2
             * skuPrice : 0.01
             * skuStock : 99
             * skuName2 : 3
             * skuId : a861a3036fb84bb48809aba37400286a
             */

            private String skuName1;
            private String skuPrice;
            private int skuStock;
            private String skuName2;
            private String skuId;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            private String image;

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
