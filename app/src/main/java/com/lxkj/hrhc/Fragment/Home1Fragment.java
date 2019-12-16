package com.lxkj.hrhc.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxkj.hrhc.Activity.BrokerageActivity;
import com.lxkj.hrhc.Activity.ClassifyActivity;
import com.lxkj.hrhc.Activity.DeatilsActivity;
import com.lxkj.hrhc.Activity.LoginActivity;
import com.lxkj.hrhc.Activity.MercenaryActivity;
import com.lxkj.hrhc.Activity.OneActivity;
import com.lxkj.hrhc.Activity.SearchActivity;
import com.lxkj.hrhc.Activity.SerchActivity;
import com.lxkj.hrhc.Activity.SpecialActivity;
import com.lxkj.hrhc.Activity.ThreeActivity;
import com.lxkj.hrhc.Activity.TwoActivity;
import com.lxkj.hrhc.Activity.WarehousesActivity;
import com.lxkj.hrhc.Activity.WebViewActivity;
import com.lxkj.hrhc.Adapter.GridViewAdapter;
import com.lxkj.hrhc.Adapter.GuessAdapter;
import com.lxkj.hrhc.Adapter.Home1Adapter;
import com.lxkj.hrhc.Adapter.ViewPagerAdapter;
import com.lxkj.hrhc.App;
import com.lxkj.hrhc.Base.BaseFragment;
import com.lxkj.hrhc.Bean.FirsePagebean;
import com.lxkj.hrhc.Bean.NoticeImagebean;
import com.lxkj.hrhc.Bean.privacyBean;
import com.lxkj.hrhc.Http.BaseCallback;
import com.lxkj.hrhc.Http.OkHttpHelper;
import com.lxkj.hrhc.Http.SpotsCallBack;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.SQSP;
import com.lxkj.hrhc.Uri.NetClass;
import com.lxkj.hrhc.Utils.SPTool;
import com.lxkj.hrhc.Utils.ToastFactory;
import com.lxkj.hrhc.Utils.Utility2;
import com.lxkj.hrhc.Utils.Utils;
import com.lxkj.hrhc.View.ActionDialog;
import com.lxkj.hrhc.View.GlideImageLoader;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.angmarch.views.NiceSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

import static com.lxkj.hrhc.App.mContext;


public class Home1Fragment extends BaseFragment implements View.OnClickListener,TextView.OnEditorActionListener{
    private final static String TAG = "Home4Fragment";
    private Banner banner;
    List<String> BanString = new ArrayList<>();
    private RecyclerView banner_recycle,guessrecycle;
    LinearLayoutManager layoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    Home1Adapter adapter;
    GuessAdapter guessAdapter;
    List<FirsePagebean.JprouctListBean> list=new ArrayList<>();
    List<FirsePagebean.JprouctListBean.PListBean> item_list=new ArrayList<>();
    private ImageView tv_mercenary,tv_warehouses,tv_special;
    private EditText et_seek;

    private List<FirsePagebean.CategoryListBean> homeEntrances = new ArrayList<>();


    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager mPager;
    private List<View> mPagerList;
    private LayoutInflater inflater;
    private String image4,image5,image6;

    private int pageCount;
    private int pageSize = 8;
    private int curIndex = 0;
    private String str_location;

    private TextView tv1,tv_yongjin,tv_tejia,tv_qingcang;

    private NestedScrollView ns;

    private int fadingHeight = 600; // 当ScrollView滑动到什么位置时渐变消失（根据需要进行调整）
    private Drawable drawable; // 顶部渐变布局需设置的Drawable
    private LinearLayout page_top;//导航栏
    private static final int START_ALPHA = 0;//scrollview滑动开始位置
    private static final int END_ALPHA = 255;//scrollview滑动结束位置

