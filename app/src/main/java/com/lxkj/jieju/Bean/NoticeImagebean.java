package com.lxkj.jieju.Bean;

import com.lxkj.jieju.Http.ResultBean;

/**
 * Created ：李迪迦
 * on:2019/12/9 0009.
 * Describe :
 */

public class NoticeImagebean extends ResultBean {

    /**
     * image : 图片
     * state : 0
     */

    private String image;
    private String state;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
