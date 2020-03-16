package com.lxkj.jieju.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lxkj.jieju.Adapter.AgencyAdapter;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Agencybean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class AgencyitemActivity extends BaseActivity {

    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    AgencyAdapter adapter;
    List<Agencybean.DataListBean> list=new ArrayList<>();
    private String id;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_agencyitem);
        setTopTitle("选择城市");
        recycle = findViewById(R.id.recycle);
    }

    @Override
    protected void initEvent() {
        layoutManager = new LinearLayoutManager(AgencyitemActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new AgencyAdapter(AgencyitemActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new AgencyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                SQSP.dailichengshi = list.get(firstPosition).getName();
//                Intent intent = new Intent();
//                intent.putExtra("location",list.get(firstPosition).getName());
//                startActivity(intent);
//                setResult(222);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        cityList(id);
    }
    //获取城市列表
    private void cityList(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "areaList");
        params.put("type", "2");
        params.put("id", id);
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
