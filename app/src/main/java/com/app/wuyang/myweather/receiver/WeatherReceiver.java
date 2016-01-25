package com.app.wuyang.myweather.receiver;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.app.wuyang.myweather.activity.DetailActivity;
import com.app.wuyang.myweather.asynctask.HandleAirQualityAndWeatherTask;
import com.app.wuyang.myweather.asynctask.HandleLocationTask;
import com.app.wuyang.myweather.db.DbQuery;
import com.app.wuyang.myweather.service.NotificationService;
import com.app.wuyang.myweather.utility.LogUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyang on 16-1-15.
 */
public class WeatherReceiver extends BroadcastReceiver{
    private NotificationManager manager;
    private Notification notification;
    private DbQuery dbQuery;
    private List<String> qualityList;


    @Override
    public void onReceive(Context context, Intent intent) {
        dbQuery =new DbQuery(context);
        manager= (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        HandleAirQualityAndWeatherTask task =new HandleAirQualityAndWeatherTask(context);
        task.execute();
        if (intent.getBooleanExtra("enable",true)){
            initData();
            String quality=dbQuery.getAirQualityContent(DbQuery.QUALITY);
            if (qualityList.contains(quality)){
                try {
                    Thread.sleep(2000);
                    LogUtility.d("abc","----------------------------WeatherReceiver");
                    startQualityNotification(context);
                    Intent intentService =new Intent(context, NotificationService.class);
                    context.startService(intentService);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    private void initData(){
        qualityList =new ArrayList<>();
        qualityList.add(DbQuery.POLLUTION_LEVEL4);
        qualityList.add(DbQuery.POLLUTION_LEVEL5);
        qualityList.add(DbQuery.POLLUTION_LEVEL6);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void startQualityNotification(Context context) {

        Intent intentDetail =new Intent(context, DetailActivity.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(context, 0,
                intentDetail, PendingIntent.FLAG_CANCEL_CURRENT);

        notification=new Notification.Builder(context)
                .setOnlyAlertOnce(true)
                .setDefaults(-1)
                .setAutoCancel(true)
                .setContentTitle(dbQuery.getTodayWeatherContent(DbQuery.WEATHER_DAY)
                +"     "+dbQuery.getAirQualityLevel(DbQuery.QUALITY))
                .setContentText(dbQuery.getAirQualityContent(DbQuery.TIME))
                .setSmallIcon(dbQuery.getWeatherImage())
                .setWhen(System.currentTimeMillis())
                .setTicker("新消息...")
                .setContentIntent(pendingIntent)
                .build();
        manager.notify(1, notification);
    }

    public WeatherReceiver() {
        super();
    }



}
