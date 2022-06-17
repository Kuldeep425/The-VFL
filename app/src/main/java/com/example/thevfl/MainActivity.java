package com.example.thevfl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSetMetaData;

public class MainActivity extends AppCompatActivity {
    public static SharedPreferences userDetails;
    SharedPreferences.Editor editor;
     public void playscreen(){
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 //This method will be executed once the timer is over
                 // Start your app main activity
                 SharedPreferences sharedPreferences=getSharedPreferences(Registration.PREFERENCE_DETAIL,0);
                 boolean hasLoggedIn=sharedPreferences.getBoolean("hasLoggedIn",false);
                 if(hasLoggedIn){
                     startActivity(new Intent(MainActivity.this,MyMenu.class));
                 }
                 else {
                     Intent i = new Intent(MainActivity.this, Introductory.class);
                     startActivity(i);
                 }
                 // close this activity
                 finish();
             }
         }, 4000);
     }
     boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!flag){
            playscreen();
            flag=true;
        }

    }


}