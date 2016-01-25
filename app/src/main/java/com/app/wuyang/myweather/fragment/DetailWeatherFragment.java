package com.app.wuyang.myweather.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.db.DbQuery;

/**
 * Created by wuyang on 16-1-25.
 */
public class DetailWeatherFragment extends Fragment {
    private TextView fragment_detail_weather_day,fragment_detail_weather_night,
            fragment_detail_temp_day,fragment_detail_temp_night,
            fragment_detail_wind_direction_day,fragment_detail_wind_direction_night,
            fragment_detail_wind_power_day,fragment_detail_wind_power_night,
            fragment_detail_sun_raise;
    private ImageView fragment_detail_img;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_detail_weather_fragment, container, false);
        initView(view);
        int position =getActivity().getIntent().getIntExtra("position", 2);
        setView(position);
        return view;
    }

    private void initView(View view){
        fragment_detail_weather_day = (TextView) view.findViewById(R.id.fragment_detail_weather_day);
        fragment_detail_weather_night = (TextView) view.findViewById(R.id.fragment_detail_weather_night);
        fragment_detail_temp_day = (TextView) view.findViewById(R.id.fragment_detail_temp_day);
        fragment_detail_temp_night = (TextView) view.findViewById(R.id.fragment_detail_temp_night);
        fragment_detail_wind_direction_day = (TextView) view.findViewById(R.id.fragment_detail_wind_direction_day);
        fragment_detail_wind_direction_night = (TextView) view.findViewById(R.id.fragment_detail_wind_direction_night);
        fragment_detail_wind_power_day = (TextView) view.findViewById(R.id.fragment_detail_wind_power_day);
        fragment_detail_wind_power_night = (TextView) view.findViewById(R.id.fragment_detail_wind_power_night);
        fragment_detail_sun_raise = (TextView) view.findViewById(R.id.fragment_detail_sun_raise);
        fragment_detail_img = (ImageView) view.findViewById(R.id.fragment_detail_img);
    }
    private void setView(int position){
        DbQuery dbQuery =new DbQuery(getContext());
        if (position==0){
            fragment_detail_img.setImageResource(dbQuery.getTomorrowWeatherImage());
            fragment_detail_weather_day.setText(dbQuery.getTomorrowWeatherContent(DbQuery.WEATHER_DAY));
            fragment_detail_weather_night.setText(dbQuery.getTomorrowWeatherContent(DbQuery.WEATHER_NIGHT));
            fragment_detail_temp_day.setText(dbQuery.getTomorrowWeatherContent(DbQuery.TEMPERATURE_DAY));
            fragment_detail_temp_night.setText(dbQuery.getTomorrowWeatherContent(DbQuery.TEMPERATURE_NIGHT));
            fragment_detail_wind_direction_day.setText(dbQuery.getTomorrowWeatherContent(DbQuery.WIND_DIRECTION_DAY));
            fragment_detail_wind_direction_night.setText(dbQuery.getTomorrowWeatherContent(DbQuery.WIND_DIRECTION_NIGHT));
            fragment_detail_wind_power_day.setText(dbQuery.getTomorrowWeatherContent(DbQuery.WIND_POWER_DAY));
            fragment_detail_wind_power_night.setText(dbQuery.getTomorrowWeatherContent(DbQuery.WIND_POWER_NIGHT));
            fragment_detail_sun_raise.setText(dbQuery.getTomorrowWeatherContent(DbQuery.SUN_TIME));
        }else {
            fragment_detail_img.setImageResource(dbQuery.getTodayAfterTomorrowWeatherImage());
            fragment_detail_weather_day.setText(dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.WEATHER_DAY));
            fragment_detail_weather_night.setText(dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.WEATHER_NIGHT));
            fragment_detail_temp_day.setText(dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.TEMPERATURE_DAY));
            fragment_detail_temp_night.setText(dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.TEMPERATURE_NIGHT));
            fragment_detail_wind_direction_day.setText(dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.WIND_DIRECTION_DAY));
            fragment_detail_wind_direction_night.setText(dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.WIND_DIRECTION_NIGHT));
            fragment_detail_wind_power_day.setText(dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.WIND_POWER_DAY));
            fragment_detail_wind_power_night.setText(dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.WIND_POWER_NIGHT));
            fragment_detail_sun_raise.setText(dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.SUN_TIME));
        }
    }
}
