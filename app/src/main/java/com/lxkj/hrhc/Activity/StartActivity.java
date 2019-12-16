package com.lxkj.hrhc.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lxkj.hrhc.App;
import com.lxkj.hrhc.Bean.AboutUsbean;
import com.lxkj.hrhc.Bean.privacyBean;
import com.lxkj.hrhc.Http.BaseCallback;
import com.lxkj.hrhc.Http.OkHttpHelper;
import com.lxkj.hrhc.Http.ResultBean;
import com.lxkj.hrhc.Http.SpotsCallBack;
import com.lxkj.hrhc.MainActivity;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.SQSP;
import com.lxkj.hrhc.Uri.NetClass;
import com.lxkj.hrhc.Utils.AppManager;
import com.lxkj.hrhc.Utils.SPTool;
import com.lxkj.hrhc.Utils.StringUtil_li;
import com.lxkj.hrhc.Utils.Utils;
import com.lxkj.hrhc.View.ActionDialog;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";
    private Handler handler=new Handler();
    private Context context ;
    private ActionDialog actionDialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).navigationBarColor(R.color.white).statusBarDarkFont(true).init();
        setContentView(R.layout.activity_start);
        privacyPolicy();


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
                actionDialog = new ActionDialog(StartActivity.this,"温馨提示","请您在使用前仔细阅读并同意"+resultBean.getDataList().get(0).getTitle()+"和"+resultBean.getDataList().get(2).getTitle()+",其中的重点条款已为您标注，方便您了解自己的权利。","不同意","同意并使用");
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
                            startActivity(new Intent(StartActivity.this, MainActivity.class));
                            finish();
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



}
