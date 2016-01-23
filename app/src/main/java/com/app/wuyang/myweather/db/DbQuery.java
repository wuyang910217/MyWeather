package com.app.wuyang.myweather.db;

import android.content.Context;

import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.data.AirQuality;
import com.app.wuyang.myweather.data.LocationInfo;
import com.app.wuyang.myweather.data.WeatherIndex;
import com.app.wuyang.myweather.data.WeatherInfo;
import com.app.wuyang.myweather.utility.WeatherAboutUtils;
import com.app.wuyang.myweather.utility.WeatherTransformUtils;

import java.util.List;

/**
 * Created by wuyang on 16-1-22.
 * 提供各个数据库中content的查询，并返回。
 * 供TextView使用。
 */
public class DbQuery {
    private WeatherAboutUtils aboutUtils;
    private AirQualityHelper airQualityHelper;
    private WeatherHelper weatherHelper;
    private LocationInfoHelper locationInfoHelper;
    private WeatherTransformUtils transformUtils;

    List<AirQuality> airQualityList;
    List<WeatherInfo> weatherInfoList;
    LocationInfo locationInfo;
    List<WeatherIndex> weatherIndexList;

    public static final String CO="co";
    public static final String NO2="no2";
    public static final String SO2="so2";
    public static final String O3="o3";
    public static final String PM10="pm10";
    public static final String PM25="pm25";
    public static final String POLLUTANT="pollutant";
    public static final String QUALITY="quality";
    public static final String TIME="time";

    public static final String LONGITUDE="longitude";
    public static final String LATITUDE="latitude";
    public static final String COUNTY="county";
    public static final String CITY="city";
    public static final String PROVINCE="province";
    public static final String NEW_CITY="newCity";
    public static final String NEW_COUNTY="newCounty";
    public static final String NEW_PROVINCE="newProvince";
    public static final String ADDRESS="address";
    public static final String CITY_CODE="cityCode";
    public static final String AD_CODE="adCode";

    public static final String TODAY="today";
    public static final String TOMORROW="tomorrow";
    public static final String TODAY_AFTER_TOMORROW="today_after_tomorrow";

    public static final String PUBLISH_TIME="publish_time";
    public static final String WEATHER_DAY="weather_day";
    public static final String WEATHER_NIGHT="weather_night";
    public static final String TEMPERATURE_DAY="temperature_day";
    public static final String TEMPERATURE_NIGHT="temperature_night";
    public static final String WIND_DIRECTION_DAY="wind_direction_day";
    public static final String WIND_DIRECTION_NIGHT="wind_direction_night";
    public static final String WIND_POWER_DAY="wind_power_day";
    public static final String WIND_POWER_NIGHT="wind_power_night";
    public static final String SUN_TIME="sun_time";

    public static final String WEATHER_INDEX="weatherIndex";
    public static final String WEATHER_INDEX_LEVEL="weatherIndexLevel";
    public static final String WEATHER_INDEX_INFO="weatherIndexInfo";

    public static final String POLLUTION_LEVEL1="优";
    public static final String POLLUTION_LEVEL2="良";
    public static final String POLLUTION_LEVEL3="轻度污染";
    public static final String POLLUTION_LEVEL4="中度污染";
    public static final String POLLUTION_LEVEL5="重度污染";
    public static final String POLLUTION_LEVEL6="严重污染";


    public DbQuery(Context context) {
        this.aboutUtils=new WeatherAboutUtils();
        this.airQualityHelper =AirQualityHelper.getInstance(context);
        this.airQualityHelper =AirQualityHelper.getInstance(context);
        this.weatherHelper=new WeatherHelper(context);
        this.locationInfoHelper =new LocationInfoHelper(context);
        this.transformUtils =new WeatherTransformUtils();
    }

    private boolean isAirQualityExist(){
        airQualityList =airQualityHelper.loadAirQuality();
        return !(airQualityList == null || airQualityList.size() == 0);
    }

    private boolean isWeatherInfoExist(){
        weatherInfoList=weatherHelper.loadWeatherInfo();
        return !(weatherInfoList == null || weatherInfoList.size() == 0);
    }

    private boolean isLocationInfoExist(){
        locationInfo =locationInfoHelper.loadLocationInfo();
        return !(locationInfo == null);
    }

    private boolean isWeatherIndexExist(){
        weatherIndexList =weatherHelper.loadWeatherIndex();
        return !(weatherIndexList == null || weatherIndexList.size() == 0);
    }

    public String getAirQualityLevel(String content){
        if (isAirQualityExist()){
            switch (content){
                case POLLUTION_LEVEL1:
                    return "空气质量"+content;
                case POLLUTION_LEVEL2:
                    return "空气质量"+content;
                case POLLUTION_LEVEL3:
                    return content;
                case POLLUTION_LEVEL4:
                    return content;
                case POLLUTION_LEVEL5:
                    return content;
                case POLLUTION_LEVEL6:
                    return content;
                default:
                    return "无数据";
            }
        }
        return "无数据";
    }


