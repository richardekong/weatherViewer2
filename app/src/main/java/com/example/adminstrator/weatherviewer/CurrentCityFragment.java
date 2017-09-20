package com.example.adminstrator.weatherviewer;

import android.app.ActionBar;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Adminstrator on 9/18/2017.
 */

public class CurrentCityFragment extends Fragment {


    private EditText cityinput;
    private ImageView weatherIcon;
    private TextView citytext, temptext, day_condition, humidityText;
    private FloatingActionButton fab;
    private  String base_url="http://api.openweathermap.org";
    private String api_key="d2a6b21c943e38d9e44edcc03c9912ad";
    private String countrycode;
    private String dayOfWeek;
    private String description;
    private String temp;
    private String humidlevel;
    private String city;
    private String imgname;
    private Map<String,Bitmap> bitmaps=new HashMap<>();
    private IconUrl iconUrl;
    //create views
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup v, Bundle bundle)
    {
        final View rootView=inflater.inflate(R.layout.currentcity,v,false);
        //instantiate content for first tab
         cityinput=rootView.findViewById(R.id.cityinput);
         weatherIcon=rootView.findViewById(R.id.weathericon);
         citytext=rootView.findViewById(R.id.citytext);
         temptext=rootView.findViewById(R.id.temptext);
         day_condition=rootView.findViewById((R.id.day_condition));
         humidityText=rootView.findViewById(R.id.humiditytext);

        FloatingActionButton fab=(FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityquery=cityinput.getText().toString();
                requestWeatherInfo(cityquery,api_key);

            }
        });
        return rootView;
    }

    public void requestWeatherInfo(String cityquery, String APPID){
        weatherApi.factory.getInstance().getWeatherInfo(cityquery,APPID).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                        //get city, country code from api
                        city=response.body().getName();
                        countrycode=response.body().getSys().getCountry();
                        //get the datetime from the api and extracts the day of the week.
                        String datetime=String.valueOf(response.body().getDt());
                        dayOfWeek=convertTimeStampToDay(response.body().getDt());
                        //get weather description
                        description=response.body().getWeather().get(0).getDescription();
                        //get the temperature in kelvin from the api
                        temp=String.valueOf((int)(response.body().getMain().getTemp()-273.15))+"\u00B0C";
                        //get the humidity level from the api
                        humidlevel=String.valueOf(NumberFormat.getPercentInstance().format(response.body().getMain().getHumidity() / 100.0));

                        //get icon from the api
                        imgname=response.body().getWeather().get(0).getIcon();
                        //Log.d("Remote image name",imgname);
                        iconUrl=new IconUrl(imgname,"http://openweathermap.org/img/w/");
                        //download weather condition image and load it to the ImageView synchronously
                        new DownloadImage(weatherIcon).execute(iconUrl.getIconUrl());
                        citytext.setText((city+", "+countrycode));
                        day_condition.setText(dayOfWeek+": "+description);
                        temptext.setText(temp);
                        humidityText.setText("Humidity: "+humidlevel);
                        //save weather information in database
                        cityHistory history=new cityHistory(countrycode,city,description,response.body().getMain().getTemp()
                                ,response.body().getMain().getHumidity(),datetime,dayOfWeek,imgname);
                        saveToHistoryLog(history);
                }
            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Snackbar.make(getActivity().findViewById(R.id.curcityrootlayout),
                        R.string.invalid_url, Snackbar.LENGTH_LONG).show();
            }
        });
    }
    // convert timestamp to a day's name (e.g., Monday, Tuesday, ...)
    private static String convertTimeStampToDay(long timeStamp) {
        Calendar calendar = Calendar.getInstance(); // create Calendar
        calendar.setTimeInMillis(timeStamp * 1000); // set time
        TimeZone tz = TimeZone.getDefault(); // get device's time zone

        // adjust time for device's time zone
        calendar.add(Calendar.MILLISECOND,
                tz.getOffset(calendar.getTimeInMillis()));

        // SimpleDateFormat that returns the day's name
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE");
        return dateFormatter.format(calendar.getTime());
    }

    public void saveToHistoryLog(cityHistory history){
        //create an instance of the database manager
        DatabaseManager dmgr=new DatabaseManager(getActivity());
        if (!(history.equals(null))){
            //insert history records in the database
            dmgr.addCityHistory(history);
        }

    }
}
