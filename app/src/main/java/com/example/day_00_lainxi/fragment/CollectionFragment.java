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
import com.example.day_00_lainxi.adapter.CollectionRlAdapter;
import com.example.day_00_lainxi.bean.DataDao;
import com.example.day_00_lainxi.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {
    private View view;
    private RecyclerView rlv;
    private ArrayList<DataDao> list = new ArrayList<>();
    private CollectionRlAdapter adapter;
    private int index = 0;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() ){
            initData();
        }else {
            list.clear();
        }
    }
    private void initData() {
        List<DataDao> query = MyUtils.getMyUtils().query();
        list.addAll(query);
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_collection, container, false);
        initView(inflate);
        return inflate;
    }
    private void initView(final View inflate) {
        rlv = (RecyclerView) inflate.findViewById(R.id.rlv);
        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CollectionRlAdapter(list, getContext());
        rlv.setAdapter(adapter);
        registerForContextMenu(rlv);
        adapter.setMyLongClick(new CollectionRlAdapter.MyLongClick() {
            @Override
            public void longClick(int i) {
                index = i;
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0,2,0,"取消收藏");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 2:
                DataDao dataDao = list.get(index);
                list.remove(dataDao);
                MyUtils.getMyUtils().delete(dataDao);
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
