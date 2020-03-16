package com.lxkj.jieju.Fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.LatLng;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxkj.jieju.Activity.BrokerageActivity;
import com.lxkj.jieju.Activity.CitySelectActivity;
import com.lxkj.jieju.Activity.ClassifyActivity;
import com.lxkj.jieju.Activity.DeatilsActivity;
import com.lxkj.jieju.Activity.MercenaryActivity;
import com.lxkj.jieju.Activity.MessageActivity;
import com.lxkj.jieju.Activity.OneActivity;
import com.lxkj.jieju.Activity.SearchActivity;
import com.lxkj.jieju.Activity.SerchActivity;
import com.lxkj.jieju.Activity.SpecialActivity;
import com.lxkj.jieju.Activity.ThreeActivity;
import com.lxkj.jieju.Activity.TwoActivity;
import com.lxkj.jieju.Activity.WarehousesActivity;
import com.lxkj.jieju.Activity.WebViewActivity;
import com.lxkj.jieju.Adapter.GridViewAdapter;
import com.lxkj.jieju.Adapter.GuessAdapter;
import com.lxkj.jieju.Adapter.Home1Adapter;
import com.lxkj.jieju.Adapter.ViewPagerAdapter;
import com.lxkj.jieju.App;
import com.lxkj.jieju.Base.BaseFragment;
import com.lxkj.jieju.Bean.EvenDyname;
import com.lxkj.jieju.Bean.FirsePagebean;
import com.lxkj.jieju.Bean.NoticeImagebean;
import com.lxkj.jieju.Bean.showFirstPagebean;
import com.lxkj.jieju.Http.BaseCallback;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.MainActivity;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.SPUtil;
import com.lxkj.jieju.Utils.StringUtil;
import com.lxkj.jieju.Utils.ToastFactory;
import com.lxkj.jieju.View.GlideImageLoader;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

import static com.lxkj.jieju.App.mContext;


public class Home1Fragment extends BaseFragment implements View.OnClickListener,TextView.OnEditorActionListener{
    private Banner banner;
    List<String> BanString = new ArrayList<>();
    private RecyclerView banner_recycle,guessrecycle;
    LinearLayoutManager layoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    Home1Adapter adapter;
    GuessAdapter guessAdapter;
    List<FirsePagebean.JprouctListBean> list=new ArrayList<>();
    List<showFirstPagebean.PListBean> item_list=new ArrayList<>();
    private ImageView tv_mercenary,tv_warehouses,tv_special;
    private TextView et_seek;
    private List<FirsePagebean.CategoryListBean> homeEntrances = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;
    private RecyclerView fenlei_recycle;
    private TextView tv1;
    private ImageView im_ziying1,im_jingxiao1,im_message;
    private PopupWindow popupWindow;
    private LinearLayout ll_sell,ll_ziying,ll_jingxiao;
    private LinearLayout ll_sell_item,ll_search;
    private List<FirsePagebean.CategoryListBean> stairlist = new ArrayList<>();
    private String image1,image2,image3,image4,image5;
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private static final String TAG = "Home1Fragment";
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
        fenlei_recycle =  view.findViewById(R.id.fenlei_recycle);
        tv1 =  view.findViewById(R.id.tv1);
        im_ziying1 =  view.findViewById(R.id.im_ziying1);
        im_jingxiao1 =  view.findViewById(R.id.im_jingxiao1);
        ll_ziying =  view.findViewById(R.id.ll_ziying);
        ll_jingxiao =  view.findViewById(R.id.ll_jingxiao);
        ll_search =  view.findViewById(R.id.ll_search);
        im_message =  view.findViewById(R.id.im_message);
//        smart =  view.findViewById(R.id.smart);


