package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/3/17 0017.
 * Describe :
 */

public class Proprietarybean extends ResultBean {

    private List<DataListBean> dataList;

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * categoryImage : http://huayihc.oss-cn-beijing.aliyuncs.com/20191127023225yVOT8k.png
         * childrenList : [{"childCategoryName":"耳环/耳坠","childCategoryId":"26","categoryName":"专柜饰品","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911262234152HAjCh.jpg","categoryId":"11"},{"childCategoryName":"手表/手链","childCategoryId":"27","categoryName":"专柜饰品","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126223529TbhWoS.jpg","categoryId":"11"},{"childCategoryName":"眼镜/帽子","childCategoryId":"29","categoryName":"专柜饰品","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/20191126223759UCYr1U.jpg","categoryId":"11"},{"childCategoryName":"戒指/项链","childCategoryId":"28","categoryName":"专柜饰品","childCategoryImage":"http://huayihc.oss-cn-beijing.aliyuncs.com/201911262236271xnqBN.jpg","categoryId":"11"}]
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

        public static class ChildrenListBean {
            /**
             * childCategoryName : 耳环/耳坠
             * childCategoryId : 26
             * categoryName : 专柜饰品
             * childCategoryImage : http://huayihc.oss-cn-beijing.aliyuncs.com/201911262234152HAjCh.jpg
             * categoryId : 11
             */

            private String childCategoryName;
            private String childCategoryId;
            private String categoryName;
            private String childCategoryImage;
            private String categoryId;

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

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getChildCategoryImage() {
                return childCategoryImage;
            }

            public void setChildCategoryImage(String childCategoryImage) {
                this.childCategoryImage = childCategoryImage;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }
        }
    }
}
