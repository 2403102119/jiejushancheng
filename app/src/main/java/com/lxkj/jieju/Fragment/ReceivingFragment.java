package com.lxkj.jieju.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lxkj.jieju.Activity.AppraiseActivity;
import com.lxkj.jieju.Activity.DetailsrefundActivity;
import com.lxkj.jieju.Activity.LookExpressActivity;
import com.lxkj.jieju.Activity.OrderdetailActivity;
import com.lxkj.jieju.Activity.PayActivity;
import com.lxkj.jieju.Adapter.OrderAdapter;
import com.lxkj.jieju.Base.BaseFragment;
import com.lxkj.jieju.Bean.Orderbean;
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
 * on:2019/11/27 0027.
 * Describe :待收货
 */

public class ReceivingFragment extends BaseFragment implements View.OnClickListener {
    public static Fragment newInstance() {
        ReceivingFragment fragment = new ReceivingFragment();
        return fragment;
    };
    private static final String TAG = "ReceivingFragment";
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    OrderAdapter adapter;
    List<Orderbean.DataListBean> list=new ArrayList<>();
    private ActionDialog shouhuodialog,shanchudialog;
    private String orderid;
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warehouse, container, false);
        initView(view);
        initData();
        return view;
    }
    private void initView(View view) {
        smart = view.findViewById(R.id.smart);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                myOrder(String.valueOf(pageNoIndex));

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    myOrder(String.valueOf(pageNoIndex));
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
        recycle = view.findViewById(R.id.recycle);
        layoutManager = new LinearLayoutManager(getContext());
        recycle.setLayoutManager(layoutManager);
        adapter = new OrderAdapter(getContext(), list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }

            @Override
            public void OnbuttonImage(int position) {
                Intent intent = new Intent(getActivity(), OrderdetailActivity.class);
                intent.putExtra("orderid",list.get(position).getOrderid());
                startActivity(intent);
            }

            @Override
            public void tuikuanxiangqing(int position) {
//                Intent intent1 = new Intent(getActivity(),DetailsrefundActivity.class);
//                intent1.putExtra("orderid",list.get(position).getOrderid());
//                startActivity(intent1);
            }

            @Override
            public void qupingjia(int position) {
//                Intent intent = new Intent(getActivity(), AppraiseActivity.class);
//                intent.putExtra("orderid",list.get(position).getOrderid());
//                startActivity(intent);
            }

            @Override
            public void chakanwuliu(int position) {
                Intent intent2 = new Intent(getActivity(),LookExpressActivity.class);
                intent2.putExtra("emsno",list.get(position).getEmsno());
                intent2.putExtra("emscode",list.get(position).getEmscode());
                intent2.putExtra("expCode",list.get(position).getEmsname());
                startActivity(intent2);
            }

            @Override
            public void querenshouhuo(int position) {
                orderid = list.get(position).getOrderid();
                shouhuodialog.show();
            }

            @Override
            public void qufukuan(int position) {
                Intent intent = new Intent(getActivity(),PayActivity.class);
                intent.putExtra("moeny",list.get(position).getOrderPrice());
                intent.putExtra("orderid",list.get(position).getOrderid());
                startActivity(intent);
            }

            @Override
            public void shaunchu(int position) {
                shanchudialog.show();
                orderid = list.get(position).getOrderid();
            }


        });
    }
    private void initData() {
        shouhuodialog = new ActionDialog(getContext(),"","","确认收货？","再想想","确认");
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
        shanchudialog = new ActionDialog(getContext(),"","","确定删除？","再想想","确认");
        shanchudialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                shanchudialog.dismiss();
            }

            @Override
            public void onRightClick() {
                delOrder(orderid);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){


        }
    }
    //确认收货
    private void finishOrder(String orderid) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "finishOrder");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("orderid",orderid);
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(getContext()) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                myOrder("1");
                Toast.makeText(getContext(),resultBean.getResultNote(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //我的订单
    private void myOrder(String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "myOrder");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("status","3");
        params.put("nowPage",nowPage);
        params.put("pageCount",SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new SpotsCallBack<Orderbean>(getContext()) {
            @Override
            public void onSuccess(Response response, Orderbean resultBean) {
                smart.finishRefresh();
                if (resultBean.getDataList() != null) {
                    totalPage = resultBean.getTotalPage();
                    if (pageNoIndex == 1) {
                        list.clear();
                    }
                    list.addAll(resultBean.getDataList());
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                smart.finishRefresh();
            }
        });
    }
    //删除订单
    private void delOrder(String orderid) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "delOrder");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("orderid",orderid);
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(getContext()) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                myOrder("1");
                Toast.makeText(getContext(),resultBean.getResultNote(),Toast.LENGTH_SHORT).show();
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
            myOrder("1");
            //TODO give the signal that the fragment is visible
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //TODO give the signal that the fragment is invisible
    }

}
