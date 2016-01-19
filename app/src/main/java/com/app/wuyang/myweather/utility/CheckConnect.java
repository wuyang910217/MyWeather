package com.app.wuyang.myweather.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by wuyang on 16-1-8.
 * 程序开始时检查是否当前有网络连接；
 */
public class CheckConnect {
    private ConnectivityManager manager;
    private NetworkInfo info;
    private Context mContext;
    public CheckConnect(Context context) {
        this.mContext = context;
    }

    public  boolean isConnect(){
        manager = (ConnectivityManager) mContext.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager!= null){
            info =manager.getActiveNetworkInfo();
            if (info != null && info.isConnected()){
                if (info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }

        }
        return false;
    }
}
