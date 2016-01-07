package com.app.wuyang.myweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.wuyang.myweather.data.WeatherInfo.AirQualityCityEntry;
import com.app.wuyang.myweather.data.WeatherInfo.AirQualityEntry;

/**
 * Created by wuyang on 16-1-5.
 */
public class MyWeatherDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    static final String DATABASE_NAME="MyWeather.db";

//    public MyWeatherDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    public MyWeatherDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_AIR_QUALITY_TABLE = "CREATE TABLE"
                + AirQualityEntry.TABLE_NAME
                + "("
                + "id integer primary key autoincrement,"
                + AirQualityEntry.COLUMN_CITY + "text not null,"
                + AirQualityEntry.COLUMN_CO + "real not null,"
                + AirQualityEntry.COLUMN_NO2 + "real not null,"
                + AirQualityEntry.COLUMN_SO2 + "real not null,"
                + AirQualityEntry.COLUMN_O3 + "real not null,"
                + AirQualityEntry.COLUMN_PM10 + "real not null,"
                + AirQualityEntry.COLUMN_PM25 + "real not null,"
                + AirQualityEntry.COLUMN_PRIMARY_POLLUTANT + "text not null,"
                + AirQualityEntry.COLUMN_AIRQUALITY + "text not null,"
                + AirQualityEntry.COLUMN_TIME + "text not null,"
                + ");";


        final String SQL_CREATE_AIR_QUALITY_CITY_TABLE = "CREATE TABLE"
                + AirQualityCityEntry.TABLE_NAME
                + "("
                + "id integer primary key autoincrement,"
                + AirQualityEntry.COLUMN_CITY + "text not null,"
                + ");";


        db.execSQL(SQL_CREATE_AIR_QUALITY_TABLE);
        db.execSQL(SQL_CREATE_AIR_QUALITY_CITY_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + AirQualityEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + AirQualityCityEntry.TABLE_NAME);
        onCreate(db);
    }
}
