package com.example.day_00_lainxi.api;

import com.example.day_00_lainxi.bean.Bean;
import com.example.day_00_lainxi.bean.DataItem;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface MyServer {

    public String Urls = "https://ws1.sinaimg.cn/large/";

    @GET("0065oQSqgy1fze94uew3jj30qo10cdka.jpg")
    Observable<ResponseBody> getData();

    public String Url1 = "http://yun918.cn/study/";

    @Multipart
    @POST("public/file_upload.php")
    Observable<Bean> postData(@Part("key")RequestBody body, @Part MultipartBody.Part file);

    public String url2 = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/";
    @GET
    Observable<DataItem> getData(@Url String string);
}
