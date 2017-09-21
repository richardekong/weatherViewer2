package com.example.adminstrator.weatherviewer;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Adminstrator on 9/20/2017.
 */

public class cityHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> items;

    public cityHistoryAdapter(Context context, List<String> items){
        this.context=context;
        this.items=items;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.city_layout_row,parent,false);
        Item item=new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((Item)holder).textView.setText(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class Item extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView textView;

        public Item(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.city_countryrec);
        }
        @Override
        public void onClick(View view) {
            //show weather information dialog
            displayWeatherInfo(items.indexOf(textView.getText())+1);

        }
    }
    public void displayWeatherInfo(int position){
        final Dialog weatherHistory=new Dialog(this.context);
        weatherHistory.setTitle("Weather History Information");
        //initialize variables
        String city="";
        String countrycode="";
        String day="";
        String weatherDesc="";
        String temp="";
        String imgName="";
        String imgUrl="";
        String humidvalue="";
        String Date;
        IconUrl iconUrl=null;
        //add ui controls by associating it with a layout
        weatherHistory.setContentView(R.layout.weatherhistorydialog);
        ImageView imgHist=(ImageView)weatherHistory.findViewById(R.id.historyicon);
        ImageView fab2=(ImageView)weatherHistory.findViewById(R.id.fab2);
        TextView city_country=(TextView)weatherHistory.findViewById(R.id.city_countryhistory),
                day_desc=(TextView)weatherHistory.findViewById(R.id.day_desc),
                temptext=(TextView)weatherHistory.findViewById(R.id.histroytemp),
                humidText=(TextView)weatherHistory.findViewById(R.id.historyhumid);
        //query the database
        DatabaseManager dmgr=new DatabaseManager(this.context);
        SQLiteDatabase sdb=dmgr.getReadableDatabase();
        cityHistory record=dmgr.getCityHistoryRecord(position);
        //fetch weather data from the record object
        countrycode=record.getCountryCode();
        city=record.getCity();
        weatherDesc=record.getWeatherDesc();
        temp=String.valueOf((int)(record.getTemperature()-273.15))+"\u00B0C";//convert to celsius
        humidvalue=NumberFormat.getPercentInstance().format(record.getHumidity()*0.01);
        Date=convertTimeStampToDay((long)Integer.parseInt(record.getDate()));
        day=record.getDay();
        //download weather image
        imgName=record.getIconName();
        imgUrl="http://openweathermap.org/img/w/";
        iconUrl=new IconUrl(imgName,imgUrl);
        new DownloadImage(imgHist).execute(iconUrl.getIconUrl());
        //present weather information
        city_country.setText(city+","+countrycode);
        day_desc.setText(day+": "+weatherDesc);
        temptext.setText(String.format("Temperature: %s",temp));
        humidText.setText(String.format("Humidity: %s",humidvalue));
        //dismiss the dialog on click of the red floating button
        fab2.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                weatherHistory.dismiss();
            }
        });
        //display the dialog
        weatherHistory.show();
    }
    private static String convertTimeStampToDay(long timeStamp) {
        Calendar calendar = Calendar.getInstance(); // create Calendar
        calendar.setTimeInMillis(timeStamp * 1000); // set time
        TimeZone tz = TimeZone.getDefault(); // get device's time zone

        // adjust time for device's time zone
        calendar.add(Calendar.MILLISECOND,
                tz.getOffset(calendar.getTimeInMillis()));

        // SimpleDateFormat that returns the day's name
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMMM yyyy HH:mm:ss");
        return dateFormatter.format(calendar.getTime());
    }
}
