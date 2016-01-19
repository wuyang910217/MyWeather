package com.app.wuyang.myweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.wuyang.myweather.data.LocationInfo;

/**
 * Created by wuyang on 16-1-17.
 * 保存和查询得到的位置信息；
 */
public class LocationInfoHelper {
    private SQLiteDatabase db;

    public LocationInfoHelper(Context context) {
        LocationInfoDbHelper dbHelper = new LocationInfoDbHelper(context);
        db= dbHelper.getWritableDatabase();
    }


    public void saveLocationInfo(LocationInfo locationInfo){
        if (locationInfo!=null){
            ContentValues values=new ContentValues();
            values.put("longitude",locationInfo.getLongitude());
            values.put("latitude",locationInfo.getLatitude());
            values.put("county",locationInfo.getCounty());
            values.put("city",locationInfo.getCity());
            values.put("province",locationInfo.getProvince());
            values.put("newCounty",locationInfo.getNewCounty());
            values.put("newCity",locationInfo.getNewCity());
            values.put("newProvince",locationInfo.getNewProvince());
            values.put("address",locationInfo.getAddress());
            values.put("cityCode",locationInfo.getCityCode());
            values.put("adCode",locationInfo.getAdCode());
            db.insert("locationInfo",null,values);
        }
    }


    public void deleteLocationInfo(){
        db.delete("locationInfo",null,null);
    }


    public LocationInfo loadLocationInfo(){
        LocationInfo locationInfo =new LocationInfo();
        Cursor cursor =db.query("locationInfo",null,null,null,null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                locationInfo.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
                locationInfo.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
                locationInfo.setCounty(cursor.getString(cursor.getColumnIndex("county")));
                locationInfo.setCity(cursor.getString(cursor.getColumnIndex("city")));
                locationInfo.setProvince(cursor.getString(cursor.getColumnIndex("province")));
                locationInfo.setNewCounty(cursor.getString(cursor.getColumnIndex("newCounty")));
                locationInfo.setNewCity(cursor.getString(cursor.getColumnIndex("newCity")));
                locationInfo.setNewProvince(cursor.getString(cursor.getColumnIndex("newProvince")));
                locationInfo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                locationInfo.setCityCode(cursor.getString(cursor.getColumnIndex("cityCode")));
                locationInfo.setAdCode(cursor.getString(cursor.getColumnIndex("adCode")));
            }
            cursor.close();
            return locationInfo;

        }
        return null;
    }

}
