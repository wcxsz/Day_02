package com.example.day_00_lainxi.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.day_00_lainxi.R;
import com.example.day_00_lainxi.adapter.HomeRlAdapter;
import com.example.day_00_lainxi.bean.DataDao;
import com.example.day_00_lainxi.bean.DataItem;
import com.example.day_00_lainxi.model.ModelImpl;
import com.example.day_00_lainxi.presenter.Presenter;
import com.example.day_00_lainxi.presenter.PresenterImpl;
import com.example.day_00_lainxi.utils.MyUtils;
import com.example.day_00_lainxi.view.HomeView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView {
    private View view;
    private RecyclerView rl;
    private ArrayList<DataItem.ResultsBean> list;
    private int page = 1;
    private HomeRlAdapter adapter;
    private int index = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        initView(inflate);
        return inflate;
    }
    private void initView(View inflate) {
        list = new ArrayList<>();
        rl = (RecyclerView) inflate.findViewById(R.id.rl);
        rl.setLayoutManager(new LinearLayoutManager(getContext()));
        initData();
        adapter = new HomeRlAdapter(list, getContext());
        rl.setAdapter(adapter);
        registerForContextMenu(rl);
        adapter.setMyLongClick(new HomeRlAdapter.MyLongClick() {
            @Override
            public void longClick(int i) {
                index = i;
            }
        });
    }

    private void initData() {
        Presenter presenter = new PresenterImpl(new ModelImpl(),this);
        presenter.getData(String.valueOf(page));
    }

    @Override
    public void onSeccoss(DataItem dataItem) {
        List<DataItem.ResultsBean> results = dataItem.getResults();
        list.addAll(results);
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFile(String string) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0,1,0,"收藏");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                DataItem.ResultsBean resultsBean = list.get(index);
                DataDao dataDao = new DataDao();
                dataDao.setId(null);
                dataDao.setUrl(resultsBean.getUrl());
                dataDao.setDesc(resultsBean.getDesc());
                MyUtils.getMyUtils().insert(dataDao);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
