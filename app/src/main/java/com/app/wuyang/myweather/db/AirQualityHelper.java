package com.app.wuyang.myweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.wuyang.myweather.data.AirQuality;
import com.app.wuyang.myweather.data.AirQualityCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyang on 16-1-7.
 * 保存和查询 有空气质量信息的所有城市的名字；
 * 保存和查询和更新 空气质量信息；
 */
public class AirQualityHelper {

    private static AirQualityHelper helper;
    private SQLiteDatabase db;

    private AirQualityHelper(Context context) {
        AirQualityDbHelper dbHelper=new AirQualityDbHelper(context);
        db=dbHelper.getWritableDatabase();
    }
    public synchronized static AirQualityHelper getInstance(Context context){
        if (helper ==null){
            helper=new AirQualityHelper(context);
        }
        return helper;
    }

    public void saveCity(List<AirQualityCity> cityList){
        if (cityList !=null){
            db.beginTransaction();
            ContentValues values =new ContentValues();
            for (AirQualityCity city:cityList){
                values.put("city",city.getName());
                db.insert("allcity",null,values);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    public List<String> loadCity(){
        Cursor cursor =db.query("allcity",null,null,null,null,null,null);
        List<String> strings = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                strings.add(cursor.getString(cursor.getColumnIndex("city")));
            }while (cursor.moveToNext());
            cursor.close();
            return strings;
        }
        return null;
    }


    public void saveAirQuality(AirQuality airQuality){
        if (airQuality!=null){
            ContentValues values =new ContentValues();
            values.put("city",airQuality.getCity());
            values.put("co",airQuality.getCo());
            values.put("no2",airQuality.getNo2());
            values.put("so2",airQuality.getSo2());
            values.put("o3",airQuality.getO3());
            values.put("pm10",airQuality.getPm10());
            values.put("pm25",airQuality.getPm25());
            values.put("pollutant",airQuality.getPollutant());
            values.put("quality",airQuality.getQuality());
            values.put("time",airQuality.getTime());
            db.insert("air_quality", null, values);
        }
    }
    public void upDateAirQuality(AirQuality airQuality){
        if (airQuality!=null){
            ContentValues values =new ContentValues();
            values.put("city",airQuality.getCity());
            values.put("co",airQuality.getCo());
            values.put("no2",airQuality.getNo2());
            values.put("so2",airQuality.getSo2());
            values.put("o3",airQuality.getO3());
            values.put("pm10",airQuality.getPm10());
            values.put("pm25",airQuality.getPm25());
            values.put("pollutant",airQuality.getPollutant());
            values.put("quality",airQuality.getQuality());
            values.put("time",airQuality.getTime());
            db.update("air_quality", values, null, null);
        }
    }

    public List<AirQuality> loadAirQuality(){
        List<AirQuality> airQualities = new ArrayList<>();
        Cursor cursor = db.query("air_quality",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                AirQuality airQuality = new AirQuality();
                airQuality.setId(cursor.getInt(cursor.getColumnIndex("id")));
                airQuality.setCity(cursor.getString(cursor.getColumnIndex("city")));
                airQuality.setCo(cursor.getInt(cursor.getColumnIndex("co")));
                airQuality.setNo2(cursor.getInt(cursor.getColumnIndex("no2")));
                airQuality.setSo2(cursor.getInt(cursor.getColumnIndex("so2")));
                airQuality.setO3(cursor.getInt(cursor.getColumnIndex("o3")));
                airQuality.setPm10(cursor.getInt(cursor.getColumnIndex("pm10")));
                airQuality.setPm25(cursor.getInt(cursor.getColumnIndex("pm25")));
                airQuality.setPollutant(cursor.getString(cursor.getColumnIndex("pollutant")));
                airQuality.setQuality(cursor.getString(cursor.getColumnIndex("quality")));
                airQuality.setTime(cursor.getString(cursor.getColumnIndex("time")));
                airQualities.add(airQuality);
            } while (cursor.moveToNext());
            cursor.close();
            return airQualities;
        } else {
            return null;
        }
    }




}
