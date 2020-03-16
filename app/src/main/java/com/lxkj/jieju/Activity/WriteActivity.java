package com.lxkj.jieju.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.Commonbean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.MainActivity;
import com.lxkj.jieju.R;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.MD5Util;
import com.lxkj.jieju.Utils.NetUtil;
import com.lxkj.jieju.Utils.StringUtil_li;
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

public class WriteActivity extends BaseActivity implements View.OnClickListener,UpLoadFileCallBack {
    //系统设置权限申请返回码
    private int requestCodeSer = 123;
    private static final int REQUEST_IMAGE1 = 1;//修改头像
    private UpFileUtil upFileUtil;
    private ArrayList<String> mSelectPath;
    private static final String TAG = "PersonalActivity";
    private TextView tv_enter;
    private String phone,iconurl;
    private RoundedImageView im_touxiang;
    private EditText edit1,edit2,edit3;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_write);
        setTopTitle("");
        tv_enter = findViewById(R.id.tv_enter);
        im_touxiang = findViewById(R.id.im_touxiang);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);


        initPhotoError();
        upFileUtil = new UpFileUtil(this, this);

    }

    @Override
    protected void initEvent() {
        tv_enter.setOnClickListener(this);
        im_touxiang.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        phone = getIntent().getStringExtra("phone");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_enter:
               if (StringUtil_li.isSpace(edit1.getText().toString())){
                   showToast("请输入您的昵称");
                   return;
               }
               if (StringUtil_li.isSpace(edit2.getText().toString())){
                   showToast("请输入密码");
                   return;
               }
               if (StringUtil_li.isSpace(edit3.getText().toString())){
                   showToast("请再起输入密码");
                   return;
               }
               if (!edit3.getText().toString().equals(edit2.getText().toString())){
                   showToast("两次输入密码不一致");
                   return;
               }
                userRegist(phone, MD5Util.encrypt(edit3.getText().toString()),edit1.getText().toString(),iconurl);
                break;
            case R.id.im_touxiang://修改头像
                checkOnlyPermissons4();
                break;
        }
    }


    //注册
    private void userRegist(final String phone, String password, String name, String icon) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","userRegist");
        params.put("phone", phone);
        params.put("password", password);
        params.put("name", name);
        params.put("icon", icon);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Commonbean>(mContext) {
            @Override
            public void onSuccess(Response response, Commonbean resultBean) {

                Intent intent = new Intent(WriteActivity.this,LoginActivity.class);
                startActivity(intent);
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
                        //给图片压缩并转换成String路径
//                String YaSuoPath = BitmapUtil2.saveBitmap(PersonalActivity.this, BitmapUtil2.compressImage(bitmap));

                        List<File> files = Luban.with(context).load(mSelectPath).get();

                        im_touxiang.setImageBitmap(bitmap);
                        if (NetUtil.isNetWork(WriteActivity.this)) {
                            showLoading(false);
                            //上传身份证正面
                            //upFileUtil.upLoad(imgPath5);
                            //upFileUtil.upLoad(YaSuoPath);
                            upFileUtil.upLoad(files.get(0).getAbsolutePath());
                        } else {
                            ToastFactory.getToast(WriteActivity.this, "网络错误,请稍后重试").show();
                            return;
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        if (ContextCompat.checkSelfPermission(WriteActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
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
        Intent intent = new Intent(WriteActivity.this, MultiImageSelectorActivity.class);
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
        iconurl = url;
    }
    @Override
    public void uoLoad(@NotNull List<String> url) {
        dismissLoading();

    }
}
