package com.app.wuyang.myweather.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.asynctask.HandleCityTask;
import com.app.wuyang.myweather.asynctask.HandleLocationTask;
import com.app.wuyang.myweather.db.AllCityOfChinaHelper;
import com.app.wuyang.myweather.utility.CheckConnect;
import com.app.wuyang.myweather.utility.ConnectUtils;
import com.app.wuyang.myweather.utility.LogUtility;

import java.util.List;

//主activity，展示app启动时的画面，并后台处理有关城市信息，存入数据库；



public class SplashActivity extends AppCompatActivity implements AMapLocationListener{
    public ProgressBar progressBar;
    public TextView loading;
    private AMapLocationClient mapLocationClient;
    private AMapLocationClientOption mapLocationClientOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_splash);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        loading = (TextView) findViewById(R.id.loading);

        mHandler.sendEmptyMessageDelayed(0, 3000);

        CheckConnect checkConnect = new CheckConnect(getApplicationContext());
        if (!checkConnect.isConnect()){
            Toast.makeText(SplashActivity.this,
                    "网络连接失败，请确认网络连接",Toast.LENGTH_SHORT).show();
        } else {
            AllCityOfChinaHelper helper =new AllCityOfChinaHelper(getApplicationContext());
            List<String> strings =helper.loadAllInfo();
            String data;
            if (strings.size()==0){
                LogUtility.d("abc", "说明省市县数据库没有数据。。。。从本地文件读取并存入数据库");
                ConnectUtils connectUtils=new ConnectUtils();
                data= connectUtils.handleJsonFromFile(getApplicationContext());
            } else {
                LogUtility.d("abc","说明省市县数据库有数据。。。。以后不用再读取"+strings.size()+"行");
                data=null;
            }

            initLocationClient();

            HandleCityTask task = new HandleCityTask(getApplicationContext(),progressBar,loading);
            task.execute(data);
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
            HandleLocationTask task =new HandleLocationTask(getApplicationContext());
            task.execute(aMapLocation);
            mapLocationClient.stopLocation();
        }

    }


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    };

//    此时按后退键不会返回 也就是不能退出app
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapLocationClient!= null){
            mapLocationClient.onDestroy();
            mapLocationClient=null;
            mapLocationClientOption=null;
        }
    }

}
