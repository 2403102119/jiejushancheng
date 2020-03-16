package com.lxkj.jieju.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Yzmcodebean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.ResultBean;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.MyCountDownTimer;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.StringUtil_li;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :修改手机号
 */
public class ChangephoneActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_login,oldCode,tv_phone,newfaCode;
    private String code,phone,ymcode,newymcode;
    private EditText et_newcode,et_oldcode,et_password;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_changephone);
        setTopTitle("修改手机号");

        tv_login = findViewById(R.id.tv_login);
        oldCode = findViewById(R.id.oldCode);
        tv_phone = findViewById(R.id.tv_phone);
        newfaCode = findViewById(R.id.newfaCode);
        et_newcode = findViewById(R.id.et_newcode);
        et_oldcode = findViewById(R.id.et_oldcode);
        et_password = findViewById(R.id.et_password);
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
        oldCode.setOnClickListener(this);
        newfaCode.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        phone = getIntent().getStringExtra("phone");
        tv_phone.setText(StringUtil_li.replacePhone(phone));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                if (StringUtil_li.isSpace(et_oldcode.getText().toString())){
                    showToast("请输入旧手机验证码");
                    return;
                }
                if (!ymcode.equals(et_oldcode.getText().toString())){
                    showToast("旧手机验证码输入错误");
                    return;
                }
                if (StringUtil_li.isSpace(et_password.getText().toString())){
                    showToast("请输入新手机号码");
                    return;
                }
                if (StringUtil_li.isSpace(et_newcode.getText().toString())){
                    showToast("请输入新手机验证码");
                    return;
                }
                if (!newymcode.equals(et_newcode.getText().toString())){
                    showToast("新手机验证码输入错误");
                    return;
                }
                updateUserPhone(et_password.getText().toString());
                break;
            case R.id.oldCode:
                code = "1";
                getValidateCode(phone);
                break;
            case R.id.newfaCode:
                if (!ymcode.equals(et_oldcode.getText().toString())){
                    showToast("旧手机验证码输入错误");
                    return;
                }
                if (StringUtil_li.isSpace(et_password.getText().toString())){
                    showToast("请输入新手机号码");
                    return;
                }
                code = "2";
                getValidateCode(et_password.getText().toString());
                break;
        }
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
                if (code.equals("1")){

                    ymcode = resultBean.getCode();
                    //设置短信验证码多少秒后重新获取
                    MyCountDownTimer timer = new MyCountDownTimer(ChangephoneActivity.this, oldCode, 60 * 1000, 1000);
                    timer.start();
                }else {
                    newymcode = resultBean.getCode();
                    //设置短信验证码多少秒后重新获取
                    MyCountDownTimer timer = new MyCountDownTimer(ChangephoneActivity.this, newfaCode, 60 * 1000, 1000);
                    timer.start();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }
    //修改手机号
    private void updateUserPhone(final String newPhone) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "updateUserPhone");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("newPhone",newPhone);
        OkHttpHelper.getInstance().post_json(ChangephoneActivity.this, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(ChangephoneActivity.this) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
                Intent intent = new Intent();
                intent.putExtra("phone",newPhone);
                setResult(333,intent);
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
}
