package com.app.wuyang.myweather.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by wuyang on 16-1-25.
 */
public class WeatherService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WeatherService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
