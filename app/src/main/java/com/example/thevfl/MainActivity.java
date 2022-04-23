package com.example.thevfl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
     public void playscreen(){
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 //This method will be executed once the timer is over
                 // Start your app main activity
                 Intent i = new Intent(MainActivity.this, TestingAct.class);
                 startActivity(i);
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