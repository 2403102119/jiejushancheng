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
import com.lxkj.jieju.Bean.Orderdetailbean;
import com.lxkj.jieju.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/19 0019.
 * Describe :退款选择商品列表
 */

public class RefundAdapter extends RecyclerView.Adapter<RefundAdapter.MyHolder> {
    private Context context;
    private List<Orderdetailbean.OrderDetailBean.OrderItemBean> list;
    private boolean isChecked = false;
    public RefundAdapter(Context context, List<Orderdetailbean.OrderDetailBean.OrderItemBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public RefundAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_refund, parent, false);
        return new RefundAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final RefundAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.ic_figure_head)
                .placeholder(R.mipmap.ic_figure_head))
                .load(list.get(position).getProductImage())
                .into(holder.image2);
        holder.tv1.setText(list.get(position).getProductName());
        holder.tv2.setText("规格："+list.get(position).getProductSkuName1()+" "+list.get(position).getProductSkuName2());
        holder.tv3.setText("¥ "+list.get(position).getProductPrice());
        holder.tv_count.setText("×"+list.get(position).getProductCount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClickListener(position);
            }
        });

        holder.imageSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (list.get(position).isIscheck() ==false){
//                    holder.imageSel.setImageResource(R.mipmap.xuanzhong);
//                    list.get(position).setIscheck(true);
//                }else {
//                    holder.imageSel.setImageResource(R.mipmap.weixuan);
//                    list.get(position).setIscheck(false);
//                }
//                onItemClickListener.OnCheckef(position,list.get(position).isIscheck());
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
        TextView tv1,tv2,tv3,tv_count;
        ImageView imageSel;
        public MyHolder(View itemView) {
            super(itemView);
            image2 = itemView.findViewById(R.id.image2);
            tv2 = itemView.findViewById(R.id.tv2);
            tv1 = itemView.findViewById(R.id.tv1);
            tv3 = itemView.findViewById(R.id.tv3);
            tv_count = itemView.findViewById(R.id.tv_count);
            imageSel = itemView.findViewById(R.id.imageSel);
        }
    }
    private RefundAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RefundAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
        void OnCheckef(int position,boolean check);
    }
}
