package com.lxkj.jieju.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jieju.App;
import com.lxkj.jieju.Bean.FirsePagebean;
import com.lxkj.jieju.Bean.Proprietarybean;
import com.lxkj.jieju.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/3/17 0017.
 * Describe :
 */

public class Second_itemAdapter extends RecyclerView.Adapter<Second_itemAdapter.MyHolder> {
    private Context context;
    private List<Proprietarybean.DataListBean.ChildrenListBean> list;
    public Second_itemAdapter(Context context, List<Proprietarybean.DataListBean.ChildrenListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public Second_itemAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_second, parent, false);
        return new Second_itemAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final Second_itemAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.bai)
                .placeholder(R.mipmap.bai))
                .load(list.get(position).getChildCategoryImage())
                .into(holder.image1);
        holder.tv1.setText(list.get(position).getChildCategoryName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClickListener(position);
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
        RoundedImageView image1;
        TextView tv1;
        public MyHolder(View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.image1);
            tv1 = itemView.findViewById(R.id.tv1);
        }
    }
    private Second_itemAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(Second_itemAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);

    }
}
