package com.bluesky.automationjiahua.base;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * @author BlueSky
 * @date 2021/7/3
 * Description:Application类,获取公共context
 */
public class App extends Application {
    public static App instance;
    public static final String HUACHAN_DEVICE_DATA_BASE_NAME = "huachan.db";
    public static final String INTER_LOCK_DATA_BASE_NAME = "liansuo.db";
    private SharedPreferences mPreferences;
    public static boolean DETAIL_PAGE_SIMPLIFY = false;
    public static String TAG;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mPreferences = getSharedPreferences(AppConstant.SP_NAME, MODE_PRIVATE);
        DETAIL_PAGE_SIMPLIFY = mPreferences.getBoolean("detail_page_simplify", false);


        //初始化LogUtils
        //全局TAG
        TAG = getPackageName();
    }

    public void setSimply(boolean value) {
        DETAIL_PAGE_SIMPLIFY = value;
        mPreferences.edit().putBoolean(AppConstant.SP_PARAM_SIMPLE, value).apply();

    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }


}
