package com.lxkj.jieju.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.lxkj.jieju.App;
import com.lxkj.jieju.Bean.Evluationbean;
import com.lxkj.jieju.R;
import com.lxkj.jieju.SQSP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/25 0025.
 * Describe :
 */

public class EvaluationAdapter extends  RecyclerView.Adapter<EvaluationAdapter.MyHolder> {
    private Context context;
    private List<Evluationbean.DataListBean> list;
    private List<String> item_list = new ArrayList<>();
    StaggeredGridLayoutManager layoutManager;
    Recycle_one_itemAdapter recycleOneItemAdapter;
    private static final String TAG = "EvaluationAdapter";
    public EvaluationAdapter(Context context, List<Evluationbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public EvaluationAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycleone, parent, false);
        return new EvaluationAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(EvaluationAdapter.MyHolder holder, final int position) {


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
        public MyHolder(View itemView) {
            super(itemView);
        }
    }
    private EvaluationAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(EvaluationAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
        void OnbuttonImage(int position);

    }
}
