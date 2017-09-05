package com.example.yukinohara.accuweather.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yukinohara.accuweather.R;
import com.example.yukinohara.accuweather.data.model.Forecast;
import com.example.yukinohara.accuweather.databinding.ForecastDayItemBinding;
import com.example.yukinohara.accuweather.utils.DataUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YukiNoHara on 4/6/2017.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>{
    private Context mContext;
    List<Forecast> mList;
    OnClickListener onClickListener;
    private Cursor mCursor;


    public ForecastAdapter(Context context){
        this.mContext = context;
        mList = new ArrayList<>();
    }

    public interface OnClickListener{
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.forecast_day_item, null);
        view.setFocusable(true);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        ForecastDayItemBinding mBinding = holder.getBinding();
        mCursor.moveToPosition(position);
        String main = mCursor.getString(MainActivity.INDEX_WEATHER_MAIN);
        String description = mCursor.getString(MainActivity.INDEX_WEATHER_DESCRIPTION);
        String highTemp = mCursor.getString(MainActivity.INDEX_WEATHER_MAXIMUM_TEMPERATURE);
        String lowTemp = mCursor.getString(MainActivity.INDEX_WEATHER_MINIMUN_TEMPERATURE);

        if (main.equalsIgnoreCase(mContext.getString(R.string.clear))){
            Picasso.with(mContext)
                    .load(R.drawable.clear)
                    .fit()
                    .centerCrop()
                    .into(mBinding.detailWeatherImv);
        } else if (main.equalsIgnoreCase(mContext.getString(R.string.clouds))){
            Picasso.with(mContext)
                    .load(R.drawable.clouds)
                    .fit()
                    .centerCrop()
                    .into(mBinding.detailWeatherImv);
        } else if (main.equalsIgnoreCase(mContext.getString(R.string.rain))){
            Picasso.with(mContext)
                    .load(R.drawable.rain)
                    .fit()
                    .centerCrop()
                    .into(mBinding.detailWeatherImv);
        } else if (main.equalsIgnoreCase(mContext.getString(R.string.snow))){
            Picasso.with(mContext)
                    .load(R.drawable.snow)
                    .fit()
                    .centerCrop()
                    .into(mBinding.detailWeatherImv);
        }
        mBinding.dayTv.setText(DataUtils.getListCurrentDay()[position]);
        mBinding.weatherTv.setText(description);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String typeUnit = preferences.getString(mContext.getString(R.string.pref_key_temperature), mContext.getString(R.string.pref_key_celsius));
        Log.e("Temp", typeUnit);
        String temp = DataUtils.formatTemperature(mContext, Double.parseDouble(highTemp), Double.parseDouble(lowTemp), typeUnit);
        mBinding.temperatureTv.setText(temp);

    }

    @Override
    public int getItemCount() {
        if (mCursor == null){
            return 0;
        } else {
            return mCursor.getCount();
        }
    }

    public void swapCursor(Cursor data){
        mCursor = data;
        notifyDataSetChanged();
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ForecastDayItemBinding mBinding;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.getRoot().setOnClickListener(this);
        }

        public ForecastDayItemBinding getBinding(){
            return mBinding;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            onClickListener.onClick(position);
        }
    }

    public void setData(List<Forecast> list){
        mList = list;
    }
}
