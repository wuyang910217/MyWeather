package com.app.wuyang.myweather.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.wuyang.myweather.R;

/**
 * Created by wuyang on 16-1-21.
 */
public class QueryWeatherFragment extends Fragment {
    private TextView dialog_title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_query_weather_fragment, container, false);
        dialog_title= (TextView) view.findViewById(R.id.dialog_title);
        dialog_title.setText("ddddddd");
        return view;
    }
}
