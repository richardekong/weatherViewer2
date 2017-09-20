package com.example.adminstrator.weatherviewer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adminstrator on 9/20/2017.
 */


public class DatabaseManager extends SQLiteOpenHelper {
    //instance variable
    private static final int databaseVersion = 10;
    //fields for city History
    private static final String Id="Id";
    private static final String databaseName = "weather_History";
    private static final String tableName="city_history";
    private static final String country_code = "country_code";
    private static final String city = "city";
    private static final String Weather_desc = "Description";
    private static final String temperature = "Temperature";
    private static final String Humidity="Humidity";
    private static final String Date="Date";
    private static final String day="Day";
    private static final String image_url="IconName";




    public DatabaseManager(Context ctx) {
        super(ctx, databaseName, null, databaseVersion);
    }

    //Overriding the onCreate method
    @Override
    public void onCreate(SQLiteDatabase db) {
        //table creation statement for User
        String createTable = "CREATE TABLE IF NOT EXISTS " + tableName + "("+Id+" INTEGER PRIMARY KEY AUTOINCREMENT," + country_code + " VARCHAR ," +
                city + " VARCHAR," + Weather_desc + " VARCHAR," + temperature + " DECIMAL,"+
                Humidity+" DECIMAL,"+Date+" DATETIME,"+day+" VARCHAR,"+ image_url+" VARCHAR"
                + ")";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        //create the table again
        onCreate(db);

    }
    //insert values into city_history table
    public void addCityHistory(cityHistory place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(country_code, place.getCountryCode());
        values.put(city, place.getCity());
        values.put(Weather_desc, place.getWeatherDesc());
        values.put(temperature,place.getTemperature());
        values.put(Humidity,place.getHumidity());
        values.put(Date,place.getDate());
        values.put(day,place.getDay());
        values.put(image_url,place.getIconName());

        db.insert(tableName, null, values);
        db.close();
    }
    //getting single city from city_history
    cityHistory getCityHistoryRecord(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, new String[]{country_code, city, Weather_desc,temperature,
                        Humidity,Date,day,image_url},
                country_code + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        cityHistory place = new cityHistory(cursor.getString(0),cursor.getString(1), cursor.getString(2),Double.parseDouble(cursor.getString(3)),
                Double.parseDouble(cursor.getString(4)),cursor.getString(5),cursor.getString(6),cursor.getString(7));
        cursor.close();
        return place;
    }

    //getting all city Histories
    public List<cityHistory> getAllCityHistories() {
        List<cityHistory> records = new ArrayList<cityHistory>();
        //select all query
        String SelectQuery = "SELECT * FROM " + tableName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQuery, null);
        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                cityHistory record = new cityHistory();
                record.setCountryCode(cursor.getString(0));
                record.setCity(cursor.getString(1));
                record.setWeatherDesc(cursor.getString(2));
                record.setTemperature(Double.parseDouble(cursor.getString(3)));
                record.setHumidity(Double.parseDouble(cursor.getString(4)));
                record.setDate(cursor.getString(5));
                record.setDay(cursor.getString(6));
                record.setIconName(cursor.getString(7));
                //adding contact to list
                records.add(record);
            }
            while (cursor.moveToNext());
        }
        //return contactList
        return records;
    }

    //Deleting one cityHistory Record
    public void deleteCity(cityHistory record) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, country_code + "=?", new String[]{String.valueOf(record.getCountryCode())});
        db.close();
    }

    public int getCount()
    {
        String query="SELECT * FROM "+tableName;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);

        return cursor.getCount();
    }

    public SQLiteDatabase createWritableDb()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db;
    }
}