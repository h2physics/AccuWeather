package com.example.yukinohara.accuweather.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by YukiNoHara on 4/19/2017.
 */

public class WeatherDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "weather.db";
    public static final int DATABASE_VERSION = 2;

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WEATHER_DATABASE = "CREATE TABLE "
                + WeatherContract.WeatherEntry.TABLE_NAME + " ("
                + WeatherContract.WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WeatherContract.WeatherEntry.COLUMN_MINIMUM_TEMPERATURE + " TEXT NOT NULL, "
                + WeatherContract.WeatherEntry.COLUMN_MAXIMUM_TEMPERATURE + " TEXT NOT NULL, "
                + WeatherContract.WeatherEntry.COLUMN_PRESSURE + " TEXT NOT NULL, "
                + WeatherContract.WeatherEntry.COLUMN_HUMIDITY + " TEXT NOT NULL, "
                + WeatherContract.WeatherEntry.COLUMN_MAIN + " TEXT NOT NULL, "
                + WeatherContract.WeatherEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                + WeatherContract.WeatherEntry.COLUMN_SPEED + " TEXT NOT NULL, "
                + WeatherContract.WeatherEntry.COLUMN_CLOUDS + " TEXT NOT NULL, "
                + WeatherContract.WeatherEntry.COLUMN_MORNING_TEMPERATURE + " TEXT NOT NULL, "
                + WeatherContract.WeatherEntry.COLUMN_NIGHT_TEMPERATURE + " TEXT NOT NULL, "
                + WeatherContract.WeatherEntry.COLUMN_DAY_TEMPERATURE + " TEXT NOT NULL"
                + ");";

        db.execSQL(SQL_CREATE_WEATHER_DATABASE);
        Log.e("CREATE DATABASE", SQL_CREATE_WEATHER_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WeatherContract.WeatherEntry.TABLE_NAME + ";");
        onCreate(db);
    }
}
