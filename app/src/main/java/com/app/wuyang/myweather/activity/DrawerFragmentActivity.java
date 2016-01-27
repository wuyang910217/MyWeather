package com.app.wuyang.myweather.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.app.wuyang.myweather.R;
import com.app.wuyang.myweather.fragment.QueryWeatherFragment;
import com.app.wuyang.myweather.fragment.ShowSevenDayWeatherFragment;

public class DrawerFragmentActivity extends AppCompatActivity {
    private FrameLayout frameLayout,frameLayout1,
            frameLayout2,frameLayout3,frameLayout4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer_fragment);

        int position =getIntent().getIntExtra("position",10);
        startFragment(getFragment(position),getFragmentLayoutId(position));

    }
    private void initView(){
        frameLayout= (FrameLayout) findViewById(R.id.showWeatherFragment);
        frameLayout1= (FrameLayout) findViewById(R.id.queryWeatherFragment);
        frameLayout2= (FrameLayout) findViewById(R.id.showAddressFrameLayout);
        frameLayout3= (FrameLayout) findViewById(R.id.showShareFrameLayout);
        frameLayout4= (FrameLayout) findViewById(R.id.showSettingFrameLayout);
    }
    private int getFragmentLayoutId(int position){
        initView();
        switch (position) {
            case 0:
                frameLayout.setVisibility(View.VISIBLE);
                return R.id.showWeatherFragment;
            case 1:
                frameLayout1.setVisibility(View.VISIBLE);
                return R.id.queryWeatherFragment;
            case 2:
                frameLayout2.setVisibility(View.VISIBLE);
                return R.id.showAddressFragment;
            case 3:
                frameLayout3.setVisibility(View.VISIBLE);
                return R.id.showShareFragment;
            case 4:
                frameLayout4.setVisibility(View.VISIBLE);
                return R.id.showSettingFragment;
            default:
                return R.id.showWeatherFragment;
        }
    }

    private Fragment getFragment(int position){
        switch (position){
            case 0:
                return new ShowSevenDayWeatherFragment();
            case 1:
                return new QueryWeatherFragment();
            case 2:
                return new QueryWeatherFragment();
            case 3:
                return new QueryWeatherFragment();
            case 4:
                return new QueryWeatherFragment();
            default:
                return new ShowSevenDayWeatherFragment();
        }
    }

    private void startFragment(Fragment fragment,int id) {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
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
                Toast.makeText(this, "1111", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_exit:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
