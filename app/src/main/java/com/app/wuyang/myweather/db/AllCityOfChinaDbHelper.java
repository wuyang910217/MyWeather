package com.app.wuyang.myweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wuyang on 16-1-11.
 * 数据库：中国所有省市县 一个表
 */
public class AllCityOfChinaDbHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME_CITIES = "allCityOfChina";
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="allCityOfChina.db";


    public AllCityOfChinaDbHelper(Context context) {
        super(context, DbDir.getDbName(context,DATABASE_NAME), null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_CITIES_TABLE = "CREATE TABLE "
                + TABLE_NAME_CITIES
                + "("
                + "id integer primary key autoincrement,"
                + "areaId integer,"
                + "county text,"
                + "city text,"
                + "province text"
                + ");";


        db.execSQL(SQL_CREATE_CITIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
