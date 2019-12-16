package com.lxkj.hrhc.Bean;

import com.lxkj.hrhc.Http.ResultBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/20 0020.
 * Describe :首页数据
 */

public class FirsePagebean extends ResultBean {

    /**
     * areaimage1 : http://huayihc.oss-cn-beijing.aliyuncs.com/201911181559044EibOQ.png
     * areaimage2 : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118155909e295Uj.png
     * areaimage3 : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118155922ki854g.png
     * bannerList : [{"image":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118155723RT25jp.png","productid":"","type":"0","url":"http://39.105.12.51/huayishop/display/banner?id=42f47b5493184a27872a62b9da69e4f1"},{"image":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911181557493XdBiU.png","productid":"","type":"0","url":"http://39.105.12.51/huayishop/display/banner?id=f5668a47bfaa41e0b9d9b0ae394b7051"}]
     * categoryList : [{"categoryId":"7","categoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191119092510mKtH3S.jpg","categoryName":"生鲜蔬菜","childrenList":[{"childCategoryId":"8","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191119092541d4tFL6.jpg","childCategoryName":"应季蔬菜"}]},{"categoryId":"9","categoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191119092819JFfDGL.jpg","categoryName":"精品水果","childrenList":[{"childCategoryId":"15","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191119092810Nsjox3.jpg","childCategoryName":"葡萄"}]},{"categoryId":"10","categoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191119092802a6t0Nh.png","categoryName":"休闲零食","childrenList":[]},{"categoryId":"11","categoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911190927547IsfJq.png","categoryName":"酒水乳品","childrenList":[]},{"categoryId":"12","categoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/2019111909274577tF9p.jpg","categoryName":"雪糕冰棒","childrenList":[]},{"categoryId":"13","categoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191119092734Fe9gOn.jpg","categoryName":"洗衣洗鞋","childrenList":[]},{"categoryId":"4","categoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191119092654fm2N3y.png","categoryName":"日用百货","childrenList":[{"childCategoryId":"6","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191119092644LcaneW.png","childCategoryName":"水乳套装"}]},{"categoryId":"14","categoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911190926106UB9EF.png","categoryName":"粮油调味","childrenList":[]}]
     * image1 : http://huayihc.oss-cn-beijing.aliyuncs.com/201911181558151wYTd0.png
     * image2 : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118155828QKeOsQ.png
     * image3 : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118155841p6sMtU.png
     * image4 : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118155931LeQn7V.jpg
     * image5 : http://huayihc.oss-cn-beijing.aliyuncs.com/201911181559391zbnP2.jpg
     * image6 : http://huayihc.oss-cn-beijing.aliyuncs.com/2019111816260692cOaG.jpg
     * jprouctList : [{"pList":[{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118161612e2Y2DA.jpg","oldPrice":"12.00","price":"10.00","productid":"6b74abebf7144d5c900e7939b71b1904","skuList":[{"skuId":"2843749a68e940cea539bbd9dea8f0f9","skuName1":"1斤","skuName2":"","skuPrice":"10.00","skuStock":188}],"title":"红薯"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118164723L2xv4t.jpg","oldPrice":"6.00","price":"3.00","productid":"023b12ca66f3452da598edec5ee7b459","skuList":[{"skuId":"25f756e9183f4ae0bb0b1e22d30d55ac","skuName1":"2斤装","skuName2":"","skuPrice":"5.00","skuStock":194},{"skuId":"5d1a50dda9264f559d91a43e54913683","skuName1":"1斤装","skuName2":"","skuPrice":"3.00","skuStock":100}],"title":"西红柿"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911181612289Td0AP.jpg","oldPrice":"500.00","price":"300.00","productid":"41b192179f8f42d2a629d4493ac796b8","skuList":[{"skuId":"15a8bac4e8d74ccfa8d9238254e9db87","skuName1":"紫葡萄","skuName2":"2kg","skuPrice":"200.00","skuStock":98},{"skuId":"99dc48b824f844d08e04cece98a32c8c","skuName1":"巨峰葡萄","skuName2":"2kg","skuPrice":"100.00","skuStock":99},{"skuId":"babb495df0544b3cb81e57ede461e700","skuName1":"巨峰葡萄","skuName2":"1kg","skuPrice":"200.00","skuStock":100},{"skuId":"fa50b8d9fc5d44baa5a83d17df6637e4","skuName1":"紫葡萄","skuName2":"1kg","skuPrice":"100.00","skuStock":99}],"title":"葡萄应季水果孕妇水果葡萄应季水果孕妇水果葡萄应季水果孕妇水果"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911181643383nyEUA.jpg","oldPrice":"30.00","price":"20.00","productid":"66da799cd2e847e49bd3deb6b187ef02","skuList":[{"skuId":"96b30681fb144fcca8791838bbb748e9","skuName1":"1斤","skuName2":"","skuPrice":"19.00","skuStock":198}],"title":"金针菇"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911181619152J5t13.jpg","oldPrice":"22.00","price":"17.00","productid":"97e75f9474204982974df5cd0b7ccdf4","skuList":[{"skuId":"77d342b2943f4d8bafb25fcc97ad44c5","skuName1":"500g","skuName2":"","skuPrice":"16.00","skuStock":198}],"title":"猕猴桃"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118164036wF1r9L.jpg","oldPrice":"12.00","price":"6.00","productid":"9ce24d2fa06b458e8d053e4ac0ab0df9","skuList":[{"skuId":"1ecf1decf67f47f2aaacd6acee23ce70","skuName1":"1斤","skuName2":"","skuPrice":"5.00","skuStock":198}],"title":"香蕉"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165013DH934o.jpg","price":"5.00","productid":"2fa7a17459c646c4877c2180ad4363f1","skuList":[],"title":"花花菜"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165413tTJ4K2.jpg","oldPrice":"8.80","price":"6.60","productid":"b22eecb4b22e4e2db34d7156077087bf","skuList":[{"skuId":"52b73a960a734fdb898ed5de23275799","skuName1":"1斤","skuName2":"","skuPrice":"5.50","skuStock":200}],"title":"青苹果"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165755UBsqRY.jpg","oldPrice":"8.00","price":"5.61","productid":"b860f43ac8864b3fbbc1ea5cc1cfd989","skuList":[{"skuId":"835c42fc3db14cd5b4e3a52d7e1680d2","skuName1":"500g","skuName2":"","skuPrice":"4.18","skuStock":200}],"title":"小甜瓜"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911181619346r3y48.jpg","oldPrice":"6.00","price":"5.00","productid":"bd602b21fe70479092c18a301d65a3f0","skuList":[{"skuId":"1fdba56a7e1e4a1b81ea0a393ce2d921","skuName1":"3颗","skuName2":"","skuPrice":"10.00","skuStock":100},{"skuId":"5b0379a91b364ac09fb6e2f7c415dd70","skuName1":"1颗","skuName2":"","skuPrice":"5.00","skuStock":100},{"skuId":"dcf684a13a654454aed32804b98a4974","skuName1":"2颗","skuName2":"","skuPrice":"8.00","skuStock":100}],"title":"大白菜新鲜整颗"}],"type":"0"},{"pList":[{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911181619152J5t13.jpg","oldPrice":"22.00","price":"17.00","productid":"97e75f9474204982974df5cd0b7ccdf4","skuList":[{"skuId":"77d342b2943f4d8bafb25fcc97ad44c5","skuName1":"500g","skuName2":"","skuPrice":"16.00","skuStock":198}],"title":"猕猴桃"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118164036wF1r9L.jpg","oldPrice":"12.00","price":"6.00","productid":"9ce24d2fa06b458e8d053e4ac0ab0df9","skuList":[{"skuId":"1ecf1decf67f47f2aaacd6acee23ce70","skuName1":"1斤","skuName2":"","skuPrice":"5.00","skuStock":198}],"title":"香蕉"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165013DH934o.jpg","price":"5.00","productid":"2fa7a17459c646c4877c2180ad4363f1","skuList":[],"title":"花花菜"}],"type":"1"},{"pList":[],"type":"2"},{"pList":[],"type":"3"}]
     */

