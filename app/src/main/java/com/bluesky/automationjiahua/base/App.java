package com.bluesky.automationjiahua.base;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, String> DOMAIN_DISPLAY ;
    {
        DOMAIN_DISPLAY=new HashMap<>();
        DOMAIN_DISPLAY.put("chuleng", "初冷");
        DOMAIN_DISPLAY.put("cubenzhengliu", "粗苯蒸馏");
        DOMAIN_DISPLAY.put("dianbujiaoyou", "电捕焦油");
        DOMAIN_DISPLAY.put("gufengji", "鼓风机");
        DOMAIN_DISPLAY.put("jiaoyouanshui", "焦油氨水");
        DOMAIN_DISPLAY.put("liuan", "硫铵");
        DOMAIN_DISPLAY.put("tuoliu", "脱硫");
        DOMAIN_DISPLAY.put("youku", "油库");
        DOMAIN_DISPLAY.put("zhengan", "蒸氨");
        DOMAIN_DISPLAY.put("zhonglengxiben", "终冷洗苯");
        DOMAIN_DISPLAY.put("yuchuli", "预处理");
        DOMAIN_DISPLAY.put("fenshao", "焚烧");
        DOMAIN_DISPLAY.put("jinghua", "净化");
        DOMAIN_DISPLAY.put("zhuanhua", "转化");
        DOMAIN_DISPLAY.put("ganxiweixi", "干吸尾吸");

    }

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
