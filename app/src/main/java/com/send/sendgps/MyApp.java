package com.send.sendgps;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import github.nisrulz.easydeviceinfo.base.EasyLocationMod;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import android.content.SharedPreferences;



public class MyApp {
	
	private SharedPreferences mSharedPreferences;
	public static final String PREFERENCE= "my_shared_pref";
	
    EasyLocationMod easyLocationMod;
    Context mContext;
    String lato,longto;
    String currentDate;
    String currentTime;
    APIInterface apiInterface;

    public MyApp(Context mContext)
    {
        this.mContext = mContext;


    }
    public void cobaGps()
    {
        easyLocationMod = new EasyLocationMod(mContext);
        Log.d("APPLOG","COBAGPS");
        double[] l = easyLocationMod.getLatLong();
        lato = String.valueOf(l[0]);
        longto = String.valueOf(l[1]);

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        Log.d("GPS",lato+"|"+longto);
        Log.d("TIME",currentDate+"|"+currentTime);
        connectServer();
    }

    public void connectServer()
    {
		
		mSharedPreferences = mContext.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<ResponseBody> call = apiInterface.postingData(lato,longto,currentDate,currentTime,mSharedPreferences.getString("Officer_ID", null));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    Log.d("Call request", call.request().toString());
                    Log.d("Call request header", call.request().headers().toString());
                    Log.d("Response raw header", response.headers().toString());
                    Log.d("Response raw", String.valueOf(response.raw().body()));
                    Log.d("Response code", String.valueOf(response.code()));

                    if(response.isSuccessful()) {
                        ResponseBody responseBody = (ResponseBody) response.body();
                        String responseBodyString= null;
                        try {
                            responseBodyString = responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response body", responseBodyString);
                    }
                    else  {
                        Log.d("Response errorBody", String.valueOf(response.errorBody()));
                    }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
            }
        });
    }
}