    private String areaimage1;
    private String areaimage2;
    private String areaimage3;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String areaTitle1;

    public String getAreaTitle1() {
        return areaTitle1;
    }

    public void setAreaTitle1(String areaTitle1) {
        this.areaTitle1 = areaTitle1;
    }

    public String getAreaTitle2() {
        return areaTitle2;
    }

    public void setAreaTitle2(String areaTitle2) {
        this.areaTitle2 = areaTitle2;
    }

    public String getAreaTitle3() {
        return areaTitle3;
    }

    public void setAreaTitle3(String areaTitle3) {
        this.areaTitle3 = areaTitle3;
    }

    private String areaTitle2;
    private String areaTitle3;

    public String getImage7() {
        return image7;
    }

    public void setImage7(String image7) {
        this.image7 = image7;
    }

    private String image7;

    public String getImage8() {
        return image8;
    }

    public void setImage8(String image8) {
        this.image8 = image8;
    }

    private String image8;

    public String getImage9() {
        return image9;
    }

    public void setImage9(String image9) {
        this.image9 = image9;
    }

    private String image9;
    private List<BannerListBean> bannerList;
    private List<CategoryListBean> categoryList;
    private List<JprouctListBean> jprouctList;

    public String getAreaimage1() {
        return areaimage1;
    }

