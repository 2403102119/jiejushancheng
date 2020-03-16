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
import com.lxkj.jieju.Bean.Cartbean;
import com.lxkj.jieju.R;
import com.lxkj.jieju.View.AmountView2;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by Administrator on 2019/10/12 0012.
 */

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.MyHolder> {
    private Context context;
    private List<Cartbean.DataListBean> list;;

    public ShoppingAdapter(Context context, List<Cartbean.DataListBean> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public ShoppingAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopcar, parent, false);
        return new ShoppingAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShoppingAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.ic_figure_head)
                .placeholder(R.mipmap.ic_figure_head))
                .load(list.get(position).getImage())
                .into(holder.image2);
        holder.tv1.setText(list.get(position).getProductName());

        holder.tv2.setText("规格: "+list.get(position).getSkuName1()+"  "+list.get(position).getSkuName2());
        holder.tv3.setText("¥ "+list.get(position).getPrice());
        holder.AmountView.setGoodsNubber(list.get(position).getCount());
        holder.AmountView.setOnAmountChangeListener(new AmountView2.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                onItemClickListener.Onselect(position,amount+"");
            }
        });
        holder.imageSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (list.get(position).isIscheck()==false){
                     list.get(position).setIscheck(true);
                     holder.imageSel.setImageResource(R.mipmap.xuanzhong);
                 }else {
                     list.get(position).setIscheck(false);
                     holder.imageSel.setImageResource(R.mipmap.weixuan);
                 }
                 onItemClickListener.OnDetal(position);
            }
        });

        if (list.get(position).isIscheck()==true){
            holder.imageSel.setImageResource(R.mipmap.xuanzhong);
        }else {
            holder.imageSel.setImageResource(R.mipmap.weixuan);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnonItemClickListener(position);
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
        TextView tv1,tv2,tv3;
        AmountView2 AmountView;
        ImageView imageSel;
        public MyHolder(View itemView) {
            super(itemView);
            image2 = itemView.findViewById(R.id.image2);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            AmountView = itemView.findViewById(R.id.AmountView);
            imageSel = itemView.findViewById(R.id.imageSel);
        }
    }
    private ShoppingAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ShoppingAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnDetal(int position);
        void Onselect(int position,String mount);
        void OnonItemClickListener(int position);

    }


}
