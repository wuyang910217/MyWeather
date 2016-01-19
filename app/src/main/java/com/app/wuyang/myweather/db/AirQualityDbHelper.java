package com.app.wuyang.myweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by wuyang on 16-1-5.
 * 数据库 包括两个表 空气质量信息  和   有空气质量信息的城市；
 */
public class AirQualityDbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION=2;
    static final String DATABASE_NAME="airquality.db";
    private static final String TABLE_NAME_ALL_CITY = "allcity";
    private static final String TABLE_NAME_AIR_QUALITY = "air_quality";
    private static final String TABLE_NAME_CITIES = "city";
    private static final String TABLE_NAME_WEATHER = "weather";

//    public AirQualityDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    public AirQualityDbHelper(Context context) {
        super(context,DbDir.getDbName(context,DATABASE_NAME),null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_AIR_QUALITY_TABLE = "CREATE TABLE "
                + TABLE_NAME_AIR_QUALITY
                + "("
                + "id integer primary key autoincrement,"
                + "city text,"
                + "co real,"
                + "no2 real,"
                + "so2 real,"
                + "o3 real,"
                + "pm10 real,"
                + "pm25 real,"
                + "pollutant text,"
                + "quality text,"
                + "time text"
                + ");";


        final String SQL_CREATE_ALL_CITY_TABLE = "CREATE TABLE "
                + TABLE_NAME_ALL_CITY
                + "("
                + "id integer primary key autoincrement,"
                + "city text not null"
                + ");";


        db.execSQL(SQL_CREATE_AIR_QUALITY_TABLE);
        db.execSQL(SQL_CREATE_ALL_CITY_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_AIR_QUALITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ALL_CITY);
        onCreate(db);
    }
}
