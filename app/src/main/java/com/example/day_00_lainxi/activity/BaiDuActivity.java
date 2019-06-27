package com.example.day_00_lainxi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.day_00_lainxi.R;

public class BaiDuActivity extends AppCompatActivity {

    private WebView web;
    private String url = "https://www.baidu.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_du);
        initView();

    }

    private void initView() {
        web = (WebView) findViewById(R.id.web);
          WebSettings settings = web.getSettings();
            settings.getJavaScriptEnabled();
            web.setWebViewClient(new WebViewClient());
            web.loadUrl(url);

    }
}
