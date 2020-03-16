package com.lxkj.jieju.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jieju.App;
import com.lxkj.jieju.Bean.Brokeragebean;
import com.lxkj.jieju.R;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/18 0018.
 * Describe :邀请好友列表
 */

public class Broketragedapter extends RecyclerView.Adapter<Broketragedapter.MyHolder>{
    private Context context;
    private List<Brokeragebean.FriendListBean> list;
    public Broketragedapter(Context context, List<Brokeragebean.FriendListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public Broketragedapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_broketrage, parent, false);
        return new Broketragedapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(Broketragedapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.ic_figure_head)
                .placeholder(R.mipmap.ic_figure_head))
                .load(list.get(position).getIcon())
                .into(holder.im_userIcon);
        holder.tv_nickName.setText(list.get(position).getName());
        holder.tv_adtime.setText(list.get(position).getAdtime());
        holder.tv_amount.setText("获取佣金："+list.get(position).getAmount()+"元");
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
         ImageView im_userIcon;
         TextView tv_nickName,tv_adtime,tv_amount;
        public MyHolder(View itemView) {
            super(itemView);
            im_userIcon = itemView.findViewById(R.id.im_userIcon);
            tv_nickName = itemView.findViewById(R.id.tv_nickName);
            tv_adtime = itemView.findViewById(R.id.tv_adtime);
            tv_amount = itemView.findViewById(R.id.tv_amount);
        }
    }
    private Broketragedapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(Broketragedapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
