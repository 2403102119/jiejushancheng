package com.lxkj.hrhc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.hrhc.App;
import com.lxkj.hrhc.Base.BaseActivity;
import com.lxkj.hrhc.Bean.UserInfobean;
import com.lxkj.hrhc.Http.OkHttpHelper;
import com.lxkj.hrhc.Http.ResultBean;
import com.lxkj.hrhc.Http.SpotsCallBack;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.SQSP;
import com.lxkj.hrhc.Uri.NetClass;
import com.lxkj.hrhc.Utils.SPTool;
import com.lxkj.hrhc.Utils.StringUtil_li;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class ChangenameActivity extends BaseActivity implements View.OnClickListener {


    private TextView tv_login,tv_name;
    private String name;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_changename);
        setTopTitle("修改昵称");
        tv_login = findViewById(R.id.tv_login);
        tv_name = findViewById(R.id.tv_name);
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                if (StringUtil_li.isSpace(tv_name.getText().toString())){
                    showToast("请输入昵称");
                    return;
                }
                updateUserName(tv_name.getText().toString());
                break;
        }
    }

    //修改昵称
    private void updateUserName(final String name) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "updateUserName");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("name",name);
        OkHttpHelper.getInstance().post_json(ChangenameActivity.this, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(ChangenameActivity.this) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
                Intent intent = new Intent();
                intent.putExtra("name",name);
                setResult(222,intent);
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
}
