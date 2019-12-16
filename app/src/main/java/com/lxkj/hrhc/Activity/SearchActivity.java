package com.lxkj.hrhc.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lxkj.hrhc.Adapter.WarehouseAdapter;
import com.lxkj.hrhc.Base.BaseActivity;
import com.lxkj.hrhc.Bean.Areabean;
import com.lxkj.hrhc.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :搜索结果
 */
public class SearchActivity extends BaseActivity {

    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    WarehouseAdapter adapter;
    List<Areabean.DataListBean> list=new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_search);
        setTopTitle("搜索结果");
        recycle = findViewById(R.id.recycle);
    }

    @Override
    protected void initEvent() {
        layoutManager = new LinearLayoutManager(SearchActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new WarehouseAdapter(SearchActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new WarehouseAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }
        });
    }

    @Override
    protected void initData() {

    }
}
