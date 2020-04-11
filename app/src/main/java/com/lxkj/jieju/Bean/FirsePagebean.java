package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/20 0020.
 * Describe :首页数据
 */

public class FirsePagebean extends ResultBean {


    /**
     * image5 : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118155909e295Uj.png
     * image6 : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118155922ki854g.png
     * image3 : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118155841p6sMtU.png
     * image4 : http://huayihc.oss-cn-beijing.aliyuncs.com/20191125105956lP1r3k.png
     * bannerList : [{"image":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191118155723RT25jp.png"},{"image":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911181557493XdBiU.png"}]
     * image7 : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118155931LeQn7V.jpg
     * categoryList : [{"categoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191127023225yVOT8k.png","childrenList":[{"childCategoryName":"耳环/耳坠","childCategoryId":"26","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911262234152HAjCh.jpg"},{"childCategoryName":"手表/手链","childCategoryId":"27","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126223529TbhWoS.jpg"},{"childCategoryName":"戒指/项链","childCategoryId":"28","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911262236271xnqBN.jpg"},{"childCategoryName":"眼镜/帽子","childCategoryId":"29","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126223759UCYr1U.jpg"}],"categoryName":"专柜饰品","categoryId":"11"},{"categoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911262236271xnqBN.jpg","childrenList":[],"categoryName":"戒指/项链","categoryId":"28"},{"categoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126223759UCYr1U.jpg","childrenList":[],"categoryName":"眼镜/帽子","categoryId":"29"},{"categoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191127021829M9cKKz.png","childrenList":[{"childCategoryName":"化妆水","childCategoryId":"30","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126225107ioio33.jpg"},{"childCategoryName":"精油皂","childCategoryId":"31","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126225159Wv4CcM.jpg"}],"categoryName":"美妆护肤","categoryId":"12"}]
     * image1 : http://huayihc.oss-cn-beijing.aliyuncs.com/201911181558151wYTd0.png
     * image2 : http://huayihc.oss-cn-beijing.aliyuncs.com/20191118155828QKeOsQ.png
     * jprouctList : [{"pList":[{"productid":"1","title":"","logo":"","price":"","sales":""}],"type":"0"},{"pList":[],"type":"1"},{"pList":[],"type":"2"}]
     */

    private String image5;
    private String image6;
    private String image3;
    private String image4;
    private String image7;
    private String image8;
    private String image9;

    public String getImage8() {
        return image8;
    }

    public void setImage8(String image8) {
        this.image8 = image8;
    }

    public String getImage9() {
        return image9;
    }

    public void setImage9(String image9) {
        this.image9 = image9;
    }

    public String getImage10() {
        return image10;
    }

    public void setImage10(String image10) {
        this.image10 = image10;
    }

    public String getImage11() {
        return image11;
    }

    public void setImage11(String image11) {
        this.image11 = image11;
    }

    public String getImage12() {
        return image12;
    }

    public void setImage12(String image12) {
        this.image12 = image12;
    }

    private String image10;
    private String image11;
    private String image12;
    private String image1;
    private String image2;
    private List<BannerListBean> bannerList;
    private List<CategoryListBean> categoryList;
    private List<JprouctListBean> jprouctList;

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

    public String getImage7() {
        return image7;
    }

    public void setImage7(String image7) {
        this.image7 = image7;
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
         */

        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public static class CategoryListBean implements Serializable {
        /**
         * categoryImage : http://huayihc.oss-cn-beijing.aliyuncs.com/20191127023225yVOT8k.png
         * childrenList : [{"childCategoryName":"耳环/耳坠","childCategoryId":"26","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911262234152HAjCh.jpg"},{"childCategoryName":"手表/手链","childCategoryId":"27","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126223529TbhWoS.jpg"},{"childCategoryName":"戒指/项链","childCategoryId":"28","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911262236271xnqBN.jpg"},{"childCategoryName":"眼镜/帽子","childCategoryId":"29","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126223759UCYr1U.jpg"}]
         * categoryName : 专柜饰品
         * categoryId : 11
         */

        private String categoryImage;
        private String categoryName;
        private String categoryId;
        private List<ChildrenListBean> childrenList;

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

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public List<ChildrenListBean> getChildrenList() {
            return childrenList;
        }

        public void setChildrenList(List<ChildrenListBean> childrenList) {
            this.childrenList = childrenList;
        }

        public static class ChildrenListBean implements Serializable {
            /**
             * childCategoryName : 耳环/耳坠
             * childCategoryId : 26
             * childCategoryImage : http://huayihc.oss-cn-beijing.aliyuncs.com/201911262234152HAjCh.jpg
             */

            private String childCategoryName;
            private String childCategoryId;
            private String childCategoryImage;

            public String getChildCategoryName() {
                return childCategoryName;
            }

            public void setChildCategoryName(String childCategoryName) {
                this.childCategoryName = childCategoryName;
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
        }
    }

    public static class JprouctListBean {
        /**
         * pList : [{"productid":"1","title":"","logo":"","price":"","sales":""}]
         * type : 0
         */

        private String type;

        public String getAreaimage() {
            return Areaimage;
        }

        public void setAreaimage(String areaimage) {
            Areaimage = areaimage;
        }

        private String Areaimage;
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
             * productid : 1
             * title :
             * logo :
             * price :
             * sales :
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
}
