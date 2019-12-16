package com.lxkj.hrhc.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.hrhc.Adapter.RefundAdapter;
import com.lxkj.hrhc.Adapter.RounditemAdapter;
import com.lxkj.hrhc.Base.BaseActivity;
import com.lxkj.hrhc.Bean.OrderCommentBean;
import com.lxkj.hrhc.Bean.Orderdetailbean;
import com.lxkj.hrhc.Bean.ResonBean;
import com.lxkj.hrhc.Http.BaseCallback;
import com.lxkj.hrhc.Http.OkHttpHelper;
import com.lxkj.hrhc.Http.ResultBean;
import com.lxkj.hrhc.Http.SpotsCallBack;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.SQSP;
import com.lxkj.hrhc.Uri.NetClass;
import com.lxkj.hrhc.Utils.GridImgAdapter;
import com.lxkj.hrhc.Utils.ImageItem;
import com.lxkj.hrhc.Utils.NetUtil;
import com.lxkj.hrhc.Utils.SPTool;
import com.lxkj.hrhc.Utils.StringUtil_li;
import com.lxkj.hrhc.View.FeedBackGridView;
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack;
import com.lxkj.qiqihunshe.app.retrofitnet.UpFileUtil;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.Request;
import okhttp3.Response;
import top.zibin.luban.Luban;

