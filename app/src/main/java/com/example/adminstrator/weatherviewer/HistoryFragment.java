package com.example.adminstrator.weatherviewer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adminstrator on 9/19/2017.
 */

public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;
    private DatabaseManager dmgr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup v, Bundle bundle){

        View rootView=inflater.inflate(R.layout.city_history_layout,v,false);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<String>Items=retreiveCities();
        recyclerView.setAdapter(new cityHistoryAdapter(getActivity(),Items));

        return rootView;
    }

    public List<String>retreiveCities(){

        //instantiate DatabaseManager
        dmgr=new DatabaseManager(getActivity());
        SQLiteDatabase sqdb=dmgr.getReadableDatabase();
        List<String>cities=new ArrayList<>(dmgr.getCount());
        Cursor cursor=sqdb.rawQuery("SELECT city, country_code from city_history",null);
        int counter=0;
        if (!(cursor.equals(null))&& cursor.moveToFirst()){
                do{
                    String city=cursor.getString(cursor.getColumnIndex("city"));
                    String country_code=cursor.getString(cursor.getColumnIndex("country_code"));
                    cities.add(city+", "+country_code);
                    counter++;
                }while (cursor.moveToNext());
        }

        return cities;

    }
}
