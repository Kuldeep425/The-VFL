package com.example.thevfl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import java.util.EventObject;

public class Registration extends AppCompatActivity {
      public void gotoLoginpage(View v){
             if(v.getId()==R.id.register){
                layout2.setVisibility(View.INVISIBLE);
                layout1.setVisibility(View.VISIBLE);
             }
             if(v.getId()==R.id.backbtn){
                layout2.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.INVISIBLE);
             }
      }

   /*String [] items={"cosmetic","Vegetables","Fertilizers","Fruit","Milk centre","Kirana Store","Bakery","other"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String>adapter;

    */
    LinearLayout layout1,layout2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        layout1=findViewById(R.id.registrationform);
        layout2=findViewById(R.id.loginpage);
    }
}