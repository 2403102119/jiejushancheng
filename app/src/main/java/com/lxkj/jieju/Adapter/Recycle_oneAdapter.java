package com.lxkj.jieju.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.lxkj.jieju.Bean.productcommentbean;
import com.lxkj.jieju.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/10/11 0011.
 */

public class Recycle_oneAdapter extends RecyclerView.Adapter<Recycle_oneAdapter.MyHolder> {
    private Context context;
    private List<productcommentbean.DataListBean> list;
    private List<String> item_list = new ArrayList<>();
    StaggeredGridLayoutManager layoutManager;
    Recycle_one_itemAdapter recycleOneItemAdapter;
    public Recycle_oneAdapter(Context context, List<productcommentbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public Recycle_oneAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycleone, parent, false);
        return new Recycle_oneAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(Recycle_oneAdapter.MyHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
  return 2;
//        if (list == null) {
//            return 0;
//        } else {
//            return list.size();
//        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        public MyHolder(View itemView) {
            super(itemView);
        }
    }
    private Recycle_oneAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(Recycle_oneAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
        void OnbuttonImage(int position);

    }
}
