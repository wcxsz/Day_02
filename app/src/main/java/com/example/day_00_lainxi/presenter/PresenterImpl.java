package com.example.day_00_lainxi.presenter;

import com.example.day_00_lainxi.bean.DataItem;
import com.example.day_00_lainxi.callback.CallBack;
import com.example.day_00_lainxi.model.Model;
import com.example.day_00_lainxi.view.HomeView;

public class PresenterImpl implements Presenter, CallBack {
    private Model model;
    private HomeView homeView;

    public PresenterImpl(Model model, HomeView homeView) {
        this.model = model;
        this.homeView = homeView;
    }

    @Override
    public void getData(String string) {
        model.getData(string,this);
    }

    @Override
    public void onSeccoss(DataItem dataItem) {
        homeView.onSeccoss(dataItem);
    }

    @Override
    public void onFile(String string) {
        homeView.onFile(string);
    }
}
