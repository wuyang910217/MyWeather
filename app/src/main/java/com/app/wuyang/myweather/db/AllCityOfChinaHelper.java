package com.app.wuyang.myweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.wuyang.myweather.data.AllCityOfChina;
import com.app.wuyang.myweather.data.LocationInfo;
import com.app.wuyang.myweather.data.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyang on 16-1-11.
 * 保存和查询中国所有省市县信息；
 */
public class AllCityOfChinaHelper {

    private SQLiteDatabase db;

    public AllCityOfChinaHelper(Context context) {
        AllCityOfChinaDbHelper dbHelper = new AllCityOfChinaDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //    保存得到的省市县信息到数据库；
    public void saveAllCityOfChina(List<AllCityOfChina> chinaList) {
        if (chinaList != null) {
            ContentValues values = new ContentValues();
            db.beginTransaction();
            for (AllCityOfChina cityOfChina : chinaList){
                values.put("areaId", cityOfChina.getAreaId());
                values.put("county", cityOfChina.getCounty());
                values.put("city", cityOfChina.getCity());
                values.put("province", cityOfChina.getProvince());
                db.insert("allCityOfChina", null, values);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    public List<String> loadAllInfo() {
        List<String> strings = new ArrayList<>();

        Cursor cursor = db.query("allCityOfChina", new String[]{"city"}, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                strings.add(cursor.getString(cursor.getColumnIndex("city")));
            }
            cursor.close();
        }
        return strings;
    }

    //    根据定位得到的城市名，查询所在城市的位置Id，并返回；
//
//    这里根据原始数据格式，指定查询的列为county,说明一定可以返回唯一一个市级的areaId；
//    如果真有重名的市名字，则返回至少两个数据，则必须用List，然而只能选择一个，
//    也就是可能会出错，返回不是当前城市的areaId;(可以通过限定 省的条件解决)
//    这里目前先假定只会返回一个数据。
    public Long getAreaIdByCity(LocationInfo locationInfo) {
        String city = locationInfo.getNewCity();
        String province =locationInfo.getNewProvince();
        Cursor cursor = db.query("allCityOfChina",
                new String[]{"areaId", "county"}, "county=? and province=?",
                new String[]{city,province}, null, null, null);
        Long areaId = null;
        if (cursor!= null) {
            while (cursor.moveToNext()) {
                areaId = cursor.getLong(cursor.getColumnIndex("areaId"));
            }
            cursor.close();
            return areaId;
        }
        return null;
    }

//------------------------参考-----------------------------
//    bug 对于新乡市和新乡县  解析后 都变成了新乡 说明会返回两个匹配的记录，必须用List
//    然后再取出后面的一组数据  （根据原始数据，第一个应该总是市）

//    根据定位得到的县级名，查询所在县级的位置Id，并返回；
//    两种格式 如封丘后面没有县或区；  如浚县这样的已经含有县的字符的格式（两个字符的）；
//
//    还不能查询新疆等特殊地区的信息 （根据定位返回的信息和原始数据而定,无法判断）
//    可以和上面的方法放在一起 先判断县级信息，如果查询不到 则返回市级的位置信息；
//    对于新乡市××区 由于原始数据没有提供××区的信息，就算定位出来，也只能按照市级的位置信息返回；
//-----------------------------------------------------

// 县级城市 有重名情况 查询必须用两个条件
//    判断
//        2  直接传入定位的县级信息 看是否为空 不为空 说明是（新乡县新乡市，洋县等）这种情况
//        3  传入截取后一位的县级信息  一般情况

    public Long getAreaIdByRawCounty(LocationInfo locationInfo) {
        String rawCounty = locationInfo.getCounty();
        String city = locationInfo.getNewCity();
        Long areaId = null;
        Cursor cursor = db.query("allCityOfChina",
                new String[]{"areaId", "county", "city"},
                "county=? and city=?", new String[]{rawCounty,city},
                null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                areaId = cursor.getLong(cursor.getColumnIndex("areaId"));
            }
            cursor.close();
            return areaId;
        }
        return null;
    }

    public Long getAreaIdByCounty(LocationInfo locationInfo) {
        String county = locationInfo.getNewCounty();
        String city = locationInfo.getNewCity();
        Long areaId = null;
        Cursor cursor= db.query("allCityOfChina",
                new String[]{"areaId", "county", "city"},
                "county=? and city=?", new String[]{county,city},
                null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                areaId = cursor.getLong(cursor.getColumnIndex("areaId"));
            }
            cursor.close();
            return areaId;
        }
        return null;
    }

    public List<AllCityOfChina> queryAreaIdByCity(String name){
        List<AllCityOfChina> allCityOfChinaList=new ArrayList<>();

        Cursor cursor =db.query("allCityOfChina",null,"county=?",
                new String[]{name},null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                AllCityOfChina allCityOfChina =new AllCityOfChina();
                allCityOfChina.setCity(cursor.getString(cursor.getColumnIndex("city")));
                allCityOfChina.setCounty(cursor.getString(cursor.getColumnIndex("county")));
                allCityOfChina.setProvince(cursor.getString(cursor.getColumnIndex("province")));
                allCityOfChina.setAreaId(cursor.getLong(cursor.getColumnIndex("areaId")));
                allCityOfChinaList.add(allCityOfChina);
            }
            cursor.close();
            return allCityOfChinaList;
        }
        return null;
    }
}
