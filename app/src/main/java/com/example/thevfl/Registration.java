package com.example.thevfl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ReactiveGuide;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Registration extends AppCompatActivity {
      public void gotoLoginpage(View v){
             if(v.getId()==R.id.register){
                layout2.setVisibility(View.INVISIBLE);
                layout1.setVisibility(View.VISIBLE);
             }
             if(v.getId()==R.id.backbtn){
                 layout1.setVisibility(View.INVISIBLE);
                 layout2.setVisibility(View.VISIBLE);
             }
      }
     public void  goToMainPage(View v){
          if(v.getId()==R.id.registerbtn){
              if((password.getText().toString()==repassword.getText().toString())){
                  Toast.makeText(Registration.this, "Password does not match", Toast.LENGTH_SHORT).show();
              }
              else if(name.getText().length()==0 || phonenumber.getText().length()==0 || email.getText().length()==0 || password.getText().length()==0 || repassword.getText().length()==0){
                  Toast.makeText(Registration.this, "Complete the Details", Toast.LENGTH_SHORT).show();
              }
              else{
                 apiCallForVerification(name.getText().toString(),email.getText().toString(),password.getText().toString());
                 //startActivity(new Intent(Registration.this,Mainpage.class));
              }

          }
          if(v.getId()==R.id.logintbn){
             startActivity(new Intent(Registration.this,MyMenu.class));
          }
      }

    private void apiCallForVerification(String name, String email, String password) {

        JSONObject details = new JSONObject();
        try {
            details.put("email", email);
            details.put("password", password);
            details.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //@kuldeep , you need to write the ip address of desktop(server) instead of localhost in the url
        //connect your computer with your mobile hotspot then see the ip address of your computer (by typing ipconfig command in windows cmd)

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://192.168.43.28:8000/api/auth/register", details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //@Kuldeep response is a jsonObject, see how it appears in terminal and act accordingly
                System.out.println("response " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //@kuldeep, if status code is 400 i.e., error simply display error page on ui
                System.out.println("this is error page...." + error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    LinearLayout layout1,layout2;
    int statusCode;
    EditText name,phonenumber,email,password,repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        layout1=findViewById(R.id.registrationform);
        layout2=findViewById(R.id.loginpage);
        name=findViewById(R.id.username);
        phonenumber=findViewById(R.id.phonenumber);
        email=findViewById(R.id.email_id);
        password=findViewById(R.id.password);
        repassword=findViewById(R.id.repassword);
    }
}