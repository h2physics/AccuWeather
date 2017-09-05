package com.example.yukinohara.accuweather.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.yukinohara.accuweather.R;
import com.example.yukinohara.accuweather.data.local.WeatherContract;
import com.example.yukinohara.accuweather.data.model.Forecast;
import com.example.yukinohara.accuweather.data.model.Weather;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by YukiNoHara on 4/17/2017.
 */

public class DataUtils {
    public static String formatTemperature(Context context, double high, double low, String typeUnit){
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        if (typeUnit.equals(context.getString(R.string.pref_key_celsius))){
            return roundedHigh + "/" + roundedLow;
        } else {
            long roundedHighFormat = Math.round((roundedHigh * 1.8) + 32);
            long roundedLowFormat = Math.round((roundedLow * 1.8) + 32);

            return roundedHighFormat + "/" + roundedLowFormat;
        }
    }

    public static String[] getListCurrentDay(){
        Date date = new Date();

        String[] day = new String[7];
        long currentTime = date.getTime();
        for (int i = 0; i < 7; i++){
            date.setTime(currentTime);
            String[] str = date.toString().split(" ");
            day[i] = str[0] + " - " + str[1] + " - " + str[2];
            currentTime += TimeUnit.DAYS.toMillis(1);
        }

        return day;
    }

    public static int insertDataFromResponse(Context context, List<Forecast> list){
        List<ContentValues> mListValues = new ArrayList<>();
        for (Forecast forecast : list){
            ContentValues cv = new ContentValues();
            cv.put(WeatherContract.WeatherEntry.COLUMN_MINIMUM_TEMPERATURE, forecast.getmLowTemperature());
            cv.put(WeatherContract.WeatherEntry.COLUMN_MAXIMUM_TEMPERATURE, forecast.getmHighTemperature());
            cv.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, forecast.getmPressure());
            cv.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, forecast.getmHumidity());
            cv.put(WeatherContract.WeatherEntry.COLUMN_MAIN, forecast.getMain());
            cv.put(WeatherContract.WeatherEntry.COLUMN_DESCRIPTION, forecast.getDescription());
            cv.put(WeatherContract.WeatherEntry.COLUMN_SPEED, forecast.getmSpeed());
            cv.put(WeatherContract.WeatherEntry.COLUMN_CLOUDS, forecast.getmClouds());
            cv.put(WeatherContract.WeatherEntry.COLUMN_MORNING_TEMPERATURE, forecast.getMorningTemperature());
            cv.put(WeatherContract.WeatherEntry.COLUMN_NIGHT_TEMPERATURE, forecast.getNightTemperature());
            cv.put(WeatherContract.WeatherEntry.COLUMN_DAY_TEMPERATURE, forecast.getDayTemperature());
            mListValues.add(cv);
        }

        if (!mListValues.isEmpty()){
            ContentResolver contentResolver = context.getContentResolver();
            contentResolver.delete(WeatherContract.WeatherEntry.CONTENT_URI, null, null);
            contentResolver.bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI, mListValues.toArray(new ContentValues[mListValues.size()]));
        }
        return mListValues.size();
    }

}
