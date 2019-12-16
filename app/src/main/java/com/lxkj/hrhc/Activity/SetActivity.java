package com.lxkj.hrhc.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.lxkj.hrhc.Base.BaseActivity;
import com.lxkj.hrhc.Bean.AboutUsbean;
import com.lxkj.hrhc.Bean.CheckUpdateBean;
import com.lxkj.hrhc.Bean.privacyBean;
import com.lxkj.hrhc.Http.BaseCallback;
import com.lxkj.hrhc.Http.OkHttpHelper;
import com.lxkj.hrhc.Http.SpotsCallBack;
import com.lxkj.hrhc.MainActivity;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.SQSP;
import com.lxkj.hrhc.Service.DownloadReceiver;
import com.lxkj.hrhc.Service.DownloadService;
import com.lxkj.hrhc.Uri.NetClass;
import com.lxkj.hrhc.Utils.APKVersionCodeUtils;
import com.lxkj.hrhc.Utils.ActivityManager;
import com.lxkj.hrhc.Utils.DataCleanManager;
import com.lxkj.hrhc.Utils.SPTool;
import com.lxkj.hrhc.Utils.ToastFactory;
import com.lxkj.hrhc.View.ActionDialog;
import com.vector.update_app.utils.AppUpdateUtils;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class SetActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "SetActivity";
    private RelativeLayout rel2,rel1,rel3,yonghu;
    private String phone;
    private TextView tv_login,tv_huancun,banbenhao;
    private LinearLayout ll_clear;
    private int numberServer;
    private int verCode;
    private String vername;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_set);
        setTopTitle("设置");
        rel2 = findViewById(R.id.rel2);
        rel1 = findViewById(R.id.rel1);
        tv_login = findViewById(R.id.tv_login);
        ll_clear = findViewById(R.id.ll_clear);
        tv_huancun = findViewById(R.id.tv_huancun);
        banbenhao = findViewById(R.id.banbenhao);
        yonghu = findViewById(R.id.yonghu);
        banbenhao.setText(APKVersionCodeUtils.getVerName(SetActivity.this)+"");
        rel3 = findViewById(R.id.rel3);
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(this);
            tv_huancun.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initEvent() {
        rel2.setOnClickListener(this);
        rel1.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        ll_clear.setOnClickListener(this);
        rel3.setOnClickListener(this);
        yonghu.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        phone = getIntent().getStringExtra("phone");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel2://意见反馈
                Intent intent = new Intent(SetActivity.this,IdeaActivity.class);
                startActivity(intent);
                break;
            case R.id.rel1://更改密码
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, "请先登录").show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent1 = new Intent(SetActivity.this,ChangepasswordActivity.class);
                intent1.putExtra("phone",phone);
                startActivity(intent1);
                break;
            case R.id.tv_login://退出登录
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, "请先登录").show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                StyledDialog.init(SetActivity.this);
                StyledDialog.buildIosAlert("", "\r是否退出登录?", new MyDialogListener() {
                    @Override
                    public void onFirst() {

                    }

                    @Override
                    public void onSecond() {
                        SignOut();
                    }
                }).setBtnColor(R.color.mainColor2, R.color.mainColor1, 0).setBtnText("取消", "确定").show();
                break;
            case R.id.ll_clear:
                clearAllCachecatch();
                break;
            case R.id.rel3:
                checkUpdate();
                break;
            case R.id.yonghu:
                Intent intent2 = new Intent(SetActivity.this,ProtocolActivity.class);
                startActivity(intent2);
                break;
        }
    }
    /*清理缓存*/
    private void clearAllCachecatch() {
        DataCleanManager.clearAllCache(this);
        try {
            Log.i(TAG, "initViews: 剩余缓存" + DataCleanManager.getTotalCacheSize(this));
            tv_huancun.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //退出登录
    private void SignOut() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "SignOut");
        params.put("uid",SPTool.getSessionValue(SQSP.uid));
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<AboutUsbean>(mContext) {
            @Override
            public void onSuccess(Response response, AboutUsbean resultBean) {
                SPTool.addSessionMap(SQSP.isLogin, false);
                SPTool.addSessionMap(SQSP.uid, "");

                Intent intent3 = new Intent(SetActivity.this, LoginActivity.class);
                startActivity(intent3);
                ActivityManager.finishActivity();
                showToast(resultBean.resultNote);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
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
                verCode = APKVersionCodeUtils.getVersionCode(SetActivity.this);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
            builder.setTitle("发现新版本了");


            //builder.setNegativeButton("取消", null);
            builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO 开始下载
                    startUpload(apkurl, APKVersionCodeUtils.getVersionCode(SetActivity.this));//下载最新的版本程序
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
        intent.setClass(SetActivity.this, DownloadService.class);
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
            AppUpdateUtils.installApp(SetActivity.this, file);
            //startInstall(file);
        }
    }


}
