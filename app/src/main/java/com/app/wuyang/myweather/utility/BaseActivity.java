package com.app.wuyang.myweather.utility;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyang on 16-1-3.
 */
public class BaseActivity extends AppCompatActivity {

    private final static String BASEACTIVITY ="baseactivity";
    private static List<AppCompatActivity> activities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(BASEACTIVITY, getClass().getSimpleName());
        addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    private static void addActivity(AppCompatActivity activity) {
        activities.add(activity);
    }

    private static void removeActivity(AppCompatActivity activity){
        activities.remove(activity);

    }

    private static void finishAll() {
        for (AppCompatActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
