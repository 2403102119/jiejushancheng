package com.lxkj.jieju.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxkj.jieju.Adapter.EvaluationAdapter;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Evluationbean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.ResultBean;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.StringUtil_li;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;
import top.zibin.luban.Luban;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :经销商入驻
 */
public class EvaluationActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_name,et_phone,et_site;
    private LinearLayout ll_site,ll_zhanwei;
    private TextView tv_site,tv_login,tv_zhuangtai;
    private String agentState;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_evaluation);
        setTopTitle("经销商入驻");
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_site = findViewById(R.id.et_site);
        ll_site = findViewById(R.id.ll_site);
        tv_site = findViewById(R.id.tv_site);
        tv_login = findViewById(R.id.tv_login);
        ll_zhanwei = findViewById(R.id.ll_zhanwei);
        tv_zhuangtai = findViewById(R.id.tv_zhuangtai);
    }

    @Override
    protected void initEvent() {
        ll_site.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        agentState = getIntent().getStringExtra("agentState");
        if (agentState.equals("1")){
            ll_zhanwei.setVisibility(View.VISIBLE);
            tv_zhuangtai.setText("正在后台审核，请耐心等待");
        }else if (agentState.equals("2")){
            ll_zhanwei.setVisibility(View.VISIBLE);
            tv_zhuangtai.setText("审核通过");
        }else {
            ll_zhanwei.setVisibility(View.GONE);
        }

//        if (!StringUtil_li.isSpace(getIntent().getStringExtra("location"))){
//            tv_site.setText(getIntent().getStringExtra("location"));
//        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 111&& resultCode == 222) {
//            tv_site.setText(data.getStringExtra("location"));
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_site://选择代理城市
                Intent intent = new Intent(mContext,AgencyActivity.class);
                startActivityForResult(intent,111);
                break;
            case R.id.tv_login://提交申请
                if (StringUtil_li.isSpace(et_name.getText().toString())){
                    showToast("请输入您的姓名");
                    return;
                }
                if (StringUtil_li.isSpace(et_phone.getText().toString())){
                    showToast("请输入您的手机号");
                    return;
                }
                if (!StringUtil_li.isPhone(et_phone.getText().toString())){
                    showToast("请输入正确的手机号");
                    return;
                }
                if (StringUtil_li.isSpace(et_site.getText().toString())){
                    showToast("请输入您的地址");
                    return;
                }
                if (StringUtil_li.isSpace(tv_site.getText().toString())){
                    showToast("请选择地址");
                    return;
                }
                addAgent(et_name.getText().toString(),et_phone.getText().toString(),et_site.getText().toString(),tv_site.getText().toString());
                break;
        }
    }


    //提交经销商申请
    private void addAgent(String name,String phone,String address,String city) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "addAgent");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("name", name);
        params.put("phone",phone);
        params.put("address",address);
        params.put("city",city);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                ll_zhanwei.setVisibility(View.VISIBLE);
                SQSP.dailichengshi="dailichengshi";
            }
            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SQSP.dailichengshi.equals("dailichengshi")){
            tv_site.setText("");
        }else {
            tv_site.setText(SQSP.dailichengshi);
        }
    }
}
