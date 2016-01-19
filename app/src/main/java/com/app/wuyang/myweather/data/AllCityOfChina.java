package com.app.wuyang.myweather.data;

import java.io.Serializable;

/**
 * Created by wuyang on 16-1-11.
 * 中国所有省市县的实体类；
 */
public class AllCityOfChina implements Serializable{
    private int id;
    private long areaId;
    private String county;
    private String city;
    private String province;

    public AllCityOfChina() {
        super();
    }

    public AllCityOfChina(int id, long areaId, String county, String city, String province) {
        this.id = id;
        this.areaId = areaId;
        this.county = county;
        this.city = city;
        this.province = province;
    }

    @Override
    public String toString() {
        return "AllCityOfChina{" +
                "id=" + id +
                ", areaId=" + areaId +
                ", county='" + county + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAreaId() {
        return areaId;
    }

    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
