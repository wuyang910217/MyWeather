package com.app.wuyang.myweather.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.wuyang.myweather.CircleImageView;
import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.adapter.DrawerAdapter;
import com.app.wuyang.myweather.adapter.WeatherAdapter;
import com.app.wuyang.myweather.asynctask.HandleAirQualityAndWeatherTask;
import com.app.wuyang.myweather.data.DrawerData;
import com.app.wuyang.myweather.data.WeatherData;
import com.app.wuyang.myweather.db.DbQuery;
import com.app.wuyang.myweather.service.TimerService;
import com.app.wuyang.myweather.utility.CheckConnect;
import com.app.wuyang.myweather.utility.LogUtility;
import com.app.wuyang.myweather.utility.SetImageUtility;
import com.app.wuyang.myweather.utility.WeatherAboutUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private List<DrawerData> mData= new ArrayList<>();
    private List<WeatherData> weatherDatas =new ArrayList<>();
    private ListView mDrawerList;
    private ListView weatherList;
    private WeatherAdapter weatherAdapter;
    private TextView showTodayDate,showCityName,showQuality,showPm25,
            showPm10,showWeather,showTempMax,showTempMin,showWind,showWeatherIndex,showDetail;
    private ImageView showImage;
//    private CardView cardView;
    private CircleImageView circleImageView;
    private SetImageUtility imageUtility;
    public static final String DETAIL_ACTION_TODAY="DETAIL_ACTION_TODAY";
//    public static final String DETAIL_ACTION_OTHER="DETAIL_ACTION_OTHER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtility.d("abc", "现在进入到了。。。。" + getClass());

//    有网 后台执行查询操作
        CheckConnect checkConnect = new CheckConnect(getApplicationContext());
        if (checkConnect.isConnect()){
            HandleAirQualityAndWeatherTask task =new
                    HandleAirQualityAndWeatherTask(MainActivity.this);
            task.execute();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        initData();
        initView();

        LogUtility.d("abc","------------------------"+getPackageName());
        mDrawerList = (ListView) findViewById(R.id.item_list);
        mDrawerList.setAdapter(new DrawerAdapter(
                MainActivity.this,
                R.layout.layout_drawer_item,
                mData));

        mDrawerList.setOnItemClickListener(this);

        weatherList = (ListView) findViewById(R.id.item_list_main);

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

        imageUtility=new SetImageUtility(MainActivity.this);
        Bitmap bitmap=imageUtility.setImage();
        if (bitmap!=null){
            circleImageView.setImageBitmap(bitmap);
        }

        showUiTask showUiTask =new showUiTask();
        showUiTask.execute();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

//        IntentFilter filter =new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        NetWorkReceiver receiver =new NetWorkReceiver();
//        registerReceiver(receiver,filter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view, int position, long id) {
        Intent intent =new Intent(this,DrawerFragmentActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);

    }

    public class showUiTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            try {
                LogUtility.d("abc","start------"+System.currentTimeMillis());
                Thread.sleep(2300);
                LogUtility.d("abc", "end-----" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showUi();
            weatherAdapter = new WeatherAdapter(MainActivity.this,
                    R.layout.layout_cardview_item, addToList());
            weatherList.setAdapter(weatherAdapter);


            Intent intent =new Intent(getApplicationContext(), TimerService.class);
            intent.putExtra("enable", true);
            startService(intent);


        }
    }

    private List<WeatherData> addToList(){
        DbQuery dbQuery=new DbQuery(this);

        WeatherData weatherData=new WeatherData(dbQuery.getTomorrowWeatherImage(),
                dbQuery.getTomorrowWeatherContent(DbQuery.WEATHER_DAY),
                dbQuery.getTomorrowWeatherContent(DbQuery.TOMORROW),
                "白天气温："+dbQuery.getTomorrowWeatherContent(DbQuery.TEMPERATURE_DAY),
                "夜晚气温："+dbQuery.getTomorrowWeatherContent(DbQuery.TEMPERATURE_NIGHT),
                dbQuery.getTomorrowWeatherContent(DbQuery.WIND_POWER_DAY),
                dbQuery.getTomorrowWeatherContent(DbQuery.WIND_DIRECTION_DAY));
        weatherDatas.add(weatherData);

        WeatherData weatherData1=new WeatherData(dbQuery.getTodayAfterTomorrowWeatherImage(),
                dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.WEATHER_DAY),
                dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.TODAY_AFTER_TOMORROW),
                "白天气温："+dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.TEMPERATURE_DAY),
                "夜晚气温："+dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.TEMPERATURE_NIGHT),
                dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.WIND_POWER_DAY),
                dbQuery.getTodayAfterTomorrowWeatherContent(DbQuery.WIND_DIRECTION_DAY));
        weatherDatas.add(weatherData1);
        return weatherDatas;
    }
    private void initView(){
        showTodayDate= (TextView) findViewById(R.id.showTodayDate);
        showCityName = (TextView) findViewById(R.id.showCityName);
        showQuality = (TextView) findViewById(R.id.showQuality);
        showPm25 = (TextView) findViewById(R.id.showPm25);
        showPm10 = (TextView) findViewById(R.id.showPm10);
        showWeather = (TextView) findViewById(R.id.showWeather);
        showTempMax = (TextView) findViewById(R.id.showTempMax);
        showTempMin = (TextView) findViewById(R.id.showTempMin);
        showWind = (TextView) findViewById(R.id.showWind);
        showImage= (ImageView) findViewById(R.id.showImage);
        showDetail= (TextView) findViewById(R.id.showDetail);
        showWeatherIndex= (TextView) findViewById(R.id.showWeatherIndex);
        circleImageView = (CircleImageView) findViewById(R.id.circleImage);
    }


    public void showUi(){
        DbQuery dbQuery=new DbQuery(this);
        showTodayDate.setText(dbQuery.getTodayWeatherContent(DbQuery.TODAY));
        showCityName.setText(dbQuery.getLocationContent(DbQuery.COUNTY));
        showPm25.setText("PM2.5："+dbQuery.getAirQualityContent(DbQuery.PM25));
        showPm10.setText("PM10："+dbQuery.getAirQualityContent(DbQuery.PM10));
        showWeather.setText(dbQuery.getTodayWeatherContent(DbQuery.WEATHER_DAY));
        WeatherAboutUtils aboutUtils=new WeatherAboutUtils();
        if (aboutUtils.isNight()){
            showTempMax.setVisibility(View.GONE);
        }
        showTempMax.setText("白天气温："+dbQuery.getTodayWeatherContent(DbQuery.TEMPERATURE_DAY));
        showTempMin.setText("夜晚气温：" + dbQuery.getTodayWeatherContent(DbQuery.TEMPERATURE_NIGHT));
        showWind.setText(dbQuery.getTodayWeatherContent(DbQuery.WIND_DIRECTION_DAY)
                + "  " + dbQuery.getTodayWeatherContent(DbQuery.WIND_POWER_DAY));
        showImage.setImageResource(dbQuery.getWeatherImage());
        String publish_time=dbQuery.getTodayWeatherContent(DbQuery.PUBLISH_TIME_FULL);
        showDetail.setText("天气更新时间："+publish_time);
        showWeatherIndex.setText(dbQuery.getWeatherIndexClothContent(DbQuery.WEATHER_INDEX_INFO));
        String quality= dbQuery.getAirQualityContent(DbQuery.QUALITY);
        showQuality.setText(dbQuery.getAirQualityLevel(quality));
        showQuality.setBackgroundResource(dbQuery.getAirQualityLevelId(quality));
    }

    public void showCircleImage(View view){

        final String[] items ={"从相册选择","照相"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setCancelable(true);
        dialog.setTitle("选择需要的操作");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent = imageUtility.chooseFromAlbum();
                        startActivityForResult(intent,
                                SetImageUtility.CHOOSE_PHOTO);
                        break;
                    case 1:
                        Intent intent1 = imageUtility.takePhoto();
                        startActivityForResult(intent1,
                                SetImageUtility.TAKE_PHOTO);
                        break;
                }
            }
        });
        AlertDialog alertDialog =dialog.create();
        alertDialog.show();
