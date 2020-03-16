package com.lxkj.jieju.Activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.jieju.Adapter.Broketragedapter;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Brokeragebean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.ShareUtils;
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
 * Describe :佣金奖励
 */
public class BrokerageActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    Broketragedapter adapter;
    List<Brokeragebean.FriendListBean> list=new ArrayList<>();
    private String type = "1";
    private TextView tv_login;
    private TextView tv_code,tv_people,tv_money,tv_one,tv_two;
    private View view_one,view_two;
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private static final String TAG = "BrokerageActivity";

    public void initSystemBar2(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_brokerage);
        initSystemBar2(this);

        baseTop.setVisibility(View.GONE);
        ImageView finishBack = findViewById(R.id.finishBack);
        TextView titleName = findViewById(R.id.titleName);
        FrameLayout fl_top = findViewById(R.id.fl_top);
        fl_top.setBackgroundColor(getResources().getColor(R.color.red_them));
        finishBack.setVisibility(View.VISIBLE);
        finishBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleName.setText("邀请好友");
        recycle = findViewById(R.id.recycle);
        tv_code = findViewById(R.id.tv_code);
        tv_people = findViewById(R.id.tv_people);
        tv_money = findViewById(R.id.tv_money);
        tv_one = findViewById(R.id.tv_one);
        tv_two = findViewById(R.id.tv_two);
        view_one = findViewById(R.id.view_one);
        view_two = findViewById(R.id.view_two);
        smart = findViewById(R.id.smart);
        tv_login = findViewById(R.id.tv_login);

    }

    @Override
    protected void initEvent() {

        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);
        tv_login.setOnClickListener(this);

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                commissionAward(type,String.valueOf(pageNoIndex));

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    commissionAward(type,String.valueOf(pageNoIndex));
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

        layoutManager = new LinearLayoutManager(BrokerageActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new Broketragedapter(BrokerageActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new Broketragedapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {


            }
        });
    }

    @Override
    protected void initData() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_one:
                type = "1";
                tv_one.setTextColor(getResources().getColor(R.color.red_them));
                view_one.setBackgroundColor(getResources().getColor(R.color.red_them));
                tv_two.setTextColor(getResources().getColor(R.color.black));
                view_two.setBackgroundColor(getResources().getColor(R.color.white));
                commissionAward(type,"1");
                break;
            case R.id.tv_two:
                type = "2";
                tv_one.setTextColor(getResources().getColor(R.color.black));
                view_one.setBackgroundColor(getResources().getColor(R.color.white));
                tv_two.setTextColor(getResources().getColor(R.color.red_them));
                view_two.setBackgroundColor(getResources().getColor(R.color.red_them));
                commissionAward(type,"1");
                break;
            case R.id.tv_login://分享给好友
                new ShareUtils(BrokerageActivity.this).share("http://m.huayihc.com/share/index.html","华谊凰城邀请您下载注册app", SQSP.shareName);
                break;
        }
    }


    //佣金奖励页面
    private void commissionAward(String type,String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "commissionAward");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("type", type);
        params.put("nowPage", nowPage);
        params.put("pageCount", SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Brokeragebean>(mContext) {
            @Override
            public void onSuccess(Response response, Brokeragebean resultBean) {
                tv_code.setText(resultBean.getInviteCode());
                tv_people.setText(resultBean.getInviteNum());
                tv_money.setText(resultBean.getCommission());

                smart.finishRefresh();

//                totalPage = Integer.parseInt(resultBean.getTotalPage());
//                if (pageNoIndex == 1) {
                    list.clear();
//                }
                list.addAll(resultBean.getFriendList());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                smart.finishRefresh();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        commissionAward(type,"1");
    }


}
