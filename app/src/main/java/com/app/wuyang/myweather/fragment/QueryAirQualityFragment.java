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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.data.AirQuality;
import com.app.wuyang.myweather.db.AirQualityHelper;
import com.app.wuyang.myweather.utility.ConnectUtils;
import com.app.wuyang.myweather.utility.LogUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by wuyang on 16-1-28.
 */
public class QueryAirQualityFragment extends Fragment {
    private AlertDialog alertDialog;
    private EditText dialog_quality_input;
    private ProgressBar query_progress_bar;
    private TextView query_air_quality_area,query_air_quality,query_pollutant,query_pm25,
            query_pm10,query_o3,query_so2,query_no2,query_co,query_time;
    private final static String URL_PATH=
            "http://www.pm25.in/api/querys/aqi_details.json?city=";
    private final static String URL_TOKEN=
            "&token=5j1znBVAsnSf5xQyNQyq&stations=no";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.item_air_quality);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_query_air_quality_fragment, container, false);
        initView(view);
        showInputMethod(getContext(), view);
        showDialog();
        return view;
    }

    private void showInputMethod(Context context,View view){
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(view, 0);
    }

    private void initView(View view){
        query_progress_bar = (ProgressBar) view.findViewById(R.id.query_progress_bar);
        query_air_quality = (TextView) view.findViewById(R.id.query_air_quality);
        query_air_quality_area = (TextView) view.findViewById(R.id.query_air_quality_area);
        query_pm10 = (TextView) view.findViewById(R.id.query_pm10);
        query_pm25 = (TextView) view.findViewById(R.id.query_pm25);
        query_pollutant = (TextView) view.findViewById(R.id.query_pollutant);
        query_time = (TextView) view.findViewById(R.id.query_time);
        query_co = (TextView) view.findViewById(R.id.query_co);
        query_so2 = (TextView) view.findViewById(R.id.query_so2);
        query_o3 = (TextView) view.findViewById(R.id.query_o3);
        query_no2 = (TextView) view.findViewById(R.id.query_no2);
    }
    private void showDialog(){
        LayoutInflater inflater =LayoutInflater.from(getContext());
        View view =inflater.inflate(R.layout.layout_query_quality_dialog_view,null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.ic_grain_black_24dp);
        dialog.setTitle(R.string.query_quality_dialog_title);
        dialog.setView(view);

        alertDialog =dialog.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(850, 550);

        Button dialog_btn_cancel = (Button) view.findViewById(R.id.dialog_cancel);
        Button dialog_btn_confirm = (Button) view.findViewById(R.id.dialog_confirm);
        dialog_quality_input = (EditText) view.findViewById(R.id.dialog_quality_input);
        dialog_btn_cancel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        dialog_btn_confirm.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city =dialog_quality_input.getText().toString();
                AirQualityHelper airQualityHelper =AirQualityHelper.getInstance(getContext());
                List<String> list =airQualityHelper.loadCity();
                if (city.length()==0){
                    Toast.makeText(getContext(),"请输入要查询的城市名",Toast.LENGTH_SHORT).show();
                }
                if (list==null || list.size()==0){
                    Toast.makeText(getContext(),"查询失败，请稍后重试",Toast.LENGTH_SHORT).show();
                } else if (!list.contains(city)) {
                    Toast.makeText(getContext(),"此城市还没有空气质量监测点",Toast.LENGTH_SHORT).show();
                } else {
                    alertDialog.cancel();
                    new QueryQualityTask().execute(city);
                }
            }
        });
    }

    private class QueryQualityTask extends AsyncTask<String,Void,AirQuality>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            query_progress_bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(AirQuality airQuality) {
            super.onPostExecute(airQuality);
            query_progress_bar.setVisibility(View.GONE);
            if (airQuality!=null){
                LogUtility.d("abc", "--------------查询成功 准备显示出来");
                setView(airQuality);
            }else {
                LogUtility.d("abc","--------------查询失败");
                Toast.makeText(getContext(),"查询失败，请稍后再试",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected AirQuality doInBackground(String... params) {
            String cityName =params[0];
            AirQualityHelper airQualityHelper =AirQualityHelper.getInstance(getContext());
            List<String> list =airQualityHelper.loadCity();
            if (list!=null && list.size()!=0){
                if (list.contains(cityName)){
                    LogUtility.d("abc", "--------------do in background ");
                    try {
                        String encodeCity = URLEncoder.encode(cityName, "utf-8");
                        String urlPath= URL_PATH+encodeCity+URL_TOKEN;
                        return getAirQuality(urlPath);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
            LogUtility.d("abc", "--------------do in background  说明没有airquality数据 返回null ");
            return null;
        }
    }

    private void setView(AirQuality airQuality) {
        query_air_quality.setText("空气质量：         " +airQuality.getQuality());
        query_pollutant.setText("首要污染物：      " +airQuality.getPollutant());
        query_air_quality_area.setText("城市名称：         " +airQuality.getCity());
        query_co.setText("一氧化碳含量：   " + airQuality.getCo());
        query_no2.setText("二氧化氮含量：   "+airQuality.getNo2());
        query_so2.setText("二氧化硫含量：   "+airQuality.getSo2());
        query_o3.setText("臭氧含量：         "+airQuality.getO3());
        query_pm10.setText("Pm10含量：      "+airQuality.getPm10());
        query_pm25.setText("Pm2.5含量：     "+airQuality.getPm25());
        String time=airQuality.getTime();
        query_time.setText("发布时间：         "+time.substring(11,16));
    }

    private AirQuality getAirQuality(String urlPath){
        if (urlPath==null){
            LogUtility.d("abc", "测试 说明getAirQuality获得的url为空，直接返回null....");
            return null;
        }
        AirQuality airQuality =new AirQuality();
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
                LogUtility.d("abc", "说明getAirQuality解析json数据成功");
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

}
