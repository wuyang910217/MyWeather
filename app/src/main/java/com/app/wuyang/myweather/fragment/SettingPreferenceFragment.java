package com.app.wuyang.myweather.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.app.wuyang.myweather.R;

/**
 * Created by wuyang on 16-2-2.
 *
 */
public class SettingPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_setting);
    }
}
