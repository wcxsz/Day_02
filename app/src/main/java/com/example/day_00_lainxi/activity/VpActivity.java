package com.example.day_00_lainxi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.day_00_lainxi.R;
import com.example.day_00_lainxi.VpAdapter;
import com.example.day_00_lainxi.activity.HomeActivity;

import java.util.ArrayList;

public class VpActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vp_vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp);
        initView();
    }

    private void initView() {
        vp_vp = (ViewPager) findViewById(R.id.vp_vp);
        ArrayList<View> views = new ArrayList<>();
        View item1 = LayoutInflater.from(this).inflate(R.layout.item1, null);
        View item2 = LayoutInflater.from(this).inflate(R.layout.item2, null);
        View item3 = LayoutInflater.from(this).inflate(R.layout.item3, null);
        View item4 = LayoutInflater.from(this).inflate(R.layout.item4, null);
        views.add(item1);
        views.add(item2);
        views.add(item3);
        views.add(item4);
        Button bt = item4.findViewById(R.id.bt_item4);
        bt.setOnClickListener(this);
        VpAdapter vpAdapter = new VpAdapter(views);
        vp_vp.setAdapter(vpAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
