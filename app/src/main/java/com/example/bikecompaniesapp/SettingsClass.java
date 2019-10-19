package com.example.bikecompaniesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SettingsSlicesContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Set;

public class SettingsClass extends AppCompatActivity {
    public static final String TAG="SettingsClass";
    private Button setbutton;
    private EditText editText;
    private int minutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");
        setbutton=findViewById(R.id.setexpiry);
        editText=findViewById(R.id.expiry);
        setbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // int minutes=Integer.parseInt(editText.getText().toString());
                Log.i(TAG,"use chose "+ minutes + " minutes");
                if(editText.getText().toString().isEmpty()){
                 showAlert("Empty information","You haven't entered anything!");
                }
                else if(!(editText.getText().toString().matches("[0-9]+"))){
                    showAlert("Wrong input ","You should use numbers!");
                }
                else{
                    int minutes=Integer.parseInt(editText.getText().toString());
                    if(minutes>=15 && minutes<=60){
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SettingsClass.this);
                        prefs.edit().putInt("expiry", minutes).commit();
                        finish();
                    }
                    else{

                        showAlert("Wrong Input","Numbers should be between 15 and 60 minutes");
                    }

                }
            }
        });

    }
    public void showAlert(String title,String message){
        AlertDialog alertDialog = new AlertDialog.Builder(SettingsClass.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
