package com.app.wuyang.myweather.utility;

import com.app.wuyang.myweather.db.WeatherHelper;

/**
 * Created by wuyang on 16-1-15.
 * 把数据库中的天气描述的字符   转换为对应的天气信息
 */
public class WeatherTransformUtils {

    private WeatherHelper helper;


    private static final String sunnyDescribe ="00";
    private static final String cloudyDescribe="01";
    private static final String overcastDescribe="02";
    private static final String showerDescribe="03";
    private static final String thunderShowerDescribe="04";
    private static final String thunderShowerWithHailDescribe="05";
    private static final String sleetDescribe="06";
    private static final String lightRainDescribe="07";
    private static final String moderateRainDescribe ="08";
    private static final String heavyRainDescribe="09";
    private static final String stormDescribe="10";
    private static final String heavyStormDescribe="11";
    private static final String severeStormDescribe="12";
    private static final String snowFlurryDescribe="13";
    private static final String lightSnowDescribe="14";
    private static final String moderateSnowDescribe ="15";
    private static final String heavySnowDescribe="16";
    private static final String snowStormDescribe="17";
    private static final String foggyDescribe="18";
    private static final String iceRainDescribe="19";
    private static final String dustStormDescribe ="20";
    private static final String lightToModerateRainDescribe="21";
    private static final String moderateToHeavyRainDescribe="22";
    private static final String heavyRainToStormDescribe="23";
    private static final String stormToHeavyStormDescribe="24";
    private static final String heavyToSevereStormDescribe="25";
    private static final String lightToModerateSnowDescribe="26";
    private static final String moderateToHeavySnowDescribe="27";
    private static final String heavySnowToSnowStormDescribe="28";
    private static final String dustDescribe="29";
    private static final String sandDescribe="30";
    private static final String sandStormDescribe="31";
    private static final String hazeDescribe="53";
    private static final String unknownDescribe="99";

    private static final String sunny="晴";
    private static final String cloudy="多云";
    private static final String overcast="阴";
    private static final String shower="阵雨";
    private static final String thunderShower="雷阵雨";
    private static final String thunderShowerWithHail="雷阵雨伴有冰雹";
    private static final String sleet="雨夹雪";
    private static final String lightRain="小雨";
    private static final String moderateRain ="中雨";
    private static final String heavyRain="大雨";
    private static final String storm="暴雨";
    private static final String heavyStorm="大暴雨";
    private static final String severeStorm="特大暴雨";
    private static final String snowFlurry="阵雪";
    private static final String lightSnow="小雪";
    private static final String moderateSnow ="中雪";
    private static final String heavySnow="大雪";
    private static final String snowStorm="暴雪";
    private static final String foggy="雾";
    private static final String iceRain="冻雨";
    private static final String dustStorm="沙尘暴";
    private static final String lightToModerateRain="小到中雨";
    private static final String moderateToHeavyRain="中到大雨";
    private static final String heavyRainToStorm="大到暴雨";
    private static final String stormToHeavyStorm="暴雨到大暴雨";
    private static final String heavyToSevereStorm="大暴雨到特大暴雨";
    private static final String lightToModerateSnow="小到中雪";
    private static final String moderateToHeavySnow="中到大雪";
    private static final String heavySnowToSnowStorm="大到暴雪";
    private static final String dust="浮尘";
    private static final String sand="扬沙";
    private static final String sandStorm="强沙尘暴";
    private static final String haze="霾";
    private static final String unknown="未知";


    private static final String noWindDescribe="0";
    private static final String northEastDescribe="1";
    private static final String eastDescribe="2";
    private static final String southEastDescribe="3";
    private static final String southDescribe="4";
    private static final String southWestDescribe="5";
    private static final String westDescribe="6";
    private static final String northWestDescribe="7";
    private static final String northDescribe="8";
    private static final String whirlWindDescribe="9";

    private static final String noWind="无持续风向";
    private static final String northEast="东北风";
    private static final String east="东风";
    private static final String southEast="东南风";
    private static final String south="南风";
    private static final String southWest="西南风";
    private static final String west="西风";
    private static final String northWest="西北风";
    private static final String north="北风";
    private static final String whirlWinde="旋转风";


