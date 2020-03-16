package com.lxkj.jieju.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Checkphonebean;
import com.lxkj.jieju.Bean.Commonbean;
import com.lxkj.jieju.Bean.Yzmcodebean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.SpotsCallBack;
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

public class ForgetActivity extends BaseActivity implements View.OnClickListener{

    private TextView faCode,tv_login;
    private EditText edit1,tv_password,edit2,tv_affirm_password;
    private String yzmcode;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_forget);
        setTopTitle("找回密码");
        faCode = findViewById(R.id.faCode);
        edit1 = findViewById(R.id.edit1);
        tv_login = findViewById(R.id.tv_login);
        tv_password = findViewById(R.id.tv_password);
        edit2 = findViewById(R.id.edit2);
        tv_affirm_password = findViewById(R.id.tv_affirm_password);
    }

    @Override
    protected void initEvent() {
        faCode.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.faCode:
                if (StringUtil_li.isSpace(edit1.getText().toString())){
                    showToast("请输入手机号");
                    return;
                }
                getValidateCode(edit1.getText().toString());
                break;
            case R.id.tv_login:
                if (StringUtil_li.isSpace(edit1.getText().toString())){
                    showToast("请输入手机号");
                    return;
                }
                if (StringUtil_li.isSpace(edit2.getText().toString())){
                    showToast("请输入验证码");
                    return;
                }
                if (!yzmcode.equals(edit2.getText().toString())){
                    showToast("验证码不正确");
                    return;
                }
                if (StringUtil_li.isSpace(tv_password.getText().toString())){
                    showToast("请输入新密码");
                    return;
                }
                if (StringUtil_li.isSpace(tv_affirm_password.getText().toString())){
                    showToast("请再输入一次新密码");
                    return;
                }
                if (!tv_affirm_password.getText().toString().equals(tv_password.getText().toString())){
                    showToast("新密码验证失败");
                    return;
                }
                forgotPassword(edit1.getText().toString(), MD5Util.encrypt(tv_password.getText().toString()));
                break;
        }
    }


//    //验证手机号是否注册
//    private void checkPhone(final String phone) {
//        Map<String, String> params = new HashMap<>();
//        params.put("cmd","checkPhone");
//        params.put("phone", phone);
//        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Checkphonebean>(mContext) {
//            @Override
//            public void onSuccess(Response response, Checkphonebean resultBean) {
//                if (resultBean.getExistence().equals("0")){
//                    getValidateCode(phone);
//                }else {
//                    showToast("手机号码已存在");
//                }
//            }
//
//            @Override
//            public void onError(Response response, int code, Exception e) {
//
//            }
//        });
//
//
//    }

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
                MyCountDownTimer timer = new MyCountDownTimer(ForgetActivity.this, faCode, 60 * 1000, 1000);
                timer.start();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }

    //忘记密码
    private void forgotPassword(String phone, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","forgotPassword");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("phone", phone);
        params.put("password", password);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Commonbean>(mContext) {
            @Override
            public void onSuccess(Response response, Commonbean resultBean) {
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }
}
