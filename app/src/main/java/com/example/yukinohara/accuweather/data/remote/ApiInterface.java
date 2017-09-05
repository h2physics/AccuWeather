package com.example.yukinohara.accuweather.data.remote;

import com.example.yukinohara.accuweather.data.model.ForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by YukiNoHara on 4/6/2017.
 */

public interface ApiInterface {
    @GET("daily?")
    Call<ForecastResponse> getForecast(@Query("q") String locationSetting,
                                       @Query("mode") String typeResponse,
                                       @Query("units") String typeUnit,
                                       @Query("cnt") String numDays,
                                       @Query("APPID") String apiKey);
//    Call<ForecastResponse> getForecast();
}
