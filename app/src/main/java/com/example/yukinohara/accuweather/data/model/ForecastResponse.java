package com.example.yukinohara.accuweather.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YukiNoHara on 4/6/2017.
 */

public class ForecastResponse {
    @SerializedName("list")
    private List<Forecast> list;

    public List<Forecast> getList() {
        return list;
    }

    public void setList(List<Forecast> list) {
        this.list = list;
    }
}
