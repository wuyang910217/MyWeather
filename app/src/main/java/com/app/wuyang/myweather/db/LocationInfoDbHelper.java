package com.app.wuyang.myweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wuyang on 16-1-17.
 * 位置数据库 一个表
 */
public class LocationInfoDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="location.db";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME="locationInfo";

    public LocationInfoDbHelper(Context context) {
        super(context, DbDir.getDbName(context,DB_NAME), null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE "
                + TABLE_NAME
                + "("
                + "id integer primary key autoincrement,"
                + "longitude text,"
                + "latitude text,"
                + "county text,"
                + "city text,"
                + "province text,"
                + "newCounty text,"
                + "newCity text,"
                + "newProvince text,"
                + "address text,"
                + "cityCode text,"
                + "adCode text"
                + ");";

        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}
