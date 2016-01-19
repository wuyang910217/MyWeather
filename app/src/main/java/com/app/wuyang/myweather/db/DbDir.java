package com.app.wuyang.myweather.db;

import android.content.Context;
import android.os.Environment;

import com.app.wuyang.myweather.utility.LogUtility;

import java.io.File;

/**
 * Created by wuyang on 16-1-14.
 * 把数据库文件储存在sd目录;
 */
public class DbDir {


    public static String getDbName(Context context,String databaseName) {
        String storageDir = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        String appDir = File.separator + "Android" + File.separator + "data";
        String packageName = context.getPackageName();
        String dir = File.separator + "data" + File.separator;
        boolean isSdcardEnable = false;
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            isSdcardEnable = true;
        }

        String dbDir;
        if (isSdcardEnable) {
            dbDir = storageDir + appDir + File.separator + packageName + dir;
            LogUtility.d("abc", "在sd卡创建db的目录" + dbDir);
        } else {
            dbDir = context.getFilesDir().getPath() + File.separator + "data" + packageName + dir;
            LogUtility.d("abc", "在手机上创建db的目录" + dbDir);
        }
        File dbFolder = new File(dbDir);
        if (!dbFolder.exists()) {
            LogUtility.d("abc", "说明没有此目录...在这创建");
            dbFolder.mkdirs();
        }
        return dbDir + databaseName;
    }
}
