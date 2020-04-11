package com.lxkj.jieju.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jieju.Adapter.SecondAdapter;
import com.lxkj.jieju.Adapter.Second_itemAdapter;
import com.lxkj.jieju.Adapter.StairAdapter;
import com.lxkj.jieju.Adapter.Stair_itemAdapter;
import com.lxkj.jieju.App;
import com.lxkj.jieju.Base.BaseActivity;
import com.lxkj.jieju.Bean.FirsePagebean;
import com.lxkj.jieju.Bean.Proprietarybean;
import com.lxkj.jieju.Bean.showFirstPagebean;
import com.lxkj.jieju.Http.BaseCallback;
import com.lxkj.jieju.Http.OkHttpHelper;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;
import com.lxkj.jieju.Uri.NetClass;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :
 */
public class ProprietaryActivity extends BaseActivity {

    private RecyclerView recyclerViewLeft;
    private RecyclerView recyclerViewRight;
    LinearLayoutManager layoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private Stair_itemAdapter stairAdapter;
    private Second_itemAdapter secondAdapter;
    private List<Proprietarybean.DataListBean> stairlist = new ArrayList<>();
    private List<Proprietarybean.DataListBean.ChildrenListBean> secondlist = new ArrayList<>();
    private String toptit,categoryId;
    int selectePosition = 0;
    private RoundedImageView im_dingbu;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_proprietary);
        setTopTitle("分类");
        recyclerViewLeft = findViewById(R.id.RecyclerViewLeft);
        recyclerViewRight = findViewById(R.id.RecyclerViewRight);
        im_dingbu = findViewById(R.id.im_dingbu);
    }

    @Override
    protected void initEvent() {
        layoutManager = new LinearLayoutManager(mContext);
        recyclerViewLeft.setLayoutManager(layoutManager);
        stairAdapter = new Stair_itemAdapter(mContext, stairlist);
        recyclerViewLeft.setAdapter(stairAdapter);
        stairAdapter.setOnItemClickListener(new Stair_itemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                selectePosition = position;
                secondlist.clear();
                secondlist.addAll(stairlist.get(position).getChildrenList());
                secondAdapter.notifyDataSetChanged();
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .centerCrop()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(stairlist.get(position).getCategoryImage())
                        .into(im_dingbu);
            }

        });

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerViewRight.setLayoutManager(staggeredGridLayoutManager);
        secondAdapter=new Second_itemAdapter(mContext,secondlist);
        recyclerViewRight.setAdapter(secondAdapter);
        secondAdapter .setOnItemClickListener(new Second_itemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener( int position) {
                Intent intent = new Intent(mContext, ClassifyActivity.class);
                intent.putExtra("childCategoryId",secondlist.get(position).getChildCategoryId());
                startActivity(intent);
            }

        });
    }

    @Override
    protected void initData() {
        productCategory();
    }
    //商品分类
    private void productCategory() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "productCategory");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new BaseCallback<Proprietarybean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, final Proprietarybean resultBean) {
                stairlist.clear();
                secondlist.clear();

                stairlist.addAll(resultBean.getDataList());
                secondlist.addAll(stairlist.get(0).getChildrenList());
                secondAdapter.notifyDataSetChanged();
                stairAdapter.notifyDataSetChanged();
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .centerCrop()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(SQSP.shouyelist.get(0).getCategoryImage())
                        .into(im_dingbu);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }
}