    private PopupWindow popupWindow;
    private LinearLayout ll_sell;
    private LinearLayout ll_sell_item;
    private String title1,title2,title3;
    private List<FirsePagebean.CategoryListBean> stairlist = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        initView(view);
        initData();
        return view;
    }
    private void initView(View view) {
        banner = view.findViewById(R.id.banner);
        tv_mercenary = view.findViewById(R.id.tv_mercenary);
        banner_recycle = view.findViewById(R.id.banner_recycle);
        guessrecycle = view.findViewById(R.id.guessrecycle);
        tv_warehouses = view.findViewById(R.id.tv_warehouses);
        tv_special = view.findViewById(R.id.tv_special);
        et_seek = view.findViewById(R.id.et_seek);
        mPager =  view.findViewById(R.id.viewpager);
        et_seek =  view.findViewById(R.id.et_seek);
        tv1 =  view.findViewById(R.id.tv1);
        ns =  view.findViewById(R.id.ns);
        page_top =  view.findViewById(R.id.page_top);
        tv_yongjin =  view.findViewById(R.id.tv_yongjin);
        tv_tejia =  view.findViewById(R.id.tv_tejia);
        tv_qingcang =  view.findViewById(R.id.tv_qingcang);
        drawable = getResources().getDrawable(R.color.red_them);
        drawable.setAlpha(START_ALPHA);
        page_top.setBackgroundDrawable(drawable);

        //调用方法
        ns.setOnScrollChangeListener(scrollChangedListener);

        String XingQu = SPTool.getSessionValue(SQSP.Shi);
        if (!TextUtils.isEmpty(XingQu)) {
            tv1.setText(XingQu);
        } else {
            tv1.setText("郑州市");
        }
        tv_mercenary.setOnClickListener(this);
        tv_warehouses.setOnClickListener(this);
        tv_special.setOnClickListener(this);
        et_seek.setOnEditorActionListener(this);

        layoutManager = new LinearLayoutManager(getActivity());
        banner_recycle.setLayoutManager(layoutManager);
        adapter = new Home1Adapter(getActivity(), list);
        banner_recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new Home1Adapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition, int position) {
                Intent intent = new Intent(getContext(), DeatilsActivity.class);
                intent.putExtra("productid",list.get(position).getPList().get(firstPosition).getProductid());
                startActivity(intent);
            }

            @Override
            public void OnImage(int position) {
                String type = null;
                if (position == 0){
                    type = "3";
                    Intent intent = new Intent(getContext(),OneActivity.class);
                    intent.putExtra("image",list.get(position).getPictures());
                    intent.putExtra("position",type);
                    intent.putExtra("title",title1);
                    startActivity(intent);
                }else if (position == 1){
                    type = "4";
                    Intent intent = new Intent(getContext(),TwoActivity.class);
                    intent.putExtra("image",list.get(position).getPictures());
                    intent.putExtra("position",type);
                    intent.putExtra("title",title2);
                    startActivity(intent);
                }else if (position== 2){
                    type = "5";
                    Intent intent = new Intent(getContext(),ThreeActivity.class);
                    intent.putExtra("image",list.get(position).getPictures());
                    intent.putExtra("position",type);
                    intent.putExtra("title",title3);
                    startActivity(intent);
                }

            }

        });

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        guessrecycle.setLayoutManager(staggeredGridLayoutManager);
        guessAdapter=new GuessAdapter(getActivity(),item_list);
        guessrecycle.setAdapter(guessAdapter);
        guessAdapter .setOnItemClickListener(new GuessAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener( int position) {
//                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))) {
//                    ToastFactory.getToast(getActivity(), "请先登录").show();
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                    //finish();
//                    return;
//                }
                Intent intent = new Intent(getContext(), DeatilsActivity.class);
                intent.putExtra("productid",item_list.get(position).getProductid());
                startActivity(intent);
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
        noticeImage();
        userLogin();

        et_seek.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                 /*判断是否是“GO”键*/
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    return true;
                }

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (!TextUtils.isEmpty(et_seek.getText().toString())) {
                        String edStr = et_seek.getText().toString().trim();
                        Intent intent = new Intent(getActivity(), SerchActivity.class);
                        intent.putExtra("text",edStr);
                        startActivity(intent);
//                        //隐藏软键盘
//                      hideInputMethod();
                    } else {
                        ToastFactory.getToast(mContext, "关键字不能为空").show();
                    }
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch(actionId){
            case EditorInfo.IME_ACTION_DONE:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    public  void  state(String url){
        popupWindow=new PopupWindow(getContext());
        View view=getLayoutInflater().inflate(R.layout.popup_image,null);
        ll_sell_item= view.findViewById(R.id.ll_sell_item);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setClippingEnabled(false);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        ll_sell=view.findViewById(R.id.ll_sell);
        ImageView image = view.findViewById(R.id.image);
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.ic_figure_head)
                .placeholder(R.mipmap.ic_figure_head))
                .load(url)
                .into(image);
        ll_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                ll_sell.clearAnimation();
            }
        });


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_mercenary://成为佣兵
                if (SPTool.getSessionValue(SQSP.yongjin).equals("1")){
                    Intent intent7 = new Intent(getActivity(), BrokerageActivity.class);
                    startActivity(intent7);
                }else {
                    Intent intent = new Intent(getActivity(), MercenaryActivity.class);
                    intent.putExtra("image4",image4);
                    startActivity(intent);
                }
                break;
            case R.id.tv_warehouses://品牌清仓
                Intent intent1 = new Intent(getActivity(), WarehousesActivity.class);
                intent1.putExtra("image6",image6);
                startActivity(intent1);
                break;
            case R.id.tv_special://特价限购
                Intent intent2 = new Intent(getActivity(), SpecialActivity.class);
                intent2.putExtra("image5",image5);
                startActivity(intent2);
                break;
        }
    }

    //首页初始化
    private void userLogin() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "firstPageinit");
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new SpotsCallBack<FirsePagebean>(getContext()) {
            @Override
            public void onSuccess(Response response, final FirsePagebean resultBean) {
                title1 = resultBean.getAreaTitle1();
                title2 = resultBean.getAreaTitle2();
                title3 = resultBean.getAreaTitle3();
                image4 = resultBean.getImage4();
                SQSP.image4 = resultBean.getImage4();
                image5 = resultBean.getImage5();
                image6 = resultBean.getImage6();
                SQSP.shouyelist = resultBean.getCategoryList();

                BanString.clear();
                for (int i = 0; i <resultBean.getBannerList().size() ; i++) {
                    BanString.add(resultBean.getBannerList().get(i).getImage());
                }
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                banner.setImageLoader(new GlideImageLoader());
                banner.setBannerAnimation(Transformer.DepthPage);
                banner.setIndicatorGravity(BannerConfig.CENTER);
                banner.setDelayTime(5000);
                banner.isAutoPlay(true);
                banner.setIndicatorGravity(BannerConfig.CENTER);
                banner.setImages(BanString).setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        if ( resultBean.getBannerList().get(position).getType().equals("0")){
                            Intent intent = new Intent(getActivity(), WebViewActivity.class);
                            intent.putExtra("webTitle","华谊凰城");
                            intent.putExtra("webUrl",resultBean.getBannerList().get(position).getUrl());
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(getActivity(), DeatilsActivity.class);
                            intent.putExtra("productid",resultBean.getBannerList().get(position).getProductid());
                            startActivity(intent);
                        }
//                        showImage(new ImageView(getActivity()), position);
                    }
                }).start();

                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(resultBean.getImage1())
                        .into(tv_mercenary);
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(resultBean.getImage2())
                        .into(tv_special);
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(resultBean.getImage3())
                        .into(tv_warehouses);


                list.clear();
                item_list.clear();
                for (int i = 0; i <resultBean.getJprouctList().size() ; i++) {
                    if (resultBean.getJprouctList().get(i).getType().equals("0")){
                        item_list.addAll(resultBean.getJprouctList().get(i).getPList());
                        guessAdapter.notifyDataSetChanged();
                    }else if (resultBean.getJprouctList().get(i).getType().equals("1")){
                        resultBean.getJprouctList().get(i).setAreaimage(resultBean.getAreaimage1());
                        resultBean.getJprouctList().get(i).setPictures(resultBean.getImage7());
                        list.add(resultBean.getJprouctList().get(i));
                    }else if (resultBean.getJprouctList().get(i).getType().equals("2")){
                        resultBean.getJprouctList().get(i).setAreaimage(resultBean.getAreaimage2());
                        resultBean.getJprouctList().get(i).setPictures(resultBean.getImage8());
                        list.add(resultBean.getJprouctList().get(i));
                    }else if (resultBean.getJprouctList().get(i).getType().equals("3")){
                        resultBean.getJprouctList().get(i).setAreaimage(resultBean.getAreaimage3());
                        resultBean.getJprouctList().get(i).setPictures(resultBean.getImage9());

                        list.add(resultBean.getJprouctList().get(i));
                    }
                }
                adapter.notifyDataSetChanged();

                List<FirsePagebean.CategoryListBean.ChildrenListBean> allSecondlist = new ArrayList<>();
                FirsePagebean.CategoryListBean allCategoryListBean = new FirsePagebean.CategoryListBean();
                for (int i = 0; i <SQSP.shouyelist.size() ; i++) {
                    allSecondlist.addAll(SQSP.shouyelist.get(i).getChildrenList());
                }
                allCategoryListBean.setCategoryName("全部");
                allCategoryListBean.setChildrenList(allSecondlist);
                stairlist.clear();
                stairlist.add(allCategoryListBean);
                stairlist.addAll(SQSP.shouyelist);


                homeEntrances.addAll(resultBean.getCategoryList());
                inflater = LayoutInflater.from(getContext());
                //总的页数=总数/每页数量，并取整
                pageCount = (int) Math.ceil(homeEntrances.size() * 1.0 / pageSize);
                mPagerList = new ArrayList<>();
                for (int i = 0; i <= pageCount; i++) {
                    // 每个页面都是inflate出一个新实例
                    View view =  inflater.inflate(R.layout.item_grid, mPager, false);
                    GridView gridView = view.findViewById(R.id.gridview);
                    gridView.setAdapter(new GridViewAdapter(getContext(), homeEntrances, i, pageSize));
                    mPagerList.add(gridView);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int pos = position + curIndex * pageSize;
//                            Toast.makeText(getContext(), homeEntrances.get(pos).getCategoryName(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), ClassifyActivity.class);
                            intent.putExtra("categoryId",homeEntrances.get(pos).getCategoryId());
                            intent.putExtra("title", homeEntrances.get(pos).getCategoryName());
                            intent.putExtra("bean",stairlist.get(position + 1));
                            intent.putExtra("type","0");
                            startActivity(intent);
                        }
                    });

                    viewPagerAdapter = new ViewPagerAdapter(mPagerList);
                    //设置适配器
                    mPager.setAdapter(viewPagerAdapter);
                }



            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //首页通知图
    private void noticeImage() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "noticeImage");
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new BaseCallback<NoticeImagebean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, final NoticeImagebean resultBean) {
                if (resultBean.getState().equals("1")){
                    state(resultBean.getImage());
                    ll_sell.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.activity_translate_in));
                    popupWindow.showAtLocation(getView(), Gravity.BOTTOM,0,0);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }




    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {

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

    private void showImage(final ImageView iv, int position) {
        List<Object> urls = new ArrayList<>();
        urls.addAll(BanString);
        new XPopup.Builder(getActivity()).asImageViewer(iv, position, urls, new OnSrcViewUpdateListener() {
            @Override
            public void onSrcViewUpdate(ImageViewerPopupView popupView, int position) {
                popupView.updateSrcView(iv);
            }
        }, new ImageLoader())
                .show();
    }


    class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object url, @NonNull ImageView imageView) {
            //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
            Glide.with(imageView).load(url).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher).override(Target.SIZE_ORIGINAL)).into(imageView);
        }

        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {
            try {
                return Glide.with(context).downloadOnly().load(uri).submit().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
