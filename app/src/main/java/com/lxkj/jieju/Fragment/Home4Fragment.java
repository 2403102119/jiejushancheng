package com.lxkj.jieju.Fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jieju.Activity.BrokerageActivity;
import com.lxkj.jieju.Activity.CollectActivity;
import com.lxkj.jieju.Activity.DeatilsActivity;
import com.lxkj.jieju.Activity.EvaluationActivity;
import com.lxkj.jieju.Activity.FaqActivity;
import com.lxkj.jieju.Activity.LoginActivity;
import com.lxkj.jieju.Activity.MercenaryActivity;
import com.lxkj.jieju.Activity.MessageActivity;
import com.lxkj.jieju.Activity.OrderActivity;
import com.lxkj.jieju.Activity.PersonalActivity;
import com.lxkj.jieju.Activity.ReceivewActivity;
import com.lxkj.jieju.Activity.SetActivity;
import com.lxkj.jieju.Activity.WalletActivity;
import com.lxkj.jieju.Activity.WebViewActivity;
import com.lxkj.jieju.Adapter.Home4Adapter;
import com.lxkj.jieju.Adapter.Recycle_oneAdapter;
import com.lxkj.jieju.Adapter.recommendProductAdapter;
import com.lxkj.jieju.App;
import com.lxkj.jieju.Base.BaseFragment;
import com.lxkj.jieju.Bean.AboutUsbean;
import com.lxkj.jieju.Bean.UserInfobean;
import com.lxkj.jieju.Bean.productcommentbean;
import com.lxkj.jieju.Bean.recommendProductbean;
import com.lxkj.jieju.Http.BaseCallback;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.StringUtil_li;
import com.lxkj.jieju.Utils.ToastFactory;
import com.lxkj.jieju.View.ActionDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class Home4Fragment extends BaseFragment implements View.OnClickListener{
    private final static String TAG = "Home4Fragment";
    private LinearLayout ll_about,ll_personal_details,ll_receiver_address,ll_set,ll_collect,ll_faq,ll_wallet,ll_evaluation,ll_brokerage,ll_updateIcon;
    private TextView tv_all,tv_name,tv_phone,tv_balance,tv_todyIncome,tv_historyIncome;
    private RoundedImageView ri_icon;
    private String name,icon_url,phone,sex;
    private LinearLayout ll_obligation,ll_Overhang,ll_receiving,ll_evaluated,ll_refund;
    private ImageView im_message;
    private String isopen;//是否开通佣金 0未开通 1已开通
    private RecyclerView recycle;
    StaggeredGridLayoutManager layoutManager;
    recommendProductAdapter adapter;
    private List<recommendProductbean.DataListBean> list=new ArrayList<>();

    private NestedScrollView ns;

    private int fadingHeight = 200; // 当ScrollView滑动到什么位置时渐变消失（根据需要进行调整）
    private Drawable drawable; // 顶部渐变布局需设置的Drawable
    private LinearLayout ll_message;//导航栏
    private static final int START_ALPHA = 0;//scrollview滑动开始位置
    private static final int END_ALPHA = 255;//scrollview滑动结束位置
    private String agentState;
    private ActionDialog shaunchudialog;

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
        recycle = view.findViewById(R.id.recycle);
        ns = view.findViewById(R.id.ns);
        ns = view.findViewById(R.id.ns);

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


        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recycle.setLayoutManager(layoutManager);
        adapter = new recommendProductAdapter(getContext(), list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new recommendProductAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                Intent intent = new Intent(getContext(), DeatilsActivity.class);
                intent.putExtra("productid",list.get(firstPosition).getProductid());
                startActivity(intent);
            }


        });


        drawable = getResources().getDrawable(R.color.red_them);
        drawable.setAlpha(START_ALPHA);
        ll_message.setBackgroundDrawable(drawable);

        //调用方法
        ns.setOnScrollChangeListener(scrollChangedListener);

        shaunchudialog = new ActionDialog(getContext(),"","","审核失败，请重新申请!","取消","确认");
        shaunchudialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                shaunchudialog.dismiss();
            }

            @Override
            public void onRightClick() {
                Intent intent6 = new Intent(getActivity(), EvaluationActivity.class);
                intent6.putExtra("agentState",agentState);
                startActivity(intent6);
            }
        });
    }
    /**
     * ScrollView的滚动监听
     */
    private NestedScrollView.OnScrollChangeListener scrollChangedListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
            if (i1 > fadingHeight) {
                i1 = fadingHeight; // 当滑动到指定位置之后设置颜色为纯色，之前的话要渐变---实现下面的公式即可

//                relativela_id.setBackgroundColor(Color.WHITE);
            } else if (i1 < 0) {
                i1 = 0;
            } else {
//                relativela_id.setBackgroundColor(0x99FFFFFF);
            }
            drawable.setAlpha(i1 * (END_ALPHA - START_ALPHA) / fadingHeight
                    + START_ALPHA);
        }
    };
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
            case R.id.ll_receiver_address://我的足迹
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent5 = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent5);
                break;
            case R.id.ll_collect://收货地址
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent1 = new Intent(getActivity(), ReceivewActivity.class);
                intent1.putExtra("type","type");
                startActivity(intent1);
                break;
            case R.id.ll_faq://常见问题
                Intent intent4 = new Intent(getActivity(), FaqActivity.class);
                startActivity(intent4);
                break;
            case R.id.ll_evaluation://经销申请
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                if (agentState.equals("3")){
                   shaunchudialog.show();
                }else {
                    Intent intent6 = new Intent(getActivity(), EvaluationActivity.class);
                    intent6.putExtra("agentState",agentState);
                    startActivity(intent6);
                }
                break;
            case R.id.ll_brokerage://商品收藏
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent3 = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent3);
                break;
            case R.id.ll_message://设置
