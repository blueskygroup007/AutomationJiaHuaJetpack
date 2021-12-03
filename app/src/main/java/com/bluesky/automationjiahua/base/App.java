package com.bluesky.automationjiahua.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author BlueSky
 * @date 2021/7/3
 * Description:Application类,获取公共context
 */
public class App extends Application {
    public static final String HUACHAN_DEVICE_DATA_BASE_NAME = "huachan.db";
    public static final String INTER_LOCK_DATA_BASE_NAME = "liansuo.db";
    private static Context mContext;
    private SharedPreferences mPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mPreferences = getSharedPreferences(AppConstant.SP_NAME, MODE_PRIVATE);
        //拷贝assets中的db到database目录
        //AssetsDatabaseManager.initManager(mContext);
        //AssetsDatabaseManager assetsDatabaseManager=AssetsDatabaseManager.getManager();

        //初始化LogUtils
        //全局TAG


    }



    public static Context getContext() {
        return mContext;
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }


}
