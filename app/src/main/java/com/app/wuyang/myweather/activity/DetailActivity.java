package com.app.wuyang.myweather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.data.AirQuality;
import com.app.wuyang.myweather.data.LocationInfo;
import com.app.wuyang.myweather.data.WeatherIndex;
import com.app.wuyang.myweather.data.WeatherInfo;
import com.app.wuyang.myweather.db.AirQualityHelper;
import com.app.wuyang.myweather.db.LocationInfoHelper;
import com.app.wuyang.myweather.db.WeatherHelper;
import com.app.wuyang.myweather.utility.WeatherTransformUtils;

import java.util.List;


/**
 * Created by wuyang on 16-1-1.
 * 显示详情页;
 */
public class DetailActivity extends AppCompatActivity{
    private TextView detail_weather_day,detail_weather_night,detail_temp_day,detail_temp_night,
            detail_weather_index1,detail_weather_index2,detail_weather_index3,detail_sun_raise,
            detail_publish_time,detail_air_quality,detail_pollutant,detail_pm25,detail_pm10,
            detail_o3,detail_so2,detail_no2,detail_co,detail_county_name,detail_city_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail);


        initView();
        Intent intent =getIntent();
        switch (intent.getAction()){
            case MainActivity.DETAIL_ACTION_TODAY:
                showDetailToday();
                break;
            case MainActivity.DETAIL_ACTION_OTHER:
                showDetailOther();
                break;
            default:
                break;
        }



    }
    private void initView(){
        detail_weather_day= (TextView) findViewById(R.id.detail_weather_day);
        detail_weather_night= (TextView) findViewById(R.id.detail_weather_night);
        detail_temp_day= (TextView) findViewById(R.id.detail_temp_day);
        detail_temp_night= (TextView) findViewById(R.id.detail_temp_night);
        detail_weather_index1= (TextView) findViewById(R.id.detail_weather_index1);
        detail_weather_index2= (TextView) findViewById(R.id.detail_weather_index2);
        detail_weather_index3= (TextView) findViewById(R.id.detail_weather_index3);
        detail_sun_raise= (TextView) findViewById(R.id.detail_sun_raise);
        detail_publish_time= (TextView) findViewById(R.id.detail_publish_time);
        detail_air_quality= (TextView) findViewById(R.id.detail_air_quality);
        detail_pollutant= (TextView) findViewById(R.id.detail_pollutant);
        detail_pm25= (TextView) findViewById(R.id.detail_pm25);
        detail_pm10= (TextView) findViewById(R.id.detail_pm10);
        detail_o3= (TextView) findViewById(R.id.detail_o3);
        detail_so2= (TextView) findViewById(R.id.detail_so2);
        detail_no2= (TextView) findViewById(R.id.detail_no2);
        detail_co= (TextView) findViewById(R.id.detail_co);
        detail_county_name= (TextView) findViewById(R.id.detail_county_name);
        detail_city_name= (TextView) findViewById(R.id.detail_city_name);
    }

    private void showDetailOther() {

    }

    private void showDetailToday() {
        AirQualityHelper airQualityHelper =AirQualityHelper.getInstance(this);
        WeatherHelper weatherHelper=new WeatherHelper(this);
        LocationInfoHelper locationInfoHelper =new LocationInfoHelper(this);
        WeatherTransformUtils transformUtils =new WeatherTransformUtils();


        List<AirQuality> airQualityList =airQualityHelper.loadAirQuality();
        List<WeatherInfo> weatherInfoList=weatherHelper.loadWeatherInfo();
        LocationInfo locationInfo =locationInfoHelper.loadLocationInfo();
        List<WeatherIndex> weatherIndexList =weatherHelper.loadWeatherIndex();

        if (airQualityList==null){
            detail_air_quality.setText("无数据");
            detail_pollutant.setText("无数据");
            detail_pm25.setText("无数据");
            detail_pm10.setText("无数据");
            detail_o3.setText("无数据");
            detail_so2.setText("无数据");
            detail_no2.setText("无数据");
            detail_co.setText("无数据");
        }else {
            detail_air_quality.setText(airQualityList.get(0).getQuality());
            String pollutant =airQualityList.get(0).getPollutant();
            if (pollutant!=null){
                detail_pollutant.setText(pollutant);
            }else {
                detail_pollutant.setText("无数据");
            }
            detail_pm25.setText(""+airQualityList.get(0).getPm25());
            detail_pm10.setText(""+airQualityList.get(0).getPm10());
            detail_o3.setText(""+airQualityList.get(0).getO3());
            detail_so2.setText(""+airQualityList.get(0).getSo2());
            detail_no2.setText(""+airQualityList.get(0).getNo2());
            detail_co.setText(""+airQualityList.get(0).getCo());
        }
        if (weatherInfoList==null){
            detail_weather_day.setText("无数据");
            detail_weather_night.setText("无数据");
            detail_temp_day.setText("无数据");
            detail_temp_night.setText("无数据");
            detail_sun_raise.setText("无数据");
            detail_publish_time.setText("无数据");
        }else {
            String weatherDay =transformUtils.tranWeather(
                    weatherInfoList.get(0).getWeather_day());
            if (weatherDay==null){
                detail_weather_day.setText("无数据");
            }else {
                detail_weather_day.setText(weatherDay);
            }

            detail_weather_night.setText(transformUtils.tranWeather(
                    weatherInfoList.get(0).getWeather_night()));
            detail_temp_day.setText(weatherInfoList.get(0).getTemperature_day()+"℃");
            detail_temp_night.setText(weatherInfoList.get(0).getTemperature_night()+"℃");
            detail_sun_raise.setText(weatherInfoList.get(0).getSun_time());

            String publish_time =weatherInfoList.get(0).getPublish_time();
            String publish1=publish_time.substring(8,10);
            String publish2=publish_time.substring(10);
            detail_publish_time.setText(publish1+":"+publish2);
        }

        if (weatherIndexList==null){
            detail_weather_index1.setText("无数据");
            detail_weather_index2.setText("无数据");
            detail_weather_index3.setText("无数据");
        }else {
            detail_weather_index1.setText(weatherIndexList.get(0).getWeatherIndexLevel());
            detail_weather_index2.setText(weatherIndexList.get(1).getWeatherIndexLevel());
            detail_weather_index3.setText(weatherIndexList.get(2).getWeatherIndexLevel());
        }

        if (locationInfo==null){
            detail_city_name.setText("无数据");
            detail_county_name.setText("无数据");
        }else {
            detail_county_name.setText(locationInfo.getCounty());
            detail_city_name.setText(locationInfo.getCity());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_about:
                Toast.makeText(this, "1111", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_exit:
                finish();
                break;
            case R.id.action_share:
                Toast.makeText(this, "1111", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}


