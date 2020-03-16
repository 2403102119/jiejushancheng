package com.lxkj.jieju;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.CheckUpdateBean;
import com.lxkj.jieju.Fragment.Home3Fragment;
import com.lxkj.jieju.Fragment.Home4Fragment;
import com.lxkj.jieju.Fragment.Home1Fragment;
import com.lxkj.jieju.Fragment.Home2Fragment;
import com.lxkj.jieju.Fragment.Home5Fragment;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.Http.SpotsCallBack;
import com.lxkj.jieju.Service.DownloadReceiver;
import com.lxkj.jieju.Service.DownloadService;
import com.lxkj.jieju.Uri.NetClass;
import com.lxkj.jieju.Utils.APKVersionCodeUtils;
import com.lxkj.jieju.Utils.SPTool;
import com.lxkj.jieju.Utils.Utils;
import com.lxkj.jieju.View.NoScrollViewPager;
import com.umeng.socialize.UMShareAPI;
import com.vector.update_app.utils.AppUpdateUtils;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;


public class MainActivity extends BaseActivity implements DownloadReceiver.Receiver {


    public NoScrollViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private MyPagerAdapter adapter;
    private RadioButton rB1;
    private RadioButton rB2;
    private RadioButton rB3;
    private RadioButton rB4;
    private RadioButton rB5;
    private static final String TAG = "MainActivity";
    private int numberServer;
    private int verCode;

