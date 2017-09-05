package com.example.yukinohara.accuweather.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YukiNoHara on 4/6/2017.
 */

public class ApiClient {
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/";
    private static Retrofit mRetrofit = null;

    public static Retrofit getClient(){
        if (mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