//        alertDialog.getWindow().setLayout(800, 500);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SetImageUtility.TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    Intent intent =imageUtility.scalePhoto();
                    startActivityForResult(intent,
                            SetImageUtility.CROP_PHOTO);
                }
                break;
            case SetImageUtility.CROP_PHOTO :
                if (resultCode == RESULT_OK ) {
                    Bitmap bitmap=imageUtility.setImage();
                    if (bitmap!=null){
                        circleImageView.setImageBitmap(bitmap);
                    }
                }
                break;
            case SetImageUtility.CHOOSE_PHOTO:
                if (resultCode == RESULT_OK && data != null) {
                    Intent intent =imageUtility.cropPhoto(data);
                    startActivityForResult(intent,
                            SetImageUtility.CROP_PHOTO);
                }
                break;
            default:
                break;
        }
    }
    private void initData() {
        DrawerData a= new DrawerData(R.drawable.ic_mood_black_24dp,R.string.item_weather);
        mData.add(a);
        DrawerData b = new DrawerData(R.drawable.ic_wb_sunny_black_24dp,R.string.item_query);
        mData.add(b);
        DrawerData c = new DrawerData(R.drawable.ic_grain_black_24dp,R.string.item_air_quality);
        mData.add(c);
        DrawerData d= new DrawerData(R.drawable.ic_place_black_24dp,R.string.item_address);
        mData.add(d);
        DrawerData e = new DrawerData(R.drawable.ic_settings_black_24dp,R.string.item_setting);
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
            case android.R.id.home:
                LogUtility.d("abc","-------------"+getComponentName()+"android.R.id.home press");
                break;
            case R.id.action_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.action_exit:
                finish();
                break;
            case R.id.action_refresh:
//                refresh(item);
                Toast.makeText(this, "1111", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh(MenuItem item){
        ImageView imageView = (ImageView) getLayoutInflater().inflate(R.layout.layout_refresh_view, null);
        Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.refresh_action);
        item.setActionView(imageView);
        imageView.startAnimation(rotation);

//        item.getActionView().clearAnimation();
//        item.setActionView(null);
    }

}
