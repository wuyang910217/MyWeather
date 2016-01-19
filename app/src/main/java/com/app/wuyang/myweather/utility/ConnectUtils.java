package com.app.wuyang.myweather.utility;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wuyang on 16-1-14.
 * 处理json数据    从本地  从网络
 */
public class ConnectUtils {

    private HttpURLConnection connection;




    public String handleJson(String urlPath){
        try {
            URL url = new URL(urlPath);
            connection =(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Accept-Charset", "utf-8");
//            connection.setRequestProperty("contentType", "utf-8");
            connection.setConnectTimeout(3000);
            connection.connect();

            if (connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream is = connection.getInputStream();
                StringBuilder sb = new StringBuilder();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                LogUtility.d("abc", "测试 说明  从网络解析成数据流 准备返回");
                return sb.toString();
            } else {
                LogUtility.d("abc", "测试 说明  从网络解析成数据流 出错 response!=OK");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (connection!=null){
                connection.disconnect();
            }
        }
        LogUtility.d("abc", "测试 说明  从网络解析成数据流  返回的为null....");
        return null;
    }

    public String handleJsonFromFile(Context context){
        String jsonData;
        try {
            InputStream is = context.getResources().getAssets().open("allcity.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            jsonData = new String(buffer, "utf-8");
            LogUtility.d("abc","从文件解析到数据。。传入task");
            return jsonData;
        } catch (Exception e){
            e.printStackTrace();
        }
        LogUtility.d("abc","从文件解析到数据。。真假测试");
        return null;

    }
}
