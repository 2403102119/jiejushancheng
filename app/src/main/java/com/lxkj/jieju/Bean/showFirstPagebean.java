package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/3/2 0002.
 * Describe :
 */

public class showFirstPagebean extends ResultBean{

    /**
     * totalPage : 10
     * pList : [{"productid":"商品id","title":"商品名称","logo":"商品缩略图","price":"价格","sales":"11"}]
     */

    private int totalPage;
    private List<PListBean> pList;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<PListBean> getPList() {
        return pList;
    }

    public void setPList(List<PListBean> pList) {
        this.pList = pList;
    }

    public static class PListBean {
        /**
         * productid : 商品id
         * title : 商品名称
         * logo : 商品缩略图
         * price : 价格
         * sales : 11
         */

        private String productid;
        private String title;
        private String logo;
        private String price;
        private String sales;

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }
    }
}
