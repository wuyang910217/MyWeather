package com.app.wuyang.myweather.data;

import java.io.Serializable;

/**
 * Created by wuyang on 16-1-7.
 */
public class AirQuality implements Serializable {

    private int id;
    private int so2;
    private int no2;
    private int co;
    private int o3;
    private int pm25;
    private int pm10;
    private String city;
    private String pollutant;
    private String quality;
    private String time;

    public AirQuality() {
        super();
    }

    public AirQuality(int id, int so2, int no2, int co,
                      int o3, int pm25, int pm10, String city,
                      String pollutant, String quality, String time) {
        this.id = id;
        this.so2 = so2;
        this.no2 = no2;
        this.co = co;
        this.o3 = o3;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.city = city;
        this.pollutant = pollutant;
        this.quality = quality;
        this.time = time;
    }

    @Override
    public String toString() {
        return "AirQuality{" +
                "id=" + id +
                ", so2=" + so2 +
                ", no2=" + no2 +
                ", co=" + co +
                ", o3=" + o3 +
                ", pm25=" + pm25 +
                ", pm10=" + pm10 +
                ", city='" + city + '\'' +
                ", pollutant='" + pollutant + '\'' +
                ", quality='" + quality + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSo2() {
        return so2;
    }

    public void setSo2(int so2) {
        this.so2 = so2;
    }

    public int getNo2() {
        return no2;
    }

    public void setNo2(int no2) {
        this.no2 = no2;
    }

    public int getCo() {
        return co;
    }

    public void setCo(int co) {
        this.co = co;
    }

    public int getO3() {
        return o3;
    }

    public void setO3(int o3) {
        this.o3 = o3;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }

    public int getPm10() {
        return pm10;
    }

    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPollutant() {
        return pollutant;
    }

    public void setPollutant(String pollutant) {
        this.pollutant = pollutant;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
