package com.app.wuyang.myweather.utility;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by wuyang on 16-1-3.
 * 提供屏幕尺寸的工具类
 */
public class ScreenUtility {

    public static DisplayMetrics getScreenSize(Context context){
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm  = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }


    public static float getDeviceDensity(Context context){
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }
}
