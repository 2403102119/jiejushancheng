package com.lxkj.jieju.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lxkj.jieju.Adapter.AgencyAdapter;
import com.lxkj.jieju.Adapter.Broketragedapter;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Agencybean;
import com.lxkj.jieju.Bean.Brokeragebean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.ResultBean;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.SPTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class AgencyActivity extends BaseActivity {
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    AgencyAdapter adapter;
    List<Agencybean.DataListBean> list=new ArrayList<>();
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_agency);
        setTopTitle("选择城市");
        recycle = findViewById(R.id.recycle);
    }

    @Override
    protected void initEvent() {
        layoutManager = new LinearLayoutManager(AgencyActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new AgencyAdapter(AgencyActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new AgencyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                Intent intent = new Intent(mContext,AgencyitemActivity.class);
                intent.putExtra("id",list.get(firstPosition).getId());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        cityList();
    }
    //获取城市列表
    private void cityList() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "areaList");
        params.put("type", "1");
        params.put("id", "");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Agencybean>(mContext) {
            @Override
            public void onSuccess(Response response, Agencybean resultBean) {
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