        String XingQu = SPTool.getSessionValue(SQSP.Shi);
        if (!TextUtils.isEmpty(XingQu)) {
            tv1.setText(XingQu);
        } else {
            tv1.setText("郑州市");
        }
        tv_mercenary.setOnClickListener(this);
        tv_warehouses.setOnClickListener(this);
        tv_special.setOnClickListener(this);
        ll_ziying.setOnClickListener(this);
        tv1.setOnClickListener(this);
        ll_jingxiao.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        im_message.setOnClickListener(this);


//        smart.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                pageNoIndex = 1;
//                showFirstPage(tv1.getText().toString(),String.valueOf(pageNoIndex));
//
//                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
//            }
//        });
//        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                if (pageNoIndex < totalPage) {
//                    pageNoIndex++;
//                    showFirstPage(tv1.getText().toString(),String.valueOf(pageNoIndex));
//                    Log.i(TAG, "onLoadMore: 执行上拉加载");
//                    smart.finishLoadMore();
//                } else {
//                    Log.i(TAG, "onLoadMore: 相等不可刷新");
//                    //smartRefreshLayout.setEnableLoadMore(false);
//                    smart.finishRefresh(2000, true);//传入false表示刷新失败
//                    smart.finishLoadMore();
//                }
//            }
//        });

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
                if (position == 0){
                    Intent intent = new Intent(getContext(),OneActivity.class);
                    intent.putExtra("image",list.get(position).getAreaimage());
                    intent.putExtra("title","网红专区");
                    intent.putExtra("type","3");
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(),OneActivity.class);
                    intent.putExtra("image",list.get(position).getAreaimage());
                    intent.putExtra("title","淘宝热卖");
                    intent.putExtra("type","4");
                    startActivity(intent);
                }
            }

        });

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fenlei_recycle.setLayoutManager(layoutManager);
        viewPagerAdapter = new ViewPagerAdapter(getActivity(), homeEntrances);
        fenlei_recycle.setAdapter(viewPagerAdapter);
        viewPagerAdapter.setOnItemClickListener(new ViewPagerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
//                Intent intent = new Intent(getActivity(), ClassifyActivity.class);
//                intent.putExtra("categoryId",homeEntrances.get(position).getChildCategoryId());
//                intent.putExtra("title", homeEntrances.get(position).getChildCategoryName());
//                intent.putExtra("bean",stairlist.get(position+1));
//                intent.putExtra("type","0");
//                startActivity(intent);
                ((MainActivity) getActivity()).viewPager.setCurrentItem(1);
                EventBus.getDefault().post(new EvenDyname(position));

            }
        });

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        guessrecycle.setLayoutManager(staggeredGridLayoutManager);
        guessAdapter=new GuessAdapter(getActivity(),item_list);
        guessrecycle.setAdapter(guessAdapter);
        guessAdapter .setOnItemClickListener(new GuessAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener( int position) {
                Intent intent = new Intent(getContext(), DeatilsActivity.class);
                intent.putExtra("productid",item_list.get(position).getProductid());
                startActivity(intent);
            }

        });

    }


    private void initData() {

        userLogin();
        showFirstPage(tv1.getText().toString(),"1");
//
//        et_seek.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                 /*判断是否是“GO”键*/
//                if (actionId == EditorInfo.IME_ACTION_GO) {
//                    /*隐藏软键盘*/
//                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    if (imm.isActive()) {
//                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
//                    }
//                    return true;
//                }
//
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    /*隐藏软键盘*/
//                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    if (imm.isActive()) {
//                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
//                    }
//                    if (!TextUtils.isEmpty(et_seek.getText().toString())) {
//                        String edStr = et_seek.getText().toString().trim();
//                        Intent intent = new Intent(getActivity(), SerchActivity.class);
//                        intent.putExtra("text",edStr);
//                        startActivity(intent);
////                        //隐藏软键盘
////                      hideInputMethod();
//                    } else {
//                        ToastFactory.getToast(mContext, "关键字不能为空").show();
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==888 && requestCode==666){
            String cityStr = data.getStringExtra("cityStr");
            SPTool.addSessionMap(SQSP.Shi, cityStr);
            tv1.setText(cityStr);
            showFirstPage(tv1.getText().toString(),"1");
        }
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
            case R.id.tv_mercenary://新款推荐
                Intent intent4 = new Intent(getContext(),OneActivity.class);
                intent4.putExtra("image",image3);
                intent4.putExtra("title","新款推荐");
                intent4.putExtra("type","0");
                startActivity(intent4);
                break;
            case R.id.tv_warehouses://套餐系列
                Intent intent1 = new Intent(getActivity(), OneActivity.class);
                intent1.putExtra("image",image5);
                intent1.putExtra("title","套餐系列");
                intent1.putExtra("type","2");
                startActivity(intent1);
                break;
            case R.id.tv_special://特价专区
                Intent intent2 = new Intent(getActivity(), OneActivity.class);
                intent2.putExtra("image",image4);
                intent2.putExtra("title","特价专区");
                intent2.putExtra("type","1");
                startActivity(intent2);
                break;
            case R.id.tv1://地理位置
                Intent intent=new Intent(getActivity(),CitySelectActivity.class);
                intent.putExtra("flag",0);
                intent.putExtra("city",SPTool.getSessionValue(SQSP.Shi));
                intent.putExtra("lat",SPTool.getSessionValue(SQSP.JingDu));
                intent.putExtra("lng",SPTool.getSessionValue(SQSP.WeiDu));
                intent.putExtra("site",tv1.getText().toString());
                startActivityForResult(intent,666);
                break;
            case R.id.ll_ziying://自营商品库
                ((MainActivity) getActivity()).viewPager.setCurrentItem(1);
                break;
            case R.id.ll_jingxiao://经销商品库
                Intent intent3 = new Intent(getActivity(), ClassifyActivity.class);
                intent3.putExtra("type","1");
                startActivity(intent3);
                break;
            case R.id.ll_search://搜索
                Intent intent5 = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent5);
                break;
            case R.id.im_message:
                Intent intent6 = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent6);
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
                image1 = resultBean.getImage1();
                image2 = resultBean.getImage2();
                image3 = resultBean.getImage3();
                image4 = resultBean.getImage4();
                image5 = resultBean.getImage5();

                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(resultBean.getImage1())
                        .into(im_ziying1);
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(resultBean.getImage2())
                        .into(im_jingxiao1);


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
                        showImage(new ImageView(getActivity()), position);
                    }
                }).start();

                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(resultBean.getImage3())
                        .into(tv_mercenary);
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(resultBean.getImage4())
                        .into(tv_special);
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(resultBean.getImage5())
                        .into(tv_warehouses);


                list.clear();
                resultBean.getJprouctList().get(0).setAreaimage(resultBean.getImage6());
                resultBean.getJprouctList().get(1).setAreaimage(resultBean.getImage7());
                list.addAll(resultBean.getJprouctList());
                adapter.notifyDataSetChanged();


//                List<FirsePagebean.CategoryListBean.ChildrenListBean> allSecondlist = new ArrayList<>();
//                FirsePagebean.CategoryListBean allCategoryListBean = new FirsePagebean.CategoryListBean();
//                for (int i = 0; i <SQSP.shouyelist.size() ; i++) {
//                    allSecondlist.addAll(SQSP.shouyelist.get(i).getChildrenList());
//                }
//                allCategoryListBean.setCategoryName("全部");
//                allCategoryListBean.setChildrenList(allSecondlist);
                stairlist.clear();
//                stairlist.add(allCategoryListBean);
                stairlist.addAll(SQSP.shouyelist);

                homeEntrances.clear();
                homeEntrances.addAll(resultBean.getCategoryList());
                viewPagerAdapter.notifyDataSetChanged();



            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }



    //首页底部商品展示
    private void showFirstPage(String city,String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "showFirstPage");
        params.put("city", city);
        params.put("nowPage", nowPage);
        params.put("pageCount","4");
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new BaseCallback<showFirstPagebean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, final showFirstPagebean resultBean) {
                item_list.clear();
                item_list.addAll(resultBean.getPList());
                guessAdapter.notifyDataSetChanged();
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
