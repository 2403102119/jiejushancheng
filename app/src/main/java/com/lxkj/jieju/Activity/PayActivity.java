package com.lxkj.jieju.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Utils.ActionPopoverUtils;
import com.lxkj.jieju.Utils.StringUtil_li;
import com.lxkj.jieju.Utils.ToastFactory;

import cn.beecloud.BCPay;
import cn.beecloud.BeeCloud;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCPayResult;
import cn.beecloud.entity.BCReqParams;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :支付界面
 */
public class PayActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "PayActivity";
    private TextView tv_pay,tv_money;
    private String moeny = "0.01",orderid;
    private ImageView wexin,alipay;
    private String type = "0";
    private String alipaytype = "2";
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_pay);
        setTopTitle("付款");
        initBeeCloud();
        tv_pay = findViewById(R.id.tv_pay);
        tv_money = findViewById(R.id.tv_money);
        wexin = findViewById(R.id.wexin);
        alipay = findViewById(R.id.alipay);
    }

    @Override
    protected void initEvent() {
        tv_pay.setOnClickListener(this);
        wexin.setOnClickListener(this);
        alipay.setOnClickListener(this);
    }

    @Override
    protected void initData() {
//        moeny = getIntent().getStringExtra("moeny");
        orderid = getIntent().getStringExtra("orderid");
        tv_money.setText(moeny);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pay://立即支付
                 if (type.equals("0")){
                     payWeiXin();
                 }else if (alipaytype.equals("3")){
                     payZhiFuBao();
                 }else {
                     showToast("请选择支付方式");
                 }
                break;
            case R.id.wexin://微信
                if (type.equals("1")){
                    wexin.setImageResource(R.mipmap.xuanzhong);
                    alipay.setImageResource(R.mipmap.weixuan);
                    type = "0";
                    alipaytype= "2";
                }else if (type.equals("0")){
                    wexin.setImageResource(R.mipmap.weixuan);
                    alipay.setImageResource(R.mipmap.weixuan);
                    type= "1";
                    alipaytype= "2";
                }
                break;
            case R.id.alipay:

                if (alipaytype.equals("2")){
                    alipay.setImageResource(R.mipmap.xuanzhong);
                    wexin.setImageResource(R.mipmap.weixuan);
                    alipaytype= "3";
                    type = "1";
                }else if (alipaytype.equals("3")){
                    alipay.setImageResource(R.mipmap.weixuan);
                    wexin.setImageResource(R.mipmap.weixuan);
                    alipaytype= "2";
                    type = "1";
                }
                break;
        }
    }

    private int ceilInt(double number) {
        return (int) Math.ceil(number);
    }

    private String toastMsg;
    private boolean isDetail;//是否是详情跳转

    /*支付宝支付*/
    private void payZhiFuBao() {
        BCPay.PayParams aliParam = new BCPay.PayParams();
        aliParam.channelType = BCReqParams.BCChannelTypes.ALI_APP;
        //aliParam.billTitle = "保证金" + ContextAndSP.getNum();//测试的订单标题
        aliParam.billTitle = orderid + StringUtil_li.getNum() /*+ MoGuSP.getNum()*/;//测试的订单标题
        aliParam.billTotalFee = ceilInt(parseDouble(moeny) * 100);       //订单金额(分)
        //aliParam.billTotalFee = ceilInt(parseDouble("0.01") * 100);       //订单金额(分)
        //aliParam.billNum = orderId;//测试的订单编号
        //aliParam.billNum = orderId + MoGuSP.getNum();//测试的订单编号  订单号必须是长度8~32位字母和/或数字组合成的字符串
        aliParam.billNum = orderid + StringUtil_li.getNum();//测试的订单编号
//        Log.i(TAG, "payZhiFuBao: 支付宝支付内容" + orderid.substring(0, 20) + "---" + orderid + StringUtil_li.getNum());
        BCPay.getInstance(this).reqPaymentAsync(aliParam, bcCallback);
    }


    /*微信支付*/
    private double parseDouble(String price) {
        if (TextUtils.isEmpty(price)) {
            return 0;
        }

        double d = 0;
        try {
            d = Double.parseDouble(price);
        } catch (Exception e) {

        }
        return d;
    }


    /*微信支付*/
    private void payWeiXin() {
        //对于微信支付, 手机内存太小会有OutOfResourcesException造成的卡顿, 以致无法完成支付
        //这个是微信自身存在的问题
        if (BCPay.isWXAppInstalledAndSupported() && BCPay.isWXPaySupported()) {
            BCPay.PayParams payParams = new BCPay.PayParams();
            payParams.channelType = BCReqParams.BCChannelTypes.WX_APP;
            //payParams.billTitle = "保证金" + ContextAndSP.getNum();
            payParams.billTitle = orderid + StringUtil_li.getNum();
            //订单标题
            //payParams.billTotalFee = ceilInt(parseDouble("0.01") * 100);    //订单金额(分)
            payParams.billTotalFee = ceilInt(parseDouble(moeny) * 100);    //订单金额(分)
//            payParams.billNum = ordernum;  //订单流水号
            //payParams.billNum = orderId;  //订单流水号
            payParams.billNum = orderid + StringUtil_li.getNum();  //订单流水号
            BCPay.getInstance(this).reqPaymentAsync(payParams, bcCallback);            //支付完成后回调入口
        } else {
            showToast("您尚未安装微信或者安装的微信版本不支持");
        }
    }


    private void initBeeCloud() {
        BeeCloud.setAppIdAndSecret(SQSP.Beecloud_ID, SQSP.Beecloud_Secret);
        // 如果用到微信支付，在用到微信支付的Activity的onCreate函数里调用以下函数.
        // 第二个参数需要换成你自己的微信AppID.
        String initInfo = BCPay.initWechatPay(this, SQSP.WX_APP_ID);
        if (initInfo != null) {
            Toast.makeText(this, "微信初始化失败：" + initInfo, Toast.LENGTH_SHORT).show();
        }
    }


    private Handler mHandler = new Handler(new Handler.Callback() {
        /**
         * Callback interface you can use when instantiating a Handler to avoid
         * having to implement your own subclass of Handler.
         *
         * handleMessage() defines the operations to perform when
         * the Handler receives a new Message to process.
         */
        @Override
        public boolean handleMessage(Message msg) {
            showToast(toastMsg);
            switch (msg.what) {
                case 1:
                    if (isDetail) {
                        setResult(SQSP.CODE_REFRESH);

                        Intent intent = new Intent(PayActivity.this, PayOkActivity.class);
                        startActivity(intent);
                        finish();

                        Log.i(TAG, "handleMessage: 执行方法");
                    } else {
                        ActionPopoverUtils utils = new ActionPopoverUtils(PayActivity.this);
                        boolean showTip = getIntent().getBooleanExtra("showTip", false);
                        if (showTip) {

                            ToastFactory.getToast(PayActivity.this, "支付成功!").show();

                            Intent intent = new Intent(PayActivity.this, PayOkActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            ToastFactory.getToast(PayActivity.this, "支付成功").show();
                            Intent intent = new Intent(PayActivity.this, PayOkActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    break;
            }
            return true;
        }
    });


    //支付结果返回入口
    BCCallback bcCallback = new BCCallback() {
        @Override
        public void done(final BCResult bcResult) {
            //pay.setClickable(true);
            final BCPayResult bcPayResult = (BCPayResult) bcResult;
            //此处关闭loading界面
            //根据你自己的需求处理支付结果
            String result = bcPayResult.getResult();

            Log.i("BCCallback", result + "=" + bcPayResult.getErrMsg() + "=" + bcPayResult.getErrCode());

              /*注意！
              所有支付渠道建议以服务端的状态金额为准，此处返回的RESULT_SUCCESS仅仅代表手机端支付成功*/

            Message msg = mHandler.obtainMessage();
            //单纯的显示支付结果
            msg.what = 2;
            if (result.equals(BCPayResult.RESULT_SUCCESS)) {
                toastMsg = "支付成功";
                msg.what = 1;
                Log.e("支付成功", "支付成功");
            } else if (result.equals(BCPayResult.RESULT_CANCEL)) {
                toastMsg = "支付取消";
            } else if (result.equals(BCPayResult.RESULT_FAIL)) {

                if (bcPayResult.getErrCode() == -12) {
                    toastMsg = "您尚未安装支付软件";
                }
                /*toastMsg = "支付失败, 原因: " + bcPayResult.getErrCode() +
                        " # " + bcPayResult.getErrMsg() +
                        " # " + bcPayResult.getDetailInfo();*/
                toastMsg = "支付失败,请稍后重试";
                Log.i(TAG, "done: 支付失败的原因" + "支付失败, 原因: " + bcPayResult.getErrCode() +
                        " # " + bcPayResult.getErrMsg() +
                        " # " + bcPayResult.getDetailInfo());
                Log.e("支付错误..............", toastMsg);
                /* * 你发布的项目中不应该出现如下错误，此处由于支付宝政策原因，
                 * 不再提供支付宝支付的测试功能，所以给出提示说明*/

                if (bcPayResult.getErrMsg().equals("PAY_FACTOR_NOT_SET") &&
                        bcPayResult.getDetailInfo().startsWith("支付宝参数")) {
                    toastMsg = "支付失败：由于支付宝政策原因，故不再提供支付宝支付的测试功能，给您带来的不便，敬请谅解";
                }


                /** 以下是正常流程，请按需处理失败信息*/

                Log.e("error", toastMsg);

            } else if (result.equals(BCPayResult.RESULT_UNKNOWN)) {
                //可能出现在支付宝8000返回状态
                toastMsg = "订单状态未知";
            } else {
                toastMsg = "invalid return";
            }

            mHandler.sendMessage(msg);

        }
    };

}
