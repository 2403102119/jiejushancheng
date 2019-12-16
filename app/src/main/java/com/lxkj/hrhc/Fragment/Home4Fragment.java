package com.lxkj.hrhc.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.hrhc.Activity.BrokerageActivity;
import com.lxkj.hrhc.Activity.CollectActivity;
import com.lxkj.hrhc.Activity.EvaluationActivity;
import com.lxkj.hrhc.Activity.FaqActivity;
import com.lxkj.hrhc.Activity.LoginActivity;
import com.lxkj.hrhc.Activity.MercenaryActivity;
import com.lxkj.hrhc.Activity.MessageActivity;
import com.lxkj.hrhc.Activity.OrderActivity;
import com.lxkj.hrhc.Activity.PersonalActivity;
import com.lxkj.hrhc.Activity.ReceivewActivity;
import com.lxkj.hrhc.Activity.SetActivity;
import com.lxkj.hrhc.Activity.WalletActivity;
import com.lxkj.hrhc.Activity.WebViewActivity;
import com.lxkj.hrhc.Adapter.GridViewAdapter;
import com.lxkj.hrhc.Adapter.ViewPagerAdapter;
import com.lxkj.hrhc.App;
import com.lxkj.hrhc.Base.BaseFragment;
import com.lxkj.hrhc.Bean.AboutUsbean;
import com.lxkj.hrhc.Bean.FirsePagebean;
import com.lxkj.hrhc.Bean.UserInfobean;
import com.lxkj.hrhc.Http.OkHttpHelper;
import com.lxkj.hrhc.Http.SpotsCallBack;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.SQSP;
import com.lxkj.hrhc.Uri.NetClass;
import com.lxkj.hrhc.Utils.SPTool;
import com.lxkj.hrhc.Utils.ToastFactory;
import com.lxkj.hrhc.Utils.Utility2;
import com.lxkj.hrhc.View.GlideImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class Home4Fragment extends BaseFragment implements View.OnClickListener{
    private final static String TAG = "Home4Fragment";
    private LinearLayout ll_about,ll_personal_details,ll_receiver_address,ll_set,ll_collect,ll_faq,ll_wallet,ll_evaluation,ll_brokerage,ll_message,ll_updateIcon;
    private TextView tv_all,tv_name,tv_phone,tv_balance,tv_todyIncome,tv_historyIncome;
    private RoundedImageView ri_icon;
    private String name,icon_url,phone,sex;
    private LinearLayout ll_obligation,ll_Overhang,ll_receiving,ll_evaluated,ll_refund;
    private ImageView im_message;
    private String isopen;//是否开通佣金 0未开通 1已开通
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        initData();
        return view;
    }
    private void initView(View view) {
        ll_personal_details = view.findViewById(R.id.ll_personal_details);
        ll_receiver_address = view.findViewById(R.id.ll_receiver_address);
        ll_set = view.findViewById(R.id.ll_set);
        ll_collect = view.findViewById(R.id.ll_collect);
        ll_faq = view.findViewById(R.id.ll_faq);
        ll_wallet = view.findViewById(R.id.ll_wallet);
        ll_evaluation = view.findViewById(R.id.ll_evaluation);
        ll_brokerage = view.findViewById(R.id.ll_brokerage);
        ll_message = view.findViewById(R.id.ll_message);
        tv_all = view.findViewById(R.id.tv_all);
        tv_name = view.findViewById(R.id.tv_name);
        ri_icon = view.findViewById(R.id.ri_icon);
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_balance = view.findViewById(R.id.tv_balance);
        tv_todyIncome = view.findViewById(R.id.tv_todyIncome);
        tv_historyIncome = view.findViewById(R.id.tv_historyIncome);
        ll_updateIcon = view.findViewById(R.id.ll_updateIcon);
        ll_about = view.findViewById(R.id.ll_about);
        ll_obligation = view.findViewById(R.id.ll_obligation);
        ll_Overhang = view.findViewById(R.id.ll_Overhang);
        ll_receiving = view.findViewById(R.id.ll_receiving);
        ll_evaluated = view.findViewById(R.id.ll_evaluated);
        ll_refund = view.findViewById(R.id.ll_refund);
        im_message = view.findViewById(R.id.im_message);

        ll_personal_details.setOnClickListener(this);
        ll_receiver_address.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        ll_set.setOnClickListener(this);
        ll_collect.setOnClickListener(this);
        ll_faq.setOnClickListener(this);
        ll_wallet.setOnClickListener(this);
        ll_evaluation.setOnClickListener(this);
        ll_brokerage.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        ll_updateIcon.setOnClickListener(this);
        ll_obligation.setOnClickListener(this);
        ll_Overhang.setOnClickListener(this);
        ll_receiving.setOnClickListener(this);
        ll_evaluated.setOnClickListener(this);
        ll_refund.setOnClickListener(this);
    }
    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_personal_details://个人资料
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent = new Intent(getActivity(), PersonalActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("icon_url",icon_url);
                intent.putExtra("phone",phone);
                intent.putExtra("sex",sex);
                startActivity(intent);
                break;
            case R.id.ll_receiver_address://收货地址
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent1 = new Intent(getActivity(), ReceivewActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_set://设置
                Intent intent2 = new Intent(getActivity(), SetActivity.class);
                intent2.putExtra("phone",phone);
                startActivity(intent2);
                break;
            case R.id.ll_collect://我的收藏
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent3 = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent3);
                break;
            case R.id.ll_faq://常见问题
                Intent intent4 = new Intent(getActivity(), FaqActivity.class);
                startActivity(intent4);
                break;
            case R.id.ll_wallet://我的钱包
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent5 = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent5);
                break;
            case R.id.ll_evaluation://我的评价
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent6 = new Intent(getActivity(), EvaluationActivity.class);
                startActivity(intent6);
                break;
            case R.id.ll_brokerage://佣金管理
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                if (isopen.equals("0")){
                    Intent intent16 = new Intent(getActivity(), MercenaryActivity.class);
                    intent16.putExtra("image4",SQSP.image4);
                    startActivity(intent16);
                }else if (isopen.equals("1")){
                    Intent intent7 = new Intent(getActivity(), BrokerageActivity.class);
                    startActivity(intent7);
                }
                break;
            case R.id.ll_message://消息
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent8 = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent8);
                break;
            case R.id.tv_all://全部订单
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent9 = new Intent(getActivity(), OrderActivity.class);
                intent9.putExtra("position","0");
                startActivity(intent9);
                break;
            case R.id.ll_updateIcon://个人资料
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent10 = new Intent(getActivity(), PersonalActivity.class);
                intent10.putExtra("name",name);
                intent10.putExtra("icon_url",icon_url);
                intent10.putExtra("phone",phone);
                intent10.putExtra("sex",sex);
                startActivity(intent10);
                break;
            case R.id.ll_about://关于我们
                aboutUs();
                break;
            case R.id.ll_obligation://待付款
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent11 = new Intent(getActivity(), OrderActivity.class);
                intent11.putExtra("position","1");
                startActivity(intent11);
                break;
            case R.id.ll_Overhang://待发货
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent12 = new Intent(getActivity(),OrderActivity.class);
                intent12.putExtra("position","2");
                startActivity(intent12);
                break;
            case R.id.ll_receiving://待收货
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                Intent intent13 = new Intent(getActivity(),OrderActivity.class);
                intent13.putExtra("position","3");
                startActivity(intent13);
                break;
            case R.id.ll_evaluated://待评价
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent14 = new Intent(getActivity(),OrderActivity.class);
                intent14.putExtra("position","4");
                startActivity(intent14);
                break;
            case R.id.ll_refund://退款售后
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent15 = new Intent(getActivity(),OrderActivity.class);
                intent15.putExtra("position","5");
                startActivity(intent15);
                break;

        }
    }



    //个人信息
    private void userInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "userInfo");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new SpotsCallBack<UserInfobean>(getContext()) {
            @Override
            public void onSuccess(Response response, UserInfobean resultBean) {
                tv_name.setText(resultBean.getDataObject().getName());
                isopen = resultBean.getDataObject().getIsopen();
                name = resultBean.getDataObject().getName();
                icon_url = resultBean.getDataObject().getIcon();
                phone = resultBean.getDataObject().getPhone();
                sex = resultBean.getDataObject().getSex();
                SQSP.user_icon = resultBean.getDataObject().getIcon();
                SPTool.addSessionMap(SQSP.yongjin,resultBean.getDataObject().getIsopen());
                if (resultBean.getDataObject().getNewMsg().equals("0")){
                    im_message.setImageResource(R.mipmap.xinxi);
                }else {
                    im_message.setImageResource(R.mipmap.xiaoxi_dian);
                }

                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.ic_figure_head)
                        .placeholder(R.mipmap.ic_figure_head))
                        .load(resultBean.getDataObject().getIcon())
                        .into(ri_icon);
                tv_phone.setText("邀请码:"+resultBean.getDataObject().getInviteCode());
                tv_balance.setText(resultBean.getDataObject().getBalance());
                tv_todyIncome.setText(resultBean.getDataObject().getTodyIncome());
                tv_historyIncome.setText(resultBean.getDataObject().getHistoryIncome());
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //关于我们
    private void aboutUs() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "aboutUs");
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new SpotsCallBack<AboutUsbean>(getContext()) {
            @Override
            public void onSuccess(Response response, AboutUsbean resultBean) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("webTitle","华谊凰城");
                intent.putExtra("webUrl",resultBean.getContentUrl());
                startActivity(intent);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {
            onResume();
        } else if (!isVisibleToUser) {
            onPause();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                return;
            }
            userInfo();
            //TODO give the signal that the fragment is visible
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //TODO give the signal that the fragment is invisible
    }

}
