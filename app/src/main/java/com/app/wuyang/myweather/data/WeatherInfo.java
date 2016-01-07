package com.app.wuyang.myweather.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


/**
 * 定义weather数据库需要的常量
 * Created by wuyang on 16-1-3.
 */
public class WeatherInfo {
//包名用于确保唯一性
    public static final String CONTENT_AUTHORITY="com.app.wuyang.myweather";

//所有uri共有的基础uri
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);

//    各个数据库的内容索引路径
    public static final String PATH_AIR_QUALITY="air_quality";
    public static final String PATH_AIR_QUALITY_CITY="air_quality_city";
    public static final String PATH_WEATHER="weather";
    public static final String PATH_LOCATION_CITY="location_city";
    public static final String PATH_LOCATION_COUNTY="location_county";

//    空气质量数据库的常量和工具方法
    public static final class AirQualityEntry implements BaseColumns{

        public static final Uri CONTENT_URI=
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_AIR_QUALITY).build();

        public static final String CONTENT_TYPE=
                ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+ CONTENT_AUTHORITY+"/"+PATH_AIR_QUALITY;
        public static final String CONTENT_ITEM_TYPE=
                ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_AIR_QUALITY;

        public static final String TABLE_NAME= "Air_quality";

        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_SO2 = "so2";
        public static final String COLUMN_NO2 = "no2";
        public static final String COLUMN_CO = "co";
        public static final String COLUMN_O3 = "o3";
        public static final String COLUMN_PM25 = "pm25";
        public static final String COLUMN_PM10 = "pm10";
        public static final String COLUMN_PRIMARY_POLLUTANT = "pollutant";
        public static final String COLUMN_AIRQUALITY = "quality";
        public static final String COLUMN_TIME = "time";



    }

//    所有可查询的空气质量指标的城市数据库的常量
    public static final class AirQualityCityEntry implements BaseColumns {

        public static final Uri CONTENT_URI=
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_AIR_QUALITY_CITY).build();

        public static final String CONTENT_TYPE=
            ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+ CONTENT_AUTHORITY+"/"+PATH_AIR_QUALITY_CITY;
        public static final String CONTENT_ITEM_TYPE=
            ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_AIR_QUALITY_CITY;

        public static final String TABLE_NAME= "Air_quality_city";

        public static final String COLUMN_CITY = "city";


    }

}
