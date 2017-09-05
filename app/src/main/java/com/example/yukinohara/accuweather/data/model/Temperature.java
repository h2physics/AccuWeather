package com.example.yukinohara.accuweather.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YukiNoHara on 4/16/2017.
 */

public class Temperature {
    @SerializedName("max")
    private String mHigh;

    @SerializedName("min")
    private String mLow;

    @SerializedName("morn")
    private String mMorn;

    @SerializedName("night")
    private String mNight;

    @SerializedName("day")
    private String mDay;

    public String getmHigh() {
        return mHigh;
    }

    public String getmLow() {
        return mLow;
    }

    public String getmMorn() {
        return mMorn;
    }

    public String getmNight() {
        return mNight;
    }

    public String getmDay() {
        return mDay;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "mHigh='" + mHigh + '\'' +
                ", mLow='" + mLow + '\'' +
                ", mMorn='" + mMorn + '\'' +
                ", mNight='" + mNight + '\'' +
                ", mDay='" + mDay + '\'' +
                '}';
    }
}
