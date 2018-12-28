package com.bignerdranch.android.smarterbutter.entity;

import android.content.Context;
import android.view.LayoutInflater;

import com.bignerdranch.android.smarterbutter.adapter.WeChatAdapter;

import java.util.List;

//微信数据
public class WeChatData {

    //标题
    private String title;
    //出处
    private String source;

    //图片的Uil
    private String imgUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }



}
