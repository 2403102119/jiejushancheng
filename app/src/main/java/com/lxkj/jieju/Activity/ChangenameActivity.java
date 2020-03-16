package com.lxkj.jieju.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.ResultBean;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.StringUtil_li;

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
        rightText.setVisibility(View.VISIBLE);
        rightText.setText("保存");
    }

    @Override
    protected void initEvent() {
        rightText.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rightText:
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
