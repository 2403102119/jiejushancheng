package com.lxkj.jieju.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jieju.Adapter.LeftAdapter;
import com.lxkj.jieju.Adapter.RightAdapter;
import com.lxkj.jieju.Adapter.SizeAdapter;
import com.lxkj.jieju.Adapter.StairAdapter;
import com.lxkj.jieju.App;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.FirsePagebean;
import com.lxkj.jieju.Bean.Productlistbean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.StringUtil_li;
import com.lxkj.jieju.Utils.ToastFactory;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class ClassifyActivity extends BaseActivity implements View.OnClickListener{

    private String childCategoryId;
    private RecyclerView recyclerViewRight,size_recycle;
    LinearLayoutManager layoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    GridLayoutManager gridLayoutManager;
    private SizeAdapter sizeAdapter;
    private RightAdapter rightAdapter;
    private List<String> stairlist = new ArrayList<>();
    private List<String> sizelist = new ArrayList<>();
    private List<String> stairidlist = new ArrayList<>();
    private List<Productlistbean.DataListBean> secondlist = new ArrayList<>();
    private LinearLayout ll_sales,ll_price;
    private TextView tv_zonghe,tv_xiaoliang,tv_jiage;
    private ImageView im_shang,im_xia,im_back;
    private static final String TAG = "ClassifyActivity";
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private String sprttype = "0",t = "1";
    private String type = "2",position1 = "0",text = "";
    private String ID,jignxiao;
    private EditText et_search;
    private View view_size;
    private String size = "";
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_classify);
        recyclerViewRight = findViewById(R.id.RecyclerViewRight);
        ll_sales = findViewById(R.id.ll_sales);
        tv_zonghe = findViewById(R.id.tv_zonghe);
        tv_xiaoliang = findViewById(R.id.tv_xiaoliang);
        tv_jiage = findViewById(R.id.tv_jiage);
        ll_price = findViewById(R.id.ll_price);
        im_xia = findViewById(R.id.im_xia);
        im_shang = findViewById(R.id.im_shang);
        smart = findViewById(R.id.smart);
        et_search = findViewById(R.id.et_search);
        im_back = findViewById(R.id.im_back);
        size_recycle = findViewById(R.id.size_recycle);
        view_size = findViewById(R.id.view_size);
        baseTop.setVisibility(View.GONE);
        size_recycle.setVisibility(View.GONE);
        view_size.setVisibility(View.GONE);
    }

    @Override
    protected void initEvent() {
        ll_sales.setOnClickListener(this);
        tv_zonghe.setOnClickListener(this);
        ll_price.setOnClickListener(this);
        im_back.setOnClickListener(this);

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                if (jignxiao.equals("1")){
                    agentProductList(SQSP.Shi_item,sprttype,String.valueOf(pageNoIndex),size);
                }else {
                    if (!text.equals("")){
                        searchProduct((SQSP.Shi_item),text,sprttype,String.valueOf(pageNoIndex),size);
                    }else {
                        productList(childCategoryId,sprttype,String.valueOf(pageNoIndex),size);
                    }
                }

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    Log.i(TAG, "onLoadMore: 执行上拉加载");
                    if (jignxiao.equals("1")){
                        agentProductList(SQSP.Shi_item,sprttype,String.valueOf(pageNoIndex),size);
                    }else {
                        if (!text.equals("")){
                            searchProduct(SQSP.Shi_item,text,sprttype,String.valueOf(pageNoIndex),size);
                        }else {
                            productList(childCategoryId,sprttype,String.valueOf(pageNoIndex),size);
                        }
                    }
                    smart.finishLoadMore();
                } else {
                    Log.i(TAG, "onLoadMore: 相等不可刷新");
                    //smartRefreshLayout.setEnableLoadMore(false);
                    smart.finishRefresh(2000, true);//传入false表示刷新失败
                    smart.finishLoadMore();
                }
            }
        });
        sizelist.add("50cm");
        sizelist.add("60cm");
        sizelist.add("70cm");
        sizelist.add("80cm");
        sizelist.add("90cm");
        sizelist.add("1m");
        sizelist.add("1.1m");
        sizelist.add("1.2cm");

        gridLayoutManager = new GridLayoutManager(mContext,8);
