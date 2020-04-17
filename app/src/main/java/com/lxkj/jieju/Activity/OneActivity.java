package com.lxkj.jieju.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jieju.Adapter.SpecialAdapter;
import com.lxkj.jieju.App;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Areabean;
import com.lxkj.jieju.Bean.areaProductListbean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.ToastFactory;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :专区一
 */
public class OneActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    SpecialAdapter adapter;
    List<areaProductListbean.DataListBean> list=new ArrayList<>();
    private String image5;
    private ImageView im_top,finishBack;
    private String position;
    private RelativeLayout rl_title;
    private TextView titleName;
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private static final String TAG = "OneActivity";
    private String title;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_one);
        baseTop.setVisibility(View.GONE);
        recycle = findViewById(R.id.recycle);
        im_top = findViewById(R.id.im_top);
        finishBack = findViewById(R.id.finishBack);
        recycle = findViewById(R.id.recycle);
        rl_title = findViewById(R.id.rl_title);
        titleName = findViewById(R.id.titleName);
        smart = findViewById(R.id.smart);

    }

    @Override
    protected void initEvent() {

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    areaProductList(String.valueOf(pageNoIndex),position,SPTool.getSessionValue(SQSP.Shi));
                    Log.i(TAG, "onLoadMore: 执行上拉加载");
                    smart.finishLoadMore();
                } else {
                    Log.i(TAG, "onLoadMore: 相等不可刷新");
                    //smartRefreshLayout.setEnableLoadMore(false);
                    smart.finishRefresh(2000, true);//传入false表示刷新失败
                    smart.finishLoadMore();
                }
            }
        });

        finishBack.setOnClickListener(this);
        layoutManager = new LinearLayoutManager(OneActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new SpecialAdapter(OneActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new SpecialAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, "请先登录").show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent = new Intent(OneActivity.this,DeatilsActivity.class);
                intent.putExtra("productid",list.get(firstPosition).getProductid());
                startActivity(intent);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finishBack:
                finish();
                break;
        }
    }

    @Override
    protected void initData() {

        position = getIntent().getStringExtra("type");
        image5 = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("title");
        titleName.setText(title);
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.bai)
                .placeholder(R.mipmap.bai))
                .load(image5)
                .into(im_top);
    }
    //专区商品列表
    private void areaProductList(String nowPage,String position,String city) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "areaProductList");
        params.put("type",position);
        params.put("nowPage",nowPage);
        params.put("pageCount", SQSP.pagerSize);
        params.put("city", city);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<areaProductListbean>(mContext) {
            @Override
            public void onSuccess(Response response, areaProductListbean resultBean) {
//                smart.finishRefresh();

                if (resultBean.getDataList() != null) {

                    totalPage = resultBean.getTotalPage();
                    if (pageNoIndex == 1) {
                        list.clear();
                    }
                    list.addAll(resultBean.getDataList());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
//                smart.finishRefresh();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        areaProductList("1",position,SPTool.getSessionValue(SQSP.Shi));
    }
}
