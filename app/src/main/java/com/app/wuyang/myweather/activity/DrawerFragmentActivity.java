package com.app.wuyang.myweather.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.fragment.QueryAirQualityFragment;
import com.app.wuyang.myweather.fragment.QueryWeatherFragment;
import com.app.wuyang.myweather.fragment.SettingFragment;
import com.app.wuyang.myweather.fragment.SettingPreferenceFragment;
import com.app.wuyang.myweather.fragment.ShowAddressFragment;
import com.app.wuyang.myweather.fragment.ShowSevenDayWeatherFragment;
import com.app.wuyang.myweather.utility.LogUtility;

public class DrawerFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer_fragment);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        int position = getIntent().getIntExtra("position", 10);
        startFragment(getFragment(position));
    }

    private Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return new ShowSevenDayWeatherFragment();
            case 1:
                return new QueryWeatherFragment();
            case 2:
                return new QueryAirQualityFragment();
            case 3:
                return new ShowAddressFragment();
            case 4:
                return new SettingFragment();
            default:
                return new SettingFragment();
        }
    }

    private void startFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.baseFrameLayout, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.action_exit:
                LogUtility.d("abc", "----------" + getComponentName() + "action_exit");
                finish();
                break;
            case android.R.id.home:
                LogUtility.d("abc", "----------" + getComponentName() + "android.R.id.home");
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        LogUtility.d("abc", "----------" + getComponentName() + "onBackPressed");
        super.onBackPressed();
    }
}

