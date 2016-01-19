package com.app.wuyang.myweather.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.app.wuyang.myweather.asynctask.HandleLocationTask;
import com.app.wuyang.myweather.utility.LogUtility;

/**
 * Created by wuyang on 16-1-10.
 */
public class LocationService extends IntentService implements AMapLocationListener {
    private Context mContext;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LocationService(String name,Context context) {
        super(name);
       this.mContext=context;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtility.d("abc","intentservice-----------------");
        initLocation();
    }

    private void initLocation(){
        AMapLocationClientOption mapLocationClientOption = new AMapLocationClientOption();
        mapLocationClientOption.setLocationMode(
                AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        AMapLocationClient mapLocationClient = new AMapLocationClient(mContext);
        mapLocationClient.setLocationOption(mapLocationClientOption);
        mapLocationClient.setLocationListener(this);
        mapLocationClient.startLocation();
    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            HandleLocationTask task =new HandleLocationTask(mContext);
            task.execute(aMapLocation);
        }
    }
}
