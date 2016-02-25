package com.app.wuyang.myweather.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.adapter.WeatherAdapter;
import com.app.wuyang.myweather.data.AllCityOfChina;
import com.app.wuyang.myweather.data.WeatherData;
import com.app.wuyang.myweather.data.WeatherInfo;
import com.app.wuyang.myweather.db.AllCityOfChinaHelper;
import com.app.wuyang.myweather.utility.ConnectUtils;
import com.app.wuyang.myweather.utility.LogUtility;
import com.app.wuyang.myweather.utility.WeatherAboutUtils;
import com.app.wuyang.myweather.utility.WeatherTransformUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wuyang on 16-1-21.
 */
public class QueryWeatherFragment extends Fragment {
    private AllCityOfChinaHelper cityOfChinaHelper;
    private AlertDialog alertDialog;
    private EditText dialog_weather_input;
    private ProgressBar query_weather_progressBar;
    private FrameLayout query_weather_frameLayout;
    private ListView query_weather_listView;
    private TextView text_city;
    private List<WeatherData> dataList=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.item_query);
        cityOfChinaHelper=new AllCityOfChinaHelper(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_query_weather_fragment, container, false);
        initView(view);
        showDialog();
        showInputMethod(getContext(),view);
        return view;
    }

    private void showInputMethod(Context context,View view){
        LogUtility.d("abc","---------------------showInputMethod");
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(view,0);
    }

    private void initView(View view){
        text_city= (TextView) view.findViewById(R.id.text_city);
        query_weather_progressBar= (ProgressBar) view.findViewById(R.id.query_weather_progressBar);
        query_weather_frameLayout= (FrameLayout) view.findViewById(R.id.query_weather_frameLayout);
        query_weather_listView = (ListView) view.findViewById(R.id.query_weather_listView);
    }

    class QueryWeatherTask extends AsyncTask<String,Void,List<WeatherInfo>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            query_weather_progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<WeatherInfo> weatherInfoList1) {
            super.onPostExecute(weatherInfoList1);
            query_weather_progressBar.setVisibility(View.GONE);

            if (weatherInfoList1==null || weatherInfoList1.size()==0){
                Toast.makeText(getContext(),"查询失败，请重试",Toast.LENGTH_SHORT).show();
            }else {
                query_weather_frameLayout.setVisibility(View.VISIBLE);
                initData(weatherInfoList1);
                query_weather_listView.setAdapter(new WeatherAdapter(getContext(),
                        R.layout.layout_cardview_item,dataList));

            }
        }

        private void initData(List<WeatherInfo> weatherInfoList) {
            for (int i = 0; i < weatherInfoList.size(); i++) {
                LogUtility.d("abc", weatherInfoList.get(i).toString());
            }

            WeatherAboutUtils aboutUtils =new WeatherAboutUtils();
            WeatherTransformUtils transformUtils =new WeatherTransformUtils();

            String weatherDay;
            String windDirect;
            String windPower;
            if (aboutUtils.isNight()){
                weatherDay =weatherInfoList.get(0).getWeather_night();
                windDirect =weatherInfoList.get(0).getWind_direction_night();
                windPower =weatherInfoList.get(0).getWind_power_night();
            } else {
                weatherDay =weatherInfoList.get(0).getWeather_day();
                windDirect =weatherInfoList.get(0).getWind_direction_day();
                windPower =weatherInfoList.get(0).getWind_power_day();
            }

            String tempDay=weatherInfoList.get(0).getTemperature_day();
            String tempNight=weatherInfoList.get(0).getTemperature_night();
            String dateShow =aboutUtils.getFriendDate();
            int weatherImgId = transformUtils.tranWeatherIcon(weatherDay);

            WeatherData weatherData = new WeatherData(weatherImgId,
                    transformUtils.tranWeather(weatherDay),
                    dateShow,
                    "白天气温："+tempDay+"℃",
                    "夜晚气温："+tempNight+"℃",
                    transformUtils.tranWindPower(windPower),
                    transformUtils.tranWeather(windDirect));
            dataList.add(weatherData);

            String weatherDay1 =weatherInfoList.get(1).getWeather_day();
            String windDirect1 =weatherInfoList.get(1).getWind_direction_day();
            String windPower1 =weatherInfoList.get(1).getWind_power_day();
            String tempDay1=weatherInfoList.get(1).getTemperature_day();
            String tempNight1=weatherInfoList.get(1).getTemperature_night();
            String dateShow1 =aboutUtils.getFriendDateTomorrow();
            int weatherImgId1 = transformUtils.tranWeatherIcon(weatherDay1);
            WeatherData weatherData1 = new WeatherData(weatherImgId1,
                    transformUtils.tranWeather(weatherDay1),
                    dateShow1,
                    "白天气温："+tempDay1+"℃",
                    "夜晚气温："+tempNight1+"℃",
                    transformUtils.tranWindPower(windPower1),
                    transformUtils.tranWeather(windDirect1));
            dataList.add(weatherData1);

            String weatherDay2 =weatherInfoList.get(2).getWeather_day();
            String windDirect2 =weatherInfoList.get(2).getWind_direction_day();
            String windPower2 =weatherInfoList.get(2).getWind_power_day();
            String tempDay2=weatherInfoList.get(2).getTemperature_day();
            String tempNight2=weatherInfoList.get(2).getTemperature_night();
            String dateShow2 =aboutUtils.getFriendDateTodayAfterTomorrow();
            int weatherImgId2 = transformUtils.tranWeatherIcon(weatherDay2);
            WeatherData weatherData2 = new WeatherData(weatherImgId2,
                    transformUtils.tranWeather(weatherDay2),
                    dateShow2,
                    "白天气温："+tempDay2+"℃",
                    "夜晚气温："+tempNight2+"℃",
                    transformUtils.tranWindPower(windPower2),
                    transformUtils.tranWeather(windDirect2));
            dataList.add(weatherData2);
        }

        @Override
        protected List<WeatherInfo> doInBackground(String... params) {
            LogUtility.d("abc","------------------------"+"start QueryWeatherTask");

            String city = params[0];
            Long areaId=getAreaId(city);
            WeatherAboutUtils weatherAboutUtils =new WeatherAboutUtils();
            String weatherForecastUrl = weatherAboutUtils.getUrl(areaId, "forecast_v");
            if (weatherForecastUrl !=null){
                return getWeatherInfo(weatherForecastUrl);
            }
            return null;
        }
    }

    private void showDialog(){
        LayoutInflater inflater =LayoutInflater.from(getContext());
        View dialogView =inflater.inflate(R.layout.layout_query_weather_dialog_view,null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.ic_wb_sunny_black_24dp);
        dialog.setTitle(R.string.query_weather_dialog_title);
        dialog.setView(dialogView);

        alertDialog = dialog.create();
        alertDialog.show();
//        alertDialog.getWindow().setLayout(900, 550);

        Button dialog_btn_cancel = (Button) dialogView.findViewById(R.id.dialog_cancel);
        Button dialog_btn_confirm = (Button) dialogView.findViewById(R.id.dialog_confirm);
        dialog_weather_input= (EditText) dialogView.findViewById(R.id.dialog_weather_input);
        dialog_btn_cancel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        dialog_btn_confirm.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=dialog_weather_input.getText().toString();
                if (city.length()==0){
                    Toast.makeText(getContext(),"请输入城市名",Toast.LENGTH_SHORT).show();
                }else {
                    alertDialog.cancel();
                    QueryWeatherTask task =new QueryWeatherTask();
                    task.execute(city);
                    setText(city);
                }
            }
        });
    }
    private void setText(String city){
        List<AllCityOfChina> chinaList1 = cityOfChinaHelper.queryAreaIdByCity(city);
        if (chinaList1!=null && chinaList1.size()!=0){
            text_city.setVisibility(View.VISIBLE);
            LogUtility.d("abc",chinaList1.get(0).getCounty()+"--->"+
                    chinaList1.get(0).getCity()+"--->"+chinaList1.get(0).getProvince());
            text_city.setText(chinaList1.get(0).getCounty() + "--->" +
                    chinaList1.get(0).getCity() + "--->" + chinaList1.get(0).getProvince());
        }
    }

    private Long getAreaId(String city){
        List<String> stringList =cityOfChinaHelper.loadAllInfo();
//        String newCity =city.substring(0, city.length() - 1);
//        List<AllCityOfChina> chinaList2=cityOfChinaHelper.queryAreaIdByCity(newCity);

        List<AllCityOfChina> chinaList1 = cityOfChinaHelper.queryAreaIdByCity(city);

        if (stringList==null || stringList.size()==0){
            LogUtility.d("abc","说明省市县数据库中没有数据。。。直接返回");
            return null;
        }
        if (chinaList1 ==null || chinaList1.size()==0){
            return null;
        }
        for (int i = 0; i < chinaList1.size(); i++) {
            LogUtility.d("abc",chinaList1.size()+chinaList1.get(i).toString());
        }
        return chinaList1.get(0).getAreaId();
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
}
