package com.app.wuyang.myweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.wuyang.myweather.data.AllCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyang on 16-1-7.
 */
public class AirQualityHelper {

    private static AirQualityHelper helper;
    private SQLiteDatabase db;

    private AirQualityHelper(Context context) {
        MyWeatherDbHelper dbHelper=new MyWeatherDbHelper(context);
        db=dbHelper.getWritableDatabase();
    }
    public synchronized static AirQualityHelper getInstance(Context context){
        if (helper ==null){
            helper=new AirQualityHelper(context);
        }
        return helper;
    }

    public void saveCity(AllCity city){
        if (city !=null){
            ContentValues values =new ContentValues();
            values.put("city",city.getName());
            db.insert("allcity",null,values);
        }
    }

    public List<AllCity> loadCity(){
        List<AllCity> list =new ArrayList<>();
        Cursor cursor =db.query("allcity",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                AllCity city =new AllCity();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setName(cursor.getString(cursor.getColumnIndex("city")));
                list.add(city);
            }  while (cursor.moveToNext());
        }
        return  list;
    }



}
