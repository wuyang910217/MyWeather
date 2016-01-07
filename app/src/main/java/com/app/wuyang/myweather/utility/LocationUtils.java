package com.app.wuyang.myweather.utility;

import com.amap.api.location.AMapLocation;

/**
 * Created by wuyang on 16-1-4.
 */
public class LocationUtils {

    public final static int MSG_LOCATION_START = 0;
    public final static int MSG_LOCATION_FINISH = 1;
    public final static int MSG_LOCATION_STOP = 2;

    public synchronized static String getLocationStr(AMapLocation aMapLocation) {
//        StringBuffer stringBuffer = new StringBuffer();
        String currentAddress=null;
        if (aMapLocation != null){

            if (aMapLocation.getErrorCode()==0){

                currentAddress=aMapLocation.getDistrict()+","+aMapLocation.getCity();
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


            } else {
                currentAddress=aMapLocation.getErrorInfo();
//                stringBuffer.append("定位失败" + "\n");
//                stringBuffer.append("错误码:" + aMapLocation.getErrorCode() + "\n");
//                stringBuffer.append("错误信息:" + aMapLocation.getErrorInfo() + "\n");
//                stringBuffer.append("错误描述:" + aMapLocation.getLocationDetail() + "\n");
            }
        }
//        return stringBuffer.toString();
        return currentAddress;
    }
}
