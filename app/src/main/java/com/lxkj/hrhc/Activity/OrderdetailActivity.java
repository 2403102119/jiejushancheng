package com.lxkj.hrhc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxkj.hrhc.Adapter.CollectAdapter;
import com.lxkj.hrhc.Adapter.OrderdetailAdapter;
import com.lxkj.hrhc.Base.BaseActivity;
import com.lxkj.hrhc.Bean.Collectbean;
import com.lxkj.hrhc.Bean.Freightbean;
import com.lxkj.hrhc.Bean.Orderbean;
import com.lxkj.hrhc.Bean.Orderdetailbean;
import com.lxkj.hrhc.Bean.ResonBean;
import com.lxkj.hrhc.Http.BaseCallback;
import com.lxkj.hrhc.Http.OkHttpHelper;
import com.lxkj.hrhc.Http.ResultBean;
import com.lxkj.hrhc.Http.SpotsCallBack;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.SQSP;
import com.lxkj.hrhc.Uri.NetClass;
import com.lxkj.hrhc.Utils.SPTool;
import com.lxkj.hrhc.Utils.StringUtil_li;
import com.lxkj.hrhc.View.ActionDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :订单详情
 */
public class OrderdetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_detaile,tv_after_sale,tv_state,tv_name,tv_phone,tv_receiverAddress,tv_yunfei,tv_shi,tv_paytype,tv_beizhu,tv_number,tv_createdDate,tv_quxiao;
    private TextView tv_fukuan,tv_fahuo,tv_tuikuan,tv_shouhuo;
    private String orderid;
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    OrderdetailAdapter adapter;
    List<Orderdetailbean.OrderDetailBean.OrderItemBean> list=new ArrayList<>();
    private String yunfei,zongji,amount,code,emsno,emscode,expCode;
    private LinearLayout ll_number,ll_fukuan,ll_shouhuo,ll_fahuo,ll_tuikuan;
    private ActionDialog actionDialog,shouhuodialog;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_orderdetail);
        setTopTitle("订单详情");
        tv_detaile = findViewById(R.id.tv_detaile);
        tv_after_sale = findViewById(R.id.tv_after_sale);
        tv_state = findViewById(R.id.tv_state);
        tv_name = findViewById(R.id.tv_name);
        tv_phone = findViewById(R.id.tv_phone);
        tv_receiverAddress = findViewById(R.id.tv_receiverAddress);
        recycle = findViewById(R.id.recycle);
        tv_yunfei = findViewById(R.id.tv_yunfei);
        tv_shi = findViewById(R.id.tv_shi);
        tv_paytype = findViewById(R.id.tv_paytype);
        tv_beizhu = findViewById(R.id.tv_beizhu);
        tv_number = findViewById(R.id.tv_number);
        tv_createdDate = findViewById(R.id.tv_createdDate);
        tv_quxiao = findViewById(R.id.tv_quxiao);
        tv_fukuan = findViewById(R.id.tv_fukuan);
        tv_fahuo = findViewById(R.id.tv_fahuo);
        ll_fahuo = findViewById(R.id.ll_fahuo);
        tv_tuikuan = findViewById(R.id.tv_tuikuan);
        tv_shouhuo = findViewById(R.id.tv_shouhuo);
        ll_number = findViewById(R.id.ll_number);
        ll_fukuan = findViewById(R.id.ll_fukuan);
        ll_shouhuo = findViewById(R.id.ll_shouhuo);
        ll_tuikuan = findViewById(R.id.ll_tuikuan);

        actionDialog = new ActionDialog(mContext,"确认取消","","订单一经取消，无法再次找回","再想想","确认");
        actionDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                actionDialog.dismiss();
            }

            @Override
            public void onRightClick() {
                cancelOrder(orderid);
            }
        });
        shouhuodialog = new ActionDialog(mContext,"","","确认收货？","再想想","确认");
        shouhuodialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                shouhuodialog.dismiss();
            }

            @Override
            public void onRightClick() {
                finishOrder(orderid);
            }
        });
    }

    @Override
    protected void initEvent() {
        tv_detaile.setOnClickListener(this);
        tv_after_sale.setOnClickListener(this);
        tv_quxiao.setOnClickListener(this);

        layoutManager = new LinearLayoutManager(OrderdetailActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new OrderdetailAdapter(OrderdetailActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new OrderdetailAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
            }

            @Override
            public void OnDealte(int position) {

            }
        });

    }

    @Override
    protected void initData() {
        orderid = getIntent().getStringExtra("orderid");
        tv_number.setText(orderid);
//        orderDetail(orderid);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_detaile://退款查看详情
                if (tv_detaile.getText().toString().equals("去付款")){
                    Intent intent = new Intent(OrderdetailActivity.this,PayActivity.class);
                    intent.putExtra("moeny",amount);
                    intent.putExtra("orderid",orderid);
                    startActivity(intent);
                }else if (tv_detaile.getText().toString().equals("去评价")){
                    Intent intent1 = new Intent(OrderdetailActivity.this,AppraiseActivity.class);
                    intent1.putExtra("orderid",orderid);
                    startActivity(intent1);
                }else if (tv_detaile.getText().toString().equals("查看详情")){
                    Intent intent1 = new Intent(OrderdetailActivity.this,DetailsrefundActivity.class);
                    intent1.putExtra("orderid",orderid);
                    startActivity(intent1);
                }else if (tv_detaile.getText().toString().equals("确认收货")){
                    shouhuodialog.show();
                }
                break;
            case R.id.tv_after_sale://退款
                Intent intent = new Intent(OrderdetailActivity.this,RefundActivity.class);
                intent.putExtra("orderid",orderid);
                startActivity(intent);
                break;
            case R.id.tv_quxiao://取消订单
                if (tv_quxiao.getText().toString().equals("查看物流")){
                    Intent intent2 = new Intent(OrderdetailActivity.this,LookExpressActivity.class);
                    intent2.putExtra("emsno",emsno);
                    intent2.putExtra("emscode",emscode);
                    intent2.putExtra("expCode",expCode);
                    startActivity(intent2);
                }else {

                    actionDialog.show();
                }
                break;
        }
    }

    //订单详情
    private void orderDetail(String orderid) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "orderDetail");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("orderid",orderid);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Orderdetailbean>(mContext) {
            @Override
            public void onSuccess(Response response, Orderdetailbean resultBean) {
                emsno = resultBean.getOrderDetail().getEmsno();
                emscode = resultBean.getOrderDetail().getEmscode();
                expCode = resultBean.getOrderDetail().getEmsname();
               if (resultBean.getOrderDetail().getStatus().equals("0")){
                   tv_state.setText("待付款");
                   tv_after_sale.setVisibility(View.GONE);
                   tv_quxiao.setVisibility(View.VISIBLE);
                   tv_detaile.setVisibility(View.VISIBLE);

               }else if (resultBean.getOrderDetail().getStatus().equals("1")){
                   tv_state.setText("待发货");
                   tv_quxiao.setVisibility(View.GONE);
                   tv_detaile.setVisibility(View.GONE);
                   tv_after_sale.setVisibility(View.VISIBLE);
               }else if (resultBean.getOrderDetail().getStatus().equals("2")){
                   tv_state.setText("待收货");
                   tv_after_sale.setVisibility(View.VISIBLE);
                   tv_quxiao.setVisibility(View.VISIBLE);
                   tv_quxiao.setText("查看物流");
                   tv_detaile.setVisibility(View.VISIBLE);
                   tv_detaile.setText("确认收货");
               }else if (resultBean.getOrderDetail().getStatus().equals("3")){
                   tv_state.setText("待评价");
                   tv_after_sale.setVisibility(View.GONE);
                   tv_quxiao.setVisibility(View.GONE);
                   tv_detaile.setVisibility(View.VISIBLE);
                   tv_detaile.setText("去评价");
               }else if (resultBean.getOrderDetail().getStatus().equals("4")){
                   tv_state.setText("已完成");
                   tv_quxiao.setVisibility(View.GONE);
                   tv_detaile.setVisibility(View.GONE);
                   tv_after_sale.setVisibility(View.GONE);
               }else if (resultBean.getOrderDetail().getStatus().equals("5")){
                   tv_state.setText("已取消");
                   tv_quxiao.setVisibility(View.GONE);
                   tv_detaile.setVisibility(View.GONE);
                   tv_after_sale.setVisibility(View.GONE);

               }else if (resultBean.getOrderDetail().getStatus().equals("6")){
                   tv_state.setText("退款中");
                   tv_quxiao.setVisibility(View.GONE);
                   tv_detaile.setVisibility(View.VISIBLE);
                   tv_after_sale.setVisibility(View.GONE);
                   tv_detaile.setText("查看详情");
               }else if (resultBean.getOrderDetail().getStatus().equals("7")){
                   tv_state.setText("已退款");
                   tv_quxiao.setVisibility(View.GONE);
                   tv_detaile.setVisibility(View.GONE);
                   tv_after_sale.setVisibility(View.GONE);
               }else if (resultBean.getOrderDetail().getStatus().equals("8")){
                   tv_state.setText("退款已拒绝");
                   tv_quxiao.setVisibility(View.GONE);
                   tv_detaile.setVisibility(View.GONE);
                   tv_after_sale.setVisibility(View.GONE);
               }
                tv_name.setText(resultBean.getOrderDetail().getReceiverName());
                tv_phone.setText(resultBean.getOrderDetail().getReceiverPhone());
                tv_receiverAddress.setText(resultBean.getOrderDetail().getReceiverAddress());
                list.clear();
                list.addAll(resultBean.getOrderDetail().getOrderItem());
                adapter.notifyDataSetChanged();
                tv_yunfei.setText("¥"+resultBean.getOrderDetail().getOrderPrice());
                zongji = resultBean.getOrderDetail().getOrderPrice();
                if (resultBean.getOrderDetail().getPayType().equals("0")){
                    tv_paytype.setText("微信支付");
                }else if (resultBean.getOrderDetail().getPayType().equals("1")){
                    tv_paytype.setText("支付宝支付");
                }
                tv_beizhu.setText(resultBean.getOrderDetail().getRemark());
//                tv_number.setText(resultBean.getOrderDetail().getEmscode());

                tv_createdDate.setText(resultBean.getOrderDetail().getCreatedDate());
                if (StringUtil_li.isSpace(resultBean.getOrderDetail().getPayDate())){
                    ll_fukuan.setVisibility(View.GONE);
                }else {
                    ll_fukuan.setVisibility(View.VISIBLE);
                    tv_fukuan.setText(resultBean.getOrderDetail().getPayDate());
                }
                if (StringUtil_li.isSpace(resultBean.getOrderDetail().getDeliveryDate())){
                    ll_fahuo.setVisibility(View.GONE);
                }else {
                    ll_fahuo.setVisibility(View.VISIBLE);
                    tv_fahuo.setText(resultBean.getOrderDetail().getDeliveryDate());
                }
                if (StringUtil_li.isSpace(resultBean.getOrderDetail().getRefundDate())){
                    ll_tuikuan.setVisibility(View.GONE);
                }else {
                    ll_tuikuan.setVisibility(View.VISIBLE);
                    tv_tuikuan.setText(resultBean.getOrderDetail().getRefundDate());
                }
                if (StringUtil_li.isSpace(resultBean.getOrderDetail().getReceiveDate())){
                    ll_shouhuo.setVisibility(View.GONE);
                }else {
                    ll_shouhuo.setVisibility(View.VISIBLE);
                    tv_shouhuo.setText(resultBean.getOrderDetail().getReceiveDate());
                }


                getFreight();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    //获取平台运费
    private void getFreight() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "getFreight");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new BaseCallback<Freightbean>(){
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, Freightbean resultBean) {
                yunfei = resultBean.getAmount();
                BigDecimal yunfei = new BigDecimal(resultBean.getAmount());
                BigDecimal jiage = new BigDecimal(zongji);
                BigDecimal zongji = yunfei.add(jiage);
                if (yunfei.toString().equals("0")){
                    tv_shi.setText("包邮");
                }else {
                    tv_shi.setText("¥"+yunfei);
                }
                amount = zongji.toString();
            }

            @Override
            public void onError(Response response, int code, Exception e){

            }
        });
    }
    //取消订单
    private void cancelOrder(String orderid) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "cancelOrder");
        params.put("uid",SPTool.getSessionValue(SQSP.uid));
        params.put("orderid",orderid);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new BaseCallback<ResultBean>(){
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
                actionDialog.dismiss();
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e){

            }
        });
    }
    //确认收货
    private void finishOrder(String orderid) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "finishOrder");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("orderid",orderid);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                shouhuodialog.dismiss();
                showToast(resultBean.getResultNote());
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        orderDetail(orderid);
    }
}
