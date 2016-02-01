package com.app.wuyang.myweather.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.data.WeatherData;

import java.util.List;

/**
 * Created by wuyang on 16-1-3.
 */
public class WeatherAdapter extends ArrayAdapter<WeatherData> {

    private int resourceId;

    public WeatherAdapter(Context context, int resource, List<WeatherData> objects) {
        super(context, resource, objects);
        this.resourceId=resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeatherData data =getItem(position);
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.weatherImg= (ImageView) convertView.findViewById(R.id.weatherImgId);
            viewHolder.dateShow= (TextView) convertView.findViewById(R.id.dateShow);
            viewHolder.weatherDay= (TextView) convertView.findViewById(R.id.weatherDay);
            viewHolder.tempDay= (TextView) convertView.findViewById(R.id.tempDay);
            viewHolder.tempNight= (TextView) convertView.findViewById(R.id.tempNight);
            viewHolder.windPower= (TextView) convertView.findViewById(R.id.windPower);
            viewHolder.windDirect= (TextView) convertView.findViewById(R.id.windDirect);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.dateShow.setText(data.getDateShow());
        viewHolder.weatherDay.setText(data.getWeatherDay());
        viewHolder.tempDay.setText(data.getTempDay());
        viewHolder.tempNight.setText(data.getTempNight());
        viewHolder.windPower.setText(data.getWindPower());
        viewHolder.windDirect.setText(data.getWindDirect());
        viewHolder.weatherImg.setImageResource(data.getWeatherImgId());

        return convertView;
    }

    private class ViewHolder {
        private ImageView weatherImg;
        private TextView weatherDay;
        private TextView dateShow;
        private TextView tempDay;
        private TextView tempNight;
        private TextView windPower;
        private TextView windDirect;
    }

}
