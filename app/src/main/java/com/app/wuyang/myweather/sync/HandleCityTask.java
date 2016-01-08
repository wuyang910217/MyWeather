package com.app.wuyang.myweather.sync;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.app.wuyang.myweather.data.AllCity;
import com.app.wuyang.myweather.db.AirQualityHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wuyang on 16-1-7.
 */
public class HandleCityTask extends AsyncTask<String, Void, AllCity> {
    private HttpURLConnection connection;
    private URL url;
    private InputStream is;
    private StringBuffer sb;
    private Context mContext;
    private AirQualityHelper helper;
    private AllCity allCity;

    public HandleCityTask(Context context) {
        mContext = context;
    }
    @Override
    protected void onPostExecute(AllCity allCity) {
        if (allCity!= null){
            Log.d("abc",allCity.toString());
        }
    }

    @Override
    protected AllCity doInBackground(String... params) {
        String urlPath=params[0];
        try {
            url =new URL(urlPath);
            connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(4000);
            connection.connect();

            is=connection.getInputStream();
            sb=new StringBuffer();
            if (is == null){
                Toast.makeText(mContext,"查询失败,请查看网络连接！",Toast.LENGTH_SHORT).show();
                return null;
            }
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine())!= null){
                sb.append(line);
            }
            if (sb.length()!=0){

                String jsonData=sb.toString();
                JSONObject jsonObject=new JSONObject(jsonData);
                if (jsonObject.has("cities")){
                    String cityArray=jsonObject.getString("cities");
                    int end=cityArray.length();
                    String ss=cityArray.substring(1, end-1);
                    String[] cities=ss.split(",");
                    helper = AirQualityHelper.getInstance(mContext);
                    allCity =new AllCity();
                    for (String city : cities) {
                        allCity.setName(city);
                        helper.saveCity(allCity);
                    }
                } else {
                    Toast.makeText(mContext,"查询失败，请稍后再试！",Toast.LENGTH_SHORT).show();
                    return null;
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return allCity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

}
