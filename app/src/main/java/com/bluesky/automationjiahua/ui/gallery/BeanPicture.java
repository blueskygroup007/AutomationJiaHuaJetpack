package com.bluesky.automationjiahua.ui.gallery;

/**
 * @author BlueSky
 * @date 2021/10/11
 * Description:
 */
public class BeanPicture {
    private String id;
    private String name;
    private int picture;

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

    public BeanPicture(String id, String name, int picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }
}