    public void setAreaimage1(String areaimage1) {
        this.areaimage1 = areaimage1;
    }

    public String getAreaimage2() {
        return areaimage2;
    }

    public void setAreaimage2(String areaimage2) {
        this.areaimage2 = areaimage2;
    }

    public String getAreaimage3() {
        return areaimage3;
    }

    public void setAreaimage3(String areaimage3) {
        this.areaimage3 = areaimage3;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public List<BannerListBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerListBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<CategoryListBean> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryListBean> categoryList) {
        this.categoryList = categoryList;
    }

    public List<JprouctListBean> getJprouctList() {
        return jprouctList;
    }

    public void setJprouctList(List<JprouctListBean> jprouctList) {
        this.jprouctList = jprouctList;
    }

    public static class BannerListBean {
        /**
         * image : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118155723RT25jp.png
         * productid :
         * type : 0
         * url : http://39.105.12.51/huayishop/display/banner?id=42f47b5493184a27872a62b9da69e4f1
         */

        private String image;
        private String productid;
        private String type;
        private String url;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class CategoryListBean implements Serializable{
        /**
         * categoryId : 7
         * categoryImage : http://huayihc.oss-cn-beijing.aliyuncs.com/20191119092510mKtH3S.jpg
         * categoryName : 生鲜蔬菜
         * childrenList : [{"childCategoryId":"8","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191119092541d4tFL6.jpg","childCategoryName":"应季蔬菜"}]
         */

        private String categoryId;
        private String categoryImage;
        private String categoryName;
        private List<ChildrenListBean> childrenList;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryImage() {
            return categoryImage;
        }

        public void setCategoryImage(String categoryImage) {
            this.categoryImage = categoryImage;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<ChildrenListBean> getChildrenList() {
            return childrenList;
        }

        public void setChildrenList(List<ChildrenListBean> childrenList) {
            this.childrenList = childrenList;
        }

        public static class ChildrenListBean implements Serializable {
            /**
             * childCategoryId : 8
             * childCategoryImage : http://huayihc.oss-cn-beijing.aliyuncs.com/20191119092541d4tFL6.jpg
             * childCategoryName : 应季蔬菜
             */
            private String categoryId;
            private String childCategoryId;
            private String childCategoryImage;
            private String childCategoryName;

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getChildCategoryId() {
                return childCategoryId;
            }

            public void setChildCategoryId(String childCategoryId) {
                this.childCategoryId = childCategoryId;
            }

            public String getChildCategoryImage() {
                return childCategoryImage;
            }

            public void setChildCategoryImage(String childCategoryImage) {
                this.childCategoryImage = childCategoryImage;
            }

            public String getChildCategoryName() {
                return childCategoryName;
            }

            public void setChildCategoryName(String childCategoryName) {
                this.childCategoryName = childCategoryName;
            }
        }
    }

    public static class JprouctListBean {
        /**
         * pList : [{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118161612e2Y2DA.jpg","oldPrice":"12.00","price":"10.00","productid":"6b74abebf7144d5c900e7939b71b1904","skuList":[{"skuId":"2843749a68e940cea539bbd9dea8f0f9","skuName1":"1斤","skuName2":"","skuPrice":"10.00","skuStock":188}],"title":"红薯"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118164723L2xv4t.jpg","oldPrice":"6.00","price":"3.00","productid":"023b12ca66f3452da598edec5ee7b459","skuList":[{"skuId":"25f756e9183f4ae0bb0b1e22d30d55ac","skuName1":"2斤装","skuName2":"","skuPrice":"5.00","skuStock":194},{"skuId":"5d1a50dda9264f559d91a43e54913683","skuName1":"1斤装","skuName2":"","skuPrice":"3.00","skuStock":100}],"title":"西红柿"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911181612289Td0AP.jpg","oldPrice":"500.00","price":"300.00","productid":"41b192179f8f42d2a629d4493ac796b8","skuList":[{"skuId":"15a8bac4e8d74ccfa8d9238254e9db87","skuName1":"紫葡萄","skuName2":"2kg","skuPrice":"200.00","skuStock":98},{"skuId":"99dc48b824f844d08e04cece98a32c8c","skuName1":"巨峰葡萄","skuName2":"2kg","skuPrice":"100.00","skuStock":99},{"skuId":"babb495df0544b3cb81e57ede461e700","skuName1":"巨峰葡萄","skuName2":"1kg","skuPrice":"200.00","skuStock":100},{"skuId":"fa50b8d9fc5d44baa5a83d17df6637e4","skuName1":"紫葡萄","skuName2":"1kg","skuPrice":"100.00","skuStock":99}],"title":"葡萄应季水果孕妇水果葡萄应季水果孕妇水果葡萄应季水果孕妇水果"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911181643383nyEUA.jpg","oldPrice":"30.00","price":"20.00","productid":"66da799cd2e847e49bd3deb6b187ef02","skuList":[{"skuId":"96b30681fb144fcca8791838bbb748e9","skuName1":"1斤","skuName2":"","skuPrice":"19.00","skuStock":198}],"title":"金针菇"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911181619152J5t13.jpg","oldPrice":"22.00","price":"17.00","productid":"97e75f9474204982974df5cd0b7ccdf4","skuList":[{"skuId":"77d342b2943f4d8bafb25fcc97ad44c5","skuName1":"500g","skuName2":"","skuPrice":"16.00","skuStock":198}],"title":"猕猴桃"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118164036wF1r9L.jpg","oldPrice":"12.00","price":"6.00","productid":"9ce24d2fa06b458e8d053e4ac0ab0df9","skuList":[{"skuId":"1ecf1decf67f47f2aaacd6acee23ce70","skuName1":"1斤","skuName2":"","skuPrice":"5.00","skuStock":198}],"title":"香蕉"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165013DH934o.jpg","price":"5.00","productid":"2fa7a17459c646c4877c2180ad4363f1","skuList":[],"title":"花花菜"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165413tTJ4K2.jpg","oldPrice":"8.80","price":"6.60","productid":"b22eecb4b22e4e2db34d7156077087bf","skuList":[{"skuId":"52b73a960a734fdb898ed5de23275799","skuName1":"1斤","skuName2":"","skuPrice":"5.50","skuStock":200}],"title":"青苹果"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118165755UBsqRY.jpg","oldPrice":"8.00","price":"5.61","productid":"b860f43ac8864b3fbbc1ea5cc1cfd989","skuList":[{"skuId":"835c42fc3db14cd5b4e3a52d7e1680d2","skuName1":"500g","skuName2":"","skuPrice":"4.18","skuStock":200}],"title":"小甜瓜"},{"logo":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911181619346r3y48.jpg","oldPrice":"6.00","price":"5.00","productid":"bd602b21fe70479092c18a301d65a3f0","skuList":[{"skuId":"1fdba56a7e1e4a1b81ea0a393ce2d921","skuName1":"3颗","skuName2":"","skuPrice":"10.00","skuStock":100},{"skuId":"5b0379a91b364ac09fb6e2f7c415dd70","skuName1":"1颗","skuName2":"","skuPrice":"5.00","skuStock":100},{"skuId":"dcf684a13a654454aed32804b98a4974","skuName1":"2颗","skuName2":"","skuPrice":"8.00","skuStock":100}],"title":"大白菜新鲜整颗"}]
         * type : 0
         */

        private String type;

        public String getAreaimage() {
            return areaimage;
        }

        public void setAreaimage(String areaimage) {
            this.areaimage = areaimage;
        }

        private String areaimage;

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        private String pictures;

        private List<PListBean> pList;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<PListBean> getPList() {
            return pList;
        }

        public void setPList(List<PListBean> pList) {
            this.pList = pList;
        }

        public static class PListBean {

            /**
             * logo : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118161612e2Y2DA.jpg
             * oldPrice : 12.00
             * price : 10.00
             * productid : 6b74abebf7144d5c900e7939b71b1904
             * skuList : [{"skuId":"2843749a68e940cea539bbd9dea8f0f9","skuName1":"1斤","skuName2":"","skuPrice":"10.00","skuStock":188}]
             * title : 红薯
             */
            private String logo;
            private String oldPrice;
            private String price;
            private String productid;
            private String title;

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            private String sales;
            private List<SkuListBean> skuList;

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
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

            public List<SkuListBean> getSkuList() {
                return skuList;
            }

            public void setSkuList(List<SkuListBean> skuList) {
                this.skuList = skuList;
            }

            public static class SkuListBean {
                /**
                 * skuId : 2843749a68e940cea539bbd9dea8f0f9
                 * skuName1 : 1斤
                 * skuName2 :
                 * skuPrice : 10.00
                 * skuStock : 188
                 */

                private String skuId;
                private String skuName1;
                private String skuName2;
                private String skuPrice;
                private int skuStock;

                public String getSkuId() {
                    return skuId;
                }

                public void setSkuId(String skuId) {
                    this.skuId = skuId;
                }

                public String getSkuName1() {
                    return skuName1;
                }

                public void setSkuName1(String skuName1) {
                    this.skuName1 = skuName1;
                }

                public String getSkuName2() {
                    return skuName2;
                }

                public void setSkuName2(String skuName2) {
                    this.skuName2 = skuName2;
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
            }
        }
    }
}
