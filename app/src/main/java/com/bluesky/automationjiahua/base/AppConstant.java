package com.bluesky.automationjiahua.base;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BlueSky
 * @date 2021/7/3
 * Description:全局常量
 */
public class AppConstant {
    //sharedpreference常量
    public static final String SP_NAME = "device_sp";
    //详情页的简化参数:default="false" 和 "true"
    public static final String SP_PARAM_SIMPLE = "detail_page_simplify";

    //public static final String[] DOMAIN = App.getContext().getResources().getStringArray(R.array.spinner_query_domain_database);
    //public static final String[] COLUMN = App.getContext().getResources().getStringArray(R.array.spinner_query_column_database);

    public static final Map<String, String> DOMAIN_DISPLAY = new HashMap<>();

    /*sqlite的表名,不要用下面的DOMAIN*/
    public static final String[] TABLE_NAME = new String[]{
            "chuleng",
            "cubenzhengliu",
            "dianbujiaoyou",
            "gufengji",
            "jiaoyouanshui",
            "liuan",
            "tuoliu",
            "youku",
            "zhengan",
            "zhonglengxiben",
            "yuchuli",
            "fenshao",
            "jinghua",
            "zhuanhua",
            "ganxiweixi"};

    /*用作room数据库的区域*/
    public static final String[] DOMAIN = new String[]{
            "",//代表所有区域
            "chuleng",
            "cubenzhengliu",
            "dianbujiaoyou",
            "gufengji",
            "jiaoyouanshui",
            "liuan",
            "tuoliu",
            "youku",
            "zhengan",
            "zhonglengxiben",
            "yuchuli",
            "fenshao",
            "jinghua",
            "zhuanhua",
            "ganxiweixi"};

    public static final String[] COLUMN = new String[]{"tag", "affect", "parameter",
            "name", "range", "standard", "mode", "pipe", "type", "count", "install",
            "factory", "remark", "brand", "date"};

    public static final String[] SEARCH = new String[]{"tag", "affect", "name", "standard", "type"};


    public AppConstant() {
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
}
