package com.app.wuyang.myweather.data;

/**
 * Created by wuyang on 16-1-7.
 * 有空气质量信息的城市的实体类；
 */
public class AirQualityCity {
    private int id;
    private String name;

    public AirQualityCity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AirQualityCity() {
        super();
    }

    @Override
    public String toString() {
        return "AirQualityCity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
