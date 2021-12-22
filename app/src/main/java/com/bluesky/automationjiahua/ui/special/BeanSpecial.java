package com.bluesky.automationjiahua.ui.special;

/**
 * @author BlueSky
 * @date 2021/12/22
 * Description:特殊仪表的item的Bean
 */
public class BeanSpecial {
    private String id;
    private String name;
    private int picture;
    //备用扩展属性
    private String extra;

    public BeanSpecial(String id, String name, int picture, String extra) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.extra = extra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
