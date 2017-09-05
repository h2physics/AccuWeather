package com.example.yukinohara.accuweather.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YukiNoHara on 4/16/2017.
 */

public class Weather {
    @SerializedName("description")
    private String mDescription;

    @SerializedName("main")
    private String main;

    public String getmDescription() {
        return mDescription;
    }

    public String getMain() {
        return main;
    }
}