    public int getAirQualityLevelId(String content){
        if (isAirQualityExist()){
            switch (content){
                case POLLUTION_LEVEL1:
                    return R.drawable.air_quality_pollutant_level1;
                case POLLUTION_LEVEL2:
                    return R.drawable.air_quality_pollutant_level1;
                case POLLUTION_LEVEL3:
                    return R.drawable.air_quality_pollutant_level2;
                case POLLUTION_LEVEL4:
                    return R.drawable.air_quality_pollutant_level3;
                case POLLUTION_LEVEL5:
                    return R.drawable.air_quality_pollutant_level3;
                case POLLUTION_LEVEL6:
                    return R.drawable.air_quality_pollutant_level4;
                default:
                    return R.color.app_main_color;
            }
        }
        return R.color.app_main_color;
    }

    public String getAirQualityContent(String content){
        if (isAirQualityExist()){
            switch (content){
                case CO:
                    return ""+airQualityList.get(0).getCo();
                case NO2:
                    return ""+airQualityList.get(0).getNo2();
                case SO2:
                    return ""+airQualityList.get(0).getSo2();
                case O3:
                    return ""+airQualityList.get(0).getO3();
                case PM10:
                    return ""+airQualityList.get(0).getPm10();
                case PM25:
                    return ""+airQualityList.get(0).getPm25();
                case POLLUTANT:
                    return ""+airQualityList.get(0).getPollutant();
                case QUALITY:
                    return ""+airQualityList.get(0).getQuality();
                case TIME:
                    return ""+airQualityList.get(0).getTime();
                default:
                    return "无数据";
            }
        }
        return "无数据";
    }

    public String getLocationContent(String content){
        if (isLocationInfoExist()){
            switch (content){
                case LATITUDE:
                    return locationInfo.getLatitude();
                case LONGITUDE:
                    return locationInfo.getLongitude();
                case CITY:
                    return locationInfo.getCity();
                case CITY_CODE:
                    return locationInfo.getCityCode();
                case COUNTY:
                    return locationInfo.getCounty();
                case NEW_CITY:
                    return locationInfo.getNewCity();
                case NEW_COUNTY:
                    return locationInfo.getNewCounty();
                case NEW_PROVINCE:
                    return locationInfo.getNewProvince();
                case AD_CODE:
                    return locationInfo.getAdCode();
                case ADDRESS:
                    return locationInfo.getAddress();
                case PROVINCE:
                    return locationInfo.getProvince();
                default:
                    return "无数据";
            }
        }
        return "无数据";

    }

    public int getWeatherImage(){
        String weather=weatherInfoList.get(0).getWeather_day();
        if (isWeatherInfoExist()){
            return transformUtils.tranWeatherIcon(weather);
        }
        return R.drawable.weather_undefined;
    }

    public String getTodayWeatherContent(String content){
        if (isWeatherInfoExist()){
            switch (content){
                case TODAY:
                    return aboutUtils.getFriendDate();
                case WEATHER_DAY:
                    return transformUtils.tranWeather(
                            weatherInfoList.get(0).getWeather_day());
                case WEATHER_NIGHT:
                    return transformUtils.tranWeather(
                            weatherInfoList.get(0).getWeather_night());
                case TEMPERATURE_DAY:
                    return weatherInfoList.get(0).getTemperature_day()+"℃";
                case TEMPERATURE_NIGHT:
                    return weatherInfoList.get(0).getTemperature_night()+"℃";
                case PUBLISH_TIME:
                    String publish_time =weatherInfoList.get(0).getPublish_time();
                    String publish1=publish_time.substring(8, 10);
                    String publish2=publish_time.substring(10);
                    return publish1+":"+publish2;
                case WIND_POWER_DAY:
                    return transformUtils.tranWindPower(
                            weatherInfoList.get(0).getWind_power_day());
                case WIND_POWER_NIGHT:
                    return transformUtils.tranWindPower(
                            weatherInfoList.get(0).getWind_power_night());
                case WIND_DIRECTION_DAY:
                    return transformUtils.tranWeather(
                            weatherInfoList.get(0).getWind_direction_day());
                case WIND_DIRECTION_NIGHT:
                    return transformUtils.tranWeather(
                            weatherInfoList.get(0).getWind_direction_night());
                case SUN_TIME:
                    return weatherInfoList.get(0).getSun_time();
                default:
                    return "无数据";
            }
        }
        return "无数据";
    }

