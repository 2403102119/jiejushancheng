package com.lxkj.jieju.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jieju.Adapter.NiceAdapter;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.ResultBean;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.NetUtil;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.ToastFactory;
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack;
import com.lxkj.qiqihunshe.app.retrofitnet.UpFileUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Response;
import top.zibin.luban.Luban;

import static com.lxkj.jieju.App.context;
import static com.lxkj.jieju.Utils.StringUtil_li.decodeFile;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :个人资料
 */
public class PersonalActivity extends BaseActivity implements View.OnClickListener,UpLoadFileCallBack {
    //系统设置权限申请返回码
    private int requestCodeSer = 123;
    private static final int REQUEST_IMAGE1 = 1;//修改头像
    private UpFileUtil upFileUtil;
    private ArrayList<String> mSelectPath;
    private static final String TAG = "PersonalActivity";
    private LinearLayout ll_name,ll_phone,ll_sex;
    private String name,icon_url,phone,sex;
    private RoundedImageView ri_icon;
    private TextView tv_name,tv_sex,tv_phone;
    private RelativeLayout ll_sell;
    private LinearLayout ll_sell_item;
    private PopupWindow popupWindow;
    private NiceAdapter niceAdapter;
    private List<String> pingtailist = new ArrayList<>();
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_personal);
        setTopTitle("个人资料");
        ll_name = findViewById(R.id.ll_name);
        ll_phone = findViewById(R.id.ll_phone);
        ri_icon = findViewById(R.id.ri_icon);
        tv_name = findViewById(R.id.tv_name);
        tv_sex = findViewById(R.id.tv_sex);
        tv_phone = findViewById(R.id.tv_phone);
        ll_sex = findViewById(R.id.ll_sex);

        initPhotoError();
        upFileUtil = new UpFileUtil(this, this);

    }

    @Override
    protected void initEvent() {
        ll_name.setOnClickListener(this);
        ll_phone.setOnClickListener(this);
        ri_icon.setOnClickListener(this);
        ll_sex.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        name = getIntent().getStringExtra("name");
        icon_url = getIntent().getStringExtra("icon_url");
        phone = getIntent().getStringExtra("phone");
        tv_name.setText(name);

        tv_phone.setText(phone);
        Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.ic_figure_head)
                .placeholder(R.mipmap.ic_figure_head))
                .load(icon_url)
                .into(ri_icon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_name://修改昵称
                Intent intent = new Intent(PersonalActivity.this,ChangenameActivity.class);
                startActivityForResult(intent,111);
                break;
            case R.id.ll_phone://修改手机号
                Intent intent1 = new Intent(PersonalActivity.this,ChangephoneActivity.class);
                intent1.putExtra("phone",phone);
                startActivityForResult(intent1,222);
                break;
            case R.id.ri_icon://修改头像
                checkOnlyPermissons4();
                break;
        }
    }

    //修改头像
    private void updateIcon(final String icon) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "updateIcon");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("icon",icon);
        OkHttpHelper.getInstance().post_json(PersonalActivity.this, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(PersonalActivity.this) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
                Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.ic_figure_head)
                        .placeholder(R.mipmap.ic_figure_head))
                        .load(icon)
                        .into(ri_icon);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (null!=data){
            if (requestCode == REQUEST_IMAGE1) {
                try {
                    mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (mSelectPath.size() > 0) {
                        //上传图片
                        String imgPath5 = mSelectPath.get(0);
                        Log.i(TAG, "onActivityResult: 图片地址5" + imgPath5);

                        Bitmap bitmap = decodeFile(new File(imgPath5));


                        List<File> files = Luban.with(context).load(mSelectPath).get();

                        ri_icon.setImageBitmap(bitmap);
                        if (NetUtil.isNetWork(PersonalActivity.this)) {
//                            showLoading(false);
                            //上传身份证正面
                            //upFileUtil.upLoad(imgPath5);
                            //upFileUtil.upLoad(YaSuoPath);
                            upFileUtil.upLoad(files.get(0).getAbsolutePath());
                        } else {
                            ToastFactory.getToast(PersonalActivity.this, "网络错误,请稍后重试").show();
                            return;
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode ==111&&resultCode == 222){
            tv_name.setText(data.getStringExtra("name"));
        }
        if (requestCode ==222&&resultCode == 333){
            tv_phone.setText(data.getStringExtra("phone"));
        }
    }
    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, requestCodeSer);
    }
    /*判断是否有相机权限4*/
    private void checkOnlyPermissons4() {
        if (ContextCompat.checkSelfPermission(PersonalActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
            builder.setTitle("提示");
            builder.setMessage("请先开启读写手机存储权限!").setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    goToAppSetting();
                }
            }).show();
        } else {
            pickIntent4();
        }
    }

    /*选择照片4*/
    private void pickIntent4() {
        Intent intent = new Intent(PersonalActivity.this, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        String type = "4";
        startActivityForResult(intent, REQUEST_IMAGE1);
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
        Log.i(TAG, "上传图片:"+url);
        updateIcon(url);
    }
    @Override
    public void uoLoad(@NotNull List<String> url) {
        dismissLoading();

    }
}
