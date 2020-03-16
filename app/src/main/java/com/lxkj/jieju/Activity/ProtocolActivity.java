package com.lxkj.jieju.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lxkj.jieju.Adapter.ProtocolAdapter;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.privacyBean;
import com.lxkj.jieju.Http.BaseCallback;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.R;
import com.lxkj.jieju.Uri.NetClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class ProtocolActivity extends BaseActivity {
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    ProtocolAdapter adapter;
    List<privacyBean.DataListBean> list=new ArrayList<>();
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_protocol);
        setTopTitle("用户协议");
        recycle = findViewById(R.id.recycle);
    }

    @Override
    protected void initEvent() {
        layoutManager = new LinearLayoutManager(ProtocolActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new ProtocolAdapter(ProtocolActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new ProtocolAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                Intent intent = new Intent(ProtocolActivity.this,WebViewActivity.class);
                intent.putExtra("webTitle",list.get(firstPosition).getTitle());
                intent.putExtra("webUrl",list.get(firstPosition).getUrl());
                startActivity(intent);

            }
        });
    }

    @Override
    protected void initData() {
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
                list.clear();
                list.addAll(resultBean.getDataList());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
}
