package com.lxkj.hrhc.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.hrhc.App;
import com.lxkj.hrhc.Bean.Areabean;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.Utils.StringUtil_li;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;
import java.util.Map;

/**
 * Created ：李迪迦
 * on:2019/11/18 0018.
 * Describe :品牌闪购列表
 */

public class WarehouseAdapter extends RecyclerView.Adapter<WarehouseAdapter.MyHolder>{
    private Context context;
    private List<Areabean.DataListBean> list;
    public WarehouseAdapter(Context context, List<Areabean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public WarehouseAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_warehouse, parent, false);
        return new WarehouseAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(WarehouseAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.logo)
                .placeholder(R.mipmap.logo))
                .load(list.get(position).getLogo())
                .into(holder.image2);
         holder.tv1.setText(list.get(position).getTitle());
        if (StringUtil_li.isSpace(list.get(position).getOldPrice())){
            holder.tv_oldPrice.setText("¥ 0.00");
        }else {
            holder.tv_oldPrice.setText("¥"+list.get(position).getOldPrice());
        }
        holder.tv_oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
        holder.tv_money.setText(StringUtil_li.changTVsize(list.get(position).getPrice()));
        holder.tv_sales.setText(list.get(position).getSales()+"人付款");
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
            RoundedImageView image2;
            TextView tv1,tv_money,tv_oldPrice,tv_sales;
        public MyHolder(View itemView) {
            super(itemView);
            image2 = itemView.findViewById(R.id.image2);
            tv1 = itemView.findViewById(R.id.tv1);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_oldPrice = itemView.findViewById(R.id.tv_oldPrice);
            tv_sales = itemView.findViewById(R.id.tv_sales);
        }
    }
    private WarehouseAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(WarehouseAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
