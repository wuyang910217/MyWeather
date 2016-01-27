package com.app.wuyang.myweather.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.app.wuyang.myweather.receiver.WeatherReceiver;
import com.app.wuyang.myweather.utility.LogUtility;

/**
 * Created by wuyang on 16-1-25.
 */
public class TimerService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtility.d("abc","-------TimerService------------计时------准备启动WeatherReceiver");
        timeTask();
        flags=START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    private void timeTask(){
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour= 2*60* 60 * 1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;

        Intent intentReceiver = new Intent("WEATHER_RECEIVER_NO_NOTIFICATION");
        PendingIntent pi =PendingIntent.getBroadcast(this,0,intentReceiver,0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
