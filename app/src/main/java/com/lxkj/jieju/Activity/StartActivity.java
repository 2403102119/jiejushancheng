package com.lxkj.jieju.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiSearch;
import com.gyf.immersionbar.ImmersionBar;
import com.lxkj.jieju.App;
import com.lxkj.jieju.Bean.NoticeImagebean;
import com.lxkj.jieju.Bean.privacyBean;
import com.lxkj.jieju.Http.BaseCallback;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.MainActivity;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.Utils;
import com.lxkj.jieju.View.ActionDialog;
import com.umeng.socialize.UMShareAPI;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

import static com.lxkj.jieju.App.mContext;

public class StartActivity extends AppCompatActivity implements AMapLocationListener,View.OnClickListener {
    private static final String TAG = "StartActivity";
    private Handler handler=new Handler();
    private Context context ;
    private ActionDialog actionDialog ;
    private AMapLocationClient mlocationClient;
    // 要申请的权限
    private String[] permissions = {
            android.Manifest.permission.LOCATION_HARDWARE,//位置
            android.Manifest.permission.ACCESS_FINE_LOCATION,//位置
            android.Manifest.permission.ACCESS_COARSE_LOCATION,//位置
    };
    //权限数组下标
    //权限申请返回码
    private int requestCodePre = 321;
    //系统设置权限申请返回码
    private int requestCodeSer = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).navigationBarColor(R.color.white).statusBarDarkFont(true).init();
        setContentView(R.layout.activity_start);
        privacyPolicy();

        //声明mLocationOption对象
        AMapLocationClientOption mLocationOption = null;
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        mLocationOption.setOnceLocationLatest(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位


        if(ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(StartActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},200);
        }else{
            mlocationClient.startLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 200://刚才的识别码
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){//用户同意权限,执行我们的操作
                    mlocationClient.startLocation();
                }else{//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(StartActivity.this,"未开启定位权限,请手动到设置去开启权限",Toast.LENGTH_LONG).show();
                }
                break;
            default:break;
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }
    //协议列表
    private void privacyPolicy() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "privacyPolicy");
        OkHttpHelper.getInstance().post_json(this, NetClass.BASE_URL, params, new BaseCallback<privacyBean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, final privacyBean resultBean) {
                actionDialog = new ActionDialog(StartActivity.this,"温馨提示","请您在使用前仔细阅读并同意"+resultBean.getDataList().get(0).getTitle()+/*+"和"+resultBean.getDataList().get(2).getTitle()+*/",其中的重点条款已为您标注，方便您了解自己的权利。","不同意","同意并使用");
                actionDialog.setOnxieyiClickListener(new ActionDialog.OnxieyiClickListener() {
                    @Override
                    public void onLeftClick() {
                        actionDialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onRightClick() {
                        actionDialog.dismiss();
                        SPTool.addSessionMap(SQSP.xieyi, "1");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(StartActivity.this, MainActivity.class));
                                finish();
                            }
                        },3000);
                    }

                    @Override
                    public void onZhuce() {
                        Intent intent = new Intent(StartActivity.this,WebViewActivity.class);
                        intent.putExtra("webTitle",resultBean.getDataList().get(0).getTitle());
                        intent.putExtra("webUrl",resultBean.getDataList().get(0).getUrl());
                        startActivity(intent);
                    }

                    @Override
                    public void onyinsi() {
                        Intent intent = new Intent(StartActivity.this,WebViewActivity.class);
                        intent.putExtra("webTitle",resultBean.getDataList().get(2).getTitle());
                        intent.putExtra("webUrl",resultBean.getDataList().get(2).getUrl());
                        startActivity(intent);
                    }
                });
                if (SPTool.getSessionValue(SQSP.xieyi).equals("1")){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (SPTool.getSessionValue(SQSP.Shi).equals("Shi")){
                                startActivity(new Intent(StartActivity.this, MainActivity.class));
                                finish();
                            }else {
                                noticeImage();
                            }

                        }
                    },3000);
                }else{
                    actionDialog.show();
                }
                Log.i(TAG, "onSuccess: "+"走onSuccess");
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    //首页通知图
    private void noticeImage() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "noticeImage");
        params.put("city",SPTool.getSessionValue(SQSP.Shi));
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new BaseCallback<NoticeImagebean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, final NoticeImagebean resultBean) {
                if (resultBean.getState().equals("1")){
                    Intent intent = new Intent(StartActivity.this,AdvertisingActivity.class);
                    intent.putExtra("url",resultBean.getImage());
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mlocationClient.stopLocation();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                SPTool.addSessionMap(SQSP.Shi, amapLocation.getCity());
                Log.i(TAG, "onLocationChanged: hahahhahhah");
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }





}
