package com.lxkj.jieju.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.jieju.App;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.BindingBean;
import com.lxkj.jieju.Bean.Checkphonebean;
import com.lxkj.jieju.Bean.Servicecontextbean;
import com.lxkj.jieju.Bean.Yzmcodebean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.MainActivity;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.MD5Util;
import com.lxkj.jieju.Utils.MyCountDownTimer;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.StringUtil_li;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class BindingActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_login,faCode,tv_agreement;
    private EditText edit1,edit2,et_3,et_4,et_5;
    private String loginType,openId,yzmcode,nickname,usericon;
    private String contentUrl,bangding = "";
    private static final String TAG = "BindingActivity";
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_binding);
        setTopTitle("绑定手机号");

        tv_login = findViewById(R.id.tv_login);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        et_3 = findViewById(R.id.et_3);
        et_4 = findViewById(R.id.et_4);
        et_5 = findViewById(R.id.et_5);
        faCode = findViewById(R.id.faCode);
        tv_agreement = findViewById(R.id.tv_agreement);
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
        faCode.setOnClickListener(this);
        tv_agreement.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        loginType = getIntent().getStringExtra("loginType");
        openId = getIntent().getStringExtra("openId");
        nickname = getIntent().getStringExtra("nickname");
        usericon = getIntent().getStringExtra("usericon");
        Log.i(TAG, "onSuccess: "+openId);
        serviceContent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                if (StringUtil_li.isSpace(edit1.getText().toString())){
                    showToast("请输入手机号");
                    return;
                }
                if (StringUtil_li.isSpace(edit2.getText().toString())){
                    showToast("请输入验证码");
                    return;
                }
                if (!edit2.getText().toString().equals(yzmcode)){
                    showToast("验证码不正确");
                    return;
                }
                if (StringUtil_li.isSpace(bangding)){
                    Intent intent1 = new Intent(mContext,WriteActivity.class);
                    intent1.putExtra("phone",edit1.getText().toString());
                    intent1.putExtra("type","1");
                    intent1.putExtra("loginType",loginType);
                    intent1.putExtra("openId",openId);
                    intent1.putExtra("usericon",usericon);
                    intent1.putExtra("nickname",nickname);
                    startActivity(intent1);
                }else {
                    bindPhone(loginType,openId,edit1.getText().toString());
                }

                break;
            case R.id.faCode:
                if (StringUtil_li.isSpace(edit1.getText().toString())){
                    showToast("请输入手机号");
                    return;
                }
                checkPhone(edit1.getText().toString());
                break;
            case R.id.tv_agreement:
                Intent intent = new Intent(BindingActivity.this,WebViewActivity.class);
                intent.putExtra("webTitle","用户注册协议");
                intent.putExtra("webUrl",contentUrl);
                startActivity(intent);
                break;
        }
    }


    //绑定手机号
    private void bindPhone(String loginType,String openId,String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","bindPhone");
        params.put("loginType", loginType);
        params.put("openId", openId);
        params.put("phone", phone);
//        params.put("password", password);
//        params.put("name", name);
//        params.put("icon", icon);
//        params.put("address", address);
        params.put("token", SPTool.getSessionValue(SQSP.JupshID));
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<BindingBean>(mContext) {
            @Override
            public void onSuccess(Response response, BindingBean resultBean) {
                showToast(resultBean.getResultNote());
                String uid = resultBean.getUid();
                SPTool.addSessionMap(SQSP.uid, uid);
                SPTool.addSessionMap(SQSP.isLogin, true);
                App.login = true;
                Intent intent = new Intent(BindingActivity.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }
    //验证手机号是否注册
    private void checkPhone(final String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","checkPhone");
        params.put("phone", phone);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Checkphonebean>(mContext) {
            @Override
            public void onSuccess(Response response, Checkphonebean resultBean) {
                if (resultBean.getExistence().equals("0")){
                    bangding = "";
                }else {
                    bangding = "已存在";
                }
                getValidateCode(phone);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }

    //获取验证码
    private void getValidateCode(String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","getValidateCode");
        params.put("phone", phone);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Yzmcodebean>(mContext) {
            @Override
            public void onSuccess(Response response, Yzmcodebean resultBean) {
                showToast(resultBean.getResultNote());
                yzmcode = resultBean.getCode();
                //设置短信验证码多少秒后重新获取
                MyCountDownTimer timer = new MyCountDownTimer(BindingActivity.this, faCode, 60 * 1000, 1000);
                timer.start();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }

    //注册协议
    private void serviceContent() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","serviceContent");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Servicecontextbean>(mContext) {
            @Override
            public void onSuccess(Response response, Servicecontextbean resultBean) {
                contentUrl = resultBean.getContentUrl();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }
}
