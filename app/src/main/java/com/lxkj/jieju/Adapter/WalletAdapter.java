package com.lxkj.jieju.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxkj.jieju.Bean.Walletbean;
import com.lxkj.jieju.R;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/15 0015.
 * Describe :
 */

public class WalletAdapter extends  RecyclerView.Adapter<WalletAdapter.MyHolder> {
    private Context context;
    private List<Walletbean.DataListBean> list;
    public WalletAdapter(Context context, List<Walletbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public WalletAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallet, parent, false);
        return new WalletAdapter.MyHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(WalletAdapter.MyHolder holder, final int position) {

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
        TextView tv_title,tv_time,tv_money;
        public MyHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_money = itemView.findViewById(R.id.tv_money);
        }
    }
    private WalletAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(WalletAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
