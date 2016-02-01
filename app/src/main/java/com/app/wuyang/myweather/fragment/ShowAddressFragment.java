package com.app.wuyang.myweather.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.db.DbQuery;

/**
 * Created by wuyang on 16-1-28.
 * 显示详细地址的fragment
 */
public class ShowAddressFragment extends Fragment {
    private TextView addressName,addressLatitudeName,addressLongitudeName,
            addressCityCodeName,addressProvinceName,addressCityName,addressCountyName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.item_address);
    }

    private void initView(View view){
        addressName= (TextView) view.findViewById(R.id.address_name);
        addressLatitudeName= (TextView) view.findViewById(R.id.address_latitude_name);
        addressLongitudeName= (TextView) view.findViewById(R.id.address_longitude_name);
        addressCityCodeName= (TextView) view.findViewById(R.id.address_city_code_name);
        addressProvinceName= (TextView) view.findViewById(R.id.address_province_name);
        addressCityName= (TextView) view.findViewById(R.id.address_city_name);
        addressCountyName= (TextView) view.findViewById(R.id.address_county_name);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_show_address_fragment,container,false);
        initView(view);
        setView();
        return view;
    }

    private void setView(){
        DbQuery dbQuery =new DbQuery(getContext());
        addressName.setText(dbQuery.getLocationContent(DbQuery.ADDRESS));
        addressLatitudeName.setText(dbQuery.getLocationContent(DbQuery.LATITUDE));
        addressLongitudeName.setText(dbQuery.getLocationContent(DbQuery.LONGITUDE));
        addressCityCodeName.setText(dbQuery.getLocationContent(DbQuery.CITY_CODE));
        addressProvinceName.setText(dbQuery.getLocationContent(DbQuery.PROVINCE));
        addressCityName.setText(dbQuery.getLocationContent(DbQuery.CITY));
        addressCountyName.setText(dbQuery.getLocationContent(DbQuery.COUNTY));
    }
}
