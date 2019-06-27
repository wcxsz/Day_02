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
import com.example.day_00_lainxi.bean.DataDao;

import java.util.ArrayList;

public class CollectionRlAdapter extends RecyclerView.Adapter<CollectionRlAdapter.MyHolder> {
    private ArrayList<DataDao> list;
    private Context context;
    private MyLongClick myLongClick;
    public CollectionRlAdapter(ArrayList<DataDao> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setList(ArrayList<DataDao> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_lift_item, null);
        MyHolder myHolder = new MyHolder(inflate);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        myHolder.desc_lift.setText(list.get(i).getDesc());
        Glide.with(context).load(list.get(i).getUrl())
                .apply(requestOptions)
                .into(myHolder.url_lift);
        myHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
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
    public class MyHolder extends RecyclerView.ViewHolder{

        private final ImageView url_lift;
        private final TextView desc_lift;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            url_lift = itemView.findViewById(R.id.url_lift);
            desc_lift = itemView.findViewById(R.id.desc_lift);
        }
    }
    public interface MyLongClick{
        void longClick(int i);
    }

    public void setMyLongClick(MyLongClick myLongClick) {
        this.myLongClick = myLongClick;
    }
}
