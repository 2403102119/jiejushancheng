package com.lxkj.jieju.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.jieju.App;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Checkphonebean;
import com.lxkj.jieju.Bean.Commonbean;
import com.lxkj.jieju.Bean.Yzmcodebean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.MainActivity;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.MyCountDownTimer;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.StringUtil_li;
import com.lxkj.jieju.Utils.ToastFactory;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class ShortcutActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_mimadenglu,tv_login,faCode;
    private EditText et_phone,edit2;
    private String yzmcode;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_shortcut);
        setTopTitle("");
        tv_mimadenglu = findViewById(R.id.tv_mimadenglu);
        tv_login = findViewById(R.id.tv_login);
        et_phone = findViewById(R.id.et_phone);
        faCode = findViewById(R.id.faCode);
        edit2 = findViewById(R.id.edit2);
    }

    @Override
    protected void initEvent() {
        tv_mimadenglu.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        faCode.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_mimadenglu://密码登录
                finish();
                break;
            case R.id.tv_login://登录
                if (StringUtil_li.isSpace(et_phone.getText().toString())){
                    showToast("请输入手机号");
                    return;
                } if (StringUtil_li.isSpace(edit2.getText().toString())){
                    showToast("请输入手机号");
                    return;
                }
                if (!edit2.getText().toString().equals(yzmcode)){
                    showToast("请输入正确的验证码");
                    return;
                }
                checkPhone(et_phone.getText().toString());
                break;
            case R.id.faCode://获取验证码
                if (StringUtil_li.isSpace(et_phone.getText().toString())){
                    showToast("请输入手机号");
                    return;
                }
                getValidateCode(et_phone.getText().toString());
                break;
        }
    }



    //登录
    private void userLogin(String type, String phone,String password, String token) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","userLogin");
        params.put("type",type);
        params.put("phone", phone);
        params.put("password", password);
        params.put("token", token);

        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Commonbean>(mContext) {
            @Override
            public void onSuccess(Response response, Commonbean resultBean) {
                ToastFactory.getToast(mContext, resultBean.resultNote).show();

                if (resultBean.getResult().equals("0")) {
                    String uid = resultBean.getUid();
                    SPTool.addSessionMap(SQSP.uid, uid);
                    SPTool.addSessionMap(SQSP.isLogin, true);
                    App.login = true;
                    Intent intent = new Intent(ShortcutActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }


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
                    Intent intent = new Intent(mContext,WriteActivity.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }else {
                    userLogin("1",phone,"",SPTool.getSessionValue(SQSP.JupshID));
                }

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
                Log.i("123456", "onSuccess: "+yzmcode);
                //设置短信验证码多少秒后重新获取
                MyCountDownTimer timer = new MyCountDownTimer(ShortcutActivity.this, faCode, 60 * 1000, 1000);
                timer.start();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }

}
