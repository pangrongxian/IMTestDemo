package com.zyys.zyysdemo.bean;

import android.widget.ImageView;

/**
 * Created by Administrator on 2016/3/4.
 */
public class VoipInfo {

    private String imgUrl;
    private String name;

    public VoipInfo(String imgUrl, String name) {
        this.imgUrl = imgUrl;
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VoipInfo{" +
                "imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
