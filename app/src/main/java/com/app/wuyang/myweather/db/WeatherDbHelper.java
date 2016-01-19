package com.app.wuyang.myweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wuyang on 16-1-11.
 * 数据库 天气信息和天气指数 两个表
 */
public class WeatherDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="weather.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME_WEATHER_INFO="weatherInfo";
    private static final String TABLE_NAME_WEATHER_INDEX="weatherIndex";

    public WeatherDbHelper(Context context) {
        super(context, DbDir.getDbName(context,DATABASE_NAME), null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_TABLE_WEATHER_INFO ="CREATE TABLE "
                + TABLE_NAME_WEATHER_INFO
                + "("
                + "id integer primary key autoincrement,"
                + "publish_time text,"
                + "weather_day text,"
                + "weather_night text,"
                + "temperature_day text,"
                + "temperature_night text,"
                + "wind_direction_day text,"
                + "wind_direction_night text,"
                + "wind_power_day text,"
                + "wind_power_night text,"
                + "sun_time text"
                + ");";

        final String SQL_CREATE_TABLE_WEATHER_INDEX ="CREATE TABLE "
                + TABLE_NAME_WEATHER_INDEX
                + "("
                + "id integer primary key autoincrement,"
                + "weatherIndex text,"
                + "weatherIndexLevel text,"
                + "weatherIndexInfo text"
                + ");";


        db.execSQL(SQL_CREATE_TABLE_WEATHER_INFO);
        db.execSQL(SQL_CREATE_TABLE_WEATHER_INDEX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
