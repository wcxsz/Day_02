package com.example.day_00_lainxi.app;

import android.app.Application;

public class BaseApp extends Application {
    public static BaseApp baseApp;
    @Override
    public void onCreate() {
        super.onCreate();
        baseApp = this;
    }

    public static BaseApp getBaseApp() {
        return baseApp;
    }
}
