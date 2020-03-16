package com.lxkj.jieju.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jieju.App;
import com.lxkj.jieju.Bean.FirsePagebean;
import com.lxkj.jieju.R;
import com.lxkj.jieju.Utils.StringUtil_li;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/21 0021.
 * Describe :
 */

public class ViewPagerAdapter  extends RecyclerView.Adapter<ViewPagerAdapter.MyHolder>  {
    private Context context;
    private List<FirsePagebean.CategoryListBean> list;
    public ViewPagerAdapter(Context context, List<FirsePagebean.CategoryListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public ViewPagerAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager, parent, false);
        return new ViewPagerAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewPagerAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.logo)
                .placeholder(R.mipmap.logo))
                .load(list.get(position).getCategoryImage())
                .into(holder.ri_icon);
        holder.tv_state.setText(list.get(position).getCategoryName());
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
        RoundedImageView ri_icon;
        TextView tv_state;
        public MyHolder(View itemView) {
            super(itemView);
            ri_icon = itemView.findViewById(R.id.ri_icon);
            tv_state = itemView.findViewById(R.id.tv_state);

        }
    }
    private ViewPagerAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ViewPagerAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);

    }

}
