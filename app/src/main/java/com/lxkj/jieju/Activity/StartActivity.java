package com.lxkj.jieju.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.AnimationUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
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
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

import static com.lxkj.jieju.App.mContext;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";
    private Handler handler=new Handler();
    private Context context ;
    private ActionDialog actionDialog ;


    /*----------高德定位---------------*/
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();

    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(App.getContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);

        //locationClient.startLocation();
        startLocation();
    }
    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        //根据控件的选择，重新设置定位参数
        //resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }
    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void stopLocation() {
        locationClient = new AMapLocationClient(App.getContext());
        // 停止定位
        locationClient.stopLocation();
    }
    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(10000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
                String result = Utils.getLocationStr(loc);


                Log.i(TAG, "onLocationChanged: " + result);
            } else {
                Log.i(TAG, "onLocationChanged: 定位失败");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).navigationBarColor(R.color.white).statusBarDarkFont(true).init();
        setContentView(R.layout.activity_start);
        privacyPolicy();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MPermissions.requestPermissions(this, SQSP.PMS_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            );
        } else {
            pmsLocationSuccess();
        }

    }
    @PermissionGrant(SQSP.PMS_LOCATION)
    public void pmsLocationSuccess() {
        initLocation();
        //权限授权成功
        //ToastFactory.getToast(mContext, "成功").show();
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
        stopLocation();
    }
}
