package com.lxkj.jieju.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.jieju.Bean.Orderbean;
import com.lxkj.jieju.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/19 0019.
 * Describe :订单列表
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {
    private Context context;
    private List<Orderbean.DataListBean> list;
    private List<Orderbean.DataListBean.OrderItemBean> item_list = new ArrayList<>();
    LinearLayoutManager layoutManager;
    OrderitemAdapter recycleOneItemAdapter;
    public OrderAdapter(Context context, List<Orderbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public OrderAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderAdapter.MyHolder holder, final int position) {

        item_list.clear();
        for (int i = 0; i < list.get(position).getOrderItem().size(); i++) {
            list.get(position).getOrderItem().get(i).setOrderPrice1(list.get(position).getOrderPrice());
        }
        item_list.addAll(list.get(position).getOrderItem());

        holder.tv1.setText("订单编号："+list.get(position).getOrderid());
        if (list.get(position).getStatus().equals("0")){
            holder.tv2.setText("待付款");
            holder.tv_qufukaun.setText("去支付");
            holder.tv_chakan.setVisibility(View.GONE);
            holder.tv_qufukaun.setVisibility(View.VISIBLE);
            holder.tv_zhuangtai.setVisibility(View.GONE);
            holder.im_shanchu.setVisibility(View.GONE);
        }else if (list.get(position).getStatus().equals("1")){
            holder.tv2.setText("待发货");
            holder.tv_chakan.setVisibility(View.GONE);
            holder.tv_chakan.setText("查看物流");
            holder.tv_zhuangtai.setVisibility(View.VISIBLE);
            holder.tv_zhuangtai.setText("正在发货");
            holder.im_shanchu.setVisibility(View.GONE);
        }else if (list.get(position).getStatus().equals("2")){
            holder.tv2.setText("待收货");
            holder.tv_qufukaun.setText("确认收货");
            holder.tv_chakan.setVisibility(View.VISIBLE);
            holder.tv_qufukaun.setVisibility(View.VISIBLE);
            holder.tv_zhuangtai.setVisibility(View.VISIBLE);
            holder.tv_zhuangtai.setText("正在发货");
            holder.im_shanchu.setVisibility(View.GONE);
        }else if (list.get(position).getStatus().equals("3")){
            holder.tv2.setText("已完成");
            holder.tv_qufukaun.setText("已完成");
            holder.tv_qufukaun.setBackgroundResource(R.drawable.biankuang7);
            holder.tv_qufukaun.setVisibility(View.GONE);
            holder.tv_zhuangtai.setVisibility(View.VISIBLE);
            holder.tv_zhuangtai.setText("已完成");
            holder.im_shanchu.setVisibility(View.VISIBLE);
        }else if (list.get(position).getStatus().equals("4")){
            holder.tv2.setText("已取消");
            holder.tv_qufukaun.setText("已取消");
            holder.tv_qufukaun.setBackgroundResource(R.drawable.biankuang7);
            holder.tv_qufukaun.setVisibility(View.GONE);

            holder.tv_zhuangtai.setVisibility(View.VISIBLE);
            holder.tv_zhuangtai.setText("已取消");
            holder.im_shanchu.setVisibility(View.VISIBLE);
        }else if (list.get(position).getStatus().equals("5")){
            holder.tv2.setText("已完成");
        }else if (list.get(position).getStatus().equals("6")){
            holder.tv2.setText("退款中");
            holder.tv_qufukaun.setText("查看详情");
            holder.tv_qufukaun.setVisibility(View.VISIBLE);
        }else if (list.get(position).getStatus().equals("7")){
            holder.tv2.setText("已退款");
        }else if (list.get(position).getStatus().equals("8")){
            holder.tv2.setText("拒绝退款");
        }
        holder.tv_time.setText(list.get(position).getAdtime());
        layoutManager = new LinearLayoutManager(context);
        holder.recycle.setLayoutManager(layoutManager);
        recycleOneItemAdapter=new OrderitemAdapter(context,item_list);
        holder.recycle.setAdapter(recycleOneItemAdapter);
        recycleOneItemAdapter.setOnItemClickListener(new OrderitemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                onItemClickListener.OnbuttonImage(position);
            }
        });
      holder.tv_chakan.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              onItemClickListener.chakanwuliu(position);
          }
       });
        holder.tv_qufukaun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tv_qufukaun.getText().toString().equals("查看详情")){
                    onItemClickListener.tuikuanxiangqing(position);
                }else if (holder.tv_qufukaun.getText().toString().equals("去评价")){
                    onItemClickListener.qupingjia(position);
                }else if (holder.tv_qufukaun.getText().toString().equals("确认收货")){
                    onItemClickListener.querenshouhuo(position);
                }else if (holder.tv_qufukaun.getText().toString().equals("去支付")){
                    onItemClickListener.qufukuan(position);
                }
            }
        });
        holder.im_shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onItemClickListener.shaunchu(position);
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
        RecyclerView recycle;
        TextView tv1,tv2,tv_time,tv_chakan,tv_qufukaun,tv_zhuangtai;
        ImageView im_shanchu;
        public MyHolder(View itemView) {
            super(itemView);
            recycle = itemView.findViewById(R.id.item_recycle);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_chakan = itemView.findViewById(R.id.tv_chakan);
            tv_qufukaun = itemView.findViewById(R.id.tv_qufukaun);
            tv_zhuangtai = itemView.findViewById(R.id.tv_zhuangtai);
            im_shanchu = itemView.findViewById(R.id.im_shanchu);
        }
    }
    private OrderAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OrderAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
        void OnbuttonImage(int position);
        void tuikuanxiangqing(int position);
        void qupingjia(int position);
        void chakanwuliu(int position);
        void querenshouhuo(int position);
        void qufukuan(int position);
        void shaunchu(int position);

    }
}
