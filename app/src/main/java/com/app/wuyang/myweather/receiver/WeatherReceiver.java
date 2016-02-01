package com.app.wuyang.myweather.receiver;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.app.wuyang.myweather.activity.DetailActivity;
import com.app.wuyang.myweather.db.DbQuery;
import com.app.wuyang.myweather.service.WeatherService;
import com.app.wuyang.myweather.service.TimerService;
import com.app.wuyang.myweather.utility.CheckConnect;
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
    private CheckConnect checkConnect;

    @Override
    public void onReceive(Context context, Intent intent) {
        checkConnect=new CheckConnect(context);
        if (intent.getAction().equals("WEATHER_RECEIVER_NOTIFICATION")) {
            if (checkConnect.isConnect()){
                dbQuery =new DbQuery(context);
                manager= (NotificationManager) context.getSystemService(
                        Context.NOTIFICATION_SERVICE);

                LogUtility.d("abc", "----说明数据更新了------准备发出notification");
                initData();
                String quality=dbQuery.getAirQualityContent(DbQuery.QUALITY);
                if (qualityList.contains(quality)){
                    LogUtility.d("abc", "-------WeatherReceiver---发出notification成功");
                    startQualityNotification(context);
                }
                LogUtility.d("abc", "-------------准备进入TimerService 开始下一个循环");
                Intent intentService =new Intent(context, TimerService.class);
                intent.putExtra("enable",true);
                context.startService(intentService);
            } else {
                LogUtility.d("abc", "---------没有网----准备进入TimerService 开始下一个循环");
                Intent intentService =new Intent(context, TimerService.class);
                intent.putExtra("enable",true);
                context.startService(intentService);
            }

        }else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            LogUtility.d("abc", "-----手机开机了------准备进入-------WeatherService");
            Intent serviceIntent =new Intent(context,WeatherService.class);
            context.startService(serviceIntent);
        } else if (intent.getAction().equals("WEATHER_RECEIVER_NO_NOTIFICATION")){
            LogUtility.d("abc", "-----没有更新数据------准备进入-------WeatherService");
            Intent serviceIntent =new Intent(context,WeatherService.class);
            context.startService(serviceIntent);
        }else {
            LogUtility.d("abc","nothing to do in weatherReceiver--------------");
        }

    }
    private void initData(){
        qualityList =new ArrayList<>();
        qualityList.add(DbQuery.POLLUTION_LEVEL2);
        qualityList.add(DbQuery.POLLUTION_LEVEL3);
        qualityList.add(DbQuery.POLLUTION_LEVEL4);
        qualityList.add(DbQuery.POLLUTION_LEVEL5);
        qualityList.add(DbQuery.POLLUTION_LEVEL6);
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void startQualityNotification(Context context) {

        Intent intentDetail =new Intent(context, DetailActivity.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(context, 0,
                intentDetail, PendingIntent.FLAG_CANCEL_CURRENT);

        String quality =dbQuery.getAirQualityContent(DbQuery.QUALITY);
        notification=new Notification.Builder(context)
                .setOnlyAlertOnce(true)
                .setDefaults(-1)
                .setAutoCancel(true)
                .setContentTitle(dbQuery.getTodayWeatherContent(DbQuery.WEATHER_DAY)
                +"     "+dbQuery.getAirQualityLevel(quality))
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