    private static final String windPower03Describe="0";
    private static final String windPower34Describe="1";
    private static final String windPower45Describe="2";
    private static final String windPower56Describe="3";
    private static final String windPower67Describe="4";
    private static final String windPower78Describe="5";
    private static final String windPower89Describe="6";
    private static final String windPower910Describe="7";
    private static final String windPower1011Describe="8";
    private static final String windPower1112Describe="9";

    private static final String windPower03="微风";
    private static final String windPower34="3-4级";
    private static final String windPower45="4-5级";
    private static final String windPower56="5-6级";
    private static final String windPower67="6-7级";
    private static final String windPower78="7-8级";
    private static final String windPower89="8-9级";
    private static final String windPower910="9-10级";
    private static final String windPower1011="10-11级";
    private static final String windPower1112="11-12级";

    public String tranWindPower(String windPower){
        switch (windPower){
            case windPower03Describe:
                return windPower03;
            case windPower34Describe:
                return windPower34;
            case windPower45Describe:
                return windPower45;
            case windPower56Describe:
                return windPower56;
            case windPower67Describe:
                return windPower67;
            case windPower78Describe:
                return windPower78;
            case windPower89Describe:
                return windPower89;
            case windPower910Describe:
                return windPower910;
            case windPower1011Describe:
                return windPower1011;
            case windPower1112Describe:
                return windPower1112;
            default:
                return null;
        }
    }

    public String tranWeather(String weatherString){
        switch (weatherString){
            case sunnyDescribe:
                return sunny;
            case cloudyDescribe:
                return cloudy;
            case overcastDescribe:
                return overcast;
            case showerDescribe:
                return shower;
            case thunderShowerDescribe:
                return thunderShower;
            case thunderShowerWithHailDescribe:
                return thunderShowerWithHail;
            case sleetDescribe:
                return sleet;
            case lightRainDescribe:
                return lightRain;
            case moderateRainDescribe:
                return moderateRain;
            case heavyRainDescribe:
                return heavyRain;
            case stormDescribe:
                return storm;
            case heavyStormDescribe:
                return heavyStorm;
            case severeStormDescribe:
                return severeStorm;
            case snowFlurryDescribe:
                return snowFlurry;
            case lightSnowDescribe:
                return lightSnow;
            case moderateSnowDescribe:
                return moderateSnow;
            case heavySnowDescribe:
                return heavySnow;
            case snowStormDescribe:
                return snowStorm;
            case foggyDescribe:
                return foggy;
            case iceRainDescribe:
                return iceRain;
            case dustStormDescribe:
                return dustStorm;
            case lightToModerateRainDescribe:
                return lightToModerateRain;
            case moderateToHeavyRainDescribe:
                return moderateToHeavyRain;
            case heavyRainToStormDescribe:
                return heavyRainToStorm;
            case stormToHeavyStormDescribe:
                return stormToHeavyStorm;
            case heavyToSevereStormDescribe:
                return heavyToSevereStorm;
            case lightToModerateSnowDescribe:
                return lightToModerateSnow;
            case moderateToHeavySnowDescribe:
                return moderateToHeavySnow;
            case heavySnowToSnowStormDescribe:
                return heavySnowToSnowStorm;
            case dustDescribe:
                return dust;
            case sandDescribe:
                return sand;
            case sandStormDescribe:
                return sandStorm;
            case hazeDescribe:
                return haze;
            case unknownDescribe:
                return unknown;

            case noWindDescribe:
                return noWind;
            case northEastDescribe:
                return northEast;
            case eastDescribe:
                return east;
            case southEastDescribe:
                return southEast;
            case southDescribe:
                return south;
            case southWestDescribe:
                return southWest;
            case westDescribe:
                return west;
            case northWestDescribe:
                return northWest;
            case northDescribe:
                return north;
            case whirlWindDescribe:
                return whirlWinde;
            default:
                return null;
        }
    }
}
