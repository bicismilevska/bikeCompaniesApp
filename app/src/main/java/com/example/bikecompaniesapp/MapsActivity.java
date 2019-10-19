package com.example.bikecompaniesapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bikecompaniesapp.model.BikeCompany;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private double dLatitude = 0.0;
    private double dLongitude = 0.0;
    private String name;
    private String city;
    private String countryCode;
    private GoogleMap mMap;
    private Context context;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getApplicationContext();
        setContentView(R.layout.maps_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Maps");
        Bundle bundle = getIntent().getExtras();
        dLatitude = bundle.getDouble("latitude");
        dLongitude = bundle.getDouble("longitude");
        name = bundle.getString("name");
        city = bundle.getString("city");
        countryCode = bundle.getString("country");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng latlng = new LatLng(dLatitude, dLongitude);
        String snippet = "Name: " + name + "\n" + "City: " + city + "\n" + "Country code: " + countryCode;
        MarkerOptions options = new MarkerOptions()
                .position(latlng)
                .title("Bike Company")
                .snippet(snippet);
        marker = mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16.0f));

        mMap.setInfoWindowAdapter(new CustomInfoWindow(MapsActivity.this));


    }

}
