package com.example.bikecompaniesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bikecompaniesapp.model.BikeCompany;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {
    ArrayList<BikeCompany> bikes;
    public MyAdapter(Context context,int layout,ArrayList<BikeCompany> bikes){
        super(context,layout);
        this.bikes=bikes;
    }
    public void update(ArrayList<BikeCompany> results){
        bikes=new ArrayList<>();
        bikes.addAll(results);
        notifyDataSetChanged();
    }
    public class ViewHolder{
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }

    @Override
    public int getCount() {
        return bikes.size();
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View row;
        row=convertView;
        ViewHolder viewHolder;
        if(row==null){
            row= LayoutInflater.from(getContext()).inflate(R.layout.listview_layout,parent,false);
             viewHolder=new ViewHolder();
            viewHolder.textView1=row.findViewById(R.id.name);
            viewHolder.textView2=row.findViewById(R.id.city);
            viewHolder.textView3=row.findViewById(R.id.country);
            row.setTag(viewHolder);
        }
        else{
           viewHolder= (ViewHolder) row.getTag();
        }
        viewHolder.textView1.setText(bikes.get(position).getName());
        viewHolder.textView2.setText(bikes.get(position).getLocation().getCity());
        viewHolder.textView3.setText(bikes.get(position).getLocation().getCountry());
        return row;
    }
}
