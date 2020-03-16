package com.lxkj.jieju.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jieju.Bean.recommendProductbean;
import com.lxkj.jieju.Bean.showFirstPagebean;
import com.lxkj.jieju.R;
import com.lxkj.jieju.Utils.StringUtil_li;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/3/11 0011.
 * Describe :
 */

public class recommendProductAdapter extends RecyclerView.Adapter<recommendProductAdapter.MyHolder>  {
    private Context context;
    private List<recommendProductbean.DataListBean> list;
    public recommendProductAdapter(Context context, List<recommendProductbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public recommendProductAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_guess, parent, false);
        return new recommendProductAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(recommendProductAdapter.MyHolder holder, final int position) {
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher))
                .load(list.get(position).getLogo())
                .into(holder.image1);
        holder.tv1.setText(list.get(position).getTitle());
        NumberFormat nf = NumberFormat.getInstance();

        holder.tv2.setText(nf.format(Double.parseDouble(list.get(position).getPrice())));
        holder.tv7.setText(list.get(position).getSales()+"人付款");
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
        TextView tv1,tv2,tv7;
        public MyHolder(View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.image1);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv7 = itemView.findViewById(R.id.tv7);
        }
    }
    private recommendProductAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(recommendProductAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);

    }
}
