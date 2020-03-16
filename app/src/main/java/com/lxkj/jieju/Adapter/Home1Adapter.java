package com.lxkj.jieju.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jieju.Bean.FirsePagebean;
import com.lxkj.jieju.R;
import com.lxkj.jieju.Utils.StringUtil_li;

import java.util.ArrayList;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/13 0013.
 * Describe :首页一级Adapter
 */

public class Home1Adapter extends RecyclerView.Adapter<Home1Adapter.MyHolder>  {
    private Context context;
    private List<FirsePagebean.JprouctListBean> list;
    private  Home1_itemAdapter shop_itemAdapter;
    LinearLayoutManager layoutManager;
    private static final String TAG = "ShopAdapter";
    public Home1Adapter(Context context, List<FirsePagebean.JprouctListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public Home1Adapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_adapter, parent, false);
        return new Home1Adapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(Home1Adapter.MyHolder holder, final int position) {
         if (StringUtil_li.isSpace(list.get(position).getAreaimage())){
             holder.tv_proxyCode.setVisibility(View.GONE);

         }else {
             holder.tv_proxyCode.setVisibility(View.VISIBLE);
             Glide.with(context).applyDefaultRequestOptions(new RequestOptions()
                     .error(R.mipmap.logo)
                     .placeholder(R.mipmap.logo))
                     .load(list.get(position).getAreaimage())
                     .into(holder.tv_proxyCode);
         }
        layoutManager = new LinearLayoutManager(context);
        holder.recycle.setLayoutManager(layoutManager);
        shop_itemAdapter= new Home1_itemAdapter(context,list.get(position).getPList());
        holder.recycle.setAdapter(shop_itemAdapter);
        shop_itemAdapter.setOnItemClickListener(new Home1_itemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                  onItemClickListener.OnItemClickListener(firstPosition,position);
            }
        });
        holder.tv_proxyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnImage(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        RecyclerView recycle;
        ImageView tv_proxyCode;
        public MyHolder(View itemView) {
            super(itemView);
            tv_proxyCode = itemView.findViewById(R.id.tv_proxyCode);
            recycle = itemView.findViewById(R.id.recycle);
        }
    }
    private Home1Adapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(Home1Adapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition,int position);
        void OnImage(int position);

    }
}
