package com.lxkj.hrhc.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.hrhc.Base.BaseActivity;
import com.lxkj.hrhc.Bean.Yzmcodebean;
import com.lxkj.hrhc.Http.OkHttpHelper;
import com.lxkj.hrhc.Http.ResultBean;
import com.lxkj.hrhc.Http.SpotsCallBack;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.SQSP;
import com.lxkj.hrhc.Uri.NetClass;
import com.lxkj.hrhc.Utils.MyCountDownTimer;
import com.lxkj.hrhc.Utils.SPTool;
import com.lxkj.hrhc.Utils.StringUtil_li;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :更改密码
 */
public class ChangepasswordActivity extends BaseActivity implements View.OnClickListener{

   private String phone,yzmcode = "1234564655131354561651516";
   private TextView tv_phone,faCode,tv_login;
   private EditText edit2,et_oldpassword,et_newPassword;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_changepassword);
        setTopTitle("更改密码");
        tv_phone = findViewById(R.id.tv_phone);
        faCode = findViewById(R.id.faCode);
        tv_login = findViewById(R.id.tv_login);
        edit2 = findViewById(R.id.edit2);
        et_oldpassword = findViewById(R.id.et_oldpassword);
        et_newPassword = findViewById(R.id.et_newPassword);
    }

    @Override
    protected void initEvent() {
        faCode.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        phone = getIntent().getStringExtra("phone");
        tv_phone.setText(StringUtil_li.replacePhone(phone));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.faCode:
                getValidateCode(phone);
                break;
            case R.id.tv_login:
                if (StringUtil_li.isSpace(edit2.getText().toString())){
                    showToast("请输入验证码");
                    return;
                }
                if (!yzmcode.equals(edit2.getText().toString())){
                    showToast("验证码不正确");
                    return;
                }
                if (StringUtil_li.isSpace(et_oldpassword.getText().toString())){
                    showToast("请输入旧密码");
                    return;
                }
                if (StringUtil_li.isSpace(et_newPassword.getText().toString())){
                    showToast("请输入新密码");
                    return;
                }
                if (et_oldpassword.getText().toString().equals(et_newPassword.getText().toString())){
                    showToast("旧密码与新密码不能相同");
                    return;
                }
                updateUserPassword(et_oldpassword.getText().toString(),et_newPassword.getText().toString());
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
                yzmcode = resultBean.getCode();
                //设置短信验证码多少秒后重新获取
                MyCountDownTimer timer = new MyCountDownTimer(ChangepasswordActivity.this, faCode, 180 * 1000, 1000);
                timer.start();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }
    //更改密码
    private void updateUserPassword(String oldPwd,String newPwd) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","updateUserPassword");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("oldPwd",oldPwd);
        params.put("newPwd",newPwd);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }

}
