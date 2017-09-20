package com.example.adminstrator.weatherviewer;

/**
 * Created by Adminstrator on 9/20/2017.
 */

public class cityHistory {

    private String country_code;
    private String city;
    private String weatherDesc;
    private Double temperature;
    private Double Humidity;
    private String Date;
    private String day;
    private String IconName;

    //default constructor
    public cityHistory(){

    }
    public cityHistory(String country_code,String city, String weatherDesc,
                       double temperature, double Humidity, String Date, String day, String IconName)
    {
            this.country_code=country_code;
            this.city=city;
            this.weatherDesc=weatherDesc;
            this.temperature=temperature;
            this.Humidity=Humidity;
            this.Date=Date;
            this.day=day;
            this.IconName=IconName;
    }

    public String getCountryCode(){
        return this.country_code;
    }
    public void setCountryCode(String ccode){
        this.country_code=ccode;
    }
    public String getCity(){
        return this.city;
    }
    public void setCity(String city){
        this.city=city;
    }
    public String getWeatherDesc(){
        return this.weatherDesc;
    }
    public void setWeatherDesc(String weatherDesc){
        this.weatherDesc=weatherDesc;
    }
    public Double getTemperature(){
        return this.temperature;
    }
    public void setTemperature(double temperature){
        this.temperature=temperature;
    }
    public Double getHumidity(){
        return this.Humidity;
    }
    public void setHumidity(double Humidity){
        this.Humidity=Humidity;
    }
    public String getDate(){
        return this.Date;
    }
    public void setDate(String date){
        this.Date=date;
    }
    public String getDay(){
        return this.day;
    }
    public void setDay(String day){
        this.day=day;
    }
    public String getIconName(){
        return IconName;
    }
    public void setIconName(String IconUrl){
        this.IconName=IconName;
    }

}
