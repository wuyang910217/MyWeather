package com.app.wuyang.myweather;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.storage.StorageManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.app.wuyang.myweather.utility.LocationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wuyang on 16-1-1.
 */
public class DetailActivity extends AppCompatActivity implements AMapLocationListener {
    private TextView mTv,cityName;
    private AMapLocation mapLocation;
    private AMapLocationClient mapLocationClient;
    private AMapLocationClientOption mapLocationClientOption;
    private final static String URI_CITY=
            "http://www.pm25.in/api/querys.json?token=5j1znBVAsnSf5xQyNQyq";

    private HttpURLConnection httpURLConnection;
    private final static int CITY_MSG=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail);
        cityName= (TextView) findViewById(R.id.allcity);

        sendRequestWithHttpClient();
    }

    private void sendRequestWithHttpClient(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL(URI_CITY);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();

                    InputStream inputStream=httpURLConnection.getInputStream();
                    StringBuffer buffer=new StringBuffer();
                    if (inputStream==null){
                        return;
                    }
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    String  line=null;
                    while ((line = reader.readLine())!= null){
                        buffer.append(line+"\n");
                    }
                    if (buffer.length()!=0){

                        String jsonData=buffer.toString();
                        JSONObject jsonObject=new JSONObject(jsonData);
                        String cityArray=jsonObject.getString("cities");

                        Message msg = new Message();
                        msg.what=CITY_MSG;
                        msg.obj=cityArray;
                        mHandler.sendMessage(msg);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void locationShow(View view){
        mTv = (TextView) findViewById(R.id.location);

        initLocationClient();
    }


    private void initLocationClient(){
        mapLocationClientOption=new AMapLocationClientOption();
        mapLocationClientOption.setLocationMode(
                AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mapLocationClient =new AMapLocationClient(getApplicationContext());
        mapLocationClient.setLocationListener(this);

        mapLocationClient.setLocationOption(mapLocationClientOption);
        mapLocationClient.startLocation();
        mHandler.sendEmptyMessage(LocationUtils.MSG_LOCATION_START);
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation!=null){
            Message msg=mHandler.obtainMessage();
            msg.obj=aMapLocation;
            msg.what= LocationUtils.MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);

        }

    }
    private final  Handler mHandler = new Handler() {

        public void handleMessage(Message msg){
            switch (msg.what){
                case LocationUtils.MSG_LOCATION_START:
                    mTv.setText("start...");
                    break;
                case LocationUtils.MSG_LOCATION_FINISH:
                    mapLocation = (AMapLocation) msg.obj;
                    String currentLocation = LocationUtils.getLocationStr(mapLocation);
                    mTv.setText(currentLocation);
                    break;
                case CITY_MSG:
                    String cityOfName = (String) msg.obj;
                    int end=cityOfName.length();
                    String ss=cityOfName.substring(1,end-1);
                    String[] strings=ss.split(",");
                    StringBuffer stringBuffer =new StringBuffer();
                    for (int i = 0; i < 5; i++) {
                        stringBuffer.append(strings[i]+",");
                        Log.d("www", strings[i]);
                    }
                    String s=stringBuffer.toString();
                    cityName.setText(s);
                    Log.d("www",""+strings.length);
                default:
                    break;
            }

        }

    };


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
