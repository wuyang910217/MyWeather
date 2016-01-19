package com.app.wuyang.myweather.data;

import java.io.Serializable;

/**
 * Created by wuyang on 16-1-11.
 * 天气信息的实体类;
 */
public class WeatherInfo implements Serializable {
    private String publish_time;
    private String weather_day;
    private String weather_night;
    private String temperature_day;
    private String temperature_night;
    private String wind_direction_day;
    private String wind_direction_night;
    private String wind_power_day;
    private String wind_power_night;
    private String sun_time;

    public WeatherInfo() {
        super();
    }

    public WeatherInfo(String publish_time, String weather_day, String weather_night,
                       String temperature_day, String temperature_night, String wind_direction_day,
                       String wind_direction_night, String wind_power_day,
                       String wind_power_night, String sun_time) {
        this.publish_time = publish_time;
        this.weather_day = weather_day;
        this.weather_night = weather_night;
        this.temperature_day = temperature_day;
        this.temperature_night = temperature_night;
        this.wind_direction_day = wind_direction_day;
        this.wind_direction_night = wind_direction_night;
        this.wind_power_day = wind_power_day;
        this.wind_power_night = wind_power_night;
        this.sun_time = sun_time;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "publish_time='" + publish_time + '\'' +
                ", weather_day=" + weather_day +
                ", weather_night=" + weather_night +
                ", temperature_day=" + temperature_day +
                ", temperature_night=" + temperature_night +
                ", wind_direction_day=" + wind_direction_day +
                ", wind_direction_night=" + wind_direction_night +
                ", wind_power_day=" + wind_power_day +
                ", wind_power_night=" + wind_power_night +
                ", sun_time='" + sun_time + '\'' +
                '}';
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getWeather_day() {
        return weather_day;
    }

    public void setWeather_day(String weather_day) {
        this.weather_day = weather_day;
    }

    public String getWeather_night() {
        return weather_night;
    }

    public void setWeather_night(String weather_night) {
        this.weather_night = weather_night;
    }

    public String getTemperature_day() {
        return temperature_day;
    }

    public void setTemperature_day(String temperature_day) {
        this.temperature_day = temperature_day;
    }

    public String getTemperature_night() {
        return temperature_night;
    }

    public void setTemperature_night(String temperature_night) {
        this.temperature_night = temperature_night;
    }

    public String getWind_direction_day() {
        return wind_direction_day;
    }

    public void setWind_direction_day(String wind_direction_day) {
        this.wind_direction_day = wind_direction_day;
    }

    public String getWind_direction_night() {
        return wind_direction_night;
    }

    public void setWind_direction_night(String wind_direction_night) {
        this.wind_direction_night = wind_direction_night;
    }

    public String getWind_power_day() {
        return wind_power_day;
    }

    public void setWind_power_day(String wind_power_day) {
        this.wind_power_day = wind_power_day;
    }

    public String getWind_power_night() {
        return wind_power_night;
    }

    public void setWind_power_night(String wind_power_night) {
        this.wind_power_night = wind_power_night;
    }

    public String getSun_time() {
        return sun_time;
    }

    public void setSun_time(String sun_time) {
        this.sun_time = sun_time;
    }
}
