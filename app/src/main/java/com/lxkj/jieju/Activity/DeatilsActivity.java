package com.lxkj.jieju.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxkj.jieju.Adapter.Recycle_oneAdapter;
import com.lxkj.jieju.Adapter.recommendProductAdapter;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Detailbean;
import com.lxkj.jieju.Bean.Param;
import com.lxkj.jieju.Bean.productcommentbean;
import com.lxkj.jieju.Bean.recommendProductbean;
import com.lxkj.jieju.Bean.weixinbean;
import com.lxkj.jieju.Http.BaseCallback;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.ResultBean;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.StringUtil;
import com.lxkj.jieju.Utils.StringUtil_li;
import com.lxkj.jieju.Utils.TellUtil;
import com.lxkj.jieju.Utils.ToastFactory;
import com.lxkj.jieju.View.ActionDialog;
import com.lxkj.jieju.View.ChoiceParameterDialog;
import com.lxkj.jieju.View.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

import static com.lxkj.jieju.App.context;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :商品详情
 */
public class DeatilsActivity extends BaseActivity implements View.OnClickListener {

    private Banner banner;
    List<String> BanString = new ArrayList<>();
    private RecyclerView recycle;
    StaggeredGridLayoutManager layoutManager;
    recommendProductAdapter adapter;
    private List<recommendProductbean.DataListBean> list=new ArrayList<>();
    private LinearLayout ViewCui4,bottomView2;
    private String productid,shoucang;
    private TextView tv_price,tv_oldprice,tv_integral,tv_productName,tv_info,tv_detail,tv_pinglun,tv_Shopping_Cart,tv_guige;
    private WebView webView;
    private View view_detail,view_pinglun;
    private ImageView im_shoucang;
    private ChoiceParameterDialog choiceParameterDialog;
    List<Param.SkuBean> skuBeans = new ArrayList<>();
    List<Param.SpecBean> specBeans = new ArrayList<>();
    private  Param param = null;
    private String image_icon;
    private String type;
    private LinearLayout bottomView1;
    private String phone ="13303349099";
    private NestedScrollView ns;
    private String sorttype = "1",addskuId,addcount,addname,addpractical,addspec;
    private LinearLayout tv_quanwang;
    private ActionDialog actionDialog;
    private LinearLayout ll_xuanze;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_deatils);
        setTopTitle("商品详情");
        banner = findViewById(R.id.banner);
        recycle = findViewById(R.id.recycle);
        ViewCui4 = findViewById(R.id.ViewCui4);
        tv_price = findViewById(R.id.tv_price);
        tv_oldprice = findViewById(R.id.tv_oldprice);
        tv_integral = findViewById(R.id.tv_integral);
        tv_productName = findViewById(R.id.tv_productName);
        tv_info = findViewById(R.id.tv_info);
        webView = findViewById(R.id.webView);
        tv_detail = findViewById(R.id.tv_detail);
        tv_pinglun = findViewById(R.id.tv_pinglun);
        view_detail = findViewById(R.id.view_detail);
        view_pinglun = findViewById(R.id.view_pinglun);
        bottomView2 = findViewById(R.id.bottomView2);
        im_shoucang = findViewById(R.id.im_shoucang);
        bottomView1 = findViewById(R.id.bottomView1);
        tv_quanwang = findViewById(R.id.tv_quanwang);
        tv_guige = findViewById(R.id.tv_guige);
        ns = findViewById(R.id.ns);
        ll_xuanze = findViewById(R.id.ll_xuanze);
        tv_Shopping_Cart = findViewById(R.id.tv_Shopping_Cart);


    }

    @Override
    protected void initEvent() {

        ViewCui4.setOnClickListener(this);
        tv_detail.setOnClickListener(this);
        tv_pinglun.setOnClickListener(this);
        bottomView2.setOnClickListener(this);
        tv_Shopping_Cart.setOnClickListener(this);
        bottomView1.setOnClickListener(this);
        ll_xuanze.setOnClickListener(this);


        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recycle.setLayoutManager(layoutManager);
        adapter = new recommendProductAdapter(mContext, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new recommendProductAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                Intent intent = new Intent(mContext, DeatilsActivity.class);
                intent.putExtra("productid",list.get(firstPosition).getProductid());
                startActivity(intent);
            }


        });
    }

    @Override
    protected void initData() {
        productid = getIntent().getStringExtra("productid");
        sorttype = getIntent().getStringExtra("sorttype");
        if (!StringUtil_li.isSpace(getIntent().getStringExtra("type"))){
            type = "1";
        }
        productDetail(productid);
        showFirstPage("1");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ViewCui4://提交订单
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, "请先登录").show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                    if(choiceParameterDialog==null){
                        choiceParameterDialog = new ChoiceParameterDialog(this,param);
                        choiceParameterDialog.setSelectedListener(new ChoiceParameterDialog.SelectedListener() {
                            @Override
                            public void onSlectedChanged(boolean allSelected, String param) {

                            }
                            @Override
                            public void onComfirm(int count, String skuId,String name,String practical,String spec) {
                                    tv_guige.setText(name+"    "+spec+"    "+count+"件");
                                    choiceParameterDialog.dismiss();
                                    addskuId = skuId;
                                    addcount = String.valueOf(count);
                                    addname = name;
                                    addpractical = practical;
                                    addspec = spec;

                                    Intent intent = new Intent(DeatilsActivity.this,OrderOkActivity.class);
                                    intent.putExtra("type","0");
                                    intent.putExtra("productid",productid);
                                    intent.putExtra("skuId",addskuId);
                                    intent.putExtra("count",addcount+"");
                                    intent.putExtra("name",addname);
                                    intent.putExtra("practical",addpractical);
                                    intent.putExtra("spec",addspec);
                                    intent.putExtra("image_icon",image_icon);
                                    startActivity(intent);
                            }
                            @Override
                            public void ongouwuche(int count, String skuid, String name, String practical, String spec) {
                                tv_guige.setText(name+"    "+spec+"    "+count+"件");
                                choiceParameterDialog.dismiss();
                                addskuId = skuid;
                                addcount = String.valueOf(count);
                                addname = name;
                                addpractical = practical;
                                addspec = spec;
                                addCart(productid,addskuId,addcount);
                            }
                        });
                    }
                    choiceParameterDialog.show();
                break;
            case R.id.tv_detail://商品详情
                tv_detail.setTextColor(getResources().getColor(R.color.red_them));
                tv_pinglun.setTextColor(getResources().getColor(R.color.black));
                view_detail.setBackgroundColor(getResources().getColor(R.color.red_them));
                view_pinglun.setBackgroundColor(getResources().getColor(R.color.white));
                webView.setVisibility(View.VISIBLE);
                recycle.setVisibility(View.GONE);
                break;
            case R.id.tv_pinglun://商品评论
                tv_detail.setTextColor(getResources().getColor(R.color.black));
                tv_pinglun.setTextColor(getResources().getColor(R.color.red_them));
                view_detail.setBackgroundColor(getResources().getColor(R.color.white));
                view_pinglun.setBackgroundColor(getResources().getColor(R.color.red_them));
                webView.setVisibility(View.GONE);
                recycle.setVisibility(View.VISIBLE);
