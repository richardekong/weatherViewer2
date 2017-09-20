package com.example.adminstrator.weatherviewer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Item)holder).textView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class Item extends RecyclerView.ViewHolder{
        TextView textView;
        public Item(View itemView){
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.city_countryrec);
        }
    }
}