//                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
//                    ToastFactory.getToast(getActivity(), "请先登录").show();
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                    //finish();
//                    return;
//                }
//                Intent intent8 = new Intent(getActivity(), MessageActivity.class);
//                startActivity(intent8);
                Intent intent2 = new Intent(getActivity(), SetActivity.class);
                intent2.putExtra("phone",phone);
                startActivity(intent2);
                break;
//            case R.id.tv_all://全部订单
//                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
//                    ToastFactory.getToast(getActivity(), "请先登录").show();
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                    //finish();
//                    return;
//                }
//                Intent intent9 = new Intent(getActivity(), OrderActivity.class);
//                intent9.putExtra("position","0");
//                startActivity(intent9);
//                break;
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
                    //finish();
                    return;
                }
                Intent intent14 = new Intent(getActivity(),OrderActivity.class);
                intent14.putExtra("position","4");
                startActivity(intent14);
                break;
            case R.id.ll_evaluated://全部
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
//            case R.id.ll_refund://退款售后
//                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
//                    ToastFactory.getToast(getActivity(), "请先登录").show();
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                    //finish();
//                    return;
//                }
//                Intent intent15 = new Intent(getActivity(),OrderActivity.class);
//                intent15.putExtra("position","5");
//                startActivity(intent15);
//                break;

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
                name = resultBean.getDataObject().getName();
                icon_url = resultBean.getDataObject().getIcon();
                phone = resultBean.getDataObject().getPhone();
                agentState = resultBean.getDataObject().getAgentState();
                SQSP.user_icon = resultBean.getDataObject().getIcon();

                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.ic_figure_head)
                        .placeholder(R.mipmap.ic_figure_head))
                        .load(resultBean.getDataObject().getIcon())
                        .into(ri_icon);
                tv_phone.setText(String.valueOf(StringUtil_li.replacePhone(resultBean.getDataObject().getPhone())));
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    //商品详情底部、购物车底部商品推荐
    private void showFirstPage(String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "recommendProduct");
        params.put("productid", "");
        params.put("nowPage",nowPage);
        params.put("pageCount","4");
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new BaseCallback<recommendProductbean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, final recommendProductbean resultBean) {
                list.clear();
                list.addAll(resultBean.getDataList());
                adapter.notifyDataSetChanged();
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
                intent.putExtra("webTitle","关于我们");
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
            showFirstPage("1");
            //TODO give the signal that the fragment is visible
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //TODO give the signal that the fragment is invisible
    }

}
