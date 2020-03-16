package com.lxkj.jieju.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lxkj.jieju.Adapter.DetailsrefundAdapter;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Detailsrefoundbean;
import com.lxkj.jieju.Http.OkHttpHelper;
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

public class DetailsrefundActivity extends BaseActivity {
    private String orderid;
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    DetailsrefundAdapter adapter;
    List<Detailsrefoundbean.OrderDetailBean.OrderItemBean> list=new ArrayList<>();
    private TextView tv_bianhao,tv_moeney,tv_cause,tv_remark,tv_start,tv_endtime;
    private View view1,view2;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_detailsrefund);
        setTopTitle("退款详情");
        recycle = findViewById(R.id.recycle);
        tv_bianhao = findViewById(R.id.tv_bianhao);
        tv_moeney = findViewById(R.id.tv_moeney);
        tv_cause = findViewById(R.id.tv_cause);
        tv_remark = findViewById(R.id.tv_remark);
        tv_start = findViewById(R.id.tv_start);
        tv_endtime = findViewById(R.id.tv_endtime);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
    }

    @Override
    protected void initEvent() {

        layoutManager = new LinearLayoutManager(DetailsrefundActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new DetailsrefundAdapter(DetailsrefundActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new DetailsrefundAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }


        });
    }

    @Override
    protected void initData() {
        orderid = getIntent().getStringExtra("orderid");
        refundDetail(orderid);
        tv_bianhao.setText(orderid);
    }

    //退款详情
    private void refundDetail(String orderid) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "refundDetail");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("orderid",orderid);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Detailsrefoundbean>(mContext) {
            @Override
            public void onSuccess(Response response, Detailsrefoundbean resultBean) {
                list.clear();
                list.addAll(resultBean.getOrderDetail().getOrderItem());
                adapter.notifyDataSetChanged();
                tv_moeney.setText("¥"+resultBean.getOrderDetail().getAmount());
                tv_cause.setText(resultBean.getOrderDetail().getReason());
                tv_remark.setText(resultBean.getOrderDetail().getRemark());
                tv_start.setText(resultBean.getOrderDetail().getAdtime());
                tv_endtime.setText(resultBean.getOrderDetail().getRefundtime());
                if (resultBean.getOrderDetail().getStatus().equals("6")){
                    view1.setBackgroundColor(getResources().getColor(R.color.red_them));
                    view2.setBackgroundColor(getResources().getColor(R.color.white));
                }else if (resultBean.getOrderDetail().getStatus().equals("7")){
                    view1.setBackgroundColor(getResources().getColor(R.color.red_them));
                    view2.setBackgroundColor(getResources().getColor(R.color.red_them));
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
}
