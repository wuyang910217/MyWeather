package com.app.wuyang.myweather.utility;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by wuyang on 16-1-13.
 * 提供查询天气信息的url
 */
public class WeatherAboutUtils {

    private String getDate(){
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA);
        return dateFormat.format(new Date());
    }

    public boolean isNight(){
        Calendar calendar =Calendar.getInstance(Locale.CHINA);
        int hour =calendar.get(Calendar.HOUR_OF_DAY);
        return !(hour > 7 && hour < 18);
    }

    public String getFriendDate(){
        SimpleDateFormat dateFormat =new SimpleDateFormat("MM-dd EEEE", Locale.CHINA);
        return dateFormat.format(new Date());
    }
    public String getFriendDateTomorrow(){
        Long tomorrowTime=System.currentTimeMillis()+24*60*60*1000;
        SimpleDateFormat dateFormat =new SimpleDateFormat("MM-dd EEEE", Locale.CHINA);
        return dateFormat.format(new Date(tomorrowTime));
    }

    public String getFriendDateTodayAfterTomorrow(){
        Long todayAfterTomorrowTime=System.currentTimeMillis()+2*24*60*60*1000;
        SimpleDateFormat dateFormat =new SimpleDateFormat("MM-dd EEEE", Locale.CHINA);
        return dateFormat.format(new Date(todayAfterTomorrowTime));
    }

    public String getUrl(Long areaId,String type){
        String privateKey="35b194_SmartWeatherAPI_746682e";
        String appid="69f199f9c18ea287";
        String appidShort="69f199";
        String currentDate =getDate();
        String commonUrl="http://open.weather.com.cn/data/?areaid=";

        String publicKey= commonUrl
                + areaId
                + "&type="
                + type
                + "&date="
                + currentDate
                + "&appid="+appid;
        String publicUrl = commonUrl
                + areaId
                + "&type="
                + type
                + "&date="
                + currentDate
                + "&appid="+appidShort
                + "&key=";

        try {
            String key=getKey(publicKey,privateKey);

            LogUtility.d("abc","得到的key为 "+key);
            LogUtility.d("abc","对key编码后得到的url为 "+publicUrl+URLEncoder.encode(key,"utf-8"));

            return publicUrl+URLEncoder.encode(key,"utf-8");
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    private String getKey(String publicKey,String privateKey)
            throws GeneralSecurityException,UnsupportedEncodingException{

        SecretKey secretKey;
        byte[] keyBytes = privateKey.getBytes();
        secretKey= new SecretKeySpec(keyBytes,"HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(secretKey);
        byte[] text = publicKey.getBytes();
        String key;
        key= Base64.encodeToString(mac.doFinal(text),Base64.DEFAULT).trim();
        return key;
    }
}
