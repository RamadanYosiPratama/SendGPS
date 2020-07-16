package com.send.sendgps;
import java.util.concurrent.TimeUnit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {
    public static final String BASE_URL = "https://k0il.net/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {


        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(BASE_URL);
            builder.addConverterFactory(GsonConverterFactory.create());
            retrofit = builder
                    .build();
        }
        return retrofit;
    }
}
