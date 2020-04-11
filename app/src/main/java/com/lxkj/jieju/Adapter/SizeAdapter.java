package com.lxkj.jieju.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.jieju.Bean.FirsePagebean;
import com.lxkj.jieju.R;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/3/17 0017.
 * Describe :
 */

public class SizeAdapter  extends RecyclerView.Adapter<SizeAdapter.MyHolder> implements View.OnClickListener {
    private Context context;
    private List<String> list;
    public int selectPosition = 0;
    public SizeAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public SizeAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_size, parent, false);
        return new SizeAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final SizeAdapter.MyHolder holder, final int position) {
        holder.tv_size.setText(list.get(position));
        holder.tv_size.setTag(position);
        if (position==selectPosition){
            holder.tv_size.setBackgroundResource(R.drawable.biankuang32);
            holder.tv_size.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.tv_size.setBackgroundResource(R.drawable.biankuang20);
            holder.tv_size.setTextColor(Color.parseColor("#666666"));
        }


    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public void onClick(View v) {
        //注意这里要给某一个控件添加点击事件
        int position = (int) v.getTag();      //getTag()获取数据
        if (onItemClickListener != null) {
            switch (v.getId()) {
                case R.id.tv_size:
                    selectPosition = position;
                    onItemClickListener.OnItemClickListener(position);
                    notifyDataSetChanged();
                    break;
                default:
                    selectPosition = position;
                    break;
            }
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_size;
        public MyHolder(View itemView) {
            super(itemView);
            tv_size = itemView.findViewById(R.id.tv_size);
            tv_size.setOnClickListener(SizeAdapter.this);
        }
    }

    /*------------------以下为item中的button控件点击事件处理------------------*/
    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    private SizeAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(SizeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);

    }
}