//                productCommentList(productid,"1");
                break;
            case R.id.bottomView2://收藏
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, "请先登录").show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                collectProduct(productid);
                break;
            case R.id.tv_Shopping_Cart://加入购物车

                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, "请先登录").show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                    if(choiceParameterDialog==null){
                        choiceParameterDialog = new ChoiceParameterDialog(this,param);
                        choiceParameterDialog.setSelectedListener(new ChoiceParameterDialog.SelectedListener() {
                            @Override
                            public void onSlectedChanged(boolean allSelected, String param) {

                            }

                            @Override
                            public void onComfirm(int count, String skuId,String name,String practical,String spec) {
                                tv_guige.setText(name+"    "+spec+"    "+count+"件");
                                choiceParameterDialog.dismiss();
                                addskuId = skuId;
                                addcount = String.valueOf(count);
                                addname = name;
                                addpractical = practical;
                                addspec = spec;

                                Intent intent = new Intent(DeatilsActivity.this,OrderOkActivity.class);
                                intent.putExtra("type","0");
                                intent.putExtra("productid",productid);
                                intent.putExtra("skuId",addskuId);
                                intent.putExtra("count",addcount+"");
                                intent.putExtra("name",addname);
                                intent.putExtra("practical",addpractical);
                                intent.putExtra("spec",addspec);
                                intent.putExtra("image_icon",image_icon);
                                startActivity(intent);
                            }

                            @Override
                            public void ongouwuche(int count, String skuid, String name, String practical, String spec) {
                                tv_guige.setText(name+"    "+spec+"    "+count+"件");
                                choiceParameterDialog.dismiss();
                                addskuId = skuid;
                                addcount = String.valueOf(count);
                                addname = name;
                                addpractical = practical;
                                addspec = spec;
                                addCart(productid,addskuId,addcount);
                            }
                        });
                    }
                    choiceParameterDialog.show();

                break;
            case R.id.bottomView1://客服

                contactCustomer();
                break;
            case R.id.ll_xuanze://选择规格

                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, "请先登录").show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                    if(choiceParameterDialog==null){
                        choiceParameterDialog = new ChoiceParameterDialog(this,param);
                        choiceParameterDialog.setSelectedListener(new ChoiceParameterDialog.SelectedListener() {
                            @Override
                            public void onSlectedChanged(boolean allSelected, String param) {

                            }

                            @Override
                            public void onComfirm(int count, String skuId,String name,String practical,String spec) {
                                tv_guige.setText(name+"    "+spec+"    "+count+"件");
                                choiceParameterDialog.dismiss();
                                addskuId = skuId;
                                addcount = String.valueOf(count);
                                addname = name;
                                addpractical = practical;
                                addspec = spec;

                                Intent intent = new Intent(DeatilsActivity.this,OrderOkActivity.class);
                                intent.putExtra("type","0");
                                intent.putExtra("productid",productid);
                                intent.putExtra("skuId",addskuId);
                                intent.putExtra("count",addcount+"");
                                intent.putExtra("name",addname);
                                intent.putExtra("practical",addpractical);
                                intent.putExtra("spec",addspec);
                                intent.putExtra("image_icon",image_icon);
                                startActivity(intent);
                            }

                            @Override
                            public void ongouwuche(int count, String skuid, String name, String practical, String spec) {
                                tv_guige.setText(name+"    "+spec+"    "+count+"件");
                                choiceParameterDialog.dismiss();
                                addskuId = skuid;
                                addcount = String.valueOf(count);
                                addname = name;
                                addpractical = practical;
                                addspec = spec;
                                addCart(productid,addskuId,addcount);
                            }
                        });
                    }
                    choiceParameterDialog.show();
                break;


        }
    }
    @PermissionGrant(SQSP.PMS_LOCATION)
    public void pmsLocationSuccess() {
        //权限授权成功
        TellUtil.tell(mContext, phone);
    }
    /*拨打电话*/
    private void callPhone() {
        if (null != phone) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                MPermissions.requestPermissions(this, SQSP.PMS_LOCATION,
                        Manifest.permission.CALL_PHONE
                );
            } else {
                pmsLocationSuccess();
            }
        }
    }
    private void showImage(final ImageView iv, int position) {
        List<Object> urls = new ArrayList<>();
        urls.addAll(BanString);
        new XPopup.Builder(DeatilsActivity.this).asImageViewer(iv, position, urls, new OnSrcViewUpdateListener() {
            @Override
            public void onSrcViewUpdate(ImageViewerPopupView popupView, int position) {
                popupView.updateSrcView(iv);
            }
        }, new DeatilsActivity.ImageLoader())
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

    //商品详情
    private void productDetail(final String productid) {
        final Map<String, String> params = new HashMap<>();
        params.put("cmd", "productDetail");
        params.put("productid",productid);
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Detailbean>(mContext) {
            @Override
            public void onSuccess(Response response, Detailbean resultBean) {
                BanString.clear();
                for (int i = 0; i <resultBean.getProductDetail().getProductImages().size() ; i++) {
                    BanString.add(resultBean.getProductDetail().getProductImages().get(i));
                }
                banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
                banner.setImageLoader(new GlideImageLoader());
                banner.setBannerAnimation(Transformer.DepthPage);
                banner.setIndicatorGravity(BannerConfig.CENTER);
                banner.setDelayTime(4000);
                banner.isAutoPlay(true);
                banner.setIndicatorGravity(BannerConfig.CENTER);
                banner.setImages(BanString).setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        showImage(new ImageView(mContext), position);
                    }
                }).start();
                shoucang = resultBean.getProductDetail().getIsCollect();
                if (shoucang.equals("0")){
                    im_shoucang.setImageResource(R.mipmap.shoucang);
                }else {
                    im_shoucang.setImageResource(R.mipmap.yishoucang);
                }
                tv_price.setText(resultBean.getProductDetail().getPrice());
                tv_integral.setText("已售："+resultBean.getProductDetail().getSales());
                tv_productName.setText(resultBean.getProductDetail().getProductName());
                webSetting(resultBean.getProductDetail().getUrl());

                param = new Param();
                specBeans= new ArrayList<>();
                skuBeans = new ArrayList<>();

                Param.SpecBean specBean1 = new Param.SpecBean();
                Param.SpecBean specBean2 = new Param.SpecBean();

                List<String> speclist1= new ArrayList<>();
                List<String> speclist2 = new ArrayList<>();
                specBean1.setSpecName(resultBean.getProductDetail().getSname1());
                if (!StringUtil.isEmpty(resultBean.getProductDetail().getSname2())){
                    specBean2.setSpecName(resultBean.getProductDetail().getSname2());
                }
                for (int i = 0; i <resultBean.getProductDetail().getSkuList().size() ; i++) {
                    if (!speclist1.contains(resultBean.getProductDetail().getSkuList().get(i).getSkuName1()))
                        speclist1.add(resultBean.getProductDetail().getSkuList().get(i).getSkuName1());
                    if (!StringUtil.isEmpty(resultBean.getProductDetail().getSkuList().get(i).getSkuName2())){
                        if (!speclist2.contains(resultBean.getProductDetail().getSkuList().get(i).getSkuName2()))
                            speclist2.add(resultBean.getProductDetail().getSkuList().get(i).getSkuName2());
                    }
                    List<String> spec = new ArrayList<>();
                    spec.add(resultBean.getProductDetail().getSkuList().get(i).getSkuName1());
                    if (!StringUtil.isEmpty(resultBean.getProductDetail().getSkuList().get(i).getSkuName2()))
                        spec.add(resultBean.getProductDetail().getSkuList().get(i).getSkuName2());
                    Param.SkuBean skuBean = new Param.SkuBean(resultBean.getProductDetail().getSkuList().get(i).getSkuStock(),resultBean.getProductDetail().getSkuList().get(i).getSkuPrice(),
                            resultBean.getProductDetail().getSkuList().get(i).getSkuId(),spec);

                    skuBeans.add(skuBean);
                }
                specBean1.setSpecValue(speclist1);
                specBean2.setSpecValue(speclist2);
                specBeans.add(specBean1);
                if (!StringUtil.isEmpty(specBean2.getSpecName()))
                    specBeans.add(specBean2);
                param.setSpec(specBeans);
                param.setSku(skuBeans);
                param.setIcon(resultBean.getProductDetail().getProductImages().get(0));
                param.setName(resultBean.getProductDetail().getProductName());
                image_icon = resultBean.getProductDetail().getProductImages().get(0);
                param.setPrice(resultBean.getProductDetail().getPrice());

                if (type.equals("1")){
                    if(choiceParameterDialog==null){
                        choiceParameterDialog = new ChoiceParameterDialog(mContext,param);
                        choiceParameterDialog.setSelectedListener(new ChoiceParameterDialog.SelectedListener() {
                            @Override
                            public void onSlectedChanged(boolean allSelected, String param) {

                            }
                            @Override
                            public void onComfirm(int count, String skuId,String name,String practical,String spec) {
                                tv_guige.setText(name+"    "+spec+"    "+count+"件");
                                choiceParameterDialog.dismiss();
                                addskuId = skuId;
                                addcount = String.valueOf(count);
                                addname = name;
                                addpractical = practical;
                                addspec = spec;

                                Intent intent = new Intent(DeatilsActivity.this,OrderOkActivity.class);
                                intent.putExtra("type","0");
                                intent.putExtra("productid",productid);
                                intent.putExtra("skuId",addskuId);
                                intent.putExtra("count",addcount+"");
                                intent.putExtra("name",addname);
                                intent.putExtra("practical",addpractical);
                                intent.putExtra("spec",addspec);
                                intent.putExtra("image_icon",image_icon);
                                startActivity(intent);
                            }
                            @Override
                            public void ongouwuche(int count, String skuid, String name, String practical, String spec) {
                                tv_guige.setText(name+"    "+spec+"    "+count+"件");
                                choiceParameterDialog.dismiss();
                                addskuId = skuid;
                                addcount = String.valueOf(count);
                                addname = name;
                                addpractical = practical;
                                addspec = spec;
                                addCart(productid,addskuId,addcount);
                            }
                        });
                    }
                    choiceParameterDialog.show();
                }

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
        params.put("productid",productid);
        params.put("nowPage",nowPage);
        params.put("pageCount","4");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new BaseCallback<recommendProductbean>() {
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

    //收藏/取消收藏/删除商品收藏
    private void collectProduct(String productid) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "collectProduct");
        params.put("productid",productid);
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                if (shoucang.equals("0")){
                    shoucang = "1";
                    im_shoucang.setImageResource(R.mipmap.yishoucang);
                }else {
                    shoucang = "0";
                    im_shoucang.setImageResource(R.mipmap.shoucang);

                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //联系客服微信
    private void contactCustomer() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "contactCustomer");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<weixinbean>(mContext) {
            @Override
            public void onSuccess(Response response, final weixinbean resultBean) {
//                actionDialog = new ActionDialog(mContext,resultBean.getWX());
//                actionDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
//                    @Override
//                    public void onLeftClick() {//一键复制
//                        ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
//                        String text;
//                        text = resultBean.getWX();
//
//                        ClipData myClip = ClipData.newPlainText("text", text);
//                        myClipboard.setPrimaryClip(myClip);
//                        Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
//                        actionDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onRightClick() {//确定
//                        actionDialog.dismiss();
//                    }
//                });
//                actionDialog.show();
                phone = resultBean.getPhone();
                callPhone();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //添加购物车
    private void addCart(String productid,String skuId,String count) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "addCart");
        params.put("productId",productid);
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("skuId",skuId);
        params.put("count",count);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void webSetting(String url){
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setUseWideViewPort(true);//关键点
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(false);// 支持缩放
//        webSettings.setTextSize(WebSettings.TextSize.LARGEST);//显示字体大小

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else{
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }


/**
 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
 */
//        webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

}
