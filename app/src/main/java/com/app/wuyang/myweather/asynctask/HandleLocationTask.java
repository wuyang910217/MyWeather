package com.app.wuyang.myweather.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.amap.api.location.AMapLocation;
import com.app.wuyang.myweather.data.LocationInfo;
import com.app.wuyang.myweather.db.LocationInfoHelper;
import com.app.wuyang.myweather.utility.LogUtility;

/**
 * Created by wuyang on 16-1-17.
 * 保存定位信息于数据库；
 */
public class HandleLocationTask extends AsyncTask<AMapLocation,Void,Void> {

    private Context mContext;

    public HandleLocationTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(AMapLocation... params) {
        AMapLocation aMapLocation=params[0];
//        得到定位的城市信息
        LocationInfo locationInfo =getLocation(aMapLocation);
//       存入数据库
        saveLocationInfoToDb(locationInfo);

        return null;
    }

    private void saveLocationInfoToDb(LocationInfo locationInfo){
        LocationInfoHelper helper =new LocationInfoHelper(mContext);
        LocationInfo info =helper.loadLocationInfo();
        if (locationInfo!=null){
            if (info!=null){
                helper.deleteLocationInfo();
                helper.saveLocationInfo(locationInfo);
                LogUtility.d("abc", "更新位置数据库----------");
            }else {
                helper.saveLocationInfo(locationInfo);
                LogUtility.d("abc", "写入位置数据库----------");
            }
        }else {
            LogUtility.d("abc", "说明得到的定位信息LocationInfo为null----------");
        }

    }


    private LocationInfo getLocation(AMapLocation aMapLocation){
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                LocationInfo locationInfo =new LocationInfo();

                String district = aMapLocation.getDistrict();
                String city = aMapLocation.getCity();
                String province=aMapLocation.getProvince();

                int length = district.length() - 1;
                int length1 = city.length() - 1;
                int length2 =province.length()-1;

                String newDistrict = district.substring(0, length);
                String newCity = city.substring(0, length1);
                String newProvince = province.substring(0,length2);

                locationInfo.setLongitude(""+aMapLocation.getLongitude());
                locationInfo.setLatitude("" + aMapLocation.getLatitude());
                locationInfo.setCounty(district);
                locationInfo.setCity(city);
                locationInfo.setProvince(province);
                locationInfo.setNewCounty(newDistrict);
                locationInfo.setNewCity(newCity);
                locationInfo.setNewProvince(newProvince);
                locationInfo.setAddress(aMapLocation.getAddress());
                locationInfo.setCityCode(aMapLocation.getCityCode());
                locationInfo.setAdCode(aMapLocation.getAdCode());

                LogUtility.d("abc", "通过定位得到的城市数组：" + locationInfo.toString());
                return locationInfo;
            } else {
                LogUtility.d("abc","说明定位失败，失败原因为 "+aMapLocation.getErrorInfo());
                return null;
            }
        } else {
            LogUtility.d("abc","说明定位失败，可能没网或其他，没有解析到city");
            return null;
        }
    }
}
