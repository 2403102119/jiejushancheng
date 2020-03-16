package com.lxkj.jieju.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lxkj.jieju.Base.BaseFragment;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Utils.SPTool;

/**
 * Created ：李迪迦
 * on:2020/3/3 0003.
 * Describe :
 */

public class Home5Fragment  extends BaseFragment implements View.OnClickListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Fragment[] mFragmentArrays = new Fragment[5];
    private String[] mTabTitles = new String[5];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        initView(view);
        initData();
        return view;
    }
    private void initView(View view) {
        viewPager = view.findViewById(R.id.tab_viewpager);
        tabLayout = view.findViewById(R.id.tablayout);


        mTabTitles[0] = "全部";
        mTabTitles[1] = "待付款";
        mTabTitles[2] = "待发货";
        mTabTitles[3] = "待收货";
        mTabTitles[4] = "已完成";

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);
        mFragmentArrays[0] = WarehouseFragment.newInstance();
        mFragmentArrays[1] = ObligationFragment.newInstance();
        mFragmentArrays[2] = HumpFragment.newInstance();
        mFragmentArrays[3] = ReceivingFragment.newInstance();
        mFragmentArrays[4] = EvaluateFragment.newInstance();
        //TODO https://www.cnblogs.com/zhangqie/p/6404890.html
        PagerAdapter pagerAdapter = new MyViewPagerAdapter( getActivity().getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);

    }
    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    final class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }


        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
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
            //TODO give the signal that the fragment is visible
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //TODO give the signal that the fragment is invisible
    }

}
