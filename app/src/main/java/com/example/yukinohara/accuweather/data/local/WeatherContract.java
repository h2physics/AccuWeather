package com.example.yukinohara.accuweather.data.local;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by YukiNoHara on 4/19/2017.
 */

public class WeatherContract {
    public static final String CONTENT_AUTHORITY = "com.example.yukinohara.accuweather";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_WEATHER = "weathers";

    public static final class WeatherEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WEATHER).build();

        public static final String TABLE_NAME = "weather";

        public static final String COLUMN_MINIMUM_TEMPERATURE = "min_temp";
        public static final String COLUMN_MAXIMUM_TEMPERATURE = "max_temp";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_MAIN = "main";
        public static final String COLUMN_DESCRIPTION = "desciption";
        public static final String COLUMN_SPEED = "speed";
        public static final String COLUMN_CLOUDS = "clouds";
        public static final String COLUMN_MORNING_TEMPERATURE = "morning_temp";
        public static final String COLUMN_NIGHT_TEMPERATURE = "night_temp";
        public static final String COLUMN_DAY_TEMPERATURE = "day_temp";



    }

}
