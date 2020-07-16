package com.send.sendgps;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {



    @FormUrlEncoded
    @POST("testpost.php")
    Call<ResponseBody> postingData(
            @Field("lat") String lat,
            @Field("long") String longt,
            @Field("tgl") String tgl,
            @Field("jam") String jam,
			@Field("officer_id") String officer_id);
}
