package com.app.wuyang.myweather.data;

import java.io.Serializable;

/**
 * Created by wuyang on 16-1-17.
 * 位置信息实体类
 */
public class LocationInfo implements Serializable{
    private String longitude;
    private String latitude;
    private String county;
    private String city;
    private String province;
    private String newCity;
    private String newCounty;
    private String newProvince;
    private String address;
    private String cityCode;
    private String adCode;

    public LocationInfo() {
        super();
    }

    public LocationInfo(String longitude, String latitude, String county, String city,
                        String province, String newCity, String newCounty,
                        String newProvince, String address, String cityCode,
                        String adCode) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.county = county;
        this.city = city;
        this.province = province;
        this.newCity = newCity;
        this.newCounty = newCounty;
        this.newProvince = newProvince;
        this.address = address;
        this.cityCode = cityCode;
        this.adCode = adCode;
    }

    @Override
    public String toString() {
        return "LocationInfo{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", county='" + county + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", newCity='" + newCity + '\'' +
                ", newCounty='" + newCounty + '\'' +
                ", newProvince='" + newProvince + '\'' +
                ", address='" + address + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", adCode='" + adCode + '\'' +
                '}';
    }

    public String getNewCity() {
        return newCity;
    }

    public void setNewCity(String newCity) {
        this.newCity = newCity;
    }

    public String getNewCounty() {
        return newCounty;
    }

    public void setNewCounty(String newCounty) {
        this.newCounty = newCounty;
    }

    public String getNewProvince() {
        return newProvince;
    }

    public void setNewProvince(String newProvince) {
        this.newProvince = newProvince;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }
}




