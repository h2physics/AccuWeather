package com.example.yukinohara.accuweather.utils;

import android.content.Context;
import android.util.Log;

import com.example.yukinohara.accuweather.data.model.Forecast;
import com.example.yukinohara.accuweather.data.model.ForecastResponse;
import com.example.yukinohara.accuweather.data.remote.ApiClient;
import com.example.yukinohara.accuweather.data.remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YukiNoHara on 4/19/2017.
 */

public class NetworkUtils {
    public static void getResponseFromServer(final Context context, String locationSettings, String mode, String typeTemp, String numDays, String APIKey){

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ForecastResponse> call = apiInterface.getForecast(locationSettings, mode, typeTemp, numDays, APIKey);
        call.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                List<Forecast> list = response.body().getList();
                DataUtils.insertDataFromResponse(context, list);
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {

            }
        });


    }
}
