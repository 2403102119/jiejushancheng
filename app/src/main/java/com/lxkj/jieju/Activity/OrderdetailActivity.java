package com.lxkj.jieju.Activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.jieju.Adapter.OrderdetailAdapter;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Freightbean;
import com.lxkj.jieju.Bean.Orderdetailbean;
import com.lxkj.jieju.Bean.weixinbean;
import com.lxkj.jieju.Http.BaseCallback;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.ResultBean;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.StringUtil_li;
import com.lxkj.jieju.Utils.TellUtil;
import com.lxkj.jieju.View.ActionDialog;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

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
    private TextView tv_fukuan,tv_fahuo,tv_tuikuan,tv_shouhuo,tv_zhuangtai,tv_peisongtype,tv_xuzhifu,tv_daifukuanzhuangtia,tv_time;
    private String orderid;
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    OrderdetailAdapter adapter;
    List<Orderdetailbean.OrderDetailBean.OrderItemBean> list=new ArrayList<>();
    private String yunfei,zongji,amount,code,emsno,emscode,expCode;
    private LinearLayout ll_number,ll_fukuan,ll_shouhuo,ll_fahuo,ll_tuikuan,ll_shijian,ll_lianxi;
    private ActionDialog actionDialog,shouhuodialog,shaunchudialog;
    private PopupWindow popupWindow4;
    private LinearLayout ll_all_item;
    private RelativeLayout ll_all;
    private String yuanyin = "",qita = "0",phone;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_orderdetail);
        setTopTitle("订单状况");
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
        tv_zhuangtai = findViewById(R.id.tv_zhuangtai);
        tv_peisongtype = findViewById(R.id.tv_peisongtype);
        ll_shijian = findViewById(R.id.ll_shijian);
        tv_xuzhifu = findViewById(R.id.tv_xuzhifu);
        tv_daifukuanzhuangtia = findViewById(R.id.tv_daifukuanzhuangtia);
        tv_time = findViewById(R.id.tv_time);
        ll_lianxi = findViewById(R.id.ll_lianxi);


        actionDialog = new ActionDialog(mContext,"确认取消","","订单一经取消，无法再次找回","再想想","确认");
        actionDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                actionDialog.dismiss();
            }

            @Override
            public void onRightClick() {

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
        shaunchudialog = new ActionDialog(mContext,"","","确认删除？","再想想","确认");
        shaunchudialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                shaunchudialog.dismiss();
            }

            @Override
            public void onRightClick() {
                delOrder(orderid);
            }
        });
    }





    @Override
    protected void initEvent() {
        tv_detaile.setOnClickListener(this);
        tv_state.setOnClickListener(this);
        tv_quxiao.setOnClickListener(this);
        ll_lianxi.setOnClickListener(this);

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
                if (tv_detaile.getText().toString().equals("去支付")){
                    Intent intent = new Intent(OrderdetailActivity.this,PayActivity.class);
                    intent.putExtra("moeny",amount);
                    intent.putExtra("orderid",orderid);
                    startActivity(intent);
                }else if (tv_detaile.getText().toString().equals("删除订单")){
                    delOrder(orderid);
                }else if (tv_detaile.getText().toString().equals("查看详情")){
                    Intent intent1 = new Intent(OrderdetailActivity.this,DetailsrefundActivity.class);
                    intent1.putExtra("orderid",orderid);
                    startActivity(intent1);
                }else if (tv_detaile.getText().toString().equals("确认收货")){
                    shouhuodialog.show();
                }else if (tv_detaile.getText().toString().equals("申请售后")){
                    callPhone();
                }
                break;
            case R.id.tv_state://去支付
                if (tv_state.getText().toString().equals("去付款")){
                    Intent intent = new Intent(OrderdetailActivity.this,PayActivity.class);
                    intent.putExtra("moeny",amount);
                    intent.putExtra("orderid",orderid);
                    startActivity(intent);
                }else {
                    Intent intent2 = new Intent(OrderdetailActivity.this,LookExpressActivity.class);
                    intent2.putExtra("emsno",emsno);
                    intent2.putExtra("emscode",emscode);
                    intent2.putExtra("expCode",expCode);
                    startActivity(intent2);
                }
                break;
            case R.id.tv_quxiao://取消订单
                if (tv_quxiao.getText().toString().equals("查看物流")){
                    Intent intent2 = new Intent(OrderdetailActivity.this,LookExpressActivity.class);
                    intent2.putExtra("emsno",emsno);
                    intent2.putExtra("emscode",emscode);
                    intent2.putExtra("expCode",expCode);
                    startActivity(intent2);
                }else if (tv_quxiao.getText().toString().equals("删除订单")){
                    shaunchudialog.show();
                }else {
                    more();
                    ll_all_item.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.activity_translate_in));
                    popupWindow4.showAtLocation(v, Gravity.BOTTOM,0,0);
                }
                break;
            case R.id.ll_lianxi://联系客服
                callPhone();
                break;
        }
    }

    //取消弹窗
    public void more(){
        popupWindow4=new PopupWindow(mContext);
        View view=getLayoutInflater().inflate(R.layout.popup_more,null);
        ll_all_item= view.findViewById(R.id.ll_all_item);
        popupWindow4.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow4.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow4.setBackgroundDrawable(new BitmapDrawable());
        popupWindow4.setFocusable(true);
        popupWindow4.setOutsideTouchable(true);
        popupWindow4.setContentView(view);
        ll_all=view.findViewById(R.id.ll_all);
        ImageView im_close = view.findViewById(R.id.im_close);
        LinearLayout ll1 = view.findViewById(R.id.ll1);
        LinearLayout ll2 = view.findViewById(R.id.ll2);
        LinearLayout ll3 = view.findViewById(R.id.ll3);
        LinearLayout ll4 = view.findViewById(R.id.ll4);
        LinearLayout ll5 = view.findViewById(R.id.ll5);
        LinearLayout ll6 = view.findViewById(R.id.ll6);
        final ImageView im_1 = view.findViewById(R.id.im_1);
        final ImageView im_2 = view.findViewById(R.id.im_2);
        final ImageView im_3 = view.findViewById(R.id.im_3);
        final ImageView im_4 = view.findViewById(R.id.im_4);
        final ImageView im_5 = view.findViewById(R.id.im_5);
        final ImageView im_6 = view.findViewById(R.id.im_6);

        final TextView tv1 = view.findViewById(R.id.tv1);
        final TextView tv2 = view.findViewById(R.id.tv2);
        final TextView tv3 = view.findViewById(R.id.tv3);
        final TextView tv4 = view.findViewById(R.id.tv4);
        final TextView tv5 = view.findViewById(R.id.tv5);
        final TextView tv6 = view.findViewById(R.id.tv6);
        final TextView tv_kuaijie = view.findViewById(R.id.tv_kuaijie);
        final EditText et_casue = view.findViewById(R.id.et_casue);

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                im_1.setImageResource(R.mipmap.xuanzhong);
                im_2.setImageResource(R.mipmap.weixuan);
                im_3.setImageResource(R.mipmap.weixuan);
                im_4.setImageResource(R.mipmap.weixuan);
                im_5.setImageResource(R.mipmap.weixuan);
                im_6.setImageResource(R.mipmap.weixuan);
                et_casue.setVisibility(View.INVISIBLE);
                yuanyin = tv1.getText().toString();
                qita = "0";
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                im_1.setImageResource(R.mipmap.weixuan);
                im_2.setImageResource(R.mipmap.xuanzhong);
                im_3.setImageResource(R.mipmap.weixuan);
                im_4.setImageResource(R.mipmap.weixuan);
                im_5.setImageResource(R.mipmap.weixuan);
                im_6.setImageResource(R.mipmap.weixuan);
                et_casue.setVisibility(View.INVISIBLE);
                yuanyin = tv2.getText().toString();
                qita = "0";
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                im_1.setImageResource(R.mipmap.weixuan);
                im_2.setImageResource(R.mipmap.weixuan);
                im_3.setImageResource(R.mipmap.xuanzhong);
                im_4.setImageResource(R.mipmap.weixuan);
                im_5.setImageResource(R.mipmap.weixuan);
                im_6.setImageResource(R.mipmap.weixuan);
                et_casue.setVisibility(View.INVISIBLE);
                yuanyin = tv3.getText().toString();
                qita = "0";
            }
        });
        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                im_1.setImageResource(R.mipmap.weixuan);
                im_2.setImageResource(R.mipmap.weixuan);
                im_3.setImageResource(R.mipmap.weixuan);
                im_4.setImageResource(R.mipmap.xuanzhong);
                im_5.setImageResource(R.mipmap.weixuan);
                im_6.setImageResource(R.mipmap.weixuan);
                et_casue.setVisibility(View.INVISIBLE);
                yuanyin = tv4.getText().toString();
                qita = "0";
            }
        });
        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                im_1.setImageResource(R.mipmap.weixuan);
                im_2.setImageResource(R.mipmap.weixuan);
                im_3.setImageResource(R.mipmap.weixuan);
                im_4.setImageResource(R.mipmap.weixuan);
                im_5.setImageResource(R.mipmap.xuanzhong);
                im_6.setImageResource(R.mipmap.weixuan);
                et_casue.setVisibility(View.INVISIBLE);
                yuanyin = tv5.getText().toString();
                qita = "0";
            }
        });
        ll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                im_1.setImageResource(R.mipmap.weixuan);
                im_2.setImageResource(R.mipmap.weixuan);
                im_3.setImageResource(R.mipmap.weixuan);
                im_4.setImageResource(R.mipmap.weixuan);
                im_5.setImageResource(R.mipmap.weixuan);
                im_6.setImageResource(R.mipmap.xuanzhong);
                et_casue.setVisibility(View.VISIBLE);
                qita = "1";
            }
        });
        tv_kuaijie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qita.equals("1")){
                    yuanyin = et_casue.getText().toString();
                }
                if (StringUtil_li.isSpace(yuanyin)){
                    showToast("请选择取消原因");
                    return;
                }
                cancelOrder(orderid,yuanyin);
            }
        });

        im_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow4.dismiss();
                ll_all.clearAnimation();
            }
        });


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
                phone = resultBean.getOrderDetail().getPhone();
                emsno = resultBean.getOrderDetail().getEmsno();
                emscode = resultBean.getOrderDetail().getEmscode();
                expCode = resultBean.getOrderDetail().getEmsname();
                if (resultBean.getOrderDetail().getStatus().equals("0")){
                    tv_state.setText("去付款");
                    tv_state.setVisibility(View.VISIBLE);

                    tv_quxiao.setVisibility(View.VISIBLE);
                    tv_detaile.setVisibility(View.VISIBLE);

                    tv_zhuangtai.setText("等待付款");
                    ll_shijian.setVisibility(View.VISIBLE);

                }else if (resultBean.getOrderDetail().getStatus().equals("1")){
                    tv_state.setText("查看物流");
                    tv_state.setVisibility(View.GONE);

                    tv_quxiao.setVisibility(View.GONE);
                    tv_detaile.setVisibility(View.GONE);

                    tv_zhuangtai.setText("正在发货");
                    ll_shijian.setVisibility(View.GONE);

                }else if (resultBean.getOrderDetail().getStatus().equals("2")){
                    tv_state.setText("查看物流");
                    tv_state.setVisibility(View.VISIBLE);

                    tv_quxiao.setVisibility(View.GONE);
                    tv_quxiao.setText("查看物流");

                    tv_detaile.setVisibility(View.VISIBLE);
                    tv_detaile.setText("确认收货");

                    tv_zhuangtai.setText("正在发货");
                    ll_shijian.setVisibility(View.GONE);

                }else if (resultBean.getOrderDetail().getStatus().equals("3")){
                    tv_state.setText("已完成");
                    tv_quxiao.setVisibility(View.GONE);
                    tv_detaile.setVisibility(View.VISIBLE);
                    tv_detaile.setText("申请售后");

                    tv_zhuangtai.setText("已完成");
                    ll_shijian.setVisibility(View.GONE);
                    tv_state.setVisibility(View.GONE);
                }else if (resultBean.getOrderDetail().getStatus().equals("4")){
                    tv_state.setText("已取消");
                    tv_quxiao.setVisibility(View.VISIBLE);
                    tv_detaile.setVisibility(View.GONE);
                    tv_quxiao.setText("删除订单");

                    tv_zhuangtai.setText("已取消");
                    ll_shijian.setVisibility(View.GONE);
                    tv_state.setVisibility(View.GONE);
                }
                    tv_peisongtype.setText(resultBean.getOrderDetail().getEmsname());
                tv_name.setText(resultBean.getOrderDetail().getReceiverName());
                tv_phone.setText(StringUtil_li.replacePhone(resultBean.getOrderDetail().getReceiverPhone()));
                tv_receiverAddress.setText(resultBean.getOrderDetail().getReceiverAddress());

                list.clear();
                list.addAll(resultBean.getOrderDetail().getOrderItem());
                adapter.notifyDataSetChanged();
                if (!StringUtil_li.isSpace(resultBean.getOrderDetail().getWaitPay())){
                    long h = 00;
                    long d = 00;
                    if (Long.parseLong(resultBean.getOrderDetail().getWaitPay()) > 3600) {
                        h = Long.parseLong(resultBean.getOrderDetail().getWaitPay()) / 3600;
                    }
                    long temp = Long.parseLong(resultBean.getOrderDetail().getWaitPay())% 3600;
                    if (Long.parseLong(resultBean.getOrderDetail().getWaitPay()) > 3600) {
                        if (temp != 0) {
                            if (temp > 60) {
                                d = temp / 60;
                            }
                        }
                    } else {
                        d = Long.parseLong(resultBean.getOrderDetail().getWaitPay()) / 60;
                    }

                    tv_time.setText(h+"小时"+d+"分钟");
                }


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
    @PermissionGrant(SQSP.PMS_LOCATION)
    public void pmsLocationSuccess() {
        //权限授权成功
        TellUtil.tell(mContext, phone);
    }
    /*拨打电话*/
    private void callPhone() {
        if (null != phone) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                MPermissions.requestPermissions(this, SQSP.PMS_LOCATION,
                        Manifest.permission.CALL_PHONE
                );
            } else {
                pmsLocationSuccess();
            }
        }
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
                tv_xuzhifu.setText("¥ "+amount);
                tv_daifukuanzhuangtia.setText("¥ "+amount);
            }

            @Override
            public void onError(Response response, int code, Exception e){

            }
        });
    }
    //删除订单
    private void delOrder(String orderid) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "delOrder");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
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
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e){

            }
        });
    }
    //取消订单
    private void cancelOrder(String orderid,String reason) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "cancelOrder");
        params.put("uid",SPTool.getSessionValue(SQSP.uid));
        params.put("orderid",orderid);
        params.put("reason",reason);
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
