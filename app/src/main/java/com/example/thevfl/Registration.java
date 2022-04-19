package com.example.thevfl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.EventObject;

public class Registration extends AppCompatActivity {
    public  void displayForm(View v){
        layout1=findViewById(R.id.buttons);
        layout1.setVisibility(View.INVISIBLE);
        layout2=findViewById(R.id.buyerregistrationform);
        layout3=findViewById(R.id.sellerregistrationform);

//        System.out.println(v.callOnClick());
        if(v.getId()==R.id.buyerbtn){
            layout2.setVisibility(View.VISIBLE);
        }
        if(v.getId()==R.id.sellerbtn){
            layout3.setVisibility(View.VISIBLE);
        }
    }


    LinearLayout layout1,layout2,layout3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

    }
}