package com.app.wuyang.myweather.asynctask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.activity.MainActivity;
import com.app.wuyang.myweather.activity.SplashActivity;
import com.app.wuyang.myweather.data.AirQualityCity;
import com.app.wuyang.myweather.data.AllCityOfChina;
import com.app.wuyang.myweather.db.AirQualityHelper;
import com.app.wuyang.myweather.db.AllCityOfChinaHelper;
import com.app.wuyang.myweather.utility.ConnectUtils;
import com.app.wuyang.myweather.utility.LogUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyang on 16-1-7.
 * 处理有空气质量信息的城市并存入数据库；
 *
 */
public class HandleCityTask extends AsyncTask<String, Void, Void> {
    private final static String URI_CITY=
            "http://www.pm25.in/api/querys.json?token=5j1znBVAsnSf5xQyNQyq";
    private Context mContext;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private static final int SHOW_TIME=2000;
    public HandleCityTask(Context context,ProgressBar progressBar,TextView textView) {
        this.mContext = context;
        this.mProgressBar=progressBar;
        this.mTextView=textView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.VISIBLE);
        LogUtility.d("abc", "显示进度条。。。");
    }

    @Override
    protected void onPostExecute(Void mVoid) {
        super.onPostExecute(mVoid);
        mProgressBar.setVisibility(View.GONE);
        mTextView.setVisibility(View.GONE);
        LogUtility.d("abc", "隐藏进度条。。。");
    }



    @Override
    protected Void doInBackground(String... params) {
        long startTime=System.currentTimeMillis();
        String data=params[0];
        saveAllCityOfChina(data);
        saveAirQualityCity(URI_CITY);
        long endTime = System.currentTimeMillis();
        LogUtility.d("abc","在doInBackground 执行共耗时："
                +(endTime-startTime)+"ms,需要睡眠："
                +(SHOW_TIME-(endTime-startTime))+"ms");

        if ((endTime-startTime)<SHOW_TIME){
            try {
                Thread.sleep(SHOW_TIME-(endTime-startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }




    private void saveAirQualityCity(String urlPath) {
        AirQualityHelper helper = AirQualityHelper.getInstance(mContext);
        List<String> allCityList;
        allCityList = helper.loadCity();
        if (allCityList!=null) {
            LogUtility.d("abc", "说明数据库已经存入allcity，以后不会再联网请求 " + allCityList.size());
        } else {
            LogUtility.d("abc", "说明没有数据。。。。从pm25.in网站读取并存入数据库");
            ConnectUtils connectUtils =new ConnectUtils();
            String jsonData = connectUtils.handleJson(urlPath);
            if (jsonData!=null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    if (jsonObject.has("cities")) {

//                        --------------------格式--------------------------
                        JSONArray cityArray =jsonObject.getJSONArray("cities");
                        List<AirQualityCity> cityList =new ArrayList<>();
                        for (int i = 0; i < cityArray.length(); i++) {
                            AirQualityCity airQualityCity = new AirQualityCity();
                            airQualityCity.setName(cityArray.getString(i));
                            cityList.add(airQualityCity);
                        }
//                        --------------------------------------------------
//                        String cityArray = jsonObject.getString("cities");
//
//                        int end = cityArray.length();
//                        String ss = cityArray.substring(1, end - 1);
//                        String[] cities = ss.split(",");
//                        LogUtility.d("abc", "请求得到的所有city 共有" + cities.length);
//                        List<AirQualityCity> cityList =new ArrayList<>();
//                        for (String city : cities) {
////                            重要：airQualityCity的声明必须放在for循环内，
////                            否则最后一条记录会覆盖前面所有数据
//                            AirQualityCity airQualityCity = new AirQualityCity();
//                            airQualityCity.setName(city);
//                            cityList.add(airQualityCity);
//                        }
                        LogUtility.d("abc", "请求得到的所有city 共有" + cityArray.length());
                        helper.saveCity(cityList);
                    }else {
                        LogUtility.d("abc", "说明有错误，如次数用完。。。。获得有空气质量数据的城市失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                LogUtility.d("abc", "说明传入的数据为空。。。获得有空气质量数据的城市失败");
            }
        }
    }


    private void saveAllCityOfChina(String jsonData){
            if (jsonData!=null){
                try {
                    AllCityOfChinaHelper helper =new AllCityOfChinaHelper(mContext);
                    JSONArray jsonArray = new JSONArray(jsonData);
                    List<AllCityOfChina> chinaList =new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
//                            重要：allCityOfChina的声明必须放在for循环内，
//                            否则最后一条记录会覆盖前面所有数据
                        AllCityOfChina allCityOfChina =new AllCityOfChina();
                        JSONObject object =jsonArray.getJSONObject(i);
                        allCityOfChina.setAreaId(object.getLong("area_id"));
                        allCityOfChina.setCounty(object.getString("name_county"));
                        allCityOfChina.setCity(object.getString("name_city"));
                        allCityOfChina.setProvince(object.getString("name_province"));
                        chinaList.add(allCityOfChina);
                    }
                    helper.saveAllCityOfChina(chinaList);
                    LogUtility.d("abc","获得的所有省市县的数据的长度"+jsonArray.length());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                LogUtility.d("abc","说明数据库allcityofchina已经存在 不用再存入数据库");
            }
    }

}
