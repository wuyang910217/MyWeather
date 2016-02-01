package com.app.wuyang.myweather.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.app.wuyang.myweather.data.AirQuality;
import com.app.wuyang.myweather.data.LocationInfo;
import com.app.wuyang.myweather.data.WeatherIndex;
import com.app.wuyang.myweather.data.WeatherInfo;
import com.app.wuyang.myweather.db.AirQualityHelper;
import com.app.wuyang.myweather.db.AllCityOfChinaHelper;
import com.app.wuyang.myweather.db.LocationInfoHelper;
import com.app.wuyang.myweather.db.WeatherHelper;
import com.app.wuyang.myweather.utility.ConnectUtils;
import com.app.wuyang.myweather.utility.LogUtility;
import com.app.wuyang.myweather.utility.WeatherAboutUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyang on 16-1-8.
 * 根据定位信息，后台处理空气质量信息和天气信息，并存入数据库。
 */
public class HandleAirQualityAndWeatherTask extends AsyncTask<Void,Void,Void>{

    private Context mContext;
    private AirQuality airQuality=new AirQuality();
    private final static String URL_PATH=
            "http://www.pm25.in/api/querys/aqi_details.json?city=";
    private final static String URL_TOKEN=
                    "&token=5j1znBVAsnSf5xQyNQyq&stations=no";
    private final static String TYPE_FORECAST="forecast_v";
    private final static String TYPE_INDEX="index_v";
    List<AirQuality> airQualities =new ArrayList<>();
    List<WeatherInfo> weatherInfos=new ArrayList<>();
    List<WeatherIndex> weatherIndexes=new ArrayList<>();

    private LocationInfoHelper locationInfoHelper;
    private AllCityOfChinaHelper cityOfChinaHelper;
    private AirQualityHelper helper;
    private WeatherHelper weatherHelper;

