package com.app.wuyang.myweather.data;

import com.app.wuyang.myweather.activity.MainActivity;
import com.app.wuyang.myweather.db.DbQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyang on 16-1-22.
 */
public class WeatherData implements Serializable{
    private int weatherImgId;
    private String weatherDay;
    private String dateShow;
    private String tempDay;
    private String tempNight;
    private String windPower;
    private String windDirect;

    public WeatherData(int weatherImgId, String weatherDay,
                       String dateShow, String tempDay, String tempNight,
                       String windPower,String windDirect) {
        this.windDirect = windDirect;
        this.weatherImgId = weatherImgId;
        this.weatherDay = weatherDay;
        this.dateShow = dateShow;
        this.tempDay = tempDay;
        this.tempNight = tempNight;
        this.windPower = windPower;
    }

    public WeatherData() {
        super();
    }

    public int getWeatherImgId() {
        return weatherImgId;
    }

    public String getWeatherDay() {
        return weatherDay;
    }

    public String getDateShow() {
        return dateShow;
    }

    public String getTempDay() {
        return tempDay;
    }

    public String getTempNight() {
        return tempNight;
    }

    public String getWindPower() {
        return windPower;
    }

    public String getWindDirect() {
        return windDirect;
    }



}
