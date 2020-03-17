package com.lxkj.jieju.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lxkj.jieju.App;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.BindingBean;
import com.lxkj.jieju.Bean.Commonbean;
import com.lxkj.jieju.Bean.Jsontwobean;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.MainActivity;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.GetJsonDataUtil;
import com.lxkj.jieju.Utils.MD5Util;
import com.lxkj.jieju.Utils.NetUtil;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.StringUtil_li;
import com.lxkj.jieju.Utils.ToastFactory;
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack;
import com.lxkj.qiqihunshe.app.retrofitnet.UpFileUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

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
    private String phone,iconurl,loginType,openId,usericon,nickname,type;//0  注册页面   1  三方登陆
    private RoundedImageView im_touxiang;
    private EditText edit1,edit2,edit3,et_detail;
    private TextView tv_site;

    /*关于城市选择器*/
    private static boolean isLoaded = false;
    private List<Jsontwobean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private String tx;
    private String province;
    private String city;
    private String twon;
    private LinearLayout ll_setsite;
    private ImageView im_moren;
    private String isdefault = "0";

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        //Toast.makeText(RegisterCui1Activity.this, "开始准备数据", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
//                                initJsonData();
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    //Toast.makeText(SelectAddActivity.this, "解析成功", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    //Toast.makeText(SelectAddActivity.this, "解析失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_write);
        setTopTitle("");
        tv_enter = findViewById(R.id.tv_enter);
        im_touxiang = findViewById(R.id.im_touxiang);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
        tv_site = findViewById(R.id.tv_site);
        et_detail = findViewById(R.id.et_detail);


        initPhotoError();
        upFileUtil = new UpFileUtil(this, this);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);

    }

    @Override
    protected void initEvent() {
        tv_enter.setOnClickListener(this);
        im_touxiang.setOnClickListener(this);
        tv_site.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        phone = getIntent().getStringExtra("phone");
        type = getIntent().getStringExtra("type");
        if (type.equals("0")){

        }else {
            loginType = getIntent().getStringExtra("loginType");
            openId = getIntent().getStringExtra("openId");
            usericon = getIntent().getStringExtra("usericon");
            nickname = getIntent().getStringExtra("nickname");
            iconurl = usericon;
            Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                    .centerCrop()
                    .error(R.mipmap.ic_figure_head)
                    .placeholder(R.mipmap.ic_figure_head))
                    .load(usericon)
                    .into(im_touxiang);
            edit1.setText(nickname);
        }
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
                if (StringUtil_li.isSpace(tv_site.getText().toString())){
                    showToast("请选择地址");
                    return;
                }
                if (StringUtil_li.isSpace(et_detail.getText().toString())){
                    showToast("请填写详细地址");
                    return;
                }
               if (type.equals("0")){
                   userRegist(phone, MD5Util.encrypt(edit3.getText().toString()),edit1.getText().toString(),iconurl,tx+et_detail.getText().toString());
               }else {
                   bindPhone(loginType,openId,phone,edit1.getText().toString(),iconurl,tx+et_detail.getText().toString(),MD5Util.encrypt(edit3.getText().toString()));
               }
                break;
            case R.id.im_touxiang://修改头像
                checkOnlyPermissons4();
                break;
            case R.id.tv_site://选择地址
                hintKeyBoard();
                //选择城市
                if (isLoaded) {
                    showPickerView();
                }

                break;
        }
    }

    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    //注册
    private void userRegist(final String phone, String password, String name, String icon,String address) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","userRegist");
        params.put("phone", phone);
        params.put("password", password);
        params.put("name", name);
        params.put("icon", icon);
        params.put("address", address);
        params.put("token", SPTool.getSessionValue(SQSP.JupshID));
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Commonbean>(mContext) {
            @Override
            public void onSuccess(Response response, Commonbean resultBean) {

                showToast(resultBean.getResultNote());
                String uid = resultBean.getUid();
                SPTool.addSessionMap(SQSP.uid, uid);
                SPTool.addSessionMap(SQSP.isLogin, true);
                App.login = true;
                Intent intent = new Intent(WriteActivity.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }
    //绑定手机号
    private void bindPhone(String loginType,String openId,String phone ,String name,String icon,String address,String password) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","bindPhone");
        params.put("loginType", loginType);
        params.put("openId", openId);
        params.put("phone", phone);
        params.put("password", password);
        params.put("name", name);
        params.put("icon", icon);
        params.put("address", address);
        params.put("token", SPTool.getSessionValue(SQSP.JupshID));
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<BindingBean>(mContext) {
            @Override
            public void onSuccess(Response response, BindingBean resultBean) {
                showToast(resultBean.getResultNote());
                String uid = resultBean.getUid();
                SPTool.addSessionMap(SQSP.uid, uid);
                SPTool.addSessionMap(SQSP.isLogin, true);
                App.login = true;
                Intent intent = new Intent(WriteActivity.this,MainActivity.class);
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


    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "city.min.json");//获取assets目录下的json文件数据
        ArrayList<Jsontwobean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getC().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getC().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                if (null != jsonBean.get(i).getC().get(c).getD()){
                    for (int j = 0; j < jsonBean.get(i).getC().get(c).getD().size(); j++) {
                        city_AreaList.add(jsonBean.get(i).getC().get(c).getD().get(j).getName());
                    }
                }
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }
    /*三级联动---选择省市县*/
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";
                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";
                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";
                tx = opt1tx + opt2tx + opt3tx;
                //Toast.makeText(RegisterCui1Activity.this, tx, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onOptionsSelect: 选择的是" + tx + "---" + opt1tx + "---" + opt2tx + "---" + opt3tx);
                province = opt1tx;
                city = opt2tx;
                twon = opt3tx;
                tv_site.setText(tx);
            }
        })
                .setTitleText("请选择地区")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    public ArrayList<Jsontwobean> parseData(String result) {//Gson 解析
        ArrayList<Jsontwobean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                Jsontwobean entity = gson.fromJson(data.optJSONObject(i).toString(), Jsontwobean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
}
