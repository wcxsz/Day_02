package com.example.day_00_lainxi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.day_00_lainxi.R;
import com.example.day_00_lainxi.bean.DataItem;

import java.util.ArrayList;

public class HomeRlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<DataItem.ResultsBean> list;
    private Context context;
    private MyLongClick myLongClick;
    public HomeRlAdapter(ArrayList<DataItem.ResultsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setList(ArrayList<DataItem.ResultsBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;
        if (i == 1){
            View inflate = LayoutInflater.from(context).inflate(R.layout.layout_lift_item, null);
            holder = new MyHolder_Lift(inflate);
        }else if (i == 2){
            View inflate = LayoutInflater.from(context).inflate(R.layout.layout_right_item, null);
            holder = new MyHolder_Right(inflate);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        long itemId = getItemViewType(i);
        if (itemId == 1){
            MyHolder_Lift myHolder_lift = (MyHolder_Lift) viewHolder;
            myHolder_lift.desc_lift.setText(list.get(i).getDesc());
            Glide.with(context).load(list.get(i).getUrl()).apply(requestOptions)
                    .into(myHolder_lift.url_lift);
        }else if (itemId == 2){
            MyHolder_Right myHolder_right = (MyHolder_Right) viewHolder;
            myHolder_right.desc_right.setText(list.get(i).getDesc());
            Glide.with(context).load(list.get(i).getUrl()).apply(requestOptions)
                    .into(myHolder_right.url_right);
        }
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                myLongClick.longClick(i);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2 == 0){
            return 1;//左
        }
        return 2;//右
    }
    public class MyHolder_Lift extends RecyclerView.ViewHolder{

        private final ImageView url_lift;
        private final TextView desc_lift;

        public MyHolder_Lift(@NonNull View itemView) {
            super(itemView);
            url_lift = itemView.findViewById(R.id.url_lift);
            desc_lift = itemView.findViewById(R.id.desc_lift);
        }
    }
    public class MyHolder_Right extends RecyclerView.ViewHolder {

        private final ImageView url_right;
        private final TextView desc_right;

        public MyHolder_Right(@NonNull View itemView) {
            super(itemView);
            url_right = itemView.findViewById(R.id.url_right);
            desc_right = itemView.findViewById(R.id.desc_right);

        }
    }
    public interface MyLongClick{
        void longClick(int i);
    }

    public void setMyLongClick(MyLongClick myLongClick) {
        this.myLongClick = myLongClick;
    }
}
