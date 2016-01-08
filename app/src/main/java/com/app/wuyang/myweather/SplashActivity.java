package com.app.wuyang.myweather;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.app.wuyang.myweather.sync.HandleCityTask;


public class SplashActivity extends AppCompatActivity {

    private MyHandler myHandler;
    private HandleCityTask task;
    private final static String URI_CITY=
            "http://www.pm25.in/api/querys.json?token=5j1znBVAsnSf5xQyNQyq";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_splash);


        task=new HandleCityTask(getApplicationContext());
        task.execute(URI_CITY);
        myHandler =new MyHandler();
        myHandler.sendEmptyMessageDelayed(0, 3000);


    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