    /**
     * 授权监听
     */
    private UMShareAPI mShareAPI;
    private String thirdUid;
    private String nickName;
    private String userIcon;
    private String login_type;
    // 要申请的权限
    private String[] permissions = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,//读SD卡
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,//写SD卡
            android.Manifest.permission.READ_PHONE_STATE,//获取手机状态
            android.Manifest.permission.CAMERA,//相机
            android.Manifest.permission.LOCATION_HARDWARE,//位置
            android.Manifest.permission.ACCESS_FINE_LOCATION,//位置
            android.Manifest.permission.ACCESS_COARSE_LOCATION,//位置
            android.Manifest.permission.CALL_PHONE,//打电话
//            Manifest.permission.READ_CALENDAR,//读日历
//            Manifest.permission.WRITE_CALENDAR,//写日历

    };
    //权限数组下标
    //权限申请返回码
    private int requestCodePre = 321;
    //系统设置权限申请返回码
    private int requestCodeSer = 123;
    private ImageView wx;
    private ImageView qq;
    private ImageView zfb;
    private Intent intent;


    public void initSystemBar2(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
//        initSystemBar2(this);
        baseTop.setVisibility(View.GONE);
        String JupshID = SPTool.getSessionValue(SQSP.JupshID);
        Log.i(TAG, "initView: 极光ID" + JupshID);

        setViews();
        setListeners();
        fragments = new ArrayList<>();
        fragments.add(new Home1Fragment());
        fragments.add(new Home2Fragment());
        fragments.add(new Home3Fragment());
        fragments.add(new Home5Fragment());
        fragments.add(new Home4Fragment());

        mReceiver = new DownloadReceiver(new Handler());
        mReceiver.setReceiver(this);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(fragments.size() - 1);
        checkPermissons();


    }
    /**
     * 检查运行时权限
     */
    private void checkPermissons() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean perimissionFlas = false;
            for (String permissionStr : permissions) {
                // 检查该权限是否已经获取
                int per = ContextCompat.checkSelfPermission(this, permissionStr);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (per != PackageManager.PERMISSION_GRANTED) {
                    perimissionFlas = true;
                }
            }
            if (perimissionFlas) {
                // 如果有权限没有授予允许，就去提示用户请求授权
                ActivityCompat.requestPermissions(this, permissions, requestCodePre);
            }
        }
    }


    /**
     * 用户权限申请的回调方法grantResults授权结果
     *
     * @param requestCode  是我们自己定义的权限请求码
     * @param permissions  是我们请求的权限名称数组
     * @param grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数
     *                     组的长度，数组的数据PERMISSION_GRANTED表示允许权限，PERMISSION_DENIED表示我们点击了禁止权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == requestCodePre) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 判断该权限是否已经授权
                boolean grantFlas = false;
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        //-----------存在未授权-----------
                        grantFlas = true;
                    }
                }
                if (grantFlas) {
                    //-----------未授权-----------
                    // 判断用户是否点击了不再提醒。(检测该权限是否还可以申请)
                    // shouldShowRequestPermissionRationale合理的解释应该是：如果应用之前请求过此权限
                    //但用户拒绝了请求且未勾选"Don’t ask again"(不在询问)选项，此方法将返回 true。
                    //注：如果用户在过去拒绝了权限请求，并在权限请求系统对话框中勾选了
                    //"Don’t ask again" 选项，此方法将返回 false。如果设备规范禁止应用具有该权限，此方法会返回 false。
                    boolean shouldShowRequestFlas = false;
                    for (String per : permissions) {
                        if (shouldShowRequestPermissionRationale(per)) {
                            //-----------存在未授权-----------
                            shouldShowRequestFlas = true;
                        }
                    }
                    if (shouldShowRequestFlas) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                        builder.setTitle("提示");
                        builder.setMessage("当前还有必要权限没有授权，是否前往授权？");
                        builder.setCancelable(false);
                        builder.setPositiveButton("前往", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                goToAppSetting();
                            }
                        });
                        builder.setNegativeButton("放弃", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                } else {
                    //-----------授权成功-----------
                    //Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }

        public int getCount() {
            return fragments.size();
        }
    }

    //通过监听viewpager滑动改变Checked的属性
    private void setListeners() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rB1.setChecked(true);
                        rB1.setTextColor(getResources().getColor(R.color.red_them));
                        rB2.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB3.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB4.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB5.setTextColor(getResources().getColor(R.color.txt_orange2));
                        break;
                    case 1:
                        rB2.setChecked(true);
                        rB2.setTextColor(getResources().getColor(R.color.red_them));
                        rB1.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB3.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB4.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB5.setTextColor(getResources().getColor(R.color.txt_orange2));
                        break;
                    case 2:
                        rB3.setChecked(true);
                        rB3.setTextColor(getResources().getColor(R.color.red_them));
                        rB1.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB2.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB4.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB5.setTextColor(getResources().getColor(R.color.txt_orange2));
                        break;
                    case 3:

                        rB5.setChecked(true);
                        rB5.setTextColor(getResources().getColor(R.color.red_them));
                        rB1.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB2.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB3.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB4.setTextColor(getResources().getColor(R.color.txt_orange2));
                        break;
                    case 4:
                        rB4.setChecked(true);
                        rB4.setTextColor(getResources().getColor(R.color.red_them));
                        rB1.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB2.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB3.setTextColor(getResources().getColor(R.color.txt_orange2));
                        rB5.setTextColor(getResources().getColor(R.color.txt_orange2));
                        break;
                    default:
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void setViews() {
        viewPager = findViewById(R.id.viewPager);
        rB1 = findViewById(R.id.RadioButton1);
        rB2 = findViewById(R.id.RadioButton2);
        rB3 = findViewById(R.id.RadioButton3);
        rB4 = findViewById(R.id.RadioButton4);
        rB5 = findViewById(R.id.RadioButton5);

//        checkUpdate();
    }
    //检查更新
    private void checkUpdate() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "checkUpdate");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<CheckUpdateBean>(mContext) {
            @Override
            public void onSuccess(Response response, CheckUpdateBean resultBean) {
                String content = resultBean.getDataObject().getUpdatecontent();
                String type = "0";
                String url = resultBean.getDataObject().getDownurl();
                //服务器版本号 2
                numberServer = Integer.parseInt(resultBean.getDataObject().getNum());
//                String version = resultBean.getVersion();

                //强制更新 0否 1是
                //String verName = APKVersionCodeUtils.getVerName(MainActivity.this);//1.1
                //本地版本号  1
                verCode = APKVersionCodeUtils.getVersionCode(MainActivity.this);
                Log.i(TAG, "onSuccess: "+verCode);
                //int newVerName = Integer.parseInt(verName.replace(".", ""));//1.1----11


                switch (type) {
                    case "0":
                        xuanZe(numberServer, verCode, url);
                        break;
                    case "1":
                        qingZhi(numberServer, verCode, url);
                        break;
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Log.i(TAG, "onError: "+123456);
            }

        });
    }



    /*选择更新*/
    private void xuanZe(int numberServer, int verCode, String apkurl) {
        Log.i(TAG, "xuanZe: " + numberServer + "----" + verCode);
        if (numberServer > verCode) {
            Log.i(TAG, "xuanZe: 执行1111111111");
            AllenVersionChecker
                    .getInstance()
                    .downloadOnly(
                            UIData.create().setDownloadUrl(apkurl).setTitle("发现新版本").setContent("是否立即更新")
                    ).setNotificationBuilder(
                    NotificationBuilder.create()
                            .setRingtone(true)
                            .setIcon(R.mipmap.ic_launcher_round)
                            .setTicker("版本更新")
                            .setContentTitle("版本更新")
                            .setContentText("正在下载....")
            ).setShowDownloadingDialog(false).executeMission(mContext);
        } else {
            Log.i(TAG, "xuanZe: 执行22222222");
        }

    }

    /*强制更新*/
    private void qingZhi(int numberServer, int verCode, final String apkurl) {

        if (numberServer > verCode) {
            Log.i(TAG, "qingZhi: 执行33333");
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("发现新版本了");


            //builder.setNegativeButton("取消", null);
            builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO 开始下载
                    startUpload(apkurl, APKVersionCodeUtils.getVersionCode(MainActivity.this));//下载最新的版本程序
                }
            });
            builder.setCancelable(false);
            builder.show();
        } else {
            Log.i(TAG, "qingZhi: 执行4444");
        }


    }


    /*应用下载*/
    private DownloadReceiver mReceiver;
    private ProgressDialog progressDialog;

    private void startUpload(String url, int versions) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, DownloadService.class);
        intent.putExtra("url", url);
        intent.putExtra("name", getString(R.string.app_name) + versions);
        intent.putExtra("receiver", mReceiver);
        startService(intent);
        showProgressDialog();


    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("正在下载");
        progressDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            Log.i(TAG, "onActivityResult: 收到请求----执行安装");
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "whsq.apk");
            AppUpdateUtils.installApp(MainActivity.this, file);
            //startInstall(file);
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == DownloadService.UPDATE_FAILED) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("发现新版本了").setMessage("网络不佳");
            builder.setCancelable(false);
            builder.create().show();

        } else if (resultCode == DownloadService.UPDATE_PROGRESS) {
            progressDialog.setProgress(resultData.getInt("process"));
            if (resultData.getInt("process") == 100) {
                progressDialog.dismiss();

                Log.i(TAG, "showProgressDialog: 下载完成");
                Log.i(TAG, "showProgressDialog: 开始安装");

                //startInstallPermissionSettingActivity2(this);
                //TODO  执行应用内安装
                File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                //File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"CBP.apk");


                Log.i(TAG, "onActivityResult: 收到请求----执行安装");
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "whsq.apk");
                AppUpdateUtils.installApp(MainActivity.this, file);
            }
        }
    }


    //监听RadioButton的点击
    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.RadioButton1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.RadioButton2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.RadioButton3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.RadioButton4:
                viewPager.setCurrentItem(4);
                break;
            case R.id.RadioButton5:
                viewPager.setCurrentItem(3);
                break;
            default:
        }
    }


    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    /**
     * 第一种解决办法 通过监听keyUp
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                finish();
            }
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }
}

