package com.app.wuyang.myweather.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.ActionMenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private List<DrawerData> mData= new ArrayList<>();
    private ListView mDrawerList;

    private TextView showTodayDate,showCityName,showQuality,showPm25,
            showPm10,showWeather,showTemp,showWind,showDetail;
    private ImageView showImage;
    private CircleImageView circleImageView;

    public static final String DETAIL_ACTION_TODAY="DETAIL_ACTION_TODAY";
    public static final String DETAIL_ACTION_OTHER="DETAIL_ACTION_OTHER";
    private static final String pollutionLevel1="优";
    private static final String pollutionLevel2="良";
    private static final String pollutionLevel3="轻度污染";
    private static final String pollutionLevel4="中度污染";
    private static final String pollutionLevel5="重度污染";
    private static final String pollutionLevel6="严重污染";

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO =2;
    public static final int CHOOSE_PHOTO =3;

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

        mDrawerList = (ListView) findViewById(R.id.item_list);
        mDrawerList.setAdapter(new DrawerAdapter(
                MainActivity.this,
                R.layout.layout_drawer_item,
                mData));

        mDrawerList.setOnItemClickListener(this);


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

        setImage();
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

    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view, int position, long id) {
        DrawerData drawerData =mData.get(position);
        switch (drawerData.getItemName()){
            case R.string.item_weather:
                Toast.makeText(this,"别点我...",Toast.LENGTH_SHORT).show();
                break;
            case R.string.item_address:
                Toast.makeText(this,"别点我...",Toast.LENGTH_SHORT).show();
                break;
            case R.string.item_good:
                Toast.makeText(this,"别点我...",Toast.LENGTH_SHORT).show();
                break;
            case R.string.item_share:
                Toast.makeText(this,"别点我...",Toast.LENGTH_SHORT).show();
                break;
            case R.string.item_setting:
                Toast.makeText(this,"别点我...",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public class showUiTask extends AsyncTask<Void,Void,Void>{
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
        circleImageView = (CircleImageView) findViewById(R.id.circleImage);
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
            showImage.setImageResource(R.drawable.weather_undefined);
        }else {
            String weather =weatherInfoList.get(0).getWeather_day();
            String windPower =weatherInfoList.get(0).getWind_power_day();
            String windDirection =weatherInfoList.get(0).getWind_direction_day();

            if (weather==null){
                showWeather.setText("无数据");
            }else {
                showWeather.setText(transformUtils.tranWeather(weather));
                showImage.setImageResource(transformUtils.tranWeatherIcon(weather));
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
    public void setImage(){
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(imageUri()));
            circleImageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
                        chooseFromAlbum();
                        break;
                    case 1:
                        takePhoto();
                }
            }
        });
        AlertDialog alertDialog =dialog.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(750, 520);
    }
    private Uri imageUri(){
        String outputImageDir = Environment.
                getExternalStorageDirectory()+ File.separator+"Android"+File.separator
                +"data"+File.separator+getPackageName();
        String fileName ="temp.jpg";
        File outputImage =new File(outputImageDir);
        if (!outputImage.exists()) {
            outputImage.mkdirs();
        }
        File imageUri=new File(outputImageDir,fileName);
        return Uri.fromFile(imageUri);
    }

    private void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri());
        startActivityForResult(intent, TAKE_PHOTO);

    }

    private void chooseFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri(),"image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri());
                    startActivityForResult(intent,CROP_PHOTO);
                }
                break;
            case CROP_PHOTO :
                if (resultCode == RESULT_OK ) {
                    Bitmap bitmap= null;
                    try {
                        bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(imageUri()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    circleImageView.setImageBitmap(bitmap);

                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK && data != null) {
                    //从系统中选取的图片的Uri地址
                    Uri getImageUri = data.getData();
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(getImageUri,"image/*");
                    intent.putExtra("scale", true);
                    //输出到imageUri中，如果不改 说明覆盖了选取的图片的地址 经过剪切后 变小了
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri());
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            default:
                break;
        }
    }
    private void initData() {
        DrawerData a= new DrawerData(R.drawable.ic_wb_sunny_red_24dp,R.string.item_weather);
        mData.add(a);
        DrawerData b= new DrawerData(R.drawable.ic_place_black_36dp,R.string.item_address);
        mData.add(b);
        DrawerData c = new DrawerData(R.drawable.ic_mood_black_36dp,R.string.item_good);
        mData.add(c);
        DrawerData d = new DrawerData(R.drawable.ic_thumb_up_black_36dp,R.string.item_share);
        mData.add(d);
        DrawerData e = new DrawerData(R.drawable.ic_settings_black_36dp,R.string.item_setting);
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
//                ActionMenuView action_refresh= (ActionMenuView) findViewById(R.id.action_refresh);
                Toast.makeText(this, "1111", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
