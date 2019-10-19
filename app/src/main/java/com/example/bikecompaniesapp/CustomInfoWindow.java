package com.example.bikecompaniesapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
   private View view;
   private Context context;

    public CustomInfoWindow(Context context) {
        this.view = view;
        view= LayoutInflater.from(context).inflate(R.layout.infowindow_layout,null);
    }
    public void setValues(Marker marker,View view){
        String title=marker.getTitle();
        TextView t1=view.findViewById(R.id.title);
        t1.setText(title);

        String snippet=marker.getSnippet();
        TextView t2=view.findViewById(R.id.info);
        t2.setText(snippet);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        setValues(marker,view);
        return  view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        setValues(marker,view);
        return view;
    }
}
