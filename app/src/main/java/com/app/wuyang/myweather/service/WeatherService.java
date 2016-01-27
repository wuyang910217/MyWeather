package com.app.wuyang.myweather.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.app.wuyang.myweather.asynctask.HandleAirQualityAndWeatherTask;
import com.app.wuyang.myweather.asynctask.HandleLocationTask;
import com.app.wuyang.myweather.utility.CheckConnect;
import com.app.wuyang.myweather.utility.LogUtility;

/**
 * Created by wuyang on 16-1-25.
 */
public class WeatherService extends Service implements AMapLocationListener{
    private AMapLocationClient mapLocationClient;
    private AMapLocationClientOption mapLocationClientOption;
    private CheckConnect checkConnect;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkConnect =new CheckConnect(getApplicationContext());
        if (!checkConnect.isConnect()){
            LogUtility.d("abc", "WeatherService----over---没有网 准备进入weatherReceiver--");
            sendBroadcast(new Intent("WEATHER_RECEIVER_NOTIFICATION"));
        }else {
            HandleAirQualityAndWeatherTask task =new HandleAirQualityAndWeatherTask(getApplicationContext());
            task.execute();
            initLocationClient();

            try {
                Thread.sleep(3000);
                LogUtility.d("abc", "WeatherService----over---准备进入weatherReceiver--");
                sendBroadcast(new Intent("WEATHER_RECEIVER_NOTIFICATION"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapLocationClient!= null){
            mapLocationClient.onDestroy();
            mapLocationClient=null;
            mapLocationClientOption=null;
        }
        LogUtility.d("abc","--------------------------WeatherService destroy ---------");
    }


    private void initLocationClient() {
        mapLocationClientOption = new AMapLocationClientOption();
        mapLocationClientOption.setLocationMode(
                AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mapLocationClient = new AMapLocationClient(getApplicationContext());
        mapLocationClient.setLocationListener(this);
        mapLocationClient.setLocationOption(mapLocationClientOption);
        mapLocationClient.startLocation();
    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            HandleLocationTask task =new HandleLocationTask(this);
            task.execute(aMapLocation);
            mapLocationClient.stopLocation();
        }
    }
}
