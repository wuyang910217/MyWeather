package com.app.wuyang.myweather.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.adapter.DrawerAdapter;
import com.app.wuyang.myweather.asynctask.HandleAirQualityAndWeatherTask;
import com.app.wuyang.myweather.data.AirQuality;
import com.app.wuyang.myweather.data.DrawerData;
import com.app.wuyang.myweather.data.LocationInfo;
import com.app.wuyang.myweather.data.WeatherIndex;
import com.app.wuyang.myweather.data.WeatherInfo;
import com.app.wuyang.myweather.db.AirQualityHelper;
import com.app.wuyang.myweather.db.LocationInfoHelper;
import com.app.wuyang.myweather.db.WeatherHelper;
import com.app.wuyang.myweather.utility.LogUtility;
import com.app.wuyang.myweather.utility.WeatherAboutUtils;
import com.app.wuyang.myweather.utility.WeatherTransformUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private List<DrawerData> mData= new ArrayList<>();

    private TextView showTodayDate,showCityName,showQuality,showPm25,
            showPm10,showWeather,showTemp,showWind,showDetail;
    private ImageView showImage;
    private ProgressBar mProgressBar;

    public static final String DETAIL_ACTION_TODAY="DETAIL_ACTION_TODAY";
    public static final String DETAIL_ACTION_OTHER="DETAIL_ACTION_OTHER";
    private static final String pollutionLevel1="优";
    private static final String pollutionLevel2="良";
    private static final String pollutionLevel3="轻度污染";
    private static final String pollutionLevel4="中度污染";
    private static final String pollutionLevel5="重度污染";
    private static final String pollutionLevel6="严重污染";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        LogUtility.d("abc", "现在进入到了。。。。" + getClass());

        initData();
        initView();

        ListView mDrawerList = (ListView) findViewById(R.id.item_list);
        mDrawerList.setAdapter(new DrawerAdapter(
                MainActivity.this,
                R.layout.layout_drawer_item,
                mData));

        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        HandleAirQualityAndWeatherTask task =new
                HandleAirQualityAndWeatherTask(MainActivity.this);
        task.execute();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        showUiTask showUiTask =new showUiTask();
        showUiTask.execute();
    }
    private class showUiTask extends AsyncTask<Void,Void,Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                LogUtility.d("abc","start------"+System.currentTimeMillis());
                Thread.sleep(2000);
                LogUtility.d("abc", "end-----" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressBar.setVisibility(View.GONE);
            LogUtility.d("abc","onPostExecute----------");
            showUi();
        }
    }
    private void initView(){
        showTodayDate= (TextView) findViewById(R.id.showTodayDate);
        showCityName = (TextView) findViewById(R.id.showCityName);
        showQuality = (TextView) findViewById(R.id.showQuality);
        showPm25 = (TextView) findViewById(R.id.showPm25);
        showPm10 = (TextView) findViewById(R.id.showPm10);
        showWeather = (TextView) findViewById(R.id.showWeather);
        showTemp = (TextView) findViewById(R.id.showTemp);
        showWind = (TextView) findViewById(R.id.showWind);
        showImage= (ImageView) findViewById(R.id.showImage);
        showDetail= (TextView) findViewById(R.id.showDetail);
        mProgressBar= (ProgressBar) findViewById(R.id.main_progressbar);
    }


    public void showUi(){
        WeatherAboutUtils aboutUtils=new WeatherAboutUtils();
        AirQualityHelper airQualityHelper =AirQualityHelper.getInstance(this);
        WeatherHelper weatherHelper=new WeatherHelper(this);
        LocationInfoHelper locationInfoHelper =new LocationInfoHelper(this);
        WeatherTransformUtils transformUtils =new WeatherTransformUtils();


        List<AirQuality> airQualityList =airQualityHelper.loadAirQuality();
        List<WeatherInfo> weatherInfoList=weatherHelper.loadWeatherInfo();
        LocationInfo locationInfo =locationInfoHelper.loadLocationInfo();
        List<WeatherIndex> weatherIndexList =weatherHelper.loadWeatherIndex();

        showTodayDate.setText(aboutUtils.getFriendDate());

        if (airQualityList==null){
            showQuality.setText("无数据");
        }else {
//            根据污染级别 改变文字背景
            pollutionLevel(airQualityList);

            showPm25.setText("PM2.5: "+airQualityList.get(0).getPm25());
            showPm10.setText("PM10: "+airQualityList.get(0).getPm10());
        }

        if (weatherInfoList.size()==0){
            showWeather.setText("无数据");
            showTemp.setText("无数据");
            showWind.setText("无数据");
        }else {
            String weather =weatherInfoList.get(0).getWeather_day();
            String windPower =weatherInfoList.get(0).getWind_power_day();
            String windDirection =weatherInfoList.get(0).getWind_direction_day();

            if (weather==null){
                showWeather.setText("无数据");
            }else {
                showWeather.setText(transformUtils.tranWeather(weather));
            }
            showTemp.setText("白天气温："+weatherInfoList.get(0).getTemperature_day()+"℃"
                    +"\n"+"\n"+"夜晚气温："+weatherInfoList.get(0).
                    getTemperature_night()+"℃");
            showWind.setText(transformUtils.tranWeather(windDirection)
                    +" "+transformUtils.tranWindPower(windPower));
        }

        if (weatherIndexList.size()==0){
            showDetail.setText("无数据");
        }else {
            showDetail.setText(weatherIndexList.get(1).getWeatherIndexInfo());
        }

        if (locationInfo==null){
            showCityName.setText("无数据");
        }else {
            showCityName.setText(locationInfo.getCounty());
        }


    }

    private void pollutionLevel(List<AirQuality> airQualities) {
        String pollution=airQualities.get(0).getQuality();
        switch (pollution){
            case pollutionLevel1:
                showQuality.setBackgroundResource(R.drawable.air_quality_pollutant_level1);
                showQuality.setText("空气质量"+pollution);
                break;
            case pollutionLevel2:
                showQuality.setBackgroundResource(R.drawable.air_quality_pollutant_level1);
                showQuality.setText("空气质量"+pollution);
                break;
            case pollutionLevel3:
                showQuality.setBackgroundResource(R.drawable.air_quality_pollutant_level2);
                showQuality.setText(pollution);
                break;
            case pollutionLevel4:
                showQuality.setBackgroundResource(R.drawable.air_quality_pollutant_level3);
                showQuality.setText(pollution);
                break;
            case pollutionLevel5:
                showQuality.setBackgroundResource(R.drawable.air_quality_pollutant_level3);
                showQuality.setText(pollution);
                break;
            case pollutionLevel6:
                showQuality.setBackgroundResource(R.drawable.air_quality_pollutant_level4);
                showQuality.setText(pollution);
                break;
        }
    }


    private void initData() {
        DrawerData a= new DrawerData(R.drawable.ic_wb_sunny_red_24dp,"未来七天");
        mData.add(a);
        DrawerData b= new DrawerData(R.drawable.ic_place_black_36dp,"详细地址");
        mData.add(b);
        DrawerData c = new DrawerData(R.drawable.ic_mood_black_36dp,"好心情");
        mData.add(c);
        DrawerData d = new DrawerData(R.drawable.ic_thumb_up_black_36dp,"点赞");
        mData.add(d);
        DrawerData e = new DrawerData(R.drawable.ic_settings_black_36dp,"设置");
        mData.add(e);
    }

    public void openDetail(View view){
        mDrawerLayout.closeDrawers();
        Intent intent=new Intent(this,DetailActivity.class);
        intent.setAction(DETAIL_ACTION_TODAY);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawers();
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        }
        switch (item.getItemId()) {
            case R.id.action_about:
                Toast.makeText(this, "1111", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_exit:
                finish();
                break;
            case R.id.action_refresh:
                Toast.makeText(this, "1111", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