    public HandleAirQualityAndWeatherTask(Context context) {
        this.mContext = context;
        locationInfoHelper=new LocationInfoHelper(mContext);
        cityOfChinaHelper=new AllCityOfChinaHelper(mContext);
        helper= AirQualityHelper.getInstance(mContext);
        weatherHelper=new WeatherHelper(mContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... params) {
//        -----测试用------------------------------
//        String[] strings=new String[1];
//        strings[0]="北京";
//        --------------------------------------
        long startTime=System.currentTimeMillis();

        LocationInfo locationInfo =locationInfoHelper.loadLocationInfo();
        if (locationInfo==null){
            LogUtility.d("abc","说明此时定位信息还没有存到数据库，直接返回。。。。。可能没网--定位失败");
            return null;
        }
        LogUtility.d("abc","从定位得到的数据库中取出信息。。。。。。。。。");

//        解析出url地址
        String urlPath = getUrlPath(locationInfo);
//        把空气质量信息存入到数据库
        saveAirQualityToDb(urlPath);
//       得到定位县级或市级的城市Id 用于获取天气信息；
        Long areaId= getAreaId(locationInfo);
//        得到url地址
        WeatherAboutUtils weatherAboutUtils =new WeatherAboutUtils();
        String weatherForecastUrl = weatherAboutUtils.getUrl(areaId, TYPE_FORECAST);
        String weatherIndexUrl = weatherAboutUtils.getUrl(areaId, TYPE_INDEX);
//        把天气信息存入到数据库
        saveWeatherInfoToDb(weatherForecastUrl);
//         把天气指数信息存入到数据库
        saveWeatherIndexToDb(weatherIndexUrl);

        testInfo();

        long endTime = System.currentTimeMillis();
        LogUtility.d("abc", "在HandleAirQualityAndWeatherTask------------------ 执行共耗时："
                + (endTime - startTime) + "ms");

        return null;
    }

    private void testInfo(){
        LogUtility.d("abc", "现在进入到了 数据库测试阶段。。。。。。。");

        LocationInfo info=locationInfoHelper.loadLocationInfo();
        if (info!=null){
            LogUtility.d("abc","检查位置数据库信息----" +info.toString());
        }else {
            LogUtility.d("abc","---------------------说明位置数据库信息为空");
        }


        List<String> allCityOfChinaList =cityOfChinaHelper.loadAllInfo();
        if (allCityOfChinaList.size()!=0){
            LogUtility.d("abc", "检查中国省市县数据库共有----" + allCityOfChinaList.size() + "行...."
                    + "随机测试一个城市：" + allCityOfChinaList.get(100));
        } else {
            LogUtility.d("abc","---------------------说明中国省市县信息为空");
        }


        List<String> cityList = helper.loadCity();
        List<AirQuality> airQualities=helper.loadAirQuality();
        if (cityList!=null){
            LogUtility.d("abc", "检查空气质量城市数据库共有" + cityList.size()
                    + "行..随机测试一个城市：" + cityList.get(5));
        } else {
            LogUtility.d("abc","---------------------说明空气质量城市信息为空");
        }
        if (airQualities!=null){
            LogUtility.d("abc","得到的空气质量信息为。。"+airQualities.toString());
        } else{
            LogUtility.d("abc","---------------------说明空气质量信息为空");
        }


        List<WeatherInfo> weatherInfoList=weatherHelper.loadWeatherInfo();
        List<WeatherIndex> weatherIndexList=weatherHelper.loadWeatherIndex();
        if (weatherInfoList!=null){
            for (WeatherInfo weatherInfo:weatherInfoList){
                LogUtility.d("abc","天气信息，，，"+weatherInfo.toString());
            }
        }else{
            LogUtility.d("abc","--------------------------说明天气信息为空");
        }

        if (weatherIndexList!=null){
            for (WeatherIndex weatherIndex:weatherIndexList){
                LogUtility.d("abc","天气指数信息，，，"+weatherIndex.toString());
            }
        }else{
            LogUtility.d("abc","--------------------------说明天气指数信息为空");
        }


        WeatherAboutUtils aboutUtils=new WeatherAboutUtils();
        LogUtility.d("abc", "今天。。。"+aboutUtils.getFriendDate());
        LogUtility.d("abc", "明天。。。。"+aboutUtils.getFriendDateTomorrow());
        LogUtility.d("abc", "后天。。。。" + aboutUtils.getFriendDateTodayAfterTomorrow());

        LogUtility.d("abc", "数据库测试阶段。。。。。。。结束。。。。");

    }

    private void saveAirQualityToDb(String urlPath){
        if (urlPath != null){
            airQualities=helper.loadAirQuality();
            AirQuality data =getAirQuality(urlPath);
            if (data!=null){
                if (airQualities!=null){
                    LogUtility.d("abc", "获取城市的空气质量成功，并更新数据库");
                    helper.upDateAirQuality(data);
                } else {
                    LogUtility.d("abc","获取城市的空气质量成功，并存入数据库");
                    helper.saveAirQuality(data);
                }
            } else {
                LogUtility.d("abc","说明获取城市的空气质量失败  解析出的json数据为空");
            }

        } else {
            LogUtility.d("abc","说明获取城市的空气质量失败 url地址为空");
        }
    }


    private void saveWeatherInfoToDb(String urlPath){
        if (urlPath!=null){
            weatherInfos= weatherHelper.loadWeatherInfo();
            List<WeatherInfo> weatherInfoList = getWeatherInfo(urlPath);
            if (weatherInfoList!=null){
                if (weatherInfos!=null){
                    LogUtility.d("abc", "获取城市的天气成功，并更新数据库");
                    weatherHelper.updateWeatherInfo(weatherInfoList);
                } else {
                    LogUtility.d("abc", "获取城市的天气成功，并存入数据库");
                    weatherHelper.saveWeatherInfo(weatherInfoList);
                }
            } else {
                LogUtility.d("abc","说明获取城市的天气失败  解析出的json数据为空");
            }
        }else {
            LogUtility.d("abc","说明获取城市的天气信息失败.. url地址为空");
        }
    }


    private void saveWeatherIndexToDb(String urlPath){
        if (urlPath!=null){
            weatherIndexes=weatherHelper.loadWeatherIndex();
            List<WeatherIndex> indexList =getWeatherIndex(urlPath);
            if (indexList!=null){
                if (weatherIndexes!=null){
                    LogUtility.d("abc", "获取城市的指数信息成功，并更新数据库");
                    weatherHelper.updateWeatherIndex(indexList);
                } else {
                    LogUtility.d("abc", "获取城市的指数信息成功，并存入数据库");
                    weatherHelper.saveWeatherIndex(indexList);
                }
            }else {
                LogUtility.d("abc","说明获取城市的指数信息失败  解析出的json数据为空");
            }
        }else {
            LogUtility.d("abc","说明获取城市的指数信息失败.. url地址为空");
        }
    }

//    根据定位到的市 县 返回areaid用于获取天气信息；
//    如果县级信息 匹配不到areaid 则返回其定位到的城市的areaid；

    private Long getAreaId(LocationInfo locationInfo){
        List<String> stringList =cityOfChinaHelper.loadAllInfo();

        if (stringList.size()==0){
            LogUtility.d("abc","说明省市县数据库中没有数据。。。直接返回");
            return null;
        }else {
            LogUtility.d("abc","说明省市县数据库 存在数据-----从中随机返回一个城市-- "+stringList.get(55));
        }

        Long id=cityOfChinaHelper.getAreaIdByCity(locationInfo);
        Long id1=cityOfChinaHelper.getAreaIdByCounty(locationInfo);
        Long id2=cityOfChinaHelper.getAreaIdByRawCounty(locationInfo);

        if (id1==null && id2==null){
            LogUtility.d("abc","说明县级id无法获取，返回城市的id "+id);
            return id;
        } else {
            if (id2!=null){
                LogUtility.d("abc","得到的城市id "+id);
                LogUtility.d("abc","说明不是一般情况。得到的县级id "+id2);
                return id2;
            } else {
                LogUtility.d("abc","得到的城市id "+id);
                LogUtility.d("abc","说明是一般情况。。。得到的县级id "+id1);
                return id1;
            }
        }
    }

    private String getUrlPath(LocationInfo locationInfo){
        List<String> cityList = helper.loadCity();

//        for (String city:cityList){
//            LogUtility.d("abc",city);
//        }
        if (cityList!=null){
            LogUtility.d("abc","说明--空气质量城市信息--存在。。。" +
                    "查看数据库共有city："+cityList.size());
            String city = locationInfo.getNewCity();
            if (cityList.contains(city)){
                LogUtility.d("abc","得到的城市为："+city);
                LogUtility.d("abc","得到的查询空气质量的地址为："+URL_PATH+city+URL_TOKEN);
                try {
                    String encodeCity = URLEncoder.encode(city, "utf-8");
                    return URL_PATH+encodeCity+URL_TOKEN;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }else {
            LogUtility.d("abc","说明--空气质量城市信息--不存在  返回null");
        }
        return null;
    }

    private AirQuality getAirQuality(String urlPath){
        if (urlPath==null){
            LogUtility.d("abc", "测试 说明getAirQuality获得的url为空，直接返回null....");
            return null;
        }
        ConnectUtils connectUtils =new ConnectUtils();
        String jsonData =connectUtils.handleJson(urlPath);
        try {
            if (jsonData!=null){
                JSONArray array = new JSONArray(jsonData);
                JSONObject jsonObject = array.getJSONObject(0);

                String city = jsonObject.getString("area");
                airQuality.setCity(city);
                Double co = jsonObject.getDouble("co");
                airQuality.setCo(co);
                int no2 = jsonObject.getInt("no2");
                airQuality.setNo2(no2);
                int o3 = jsonObject.getInt("o3");
                airQuality.setO3(o3);
                int pm10 = jsonObject.getInt("pm10");
                airQuality.setPm10(pm10);
                int pm2_5 = jsonObject.getInt("pm2_5");
                airQuality.setPm25(pm2_5);
                int so2 = jsonObject.getInt("so2");
                airQuality.setSo2(so2);
                String quality = jsonObject.getString("quality");
                airQuality.setQuality(quality);
                String primary_pollutant = jsonObject.getString("primary_pollutant");
                airQuality.setPollutant(primary_pollutant);
                String time_point = jsonObject.getString("time_point");
                airQuality.setTime(time_point);
                LogUtility.d("abc", "说明getAirQuality解析json数据成功，准备存入数据库");
                return airQuality;
            } else {
                LogUtility.d("abc", "说明getAirQuality解析json数据失败，因为数据为null");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtility.d("abc", "测试 说明getAirQuality返回的为null....");
        return null;
    }


    private List<WeatherInfo> getWeatherInfo(String urlPath){
        ConnectUtils connectUtils =new ConnectUtils();
        String jsonData =connectUtils.handleJson(urlPath);
        try {
            if (jsonData!=null){
                JSONObject jsonObject=new JSONObject(jsonData);
                JSONObject f =jsonObject.getJSONObject("f");
                JSONArray weatherList = f.getJSONArray("f1");
                List<WeatherInfo> weatherInfoList =new ArrayList<>();
                for (int i = 0; i < weatherList.length(); i++) {
                    WeatherInfo weatherInfo =new WeatherInfo();
                    JSONObject weather=weatherList.getJSONObject(i);
                    weatherInfo.setPublish_time(f.getString("f0"));
                    weatherInfo.setWeather_day(weather.getString("fa"));
                    weatherInfo.setWeather_night(weather.getString("fb"));
                    weatherInfo.setTemperature_day(weather.getString("fc"));
                    weatherInfo.setTemperature_night(weather.getString("fd"));
                    weatherInfo.setWind_direction_day(weather.getString("fe"));
                    weatherInfo.setWind_direction_night(weather.getString("ff"));
                    weatherInfo.setWind_power_day(weather.getString("fg"));
                    weatherInfo.setWind_power_night(weather.getString("fh"));
                    weatherInfo.setSun_time(weather.getString("fi"));
                    weatherInfoList.add(weatherInfo);
                }
                LogUtility.d("abc", "说明getWeatherInfo解析json数据成功，准备存入数据库"+"----长度为"+weatherInfoList.size());
                return weatherInfoList;
            }else {
                LogUtility.d("abc", "说明getWeatherInfo解析json数据失败，因为数据为null");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        LogUtility.d("abc", "测试 说明getWeatherInfo返回的为null....");
        return null;
    }


    private List<WeatherIndex> getWeatherIndex(String urlPath){
        ConnectUtils connectUtils =new ConnectUtils();
        String jsonData =connectUtils.handleJson(urlPath);
        try {
            if (jsonData!=null){
                JSONObject jsonObject =new JSONObject(jsonData);
                JSONArray array =jsonObject.getJSONArray("i");
                List<WeatherIndex> indexList=new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    WeatherIndex weatherIndex=new WeatherIndex();
                    JSONObject object=array.getJSONObject(i);
                    weatherIndex.setWeatherIndex(object.getString("i2"));
                    weatherIndex.setWeatherIndexLevel(object.getString("i4"));
                    weatherIndex.setWeatherIndexInfo(object.getString("i5"));
                    indexList.add(weatherIndex);
                }
                LogUtility.d("abc", "说明getWeatherIndex解析json数据成功，准备存入数据库"+"----长度为"+indexList.size());
                return indexList;
            }else {
                LogUtility.d("abc", "说明getWeatherIndex解析json数据失败，因为数据为null");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        LogUtility.d("abc", "测试 说明getWeatherIndex返回的为null....");
        return null;
    }
}
