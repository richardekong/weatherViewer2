package com.example.adminstrator.weatherviewer;

import android.graphics.drawable.Icon;

/**
 * Created by Adminstrator on 9/19/2017.
 */

public class IconUrl {
    private String iconName;
    private String iconUrl;

    //create class constructor
    public IconUrl(String iconName,String iconUrl){
        this.iconName=iconName;
        this.iconUrl=iconUrl+""+iconName+".png";
    }
    public String getIconName(){
        return this.iconName;
    }
    public String getIconUrl(){
        return this.iconUrl;
    }
    private void setIconName(String name){
            this.iconName=name;
    }
    private void setIconUrl(String url){
        this.iconUrl=url;
    }
}
