package com.example.yukinohara.accuweather.data.model;

import com.example.yukinohara.accuweather.R;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YukiNoHara on 4/6/2017.
 */

public class Forecast {
    @SerializedName("pressure")
    private String mPressure;

    @SerializedName("humidity")
    private String mHumidity;

    @SerializedName("speed")
    private String mSpeed;

    @SerializedName("clouds")
    private String mClouds;

    @SerializedName("temp")
    private Temperature mTemp;

    @SerializedName("weather")
    private List<Weather> mWeather;

    public String getmPressure() {
        return mPressure;
    }

    public String getmHumidity() {
        return mHumidity;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "mPressure='" + mPressure + '\'' +
                ", mHumidity='" + mHumidity + '\'' +
                ", mSpeed='" + mSpeed + '\'' +
                ", mClouds='" + mClouds + '\'' +
                '}';
    }

    public String getmHighTemperature() {
        return mTemp.getmHigh();
    }

    public String getmLowTemperature() {
        return mTemp.getmLow();
    }

    public String getMorningTemperature(){
        return mTemp.getmMorn();
    }

    public String getNightTemperature(){
        return mTemp.getmNight();
    }

    public String getDayTemperature(){
        return mTemp.getmDay();
    }

    public String getDescription(){
        String des = getmWeather().get(0).getmDescription();
        return des;
    }

    public String getMain(){
        String main = getmWeather().get(0).getMain();
        return main;
    }

    public List<Weather> getmWeather() {
        return mWeather;
    }

    public String getmSpeed() {
        return mSpeed;
    }

    public String getmClouds() {
        return mClouds;
    }
}
