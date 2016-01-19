package com.app.wuyang.myweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.wuyang.myweather.data.WeatherIndex;
import com.app.wuyang.myweather.data.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyang on 16-1-11.
 * 保存和查询天气信息和天气指数；
 */
public class WeatherHelper {

    private SQLiteDatabase db;

    public WeatherHelper(Context context) {
        WeatherDbHelper helper = new WeatherDbHelper(context);
        db= helper.getWritableDatabase();
    }


    public void saveWeatherInfo(List<WeatherInfo> infoList){
        if (infoList!=null){
            db.beginTransaction();
            ContentValues values=new ContentValues();
            for (WeatherInfo weatherInfo : infoList){
                values.put("publish_time",weatherInfo.getPublish_time());
                values.put("weather_day",weatherInfo.getWeather_day());
                values.put("weather_night",weatherInfo.getWeather_night());
                values.put("temperature_day",weatherInfo.getTemperature_day());
                values.put("temperature_night",weatherInfo.getTemperature_night());
                values.put("wind_direction_day",weatherInfo.getWind_direction_day());
                values.put("wind_direction_night",weatherInfo.getWind_direction_night());
                values.put("wind_power_day",weatherInfo.getWind_power_day());
                values.put("wind_power_night",weatherInfo.getWind_power_night());
                values.put("sun_time", weatherInfo.getSun_time());
                db.insert("weatherInfo",null,values);
            }
            db.setTransactionSuccessful();
            db.endTransaction();

        }
    }

//      使用db.update()失败，   先删除后 再重新写入；
    public void updateWeatherInfo(List<WeatherInfo> infoList){
        if (infoList!=null){
            db.delete("weatherInfo",null,null);
            saveWeatherInfo(infoList);
//            db.beginTransaction();
//            ContentValues values=new ContentValues();
//            for (int i = 0; i < infoList.size(); i++) {
//                values.put("publish_time", infoList.get(i).getPublish_time());
//                values.put("weather_day",infoList.get(i).getWeather_day());
//                values.put("weather_night",infoList.get(i).getWeather_night());
//                values.put("temperature_day",infoList.get(i).getTemperature_day());
//                values.put("temperature_night",infoList.get(i).getTemperature_night());
//                values.put("wind_direction_day",infoList.get(i).getWind_direction_day());
//                values.put("wind_direction_night",infoList.get(i).getWind_direction_night());
//                values.put("wind_power_day",infoList.get(i).getWind_power_day());
//                values.put("wind_power_night",infoList.get(i).getWind_power_night());
//                values.put("sun_time", infoList.get(i).getSun_time());
////                数据库 行的序号从1开始
//                db.update("weatherInfo",values,"id="+i+1,null);
//            }
//            db.setTransactionSuccessful();
//            db.endTransaction();
        }
    }

    public List<WeatherInfo> loadWeatherInfo(){
        List<WeatherInfo> infoList=new ArrayList<>();
        Cursor cursor =db.query("weatherInfo",null,null,null,null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                WeatherInfo weatherInfo =new WeatherInfo();
                weatherInfo.setPublish_time(cursor.getString(cursor.getColumnIndex("publish_time")));
                weatherInfo.setWeather_day(cursor.getString(cursor.getColumnIndex("weather_day")));
                weatherInfo.setWeather_night(cursor.getString(cursor.getColumnIndex("weather_night")));
                weatherInfo.setTemperature_day(cursor.getString(cursor.getColumnIndex("temperature_day")));
                weatherInfo.setTemperature_night(cursor.getString(cursor.getColumnIndex("temperature_night")));
                weatherInfo.setWind_direction_day(cursor.getString(cursor.getColumnIndex("wind_direction_day")));
                weatherInfo.setWind_direction_night(cursor.getString(cursor.getColumnIndex("wind_direction_night")));
                weatherInfo.setWind_power_day(cursor.getString(cursor.getColumnIndex("wind_power_day")));
                weatherInfo.setWind_power_night(cursor.getString(cursor.getColumnIndex("wind_power_night")));
                weatherInfo.setSun_time(cursor.getString(cursor.getColumnIndex("sun_time")));
                infoList.add(weatherInfo);
            }
            cursor.close();
            return infoList;
        } else {
            return null;
        }
    }


    public void saveWeatherIndex(List<WeatherIndex> indexList){
        if (indexList!=null){
            ContentValues values =new ContentValues();
            for (WeatherIndex weatherIndex : indexList){
                values.put("weatherIndex",weatherIndex.getWeatherIndex());
                values.put("weatherIndexLevel",weatherIndex.getWeatherIndexLevel());
                values.put("weatherIndexInfo",weatherIndex.getWeatherIndexInfo());
                db.insert("weatherIndex",null,values);
            }
        }
    }

    public void updateWeatherIndex(List<WeatherIndex> indexList){
        if (indexList!=null){

            db.delete("weatherIndex",null,null);
            saveWeatherIndex(indexList);
//            ContentValues values =new ContentValues();
//            for (int i = 0; i < indexList.size(); i++) {
//                values.put("weatherIndex", indexList.get(i).getWeatherIndex());
//                values.put("weatherIndexLevel",indexList.get(i).getWeatherIndexLevel());
//                values.put("weatherIndexInfo",indexList.get(i).getWeatherIndexInfo());
//
//                db.update("weatherIndex",values,"id="+i+1,null);
//            }
        }
    }

    public List<WeatherIndex> loadWeatherIndex(){
        List<WeatherIndex> indexList =new ArrayList<>();
        Cursor cursor =db.query("weatherIndex",null,null,null,null,null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                WeatherIndex weatherIndex =new WeatherIndex();
                weatherIndex.setWeatherIndex(cursor.getString(cursor.getColumnIndex("weatherIndex")));
                weatherIndex.setWeatherIndexLevel(cursor.getString(cursor.getColumnIndex("weatherIndexLevel")));
                weatherIndex.setWeatherIndexInfo(cursor.getString(cursor.getColumnIndex("weatherIndexInfo")));
                indexList.add(weatherIndex);
            }
            cursor.close();
            return indexList;
        }
        return null;
    }
}
