package com.example.yukinohara.accuweather.ui.base;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yukinohara.accuweather.R;
import com.example.yukinohara.accuweather.data.local.WeatherContract;
import com.example.yukinohara.accuweather.databinding.ActivityDetailsBinding;
import com.example.yukinohara.accuweather.ui.main.MainActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    ActivityDetailsBinding mBinding;
    int position;
    TextView mHumidityTv;
    TextView mPressureTv;
    TextView mSpeedTv;
    TextView mCloudsTv;
    TextView mMorningTv;
    TextView mNightTv;

    public static final int WEATHER_LOADER_ID = 1102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        Intent getIntent = this.getIntent();
        position = getIntent.getIntExtra("EXTRA", 0);

        ViewGroup humidity = (ViewGroup) findViewById(R.id.humidity);
        mHumidityTv = (TextView) humidity.findViewById(R.id.content_weather_tv);
        ((TextView) humidity.findViewById(R.id.title_weather_tv)).setText(getString(R.string.humidity));

        ViewGroup pressure = (ViewGroup) findViewById(R.id.pressure);
        mPressureTv = (TextView) pressure.findViewById(R.id.content_weather_tv);
        ((TextView) pressure.findViewById(R.id.title_weather_tv)).setText(getString(R.string.pressure));

        ViewGroup speed = (ViewGroup) findViewById(R.id.speed);
        mSpeedTv = (TextView) speed.findViewById(R.id.content_weather_tv);
        ((TextView) speed.findViewById(R.id.title_weather_tv)).setText(getString(R.string.speed));

        ViewGroup clouds = (ViewGroup) findViewById(R.id.clouds);
        mCloudsTv = (TextView) clouds.findViewById(R.id.content_weather_tv);
        ((TextView) clouds.findViewById(R.id.title_weather_tv)).setText(getString(R.string.clouds));

        ViewGroup morning = (ViewGroup) findViewById(R.id.morning_temp);
        mMorningTv = (TextView) morning.findViewById(R.id.content_weather_tv);
        ((TextView) morning.findViewById(R.id.title_weather_tv)).setText(getString(R.string.morningTemp));

        ViewGroup night = (ViewGroup) findViewById(R.id.night_temp);
        mNightTv = (TextView) night.findViewById(R.id.content_weather_tv);
        ((TextView) night.findViewById(R.id.title_weather_tv)).setText(getString(R.string.nightTemp));

    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportLoaderManager().initLoader(WEATHER_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case WEATHER_LOADER_ID: {
                Uri queryUri = WeatherContract.WeatherEntry.CONTENT_URI;

                return new CursorLoader(this,
                        queryUri,
                        MainActivity.MAIN_WEATHER_PROJECTION,
                        null,
                        null,
                        null);

            }

            default:
                throw new RuntimeException("Loader is not implemented: " + id);
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() != 0){
            data.moveToPosition(position);
            String pressure = data.getString(MainActivity.INDEX_WEATHER_PRESSURE);
            String humidity = data.getString(MainActivity.INDEX_WEATHER_HUMIDITY);
            String main = data.getString(MainActivity.INDEX_WEATHER_MAIN);
            String speed = data.getString(MainActivity.INDEX_WEATHER_SPEED);
            String clouds = data.getString(MainActivity.INDEX_WEATHER_CLOUDS);
            String morningTemp = data.getString(MainActivity.INDEX_WEATHER_MORNING_TEMPERATURE);
            String nightTemp = data.getString(MainActivity.INDEX_WEATHER_NIGHT_TEMPERATURE);
            String dayTemp = data.getString(MainActivity.INDEX_WEATHER_DAY_TEMPERATURE);

            if (main.equalsIgnoreCase(getString(R.string.clear))){
                Picasso.with(this)
                        .load(R.drawable.clear)
                        .fit()
                        .centerCrop()
                        .into(mBinding.detailWeatherImv);
            } else if (main.equalsIgnoreCase(getString(R.string.clouds))){
                Picasso.with(this)
                        .load(R.drawable.clouds)
                        .fit()
                        .centerCrop()
                        .into(mBinding.detailWeatherImv);
            } else if (main.equalsIgnoreCase(getString(R.string.rain))){
                Picasso.with(this)
                        .load(R.drawable.rain)
                        .fit()
                        .centerCrop()
                        .into(mBinding.detailWeatherImv);
            } else if (main.equalsIgnoreCase(getString(R.string.snow))){
                Picasso.with(this)
                        .load(R.drawable.snow)
                        .fit()
                        .centerCrop()
                        .into(mBinding.detailWeatherImv);
            }

            mPressureTv.setText(pressure);
            mHumidityTv.setText(humidity);
            mSpeedTv.setText(speed);
            mCloudsTv.setText(clouds);
            mMorningTv.setText(morningTemp);
            mNightTv.setText(nightTemp);
            mBinding.presentTempTv.setText(dayTemp + getString(R.string.sign_temperature));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
