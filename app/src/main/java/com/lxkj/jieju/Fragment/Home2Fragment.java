package com.lxkj.jieju.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jieju.Activity.ClassifyActivity;
import com.lxkj.jieju.Adapter.SecondAdapter;
import com.lxkj.jieju.Adapter.StairAdapter;
import com.lxkj.jieju.App;
import com.lxkj.jieju.Base.BaseFragment;
import com.lxkj.jieju.Bean.EvenDyname;
import com.lxkj.jieju.Bean.FirsePagebean;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
    private RoundedImageView im_dingbu;
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
        im_dingbu = view.findViewById(R.id.im_dingbu);
        recyclerViewLeft = view.findViewById(R.id.RecyclerViewLeft);
        recyclerViewRight = view.findViewById(R.id.RecyclerViewRight);
        title.setText("分类");
        back.setVisibility(View.GONE);

        if(!EventBus.getDefault().isRegistered(this)) {//判断是否已经注册了（避免崩溃）
            EventBus.getDefault().register(this); //向EventBus注册该对象，使之成为订阅者
        }

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
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .centerCrop()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(SQSP.shouyelist.get(position).getCategoryImage())
                        .into(im_dingbu);
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
                intent.putExtra("childCategoryId",secondlist.get(position).getChildCategoryId());
                startActivity(intent);
            }

        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING,sticky = false)
    public void getEventmessage(EvenDyname event){
        selectePosition = event.getPosition();
        stairAdapter.selectPosition = selectePosition;
        secondlist.clear();
        secondlist.addAll(stairlist.get(selectePosition).getChildrenList());
        secondAdapter.notifyDataSetChanged();
        stairAdapter.notifyDataSetChanged();
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .centerCrop()
                .error(R.mipmap.logo)
                .placeholder(R.mipmap.logo))
                .load(SQSP.shouyelist.get(selectePosition).getCategoryImage())
                .into(im_dingbu);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {
//            List<FirsePagebean.CategoryListBean.ChildrenListBean> allSecondlist = new ArrayList<>();
//            FirsePagebean.CategoryListBean allCategoryListBean = new FirsePagebean.CategoryListBean();
//            for (int i = 0; i <SQSP.shouyelist.size() ; i++) {
//                for (int j = 0; j < SQSP.shouyelist.get(i).getChildrenList().size(); j++) {
//                    SQSP.shouyelist.get(i).getChildrenList().get(j).setChildCategoryId(SQSP.shouyelist.get(i).getCategoryId());
//                }
//                allSecondlist.addAll(SQSP.shouyelist.get(i).getChildrenList());
//            }
//            allCategoryListBean.setCategoryName("全部");
//            allCategoryListBean.setChildrenList(allSecondlist);
            stairlist.clear();
            secondlist.clear();

//            stairlist.add(allCategoryListBean);
            stairlist.addAll(SQSP.shouyelist);

            if (stairlist.size()>0){
                secondlist.addAll(stairlist.get(0).getChildrenList());
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .centerCrop()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(SQSP.shouyelist.get(0).getCategoryImage())
                        .into(im_dingbu);
            }
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
