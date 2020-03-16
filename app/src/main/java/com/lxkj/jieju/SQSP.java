package com.lxkj.jieju;

import com.lxkj.jieju.Bean.FirsePagebean;

import java.util.ArrayList;
import java.util.List;

public class SQSP {

    public static final String pagerSize = "10";

    //保存用户个人信息
    public static final String uid = "uid";//用户ID
    public static final String xieyi = "0";//是否同意用户协议
    public static final String isLogin = "ISLOGIN";//用户是否登录
    public static final String yongjin = "yongjin";//佣金
    public static final String JupshID = "JupshID";//极光ID
    public static final String isFirstIndex = "ISFIRSTINDEX";  //是否是第一次进入
    public static  String city = "city";  //地址


    public static final String PayBalance = "PayBalance";//互殴转账的余额
    public static String shareName = "欢迎使用洁具商城";//分享的文字说明
    public static final String SEARCH = "SEARCH";//用户搜索结果
    public static final int PMS_LOCATION = 1003;// 权限编号，自定义----定位  打电话
    public static final int PMS_CAMERA = 1005;// 权限编号，自定义----相机
    public static final String XingQu = "郑州市";// 定位的兴趣点
    public static final String JingDu = "JingDu";// 精度
    public static final String WeiDu = "WeiDu";// 纬度
    public static final String Shi = "Shi";// 郑州市
    public static  String user_icon = "user_icon";// 用户头像
    public static final String sosuojilu = "sousuolist";
    public static final String Beecloud_ID = "c9eb8c23-4ba7-46b8-a242-371708e3e44f";
    public static final String Beecloud_Secret = "3c78dabd-48ce-4af1-8c9d-0635438b78fe";
    public static final String WX_APP_ID = "wxf158dcd422b1b613";
    public static  String image4 = "wx82eea6aeca4379ad";
    public static  String dailichengshi = "dailichengshi";
    public static final int CODE_REFRESH = 168;
    public static List<FirsePagebean.CategoryListBean> shouyelist = new ArrayList<>();

}
