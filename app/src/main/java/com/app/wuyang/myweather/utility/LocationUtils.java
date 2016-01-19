package com.app.wuyang.myweather.utility;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by wuyang on 16-1-4.
 *
 * 已经无用了
 */
public class LocationUtils implements AMapLocationListener {

    private Context mContext;
    public final static int MSG_LOCATION_FINISH = 1;

    public LocationUtils(Context context) {
        this.mContext = context;
        initLocationClient();
    }

    private void initLocationClient() {
        AMapLocationClientOption mapLocationClientOption = new AMapLocationClientOption();
        mapLocationClientOption.setLocationMode(
                AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        AMapLocationClient mapLocationClient = new AMapLocationClient(mContext);
        mapLocationClient.setLocationListener(this);
        mapLocationClient.setLocationOption(mapLocationClientOption);
        mapLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            Message message = new Message();
            message.what = MSG_LOCATION_FINISH;
            message.obj = aMapLocation;
            handler.sendMessage(message);
        }
    }
    public static final Handler handler = new Handler() {
        String[] strings = new String[4];
        public String[] handleMsg(Message msg) {
            if (msg.what == MSG_LOCATION_FINISH) {
                AMapLocation mapLocation = (AMapLocation) msg.obj;
                if (mapLocation != null) {
                    if (mapLocation.getErrorCode() == 0) {
                        String district = mapLocation.getDistrict();
                        String city = mapLocation.getCity();
                        int length = district.length() - 1;
                        int length1 = city.length() - 1;
                        String newDistrict = district.substring(0, length);
                        String newCity = city.substring(0, length1);
                        strings[0] = district;
                        strings[1] = city;
                        strings[2] = newDistrict;
                        strings[3] = newCity;
                    }
                }

            }
            return strings;
        }

    };
}


//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what ==MSG_LOCATION_FINISH){
//                AMapLocation mapLocation= (AMapLocation) msg.obj;
//                String[] strings = new String[4];
//                if (mapLocation != null){
//                    if (mapLocation.getErrorCode()==0){
//                        String district = mapLocation.getDistrict();
//                        String city =mapLocation.getCity();
//                        int length = district.length()-1;
//                        int length1 = city.length()-1;
//                        String newDistrict=district.substring(0,length);
//                        String newCity = city.substring(0,length1);
//                        strings[0]=district;
//                        strings[1]=city;
//                        strings[2]=newDistrict;
//                        strings[3]=newCity;

//                stringBuffer.append("定位成功" + "\n");
//                stringBuffer.append("定位类型: " + aMapLocation.getLocationType() + "\n");
//                stringBuffer.append("经    度    : " + aMapLocation.getLongitude() + "\n");
//                stringBuffer.append("纬    度    : " + aMapLocation.getLatitude() + "\n");
//                stringBuffer.append("精    度    : " + aMapLocation.getAccuracy() + "米" + "\n");
//                stringBuffer.append("提供者    : " + aMapLocation.getProvider() + "\n");
//                stringBuffer.append("国家          :"+aMapLocation.getCountry()+ "\n");
//                stringBuffer.append("省份            : " +aMapLocation.getProvince()+ "\n");
//                stringBuffer.append("市            : " + aMapLocation.getCity() + "\n");
//                stringBuffer.append("城市编码 : " + aMapLocation.getCityCode() + "\n");
//                stringBuffer.append("区            : " + aMapLocation.getDistrict() + "\n");
//                stringBuffer.append("区域 码   : " + aMapLocation.getAdCode() + "\n");
//                stringBuffer.append("地    址    : " + aMapLocation.getAddress() + "\n");
//                stringBuffer.append("兴趣点    : " + aMapLocation.getPoiName() + "\n");
//                    }
//            else {
//                currentAddress=aMapLocation.getErrorInfo();
//                stringBuffer.append("定位失败" + "\n");
//                stringBuffer.append("错误码:" + aMapLocation.getErrorCode() + "\n");
//                stringBuffer.append("错误信息:" + aMapLocation.getErrorInfo() + "\n");
//                stringBuffer.append("错误描述:" + aMapLocation.getLocationDetail() + "\n");
//                }
//            }
//        }
//
//    }
//
//
//
//    }

