package com.bluesky.automationjiahua.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * @author BlueSky
 * @date 2021/12/3
 * Description:
 */
@Entity
public class InterLock implements Serializable {
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

    public InterLock(@NonNull Integer number, String domain, String device_name, String tag, String control_value, String interlock_device_name, String interlock_device_tag, String action_type, String remark) {
        this.number = number;
        this.domain = domain;
        this.device_name = device_name;
        this.tag = tag;
        this.control_value = control_value;
        this.interlock_device_name = interlock_device_name;
        this.interlock_device_tag = interlock_device_tag;
        this.action_type = action_type;
        this.remark = remark;
    }

    @NonNull
    public Integer getNumber() {
        return number;
    }

    public void setNumber(@NonNull Integer number) {
        this.number = number;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getControl_value() {
        return control_value;
    }

    public void setControl_value(String control_value) {
        this.control_value = control_value;
    }

    public String getInterlock_device_name() {
        return interlock_device_name;
    }

    public void setInterlock_device_name(String interlock_device_name) {
        this.interlock_device_name = interlock_device_name;
    }

    public String getInterlock_device_tag() {
        return interlock_device_tag;
    }

    public void setInterlock_device_tag(String interlock_device_tag) {
        this.interlock_device_tag = interlock_device_tag;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "InterLock{" +
                "number=" + number +
                ", domain='" + domain + '\'' +
                ", device_name='" + device_name + '\'' +
                ", tag='" + tag + '\'' +
                ", control_value='" + control_value + '\'' +
                ", interlock_device_name='" + interlock_device_name + '\'' +
                ", interlock_device_tag='" + interlock_device_tag + '\'' +
                ", action_type='" + action_type + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
