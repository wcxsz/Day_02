package com.example.day_00_lainxi.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.day_00_lainxi.R;
import com.example.day_00_lainxi.api.MyServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class XiaZaiActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    /**
     * OK下载
     */
    private Button bt_ok;
    /**
     * retrofit下载
     */
    private Button bt_retrofit;
    /**
     * httpurl下载
     */
    private Button bt_httpurl;
    private File sd;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xia_zai);
        initView();
        initData();
    }
    private void initData() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED){
            initSd();
        }else {
            ActivityCompat.requestPermissions(
                    this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    ,100
            );
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
            initSd();
        }else {
            Toast.makeText(this,"授权失败",Toast.LENGTH_LONG).show();
        }
    }
    private void initSd() {
        sd = Environment.getExternalStorageDirectory();
        //getExternalStorageDirectory
    }
    private void initView() {
        bt_ok = (Button) findViewById(R.id.bt_ok_xiazai);
        bt_ok.setOnClickListener(this);
        bt_retrofit = (Button) findViewById(R.id.bt_retrofit_xiazai);
        bt_retrofit.setOnClickListener(this);
        bt_httpurl = (Button) findViewById(R.id.bt_httpurl_xiazai);
        bt_httpurl.setOnClickListener(this);
        iv = findViewById(R.id.iv_xiazai);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_ok_xiazai:
                btokxiazai();
                break;
            case R.id.bt_retrofit_xiazai:
                initretrofit();
                break;
            case R.id.bt_httpurl_xiazai:
                http();
                break;
        }
    }
    private void http() {
        new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    URL url = new URL("https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    int responseCode = con.getResponseCode();
                    if (responseCode==200){
                        InputStream inputStream = con.getInputStream();
                        int max = con.getContentLength();
                        saveFile(inputStream,sd+"/"+"abc789.apk",max);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void initretrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyServer.Urls)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyServer myServer = retrofit.create(MyServer.class);
        Observable<ResponseBody> data = myServer.getData();
        data.subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        InputStream inputStream = responseBody.byteStream();
                        long max = responseBody.contentLength();
                        saveFile(inputStream,sd+"/"+"zxc.jpg",max);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void btokxiazai() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request build = new Request.Builder()
                .url("https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg")
                .get()
                .build();
        Call call = client.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                InputStream inputStream = body.byteStream();
                saveFile(inputStream,sd+"/"+"abc.jpg",body.contentLength());
            }
        });
    }
    private void saveFile(InputStream inputStream, final String string, long max) {
        int count = 0;
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(string));
            int len = -1;
            byte[] bytes = new byte[1024 * 10];
            while ((len = inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                count +=len;
                Log.d(TAG, "progress: " + count + "    max:" + max);
            }
            inputStream.close();
            outputStream.close();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeFile(string);
                    iv.setImageBitmap(bitmap);
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
