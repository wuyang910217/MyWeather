package com.app.wuyang.myweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.app.wuyang.myweather.asynctask.HandleLocationTask;
import com.app.wuyang.myweather.receiver.WeatherReceiver;
import com.app.wuyang.myweather.utility.LogUtility;

/**
 * Created by wuyang on 16-1-25.
 */
public class NotificationService extends Service implements AMapLocationListener{
    private AMapLocationClient mapLocationClient;
    private AMapLocationClientOption mapLocationClientOption;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initLocationClient();
        timeTask();
        LogUtility.d("abc", "NotificationService----------------------start");
        return super.onStartCommand(intent, flags, startId);
    }

    private void timeTask(){
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour= 60 * 60 * 1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;

        Intent intentReceiver = new Intent(this, WeatherReceiver.class);
        intentReceiver.putExtra("enable", true);
        PendingIntent pi =PendingIntent.getBroadcast(this,0,intentReceiver,0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapLocationClient!= null){
            mapLocationClient.onDestroy();
            mapLocationClient=null;
            mapLocationClientOption=null;
        }
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
