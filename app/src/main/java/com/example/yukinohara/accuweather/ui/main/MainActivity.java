package com.example.yukinohara.accuweather.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.yukinohara.accuweather.R;
import com.example.yukinohara.accuweather.data.local.WeatherContract;
import com.example.yukinohara.accuweather.data.model.Forecast;
import com.example.yukinohara.accuweather.databinding.ActivityMainBinding;
import com.example.yukinohara.accuweather.ui.base.DetailsActivity;
import com.example.yukinohara.accuweather.ui.base.SettingsActivity;
import com.example.yukinohara.accuweather.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ForecastAdapter.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor>, LocationListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String KEY = "f99f7329e28b995c7bfd1a33f191b59b";
    public static final int WEATHER_LOADER_ID = 1101;

    private ActivityMainBinding mBinding;
    private ForecastAdapter mAdapter;
    private List<Forecast> mList;
    private Context mContext;
    private LoaderManager.LoaderCallbacks callbacks;
    private int mPosition = RecyclerView.NO_POSITION;
    private LocationManager mLocationManager;
    private Location mLocation;
    private String mProvider;

    public static final String[] MAIN_WEATHER_PROJECTION = {
            WeatherContract.WeatherEntry._ID,
            WeatherContract.WeatherEntry.COLUMN_MINIMUM_TEMPERATURE,
            WeatherContract.WeatherEntry.COLUMN_MAXIMUM_TEMPERATURE,
            WeatherContract.WeatherEntry.COLUMN_PRESSURE,
            WeatherContract.WeatherEntry.COLUMN_HUMIDITY,
            WeatherContract.WeatherEntry.COLUMN_MAIN,
            WeatherContract.WeatherEntry.COLUMN_DESCRIPTION,
            WeatherContract.WeatherEntry.COLUMN_SPEED,
            WeatherContract.WeatherEntry.COLUMN_CLOUDS,
            WeatherContract.WeatherEntry.COLUMN_MORNING_TEMPERATURE,
            WeatherContract.WeatherEntry.COLUMN_NIGHT_TEMPERATURE,
            WeatherContract.WeatherEntry.COLUMN_DAY_TEMPERATURE
    };

    public static final int INDEX_WEATHER_ID = 0;
    public static final int INDEX_WEATHER_MINIMUN_TEMPERATURE = 1;
    public static final int INDEX_WEATHER_MAXIMUM_TEMPERATURE = 2;
    public static final int INDEX_WEATHER_PRESSURE = 3;
    public static final int INDEX_WEATHER_HUMIDITY = 4;
    public static final int INDEX_WEATHER_MAIN = 5;
    public static final int INDEX_WEATHER_DESCRIPTION = 6;
    public static final int INDEX_WEATHER_SPEED = 7;
    public static final int INDEX_WEATHER_CLOUDS = 8;
    public static final int INDEX_WEATHER_MORNING_TEMPERATURE = 9;
    public static final int INDEX_WEATHER_NIGHT_TEMPERATURE = 10;
    public static final int INDEX_WEATHER_DAY_TEMPERATURE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAdapter = new ForecastAdapter(this);
        mContext = MainActivity.this;

        mList = new ArrayList<>();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        mProvider = mLocationManager.getBestProvider(criteria, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.forecastRv.setLayoutManager(linearLayoutManager);
        mBinding.forecastRv.setHasFixedSize(true);
        mAdapter.setOnClickListener(this);

        showLoadingBar();

    }

    @Override
    protected void onStart() {
        super.onStart();
        callbacks = MainActivity.this;
        new WeatherTask().execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(int position) {
//        Log.e("Position", position + "");
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("INDEX", position);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh: {
                break;
            }

            case R.id.action_setting: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.action_map: {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission", "Permission is not allowed");
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    break;
                }
                mLocation = mLocationManager.getLastKnownLocation(mProvider);
                if (mLocation != null){
                    double latitude = mLocation.getLatitude();
                    double longitude = mLocation.getLongitude();
                    Log.e("Lat - Lon", latitude + " - " + longitude);
                    Uri geoUri = Uri.parse("geo:" + latitude + "," + longitude);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                } else {
                    Log.e("Location", "Location not available");
                }

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class WeatherTask extends AsyncTask<Void, Void, Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            String location = preferences.getString(getString(R.string.pref_key_location), getString(R.string.pref_default_location));
            Log.e("Location", location);

            NetworkUtils.getResponseFromServer(mContext, "94044", "json", "metric", "7", KEY);
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            getSupportLoaderManager().initLoader(WEATHER_LOADER_ID, null, callbacks);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case WEATHER_LOADER_ID:{
                Uri queryUri = WeatherContract.WeatherEntry.CONTENT_URI;

                return new CursorLoader(this,
                        queryUri,
                        MAIN_WEATHER_PROJECTION,
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
        mAdapter.swapCursor(data);
        mBinding.forecastRv.setAdapter(mAdapter);

        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        mBinding.forecastRv.smoothScrollToPosition(mPosition);

        if (data.getCount() != 0) hideLoadingBar();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public void showLoadingBar() {
        mBinding.forecastRv.setVisibility(View.INVISIBLE);
        mBinding.loadingPb.setVisibility(View.VISIBLE);
    }

    public void hideLoadingBar() {
        mBinding.forecastRv.setVisibility(View.VISIBLE);
        mBinding.loadingPb.setVisibility(View.INVISIBLE);
    }

}