    public String getTomrrowWeatherContent(String content){
        if (isWeatherInfoExist()) {
            switch (content) {
                case TOMORROW:
                    return aboutUtils.getFriendDateTomorrow();
                case WEATHER_DAY:
                    return transformUtils.tranWeather(
                            weatherInfoList.get(1).getWeather_day());
                case WEATHER_NIGHT:
                    return transformUtils.tranWeather(
                            weatherInfoList.get(1).getWeather_night());
                case TEMPERATURE_DAY:
                    return weatherInfoList.get(1).getTemperature_day()+"℃";
                case TEMPERATURE_NIGHT:
                    return weatherInfoList.get(1).getTemperature_night()+"℃";
                case PUBLISH_TIME:
                    String publish_time =weatherInfoList.get(1).getPublish_time();
                    String publish1=publish_time.substring(8, 10);
                    String publish2=publish_time.substring(10);
                    return publish1+":"+publish2;
                case WIND_POWER_DAY:
                    return transformUtils.tranWindPower(
                            weatherInfoList.get(1).getWind_power_day());
                case WIND_POWER_NIGHT:
                    return transformUtils.tranWindPower(
                            weatherInfoList.get(1).getWind_power_night());
                case WIND_DIRECTION_DAY:
                    return transformUtils.tranWeather(
                            weatherInfoList.get(1).getWind_direction_day());
                case WIND_DIRECTION_NIGHT:
                    return transformUtils.tranWeather(
                            weatherInfoList.get(1).getWind_direction_night());
                case SUN_TIME:
                    return weatherInfoList.get(1).getSun_time();
                default:
                    return "无数据";
            }
        }
        return "无数据";
    }
    public String getTodayAfterTomrrowWeatherContent(String content){
        if (isWeatherInfoExist()){
            switch (content) {
                case TODAY_AFTER_TOMORROW:
                    return aboutUtils.getFriendDateTodayAfterTomorrow();
                case WEATHER_DAY:
                    return transformUtils.tranWeather(
                            weatherInfoList.get(2).getWeather_day());
                case WEATHER_NIGHT:
                    return transformUtils.tranWeather(
                            weatherInfoList.get(2).getWeather_night());
                case TEMPERATURE_DAY:
                    return weatherInfoList.get(2).getTemperature_day()+"℃";
                case TEMPERATURE_NIGHT:
                    return weatherInfoList.get(2).getTemperature_night()+"℃";
                case PUBLISH_TIME:
                    String publish_time =weatherInfoList.get(2).getPublish_time();
                    String publish1=publish_time.substring(8, 10);
                    String publish2=publish_time.substring(10);
                    return publish1+":"+publish2;
                case WIND_POWER_DAY:
                    return transformUtils.tranWindPower(
                            weatherInfoList.get(2).getWind_power_day());
                case WIND_POWER_NIGHT:
                    return transformUtils.tranWindPower(
                            weatherInfoList.get(2).getWind_power_night());
                case WIND_DIRECTION_DAY:
                    return transformUtils.tranWeather(
                            weatherInfoList.get(2).getWind_direction_day());
                case WIND_DIRECTION_NIGHT:
                    return transformUtils.tranWeather(
                            weatherInfoList.get(2).getWind_direction_night());
                case SUN_TIME:
                    return weatherInfoList.get(2).getSun_time();
                default:
                    return "无数据";
            }
        }
        return "无数据";
    }

    public String getWeatherIndexExerciseContent(String content){
        if (isWeatherIndexExist()){
            switch (content){
                case WEATHER_INDEX:
                    return weatherIndexList.get(0).getWeatherIndex();
                case WEATHER_INDEX_LEVEL:
                    return weatherIndexList.get(0).getWeatherIndexLevel();
                case WEATHER_INDEX_INFO:
                    return weatherIndexList.get(0).getWeatherIndexInfo();
                default:
                    return "无数据";
            }
        }
        return "无数据";
    }
    public String getWeatherIndexClothContent(String content){
        if (isWeatherIndexExist()){
            switch (content){
                case WEATHER_INDEX:
                    return weatherIndexList.get(2).getWeatherIndex();
                case WEATHER_INDEX_LEVEL:
                    return weatherIndexList.get(2).getWeatherIndexLevel();
                case WEATHER_INDEX_INFO:
                    return weatherIndexList.get(2).getWeatherIndexInfo();
                default:
                    return "无数据";
            }
        }
        return "无数据";
    }
    public String getWeatherIndexComfortContent(String content){
        if (isWeatherIndexExist()){
            switch (content){
                case WEATHER_INDEX:
                    return weatherIndexList.get(1).getWeatherIndex();
                case WEATHER_INDEX_LEVEL:
                    return weatherIndexList.get(1).getWeatherIndexLevel();
                case WEATHER_INDEX_INFO:
                    return weatherIndexList.get(1).getWeatherIndexInfo();
                default:
                    return "无数据";
            }
        }
        return "无数据";
    }

}