public class RefundActivity extends BaseActivity implements View.OnClickListener,UpLoadFileCallBack {
    private RecyclerView commodity_recycle,cause_recycle;
    LinearLayoutManager layoutManager;
    RefundAdapter refundAdapter;
    RounditemAdapter rounditemAdapter;
    List<Orderdetailbean.OrderDetailBean.OrderItemBean> commoditylist=new ArrayList<>();
    List<ResonBean.DataListBean> causelist=new ArrayList<>();
    private String orderid,reason,moeny;
    private TextView tv_money,tv_submit;
    List<String> itemId = new ArrayList<>();
    List<String> moneylist = new ArrayList<>();
    List<String> couuntlist = new ArrayList<>();
    private static final String TAG = "RefundActivity";
    private EditText et_describe;
    List<String> listUrl1 = new ArrayList<String>();
    private UpFileUtil upFileUtil;
    private List<String> bannerImages = new ArrayList<>();
    private static final int REQUEST_IMAGE = 2;
    private int currentImage = -2;
    GridImgAdapter bannerImageAdapter;
    private FeedBackGridView gvBannerImage;
    private ArrayList<ImageItem> bannerSelectPath = new ArrayList<>();
    private int type = 0;//选择图片类型 0 banner,1 goods
    private ArrayList<String> mBannerSelectPath = new ArrayList<>();
    private String endimage = "";
    ArrayList<String> YaSouList = new ArrayList<>();

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_refund);
        setTopTitle("退款/售后");
        commodity_recycle = findViewById(R.id.commodity_recycle);
        cause_recycle = findViewById(R.id.cause_recycle);
        tv_money = findViewById(R.id.tv_money);
        tv_submit = findViewById(R.id.tv_submit);
        et_describe = findViewById(R.id.et_describe);
        gvBannerImage = findViewById(R.id.photo1);
        upFileUtil = new UpFileUtil(this, (UpLoadFileCallBack) this);
        initPhotoError();
    }

    @Override
    protected void initEvent() {
        tv_submit.setOnClickListener(this);

        layoutManager = new LinearLayoutManager(RefundActivity.this);
        commodity_recycle.setLayoutManager(layoutManager);
        refundAdapter = new RefundAdapter(RefundActivity.this, commoditylist);
        commodity_recycle.setAdapter(refundAdapter);
        refundAdapter.setOnItemClickListener(new RefundAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                Intent intent = new Intent(RefundActivity.this,DeatilsActivity.class);
                intent.putExtra("productid",commoditylist.get(firstPosition).getProductId());
                startActivity(intent);
            }

            @Override
            public void OnCheckef(int position,boolean ischeck) {
                if (ischeck == true){
                    itemId.add(commoditylist.get(position).getItemId());
                    moneylist.add(commoditylist.get(position).getProductPrice());
                    couuntlist.add(commoditylist.get(position).getProductCount());
                }else {
                    itemId.remove(commoditylist.get(position).getItemId());
                    moneylist.remove(commoditylist.get(position).getProductPrice());
                    couuntlist.remove(commoditylist.get(position).getProductCount());
                }
                float in = 0;
                for (int i = 0; i <moneylist.size() ; i++) {
                    BigDecimal jine = new BigDecimal(moneylist.get(i));
                    BigDecimal shuliang = new BigDecimal(couuntlist.get(i));
                    BigDecimal zong = jine.multiply(shuliang);
                    in += zong.floatValue();
                }

                tv_money.setText("¥"+in);
                moeny = String.valueOf(in);
                Log.i(TAG, "OnCheckef: "+itemId);
                refundAdapter.notifyDataSetChanged();
            }

        });

        layoutManager = new LinearLayoutManager(RefundActivity.this);
        cause_recycle.setLayoutManager(layoutManager);
        rounditemAdapter = new RounditemAdapter(RefundActivity.this, causelist);
        cause_recycle.setAdapter(rounditemAdapter);
        rounditemAdapter.setOnItemClickListener(new RounditemAdapter.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void OnItemClickListener(int firstPosition) {
                for(ResonBean.DataListBean person : causelist){ //遍历list集合中的数据
                    person.setChecked(false);//全部设为未选中
                }
                if(currentNum == -1){ //选中
                    causelist.get(firstPosition).setChecked(true);
                    currentNum = firstPosition;
                }else if(currentNum == firstPosition){ //同一个item选中变未选中
                    for(ResonBean.DataListBean person : causelist){
                        person.setChecked(false);
                    }
                    currentNum = -1;
                }else if(currentNum != firstPosition){ //不是同一个item选中当前的，去除上一个选中的
                    for(ResonBean.DataListBean person : causelist){
                        person.setChecked(false);
                    }
                    causelist.get(firstPosition).setChecked(true);
                    currentNum = firstPosition;
                }
                reason = causelist.get(firstPosition).getTitle();
                rounditemAdapter.notifyDataSetChanged();//刷新adapter
            }
        });

        bannerImageAdapter = new GridImgAdapter(RefundActivity.this, bannerSelectPath, -1);
        gvBannerImage.setAdapter(bannerImageAdapter);
        bannerImageAdapter.setMaxSize(6);
        bannerImageAdapter.setAddImageListencer(new GridImgAdapter.AddImageListencer() {
            @Override
            public void addImageClicked(GridImgAdapter adapter) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    type = 0;
                    checkPmsExternalStorage();
                } else {
                    pmsExternalStorageSuccess();
                }
            }
        });
        bannerImageAdapter.setDelImageListencer(new GridImgAdapter.DelImageListencer() {
            @Override
            public void delImageAtPostion(int postion, GridImgAdapter adapter) {
                currentImage = adapter.getNumber();
                if (currentImage == -1) {
                    mBannerSelectPath.remove(postion);
                    bannerSelectPath.remove(postion);
                    bannerImageAdapter.notifyDataSetChanged();
//                    listUrl1.remove(postion);
                }
            }
        });

    }

    @Override
    protected void initData() {
        orderid = getIntent().getStringExtra("orderid");
        orderDetail(orderid);
        reasonList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (type == 0) {
                    mBannerSelectPath.addAll(data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT));
                    Log.i(TAG, "onSuccess:------- "+mBannerSelectPath);
                    bannerSelectPath.clear();
                    for (String p : mBannerSelectPath) {
                        ImageItem imageItem = new ImageItem();
                        imageItem.setThumbnailPath(p);
                        bannerSelectPath.add(imageItem);
                    }
                    bannerImageAdapter.notifyDataSetChanged();
                    bannerImages.clear();
                   /* for (int i = 0; i < bannerSelectPath.size(); i++) {
                        //执行上传操作
                        //uploadImage(bannerSelectPath.get(i).getThumbnailPath());
                    }*/

                    for (int i = 0; i < bannerSelectPath.size(); i++) {
                        //执行上传操作
                        //uploadImage(bannerSelectPath.get(i).getThumbnailPath());
                        showLoading();
                        upFileUtil.upLoad(bannerSelectPath.get(i).getThumbnailPath());
                    }
                    try {
                        if (mBannerSelectPath.size() > 0)
                            listUrl1.clear();

                        List<File> files = Luban.with(mContext).load(mBannerSelectPath).get();
                        for (int i = 0; i < files.size(); i++) {
                            YaSouList.add(files.get(i).toString());

                        }

                        if (NetUtil.isNetWork(RefundActivity.this)) {
                            upFileUtil.setListPath(YaSouList);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_submit://提交
                if (itemId.size() == 0){
                    showToast("请选择商品");
                    return;
                }
                if (StringUtil_li.isSpace(reason)){
                    showToast("请选择退款原因");
                    return;
                }

                refundOrder(orderid,itemId,reason,moeny,et_describe.getText().toString(),endimage);
                break;
        }
    }

    //订单详情
    private void orderDetail(String orderid){
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "orderDetail");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("orderid",orderid);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Orderdetailbean>(mContext) {
            @Override
            public void onSuccess(Response response, Orderdetailbean resultBean) {

                commoditylist.clear();
                commoditylist.addAll(resultBean.getOrderDetail().getOrderItem());
                refundAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    //退款原因
    private void reasonList() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "reasonList");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new BaseCallback<ResonBean>(){
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, ResonBean resultBean) {
                causelist.clear();
                causelist.addAll(resultBean.getDataList());
                rounditemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e){

            }
        });
    }
    //申请退款
    private void refundOrder(String orderid,List<String> itemId,String reason,String amount,String remark,String images){
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "refundOrder");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("orderid",orderid);
        params.put("itemId",itemId);
        params.put("reason",reason);
        params.put("amount",amount);
        params.put("remark",remark);
        params.put("images",images);
        OkHttpHelper.getInstance().post_json_file(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                Intent intent1 = new Intent(RefundActivity.this,OrderActivity.class);
                intent1.putExtra("position","2");
                startActivity(intent1);
                showToast(resultBean.getResultNote());
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void checkPmsExternalStorage() {
        MPermissions.requestPermissions(this, SQSP.PMS_CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        );
    }
    @PermissionGrant(SQSP.PMS_CAMERA)
    public void pmsExternalStorageSuccess() {
        //权限授权成功
        if (type == 0) {
            MultiImageSelector.create(mContext)
                    .showCamera(true) // 是否显示相机. 默认为显示
                    .count(6 - mBannerSelectPath.size()) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                    .multi() // 多选模式, 默认模式;
//                    .origin(mBannerSelectPath) // 默认已选择图片. 只有在选择模式为多选时有效
                    .start(RefundActivity.this, REQUEST_IMAGE);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initPhotoError() {
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
    @Override
    public void uoLoad(@NotNull String url) {
        dismissLoading();
        Log.i(TAG, "uoLoad: 上传返回--------" + url);
    }

    @Override
    public void uoLoad(@NotNull List<String> url) {
        dismissLoading();
        Log.i(TAG, "uoLoad: 上传返回222" + url);

        for (int i = 0; i <url.size() ; i++) {
            endimage += ","+ url.get(i);
        }
        endimage = endimage.substring(1);
//        endlist.addAll(url);
//        JSONArray jsonArray = new JSONArray(endlist);
//        endimage = jsonArray.toString();
    }
}
