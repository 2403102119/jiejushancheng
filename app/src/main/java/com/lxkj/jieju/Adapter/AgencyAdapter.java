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
import com.lxkj.jieju.Bean.Agencybean;
import com.lxkj.jieju.Bean.Brokeragebean;
import com.lxkj.jieju.R;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/3/10 0010.
 * Describe :
 */

public class AgencyAdapter extends RecyclerView.Adapter<AgencyAdapter.MyHolder> {
    private Context context;
    private List<Agencybean.DataListBean> list;
    public AgencyAdapter(Context context, List<Agencybean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public AgencyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false);
        return new AgencyAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(AgencyAdapter.MyHolder holder, final int position) {
      holder.tv_site.setText(list.get(position).getName());
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
        TextView tv_site;
        public MyHolder(View itemView) {
            super(itemView);
            tv_site = itemView.findViewById(R.id.tv_site);
        }
    }
    private AgencyAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(AgencyAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