//        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL|GridLayoutManager.DEFAULT_SPAN_COUNT);

        size_recycle.setLayoutManager(gridLayoutManager);
        sizeAdapter = new SizeAdapter(mContext, sizelist);
        size_recycle.setAdapter(sizeAdapter);
        sizeAdapter.setOnItemClickListener(new SizeAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                size_recycle.setVisibility(View.GONE);
                view_size.setVisibility(View.GONE);

                size = sizelist.get(position);
                if (jignxiao.equals("1")){
                    agentProductList(SQSP.Shi_item,sprttype,String.valueOf(pageNoIndex),size);
                }else {
                    if (!text.equals("")){
                        searchProduct(SQSP.Shi_item,text,sprttype,String.valueOf(pageNoIndex),size);
                    }else {
                        productList(childCategoryId,sprttype,String.valueOf(pageNoIndex),size);
                    }
                }

            }
        });
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerViewRight.setLayoutManager(staggeredGridLayoutManager);
        rightAdapter=new RightAdapter(mContext,secondlist);
        recyclerViewRight.setAdapter(rightAdapter);
        rightAdapter .setOnItemClickListener(new RightAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener( int position) {
//                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
//                    ToastFactory.getToast(mContext, "请先登录").show();
//                    startActivity(new Intent(mContext, LoginActivity.class));
//                    //finish();
//                    return;
//                }
                Intent intent = new Intent(ClassifyActivity.this,DeatilsActivity.class);
                intent.putExtra("productid",secondlist.get(position).getProductid());
                startActivity(intent);
            }
        });


        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                 /*判断是否是“GO”键*/
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    return true;
                }

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (!TextUtils.isEmpty(et_search.getText().toString())) {
                                searchProduct(SQSP.Shi_item,et_search.getText().toString(),sprttype,String.valueOf(pageNoIndex),size);
                    } else {
                        ToastFactory.getToast(mContext, "关键字不能为空").show();
                    }
                    return true;
                }
                return false;
            }
        });




    }

    @Override
    protected void initData() {
        if (!StringUtil_li.isSpace(getIntent().getStringExtra("childCategoryId"))){
            childCategoryId = getIntent().getStringExtra("childCategoryId");
        }
        if (!StringUtil_li.isSpace(getIntent().getStringExtra("text"))){
            text = getIntent().getStringExtra("text");
        }
        if (StringUtil_li.isSpace(getIntent().getStringExtra("type"))){
            jignxiao = "2";
            if (!text.equals("")){
                et_search.setText(text);
                searchProduct(SQSP.Shi_item,text,sprttype,"1",size);
            }else {
                productList(childCategoryId,sprttype,"1",size);
            }
        }else {
            jignxiao = getIntent().getStringExtra("type");
            agentProductList(SQSP.Shi_item,sprttype,"1",size);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_sales://尺寸
                tv_zonghe.setTextColor(getResources().getColor(R.color.black));
                tv_xiaoliang.setTextColor(getResources().getColor(R.color.red_them));
                tv_jiage.setTextColor(getResources().getColor(R.color.black));
                im_shang.setImageResource(R.mipmap.shang);
                im_xia.setImageResource(R.mipmap.xia);



                sprttype = "1";

                if (t.equals("0")){
                    im_shang.setImageResource(R.mipmap.shang);
                    t = "1";
                    size_recycle.setVisibility(View.GONE);
                    view_size.setVisibility(View.GONE);
                }else {
                    im_shang.setImageResource(R.mipmap.xia);
                    t = "0";

                    size_recycle.setVisibility(View.VISIBLE);
                    view_size.setVisibility(View.VISIBLE);
                }

                if (jignxiao.equals("1")){
                    agentProductList(SQSP.Shi_item,sprttype,String.valueOf(pageNoIndex),size);
                }else {
                    if (!text.equals("")){
                        searchProduct((SQSP.Shi_item),text,sprttype,String.valueOf(pageNoIndex),size);
                    }else {
                        productList(childCategoryId,sprttype,String.valueOf(pageNoIndex),size);
                    }
                }
                break;
            case R.id.tv_zonghe://综合
                tv_zonghe.setTextColor(getResources().getColor(R.color.red_them));
                tv_xiaoliang.setTextColor(getResources().getColor(R.color.black));
                tv_jiage.setTextColor(getResources().getColor(R.color.black));
                im_shang.setImageResource(R.mipmap.shang);
                im_xia.setImageResource(R.mipmap.xia);

                size_recycle.setVisibility(View.GONE);
                view_size.setVisibility(View.GONE);

                sprttype = "0";


                if (jignxiao.equals("1")){
                    agentProductList(SQSP.Shi_item,sprttype,String.valueOf(pageNoIndex),size);
                }else {
                    if (!text.equals("")){
                        searchProduct((SQSP.Shi_item),text,sprttype,String.valueOf(pageNoIndex),size);
                    }else {
                        productList(childCategoryId,sprttype,String.valueOf(pageNoIndex),size);
                    }
                }
                break;
            case R.id.ll_price://价格
                tv_zonghe.setTextColor(getResources().getColor(R.color.black));
                tv_xiaoliang.setTextColor(getResources().getColor(R.color.black));
                tv_jiage.setTextColor(getResources().getColor(R.color.red_them));
                im_xia.setImageResource(R.mipmap.xia);
                im_shang.setImageResource(R.mipmap.shang);

                size_recycle.setVisibility(View.GONE);
                view_size.setVisibility(View.GONE);

                if (sprttype.equals("2")){
                    type = "3";
                    im_xia.setImageResource(R.mipmap.shang);
                }else {
                    type = "2";
                    im_xia.setImageResource(R.mipmap.xia);
                }
                sprttype = type;
                if (jignxiao.equals("1")){
                    agentProductList(SQSP.Shi_item,sprttype,String.valueOf(pageNoIndex),size);
                }else {
                    if (!text.equals("")){
                        searchProduct((SQSP.Shi_item),text,sprttype,String.valueOf(pageNoIndex),size);
                    }else {
                        productList(childCategoryId,sprttype,String.valueOf(pageNoIndex),size);
                    }
                }
                break;
            case R.id.im_back:
                finish();
                break;
        }
    }

    //根据分类id获取商品列表
    private void productList(String childCategoryId,String sortType,String nowPage,String size) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "productList");
        params.put("childCategoryId",childCategoryId);
        params.put("sortType",sortType);
        params.put("nowPage",nowPage);
        params.put("pageCount", SQSP.pagerSize);
        params.put("size",size);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Productlistbean>(mContext) {
            @Override
            public void onSuccess(Response response, Productlistbean resultBean) {

                smart.finishRefresh();

                totalPage = resultBean.getTotalPage();
                if (pageNoIndex == 1) {
                    secondlist.clear();
                }
                secondlist.addAll(resultBean.getDataList());
                rightAdapter.notifyDataSetChanged();


            }

            @Override
            public void onError(Response response, int code, Exception e) {
                smart.finishRefresh();
            }
        });
    }
    //经销商商品库
    private void agentProductList(String city,String sortType,String nowPage,String size) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "agentProductList");
        params.put("city",city);
        params.put("sortType",sortType);
        params.put("nowPage",nowPage);
        params.put("pageCount", SQSP.pagerSize);
        params.put("size",size);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Productlistbean>(mContext) {
            @Override
            public void onSuccess(Response response, Productlistbean resultBean) {

                smart.finishRefresh();

                totalPage = resultBean.getTotalPage();
                if (pageNoIndex == 1) {
                    secondlist.clear();
                }
                secondlist.addAll(resultBean.getDataList());
                rightAdapter.notifyDataSetChanged();


            }

            @Override
            public void onError(Response response, int code, Exception e) {
                smart.finishRefresh();
            }
        });
    }
    //搜索商品
    private void searchProduct(String city,String keywords,String sortType,String nowPage,String size) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "searchProduct");
        params.put("city",city);
        params.put("keywords",keywords);
        params.put("sortType",sortType);
        params.put("nowPage",nowPage);
        params.put("pageCount", SQSP.pagerSize);
        params.put("size", size);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Productlistbean>(mContext) {
            @Override
            public void onSuccess(Response response, Productlistbean resultBean) {

                smart.finishRefresh();

                totalPage = resultBean.getTotalPage();
                if (pageNoIndex == 1) {
                    secondlist.clear();
                }
                secondlist.addAll(resultBean.getDataList());
                rightAdapter.notifyDataSetChanged();


            }

            @Override
            public void onError(Response response, int code, Exception e) {
                smart.finishRefresh();
            }
        });
    }
}
