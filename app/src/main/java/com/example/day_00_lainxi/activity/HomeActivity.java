package com.example.day_00_lainxi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.day_00_lainxi.R;
import com.example.day_00_lainxi.adapter.VpAdapter;
import com.example.day_00_lainxi.fragment.CollectionFragment;
import com.example.day_00_lainxi.fragment.HomeFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    /**
     * 首页
     */
    private TextView mTv;
    private Toolbar mToolbar;
    private ViewPager mVp;
    private TabLayout mTab;
    private NavigationView mNv;
    private DrawerLayout mDl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        mTv = (TextView) findViewById(R.id.tv);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mVp = (ViewPager) findViewById(R.id.vp);
        mTab = (TabLayout) findViewById(R.id.tab);
        mNv = (NavigationView) findViewById(R.id.nv);
        mDl = (DrawerLayout) findViewById(R.id.dl);
        setSupportActionBar(mToolbar);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CollectionFragment());
        VpAdapter adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        mVp.setAdapter(adapter);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDl, mToolbar, 0, 0);
        mDl.addDrawerListener(toggle);
        toggle.syncState();
        initListener();
        mTab.addTab(mTab.newTab().setText("首页"));
        mTab.addTab(mTab.newTab().setText("收藏"));
        mTab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mVp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mVp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
    }

    private void initListener() {
        mNv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                switch (itemId){
                    /*case R.id.bt_baidu:
                        startActivity(new Intent(HomeActivity.this,BaiDuActivity.class));
                        break;*/
                    case R.id.fuwu:
                        startActivity(new Intent(HomeActivity.this, FuWuActivity.class));
                        break;
                    case R.id.shangchuan:
                        startActivity(new Intent(HomeActivity.this, ShangChuanActivity.class));
                        break;
                    case R.id.xiazai:
                        startActivity(new Intent(HomeActivity.this, XiaZaiActivity.class));
                        break;
                }

                return false;
            }
        });
        View headerView = mNv.getHeaderView(0);
        Button bt_baidu = headerView.findViewById(R.id.bt_baidu);
        bt_baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,BaiDuActivity.class));
            }
        });
    }
}
