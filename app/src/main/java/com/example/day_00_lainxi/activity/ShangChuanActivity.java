package com.example.day_00_lainxi.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.day_00_lainxi.R;
import com.example.day_00_lainxi.api.MyServer;
import com.example.day_00_lainxi.bean.Bean;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShangChuanActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * OK上传
     */
    private Button bt_ok_shangchuan;
    /**
     * retrofit上传
     */
    private Button bt_retrofit_shangchuan;
    /**
     * httpurl上传
     */
    private Button bt_httpurl_shangchuan;
    private ImageView iv;
    private File file = new File("/storage/sdcard0/Pictures/aa.jpg");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shang_chuan);
        initView();
    }

    private void initView() {
        bt_ok_shangchuan = (Button) findViewById(R.id.bt_ok_shangchuan);
        bt_ok_shangchuan.setOnClickListener(this);
        bt_retrofit_shangchuan = (Button) findViewById(R.id.bt_retrofit_shangchuan);
        bt_retrofit_shangchuan.setOnClickListener(this);
        bt_httpurl_shangchuan = (Button) findViewById(R.id.bt_httpurl_shangchuan);
        bt_httpurl_shangchuan.setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.iv_shangchuan);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_ok_shangchuan:
                initData1();
                break;
            case R.id.bt_retrofit_shangchuan:
                initData2();
                break;
            case R.id.bt_httpurl_shangchuan:
                break;
        }
    }

    private void initData2() {
        if (ContextCompat.checkSelfPermission(this
                , Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED){
            initretrofit();
        }else {
            ActivityCompat.requestPermissions(this
                    ,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},200);
        }
    }

    private void initData1() {
        if (ContextCompat.checkSelfPermission(this
                ,Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED){
            initOK();
        }else {
            ActivityCompat.requestPermissions(this
                    ,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (requestCode == 100){
                initOK();
            }else if (requestCode == 200){
                initretrofit();
            }
        }
    }

    private void initretrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyServer.Url1)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyServer myServer = retrofit.create(MyServer.class);
        MediaType parse = MediaType.parse("application/octet-stream");
        RequestBody requestBody = RequestBody.create(parse, "H1808c");

        RequestBody requestBody1 = MultipartBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part file = MultipartBody.Part.createFormData("file", this.file.getName(), requestBody1);
        Observable<Bean> data = myServer.postData(requestBody, file);
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bean bean) {
                        if (bean != null){
                            if (bean.getCode() == 200){
                                //tv.setText(bean.getRes());
                                Glide.with(ShangChuanActivity.this)
                                        .load(bean.getData().getUrl())
                                        .into(iv);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initOK() {
        OkHttpClient build = new OkHttpClient.Builder().build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "H1808C")
                .addFormDataPart("file", file.getName(), requestBody)
                .build();
        Request request = new Request.Builder()
                .url("http://yun918.cn/study/public/file_upload.php")
                .post(body)
                .build();
        Call call = build.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final Bean bean = gson.fromJson(string, Bean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean != null){
                            if (bean.getCode() == 200){
                                //tv.setText(bean.getRes());
                                Glide.with(ShangChuanActivity.this)
                                        .load(bean.getData().getUrl())
                                        .into(iv);
                            }
                        }
                    }
                });
            }
        });
    }
}
