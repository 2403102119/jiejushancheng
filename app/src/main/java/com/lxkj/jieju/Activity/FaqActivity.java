package com.lxkj.jieju.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lxkj.jieju.Adapter.FaqAdapter;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Faqbean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.Uri.NetClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :常见问题
 */
public class FaqActivity extends BaseActivity {

    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    FaqAdapter adapter;
    List<Faqbean.DataListBean> list=new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_faq);
        setTopTitle("常见问题");
        recycle = findViewById(R.id.recycle);
    }

    @Override
    protected void initEvent() {
        layoutManager = new LinearLayoutManager(FaqActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new FaqAdapter(FaqActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new FaqAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                Intent intent = new Intent(FaqActivity.this,ProblemsActivity.class);
                intent.putExtra("title",list.get(firstPosition).getTitle());
                intent.putExtra("context",list.get(firstPosition).getContent());
                startActivity(intent);

            }
        });
    }

    @Override
    protected void initData() {
        faqList();
    }

    //常见问题
    private void faqList() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "faqList");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Faqbean>(mContext) {
            @Override
            public void onSuccess(Response response, Faqbean resultBean) {

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
