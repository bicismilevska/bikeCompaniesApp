package com.example.bikecompaniesapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.SearchManager;
import android.support.v7.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import com.example.bikecompaniesapp.model.BikeCompany;
import com.example.bikecompaniesapp.model.FinalClass;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
public static final String TAG="MainActivity";
    private RetrofitClass retrofitClass;
    private ListView listView;
    private ArrayList<BikeCompany> bikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       retrofitClass = RetrofitClass.getInstance(getApplicationContext());
        listView = findViewById(R.id.listview);
        loadData();
    }
    public void loadData(){
        retrofitClass.getApi().getBikeCompanies()
                .enqueue(new Callback<FinalClass>() {
            @Override
            public void onResponse(Call<FinalClass> call, Response<FinalClass> response) {
                FinalClass finalClass=response.body();
                Log.d(TAG, "onResponse: " + response.body());
                if(response.raw().networkResponse() != null){
                    Log.d(TAG, "onResponse: response is from NETWORK...");
                }
                else if(response.raw().cacheResponse() != null
                        && response.raw().networkResponse() == null){
                    Log.d(TAG, "onResponse: response is from CACHE...");
                }
                ArrayList<BikeCompany> list= (ArrayList<BikeCompany>) finalClass.getNetworks();
                bikes=list;
                listView.setAdapter(new MyAdapter(MainActivity.this,R.layout.listview_layout,list));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //passing the data to MapsActivity
                        Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                        intent.putExtra("latitude",bikes.get(i).getLocation().getLatitude());
                        intent.putExtra("longitude",bikes.get(i).getLocation().getLongitude());
                        intent.putExtra("name",bikes.get(i).getName());
                        intent.putExtra("city",bikes.get(i).getLocation().getCity());
                        intent.putExtra("country",bikes.get(i).getLocation().getCountry());
                        startActivity(intent);
                    }
                });

                                      }

            @Override
            public void onFailure(Call<FinalClass> call, Throwable t) {
                Log.i(TAG,"something happened");
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.searchItem);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                s=s.toLowerCase();
                 ArrayList<BikeCompany> results=new ArrayList<>();
                 for(BikeCompany bikeCompany:bikes){
                     if(bikeCompany.getName().toLowerCase().contains(s))
                         results.add(bikeCompany);

                 }
                ((MyAdapter)listView.getAdapter()).update(results);

                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.settings){
            Intent i = new Intent(MainActivity.this, SettingsClass.class);
            startActivityForResult(i, 1);


            return true;
        }
        return false;
    }

}



