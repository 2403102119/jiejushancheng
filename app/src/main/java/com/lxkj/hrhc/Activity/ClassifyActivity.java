package com.lxkj.hrhc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.hrhc.Adapter.LeftAdapter;
import com.lxkj.hrhc.Adapter.RightAdapter;
import com.lxkj.hrhc.Base.BaseActivity;
import com.lxkj.hrhc.Bean.FirsePagebean;
import com.lxkj.hrhc.Bean.Productlistbean;
import com.lxkj.hrhc.Http.OkHttpHelper;
import com.lxkj.hrhc.Http.SpotsCallBack;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.SQSP;
import com.lxkj.hrhc.Uri.NetClass;
import com.lxkj.hrhc.Utils.SPTool;
import com.lxkj.hrhc.Utils.ToastFactory;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class ClassifyActivity extends BaseActivity implements View.OnClickListener{

    private String childCategoryId,title,categoryId;
    private RecyclerView recyclerViewLeft;
    private RecyclerView recyclerViewRight;
    LinearLayoutManager layoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;
    private List<String> stairlist = new ArrayList<>();
    private List<String> stairidlist = new ArrayList<>();
    private List<Productlistbean.DataListBean> secondlist = new ArrayList<>();
    private LinearLayout ll_sales,ll_price;
    private TextView tv_zonghe,tv_xiaoliang,tv_jiage;
    private ImageView im_shang,im_xia;
    private static final String TAG = "ClassifyActivity";
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private String sprttype;
    private String type,position1 = "0";
    private String ID;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_classify);
        recyclerViewLeft = findViewById(R.id.RecyclerViewLeft);
        recyclerViewRight = findViewById(R.id.RecyclerViewRight);
        ll_sales = findViewById(R.id.ll_sales);
        tv_zonghe = findViewById(R.id.tv_zonghe);
        tv_xiaoliang = findViewById(R.id.tv_xiaoliang);
        tv_jiage = findViewById(R.id.tv_jiage);
        ll_price = findViewById(R.id.ll_price);
        im_xia = findViewById(R.id.im_xia);
        im_shang = findViewById(R.id.im_shang);
        smart = findViewById(R.id.smart);
    }

    @Override
    protected void initEvent() {
        ll_sales.setOnClickListener(this);
        tv_zonghe.setOnClickListener(this);
        ll_price.setOnClickListener(this);

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                if (position1.equals("0")){
                    productList(categoryId,"",sprttype,String.valueOf(pageNoIndex));
                }else {

                    productList("",ID,sprttype,String.valueOf(pageNoIndex));
                }

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    if (position1.equals("0")){
                        productList(categoryId,"",sprttype,String.valueOf(pageNoIndex));
                    }else {
                        productList("",ID,sprttype,String.valueOf(pageNoIndex));
                    }
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
        layoutManager = new LinearLayoutManager(mContext);
        recyclerViewLeft.setLayoutManager(layoutManager);
        leftAdapter = new LeftAdapter(mContext, stairlist);
        recyclerViewLeft.setAdapter(leftAdapter);
        leftAdapter.setOnItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {

                if (stairlist.get(position).equals("全部")){
                    pageNoIndex = 1;
                    productList("",categoryId,"0","1");
                    position1 ="0";
                }else {
                    pageNoIndex = 1;
                    ID = stairidlist.get(position);
                    productList("",stairidlist.get(position),"0","1");
                    position1 ="1";
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

    }

    List<FirsePagebean.CategoryListBean.ChildrenListBean> childrenList;
    List<FirsePagebean.CategoryListBean> renList;
    @Override
    protected void initData() {
        title = getIntent().getStringExtra("title");
        categoryId = getIntent().getStringExtra("categoryId");
        type = getIntent().getStringExtra("type");
        FirsePagebean.CategoryListBean bean = ((FirsePagebean.CategoryListBean) getIntent().getSerializableExtra("bean"));
        if (null != bean){
            childrenList =  bean.getChildrenList();
            stairlist.clear();
            stairidlist.clear();
            stairlist.add("全部");
            stairidlist.add("sndghsalh");
            for (int i = 0; i <childrenList.size() ; i++) {
                stairlist.add(childrenList.get(i).getChildCategoryName());
                stairidlist.add(childrenList.get(i).getChildCategoryId());
                if (type.equals("0")){
                    leftAdapter.setSelectPosition(0);
                }else {
                    if (childrenList.get(i).getChildCategoryName().equals(title)){
                        leftAdapter.setSelectPosition(i+1);
                    }
                }

            }
        }
        leftAdapter.notifyDataSetChanged();

        setTopTitle(title);

        if (type.equals("0")){
            productList(categoryId,"","0","1");
        }else {
            productList("",categoryId,"0","1");
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_sales://销量
                tv_zonghe.setTextColor(getResources().getColor(R.color.black));
                tv_xiaoliang.setTextColor(getResources().getColor(R.color.red_them));
                tv_jiage.setTextColor(getResources().getColor(R.color.black));
                im_shang.setImageResource(R.mipmap.shang);
                im_xia.setImageResource(R.mipmap.kong);
                if (position1.equals("0")){
                    productList(categoryId,"","1","1");
                }else {
                    productList("",ID,"1","1");
                }
                sprttype = "1";
                break;
            case R.id.tv_zonghe://综合
                tv_zonghe.setTextColor(getResources().getColor(R.color.red_them));
                tv_xiaoliang.setTextColor(getResources().getColor(R.color.black));
                tv_jiage.setTextColor(getResources().getColor(R.color.black));
                im_shang.setImageResource(R.mipmap.kong);
                im_xia.setImageResource(R.mipmap.kong);
                if (position1.equals("0")){
                    productList(categoryId,"","0","1");
                }else {
                    productList("",ID,"0","1");
                }
                sprttype = "0";
                break;
            case R.id.ll_price://价格
                tv_zonghe.setTextColor(getResources().getColor(R.color.black));
                tv_xiaoliang.setTextColor(getResources().getColor(R.color.black));
                tv_jiage.setTextColor(getResources().getColor(R.color.red_them));
                im_xia.setImageResource(R.mipmap.xia);
                im_shang.setImageResource(R.mipmap.kong);
                if (position1.equals("0")){
                    productList(categoryId,"","2","1");
                }else {
                    productList("",ID,"2","1");
                }
                sprttype = "2";
                break;
        }
    }

    //根据分类id获取商品列表
    private void productList(String categoryId,String childCategoryId,String sortType,String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "productList");
        params.put("categoryId",categoryId);
        params.put("childCategoryId",childCategoryId);
        params.put("sortType",sortType);
        params.put("nowPage",nowPage);
        params.put("pageCount", SQSP.pagerSize);
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
