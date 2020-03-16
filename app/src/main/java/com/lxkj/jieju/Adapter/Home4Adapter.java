package com.lxkj.jieju.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jieju.App;
import com.lxkj.jieju.Bean.UserInfobean;
import com.lxkj.jieju.Bean.productcommentbean;
import com.lxkj.jieju.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/3/2 0002.
 * Describe :
 */

public class Home4Adapter extends RecyclerView.Adapter<Home4Adapter.MyHolder> {
    private Context context;
    private List<UserInfobean.CanListBean> list;
    private List<String> item_list = new ArrayList<>();
    StaggeredGridLayoutManager layoutManager;
    Recycle_one_itemAdapter recycleOneItemAdapter;
    public Home4Adapter(Context context, List<UserInfobean.CanListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public Home4Adapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycleone, parent, false);
        return new Home4Adapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(Home4Adapter.MyHolder holder, final int position) {
         holder.tv1.setText(list.get(position).getTitle());
         holder.tv2.setText(list.get(position).getPrice());
         holder.tv7.setText(list.get(position).getSales()+"人付款");
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.logo)
                .placeholder(R.mipmap.logo))
                .load(list.get(position).getLogo())
                .into(holder.image1);

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
        TextView tv1,tv2,tv7;
        RoundedImageView image1;
        public MyHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv7 = itemView.findViewById(R.id.tv7);
            image1 = itemView.findViewById(R.id.image1);
        }
    }
    private Home4Adapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(Home4Adapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
        void OnbuttonImage(int position);

    }
}
