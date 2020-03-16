package com.lxkj.jieju.Uri;

public class NetClass {


    public static final boolean isDeg = true;
    public static final String BASE_URL;
    public static final String Base_File = "http://101.200.212.150/api/uploadFiles";//多张图片
    public static final String Base_FileCui = "http://101.200.212.150/jiejushop/api/uploadFile";//单张图片



    static {
        if (isDeg) {
            BASE_URL = "http://101.200.212.150/jiejushop/api/service";//测试
        } else {
            BASE_URL = "http://a.caimogu666.com/mushroom/api/service";//正式
        }
    }


}