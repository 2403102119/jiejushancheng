package com.lxkj.hrhc.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.lxkj.hrhc.Base.BaseActivity;
import com.lxkj.hrhc.Fragment.EvaluateFragment;
import com.lxkj.hrhc.Fragment.HumpFragment;
import com.lxkj.hrhc.Fragment.ObligationFragment;
import com.lxkj.hrhc.Fragment.ReceivingFragment;
import com.lxkj.hrhc.Fragment.RefundFragment;
import com.lxkj.hrhc.Fragment.WarehouseFragment;
import com.lxkj.hrhc.MainActivity;
import com.lxkj.hrhc.R;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :订单
 */
public class OrderActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Fragment[] mFragmentArrays = new Fragment[6];
    private String[] mTabTitles = new String[6];
    private String position;
    private TextView title_t;
    private ImageView im_back;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_order);
        baseTop.setVisibility(View.GONE);
        title_t = findViewById(R.id.title_t);
        im_back = findViewById(R.id.im_back);
        title_t.setText("我的订单");
    }

    @Override
    protected void initEvent() {
        viewPager = findViewById(R.id.tab_viewpager);
        tabLayout = findViewById(R.id.tablayout);

        im_back.setOnClickListener(this);

        mTabTitles[0] = "全部";
        mTabTitles[1] = "待付款";
        mTabTitles[2] = "待发货";
        mTabTitles[3] = "待收货";
        mTabTitles[4] = "待评价";
        mTabTitles[5] = "退款售后";
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);
        mFragmentArrays[0] = WarehouseFragment.newInstance();
        mFragmentArrays[1] = ObligationFragment.newInstance();
        mFragmentArrays[2] = HumpFragment.newInstance();
        mFragmentArrays[3] = ReceivingFragment.newInstance();
        mFragmentArrays[4] = EvaluateFragment.newInstance();
        mFragmentArrays[5] = RefundFragment.newInstance();
        //TODO https://www.cnblogs.com/zhangqie/p/6404890.html
        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);


    }
    @Override
    protected void initData() {
        position = getIntent().getStringExtra("position");
        tabLayout.getTabAt(Integer.parseInt(position)).select();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_back:
                Intent intent = new Intent(mContext,MainActivity.class);
                startActivity(intent);
                finish();
                break;
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
}
