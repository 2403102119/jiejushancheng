package com.lxkj.hrhc.Uri;

public class NetClass {


    public static final boolean isDeg = true;
    public static final String BASE_URL;
    public static final String Base_File = "http://39.105.12.51/huayishop/api/uploadFiles";//单张图片
    public static final String Base_FileCui = "http://39.105.12.51/huayishop/api/uploadFile";//单张图片



    static {
        if (isDeg) {
            BASE_URL = "http://39.105.12.51/huayishop/api/service";//测试
        } else {
            BASE_URL = "http://a.caimogu666.com/mushroom/api/service";//正式
        }
    }


}