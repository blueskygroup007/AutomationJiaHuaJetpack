package com.bluesky.automationjiahua.base;

/**
 * @author BlueSky
 * @date 2021/7/3
 * Description:全局常量
 */
public class AppConstant {
    //sharedpreference常量
    public static final String SP_NAME = "device_sp";
    //public static final String[] DOMAIN = App.getContext().getResources().getStringArray(R.array.spinner_query_domain_database);
    //public static final String[] COLUMN = App.getContext().getResources().getStringArray(R.array.spinner_query_column_database);

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


}
