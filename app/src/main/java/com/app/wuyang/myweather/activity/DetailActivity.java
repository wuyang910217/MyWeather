package com.app.wuyang.myweather.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.db.DbQuery;


/**
 * Created by wuyang on 16-1-1.
 * 显示详情页;
 */
public class DetailActivity extends AppCompatActivity{
    private TextView detail_weather_day,detail_weather_night,detail_temp_day,detail_temp_night,
            detail_weather_index1,detail_weather_index2,detail_weather_index3,detail_sun_raise,
            detail_publish_time,detail_air_quality,detail_pollutant,detail_pm25,detail_pm10,
            detail_o3,detail_so2,detail_no2,detail_co,detail_county_name,detail_city_name;

    private static final String WEATHER_NOTIFICATION="WEATHER_NOTIFICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();
//        Intent intent =getIntent();
//        switch (intent.getAction()){
//            case MainActivity.DETAIL_ACTION_TODAY:
//                showDetailToday();
//                break;
//            case MainActivity.DETAIL_ACTION_OTHER:
//                showDetailOther();
//                break;
//            case WEATHER_NOTIFICATION:
//                showDetailToday();
//            default:
//                break;
        showDetailToday();

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
        DbQuery dbQuery=new DbQuery(this);
        detail_weather_day.setText(dbQuery.getTodayWeatherContent(DbQuery.WEATHER_DAY));
        detail_weather_night.setText(dbQuery.getTodayWeatherContent(DbQuery.WEATHER_NIGHT));
        detail_temp_day.setText(dbQuery.getTodayWeatherContent(DbQuery.TEMPERATURE_DAY));
        detail_temp_night.setText(dbQuery.getTodayWeatherContent(DbQuery.TEMPERATURE_NIGHT));
        detail_weather_index1.setText(dbQuery.getWeatherIndexExerciseContent(DbQuery.WEATHER_INDEX_LEVEL));
        detail_weather_index2.setText(dbQuery.getWeatherIndexComfortContent(DbQuery.WEATHER_INDEX_LEVEL));
        detail_weather_index3.setText(dbQuery.getWeatherIndexClothContent(DbQuery.WEATHER_INDEX_LEVEL));
        detail_sun_raise.setText(dbQuery.getTodayWeatherContent(DbQuery.SUN_TIME));
        detail_publish_time.setText(dbQuery.getTodayWeatherContent(DbQuery.PUBLISH_TIME));
        detail_air_quality.setText(dbQuery.getAirQualityContent(DbQuery.QUALITY));
        detail_pollutant.setText(dbQuery.getAirQualityContent(DbQuery.POLLUTANT));
        detail_pm25.setText(dbQuery.getAirQualityContent(DbQuery.PM25));
        detail_pm10.setText(dbQuery.getAirQualityContent(DbQuery.PM10));
        detail_o3.setText(dbQuery.getAirQualityContent(DbQuery.O3));
        detail_so2.setText(dbQuery.getAirQualityContent(DbQuery.SO2));
        detail_no2.setText(dbQuery.getAirQualityContent(DbQuery.NO2));
        detail_co.setText(dbQuery.getAirQualityContent(DbQuery.CO));
        detail_county_name.setText(dbQuery.getLocationContent(DbQuery.COUNTY));
        detail_city_name.setText(dbQuery.getLocationContent(DbQuery.CITY));
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
            case android.R.id.home:
                finish();
                break;
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


