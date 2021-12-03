package com.bluesky.automationjiahua.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author BlueSky
 * @date 2021/12/3
 * Description:
 */
@Entity
public class InterLock {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "number")
    public Integer number;
    @ColumnInfo(name = "domain")
    public String domain;
    @ColumnInfo(name = "device_name")
    public String device_name;
    @ColumnInfo(name = "tag")
    public String tag;
    @ColumnInfo(name = "control_value")
    public String control_value;
    @ColumnInfo(name = "interlock_device_name")
    public String interlock_device_name;
    @ColumnInfo(name = "interlock_device_tag")
    public String interlock_device_tag;
    @ColumnInfo(name = "action_type")
    public String action_type;
    @ColumnInfo(name = "remark")
    public String remark;

}
