package com.lxkj.hrhc.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.hrhc.Activity.ClassifyActivity;
import com.lxkj.hrhc.Adapter.GridViewAdapter;
import com.lxkj.hrhc.Adapter.SecondAdapter;
import com.lxkj.hrhc.Adapter.StairAdapter;
import com.lxkj.hrhc.Adapter.ViewPagerAdapter;
import com.lxkj.hrhc.App;
import com.lxkj.hrhc.Base.BaseFragment;
import com.lxkj.hrhc.Bean.FirsePagebean;
import com.lxkj.hrhc.Http.BaseCallback;
import com.lxkj.hrhc.Http.OkHttpHelper;
import com.lxkj.hrhc.Http.SpotsCallBack;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.SQSP;
import com.lxkj.hrhc.Uri.NetClass;
import com.lxkj.hrhc.Utils.Utility2;
import com.lxkj.hrhc.View.GlideImageLoader;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:2019.11.13
 * Describe :分类
 */
public class Home2Fragment extends BaseFragment implements View.OnClickListener{

    private ImageView back;
    private TextView title;
    private RecyclerView recyclerViewLeft;
    private RecyclerView recyclerViewRight;
    LinearLayoutManager layoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private StairAdapter stairAdapter;
    private SecondAdapter secondAdapter;
    private List<FirsePagebean.CategoryListBean> stairlist = new ArrayList<>();
    private List<FirsePagebean.CategoryListBean.ChildrenListBean> secondlist = new ArrayList<>();
    private String toptit,categoryId;
    int selectePosition = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class, container, false);
//        Utility2.setActionBar(getContext());
        initView(view);
        initData();
        return view;
    }
    private void initView(View view) {
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        recyclerViewLeft = view.findViewById(R.id.RecyclerViewLeft);
        recyclerViewRight = view.findViewById(R.id.RecyclerViewRight);
        title.setText("分类");
        back.setVisibility(View.GONE);



    }
    private void initData() {
//        userLogin();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewLeft.setLayoutManager(layoutManager);
        stairAdapter = new StairAdapter(getActivity(), stairlist);
        recyclerViewLeft.setAdapter(stairAdapter);
        stairAdapter.setOnItemClickListener(new StairAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                selectePosition = position;
                secondlist.clear();
                secondlist.addAll(stairlist.get(position).getChildrenList());
                secondAdapter.notifyDataSetChanged();
            }

        });

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerViewRight.setLayoutManager(staggeredGridLayoutManager);
        secondAdapter=new SecondAdapter(getActivity(),secondlist);
        recyclerViewRight.setAdapter(secondAdapter);
        secondAdapter .setOnItemClickListener(new SecondAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener( int position) {
                Intent intent = new Intent(getActivity(), ClassifyActivity.class);
                intent.putExtra("categoryId", secondlist.get(position).getChildCategoryId());
                intent.putExtra("title",secondlist.get(position).getChildCategoryName());
                if (selectePosition == 0){
                   String categoryId  = stairlist.get(0).getChildrenList().get(position).getCategoryId();
                    for (int i = 0; i < stairlist.size(); i++) {
                        if (null !=stairlist.get(i).getCategoryId() && categoryId.equals(stairlist.get(i).getCategoryId())){
                            intent.putExtra("bean",stairlist.get(i));
                            break;
                        }
                    }
                }else
                    intent.putExtra("bean",stairlist.get(selectePosition));
                intent.putExtra("type","1");
                startActivity(intent);
            }

        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {
            List<FirsePagebean.CategoryListBean.ChildrenListBean> allSecondlist = new ArrayList<>();
            FirsePagebean.CategoryListBean allCategoryListBean = new FirsePagebean.CategoryListBean();
            for (int i = 0; i <SQSP.shouyelist.size() ; i++) {
                for (int j = 0; j < SQSP.shouyelist.get(i).getChildrenList().size(); j++) {
                    SQSP.shouyelist.get(i).getChildrenList().get(j).setCategoryId(SQSP.shouyelist.get(i).getCategoryId());
                }
                allSecondlist.addAll(SQSP.shouyelist.get(i).getChildrenList());
            }
            allCategoryListBean.setCategoryName("全部");
            allCategoryListBean.setChildrenList(allSecondlist);
            stairlist.clear();
            stairlist.add(allCategoryListBean);
            stairlist.addAll(SQSP.shouyelist);
            secondlist.addAll(stairlist.get(0).getChildrenList());
            secondAdapter.notifyDataSetChanged();
            stairAdapter.notifyDataSetChanged();
        } else if (!isVisibleToUser) {
            onPause();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            //TODO give the signal that the fragment is visible
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //TODO give the signal that the fragment is invisible
    }



}
