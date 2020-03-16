package com.lxkj.jieju.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lxkj.jieju.Adapter.CollectAdapter;
import com.lxkj.jieju.Adapter.WalletAdapter;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Collectbean;
import com.lxkj.jieju.Bean.Walletbean;
import com.lxkj.jieju.Bean.Walletshoucangbean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.ResultBean;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.View.ActionDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :我的钱包
 */
public class WalletActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    CollectAdapter adapter;
    List<Walletshoucangbean.DataListBean> list=new ArrayList<>();
    private TextView tv_alipay,tv_bank,tv_balance;
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private static final String TAG = "WalletActivity";
    private ActionDialog shaunchudialog;
    private String Productid;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_wallet);
        setTopTitle("我的足迹");
        recycle = findViewById(R.id.recycle);
        tv_alipay = findViewById(R.id.tv_alipay);
        tv_bank = findViewById(R.id.tv_bank);
        tv_balance = findViewById(R.id.tv_balance);
        smart = findViewById(R.id.smart);
    }

    @Override
    protected void initEvent() {
        tv_alipay.setOnClickListener(this);
        tv_bank.setOnClickListener(this);


        shaunchudialog = new ActionDialog(mContext,"","","确认删除？","再想想","确认");
        shaunchudialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                shaunchudialog.dismiss();
            }

            @Override
            public void onRightClick() {
                delFootprint(Productid);
            }
        });

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                walletList(String.valueOf(pageNoIndex));

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    walletList(String.valueOf(pageNoIndex));
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

        layoutManager = new LinearLayoutManager(WalletActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new CollectAdapter(WalletActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new CollectAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                Intent intent = new Intent(mContext, DeatilsActivity.class);
                intent.putExtra("productid",list.get(firstPosition).getProductid());
                startActivity(intent);
            }

            @Override
            public void OnDealte(int position) {
                Productid = list.get(position).getProductid();
                shaunchudialog.show();
            }

            @Override
            public void Onadd(int position) {
                if (list.get(position).getSkuList().size()==1){

                    addCart(list.get(position).getProductid(),list.get(position).getSkuList().get(0).getSkuId(),"1");
                }else {
                    Intent intent = new Intent(mContext, DeatilsActivity.class);
                    intent.putExtra("productid",list.get(position).getProductid());
                    intent.putExtra("type","1");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }


    //我的足迹
    private void walletList(String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "myFootPrint");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("nowPage",nowPage);
        params.put("pageCount",SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Walletshoucangbean>(mContext) {
            @Override
            public void onSuccess(Response response, Walletshoucangbean resultBean) {
                smart.finishRefresh();
                totalPage = resultBean.getTotalPage();
                if (pageNoIndex == 1) {
                    list.clear();
                }
                list.addAll(resultBean.getDataList());

                adapter.notifyDataSetChanged();
            }


            @Override
            public void onError(Response response, int code, Exception e) {
                smart.finishRefresh();
            }
        });
    }
    //删除足迹
    private void delFootprint(String productId) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "delFootprint");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("productId",productId);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Walletshoucangbean>(mContext) {
            @Override
            public void onSuccess(Response response, Walletshoucangbean resultBean) {
               walletList(String.valueOf(pageNoIndex));
            }


            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    //添加购物车
    private void addCart(String productid,String skuId,String count) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "addCart");
        params.put("productId",productid);
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("skuId",skuId);
        params.put("count",count);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        walletList("1");
    }
}
