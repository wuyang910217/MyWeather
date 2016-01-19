package com.app.wuyang.myweather.data;

/**
 * Created by wuyang on 16-1-13.
 * 天气指数的实体类；
 */
public class WeatherIndex {
    private String weatherIndex;
    private String weatherIndexLevel;
    private String weatherIndexInfo;


    public WeatherIndex() {
        super();
    }

    public WeatherIndex(String weatherIndex, String weatherIndexLevel, String weatherIndexInfo) {
        this.weatherIndex = weatherIndex;
        this.weatherIndexLevel = weatherIndexLevel;
        this.weatherIndexInfo = weatherIndexInfo;
    }

    @Override
    public String toString() {
        return "WeatherIndex{" +
                "weatherIndex='" + weatherIndex + '\'' +
                ", weatherIndexLevel='" + weatherIndexLevel + '\'' +
                ", weatherIndexInfo='" + weatherIndexInfo + '\'' +
                '}';
    }

    public String getWeatherIndex() {
        return weatherIndex;
    }

    public void setWeatherIndex(String weatherIndex) {
        this.weatherIndex = weatherIndex;
    }

    public String getWeatherIndexLevel() {
        return weatherIndexLevel;
    }

    public void setWeatherIndexLevel(String weatherIndexLevel) {
        this.weatherIndexLevel = weatherIndexLevel;
    }

    public String getWeatherIndexInfo() {
        return weatherIndexInfo;
    }

    public void setWeatherIndexInfo(String weatherIndexInfo) {
        this.weatherIndexInfo = weatherIndexInfo;
    }
}
