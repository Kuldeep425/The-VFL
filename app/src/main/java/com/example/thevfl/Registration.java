package com.example.thevfl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ReactiveGuide;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    boolean flag=false;
    ProgressDialog progressDialog;
    LinearLayout layout1,layout2;
    public static SharedPreferences userDetail;
    public static  String PREFERENCE_DETAIL="MyPrefsFile";
    SharedPreferences.Editor myedit;
    int statusCode;
    EditText name,phonenumber,email,password,repassword;
    public void progressDialogOpen(){
        progressDialog=new ProgressDialog(Registration.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
    }
      public void gotoLoginpage(View v){
             if(v.getId()==R.id.register){
                layout2.setVisibility(View.INVISIBLE);
                //layout2.animate().alpha(0).setDuration(2000);
              //  layout1.animate().alpha(1).setDuration(2000);
                layout1.setVisibility(View.VISIBLE);
             }
             if(v.getId()==R.id.backbtn){
                 layout1.setVisibility(View.INVISIBLE);
                 layout2.setVisibility(View.VISIBLE);
                // layout1.animate().alpha(0).setDuration(2000);
                 //layout2.animate().alpha(1).setDuration(2000);
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
                  progressDialogOpen();
                 apiCallForVerification(name.getText().toString(),email.getText().toString(),password.getText().toString());
              }

          }
          if(v.getId()==R.id.logintbn){
               progressDialogOpen();
              apiCallForLoginVerification(email.getText().toString(),password.getText().toString());
            //startActivity(new Intent(Registration.this,MyMenu.class));
              progressDialog.dismiss();
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

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://192.168.246.162:8000/api/auth/register", details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //@Kuldeep response is a jsonObject, see how it appears in terminal and act accordingly
                flag=true;
                System.out.println("response " + response.toString());
                progressDialog.dismiss();
                onPause();
                showAlertDialogmessageOnResponse();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //@kuldeep, if status code is 400 i.e., error simply display error page on ui
                progressDialog.dismiss();
                showAlertDialogmessageOnErrorResponse();
                System.out.println("this is error page...." + error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    private void apiCallForLoginVerification(String email, String password) {

        JSONObject details = new JSONObject();
        try {
            details.put("email", email);
            details.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //@kuldeep , you need to write the ip address of desktop(server) instead of localhost in the url
        //connect your computer with your mobile hotspot then see the ip address of your computer (by typing ipconfig command in windows cmd)

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://192.168.246.162:8000/api/auth/login", details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //@Kuldeep response is a jsonObject, see how it appears in terminal and act accordingly
               // flag=true;

               // System.out.println(response.ge);

                System.out.println("response " + response.toString());
                myedit.putBoolean("hasLoggedIn",true);
                myedit.commit();
                startActivity(new Intent(Registration.this,MyMenu.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //@kuldeep, if status code is 400 i.e., error simply display error page on ui
                showAlertDialogmessageOnErrorResponse();
                System.out.println("this is error page...." + error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    private void showAlertDialogmessageOnErrorResponse() {
          AlertDialog.Builder builder=new AlertDialog.Builder(Registration.this);
          builder.setTitle("\t\tError");
          builder.setIcon(R.drawable.cancelicon);
          builder.setMessage("\t\tSomething went wrong\n"+"\t\tor\n"+"\t\tEmail already registered");
          builder.setCancelable(true);
        builder.setPositiveButton(
                "Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "Login?",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        layout1.setVisibility(View.INVISIBLE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                });
          AlertDialog alertDialog=builder.create();
          alertDialog.show();
    }
    private void showAlertDialogmessageOnResponse() {
        AlertDialog.Builder builder=new AlertDialog.Builder(Registration.this);
        builder.setTitle("Successfully sent");
        builder.setIcon(R.drawable.rightcheck);
        builder.setMessage("We have sent verification link on "+email.getText().toString()+"\n\nplease verify for further process..😊😊");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }



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

    @Override
    protected void onPause() {
        super.onPause();
        userDetail=getSharedPreferences(PREFERENCE_DETAIL,0);
        myedit=userDetail.edit();
       // myedit.putBoolean("hasLoggedIn",true);
        myedit.putString("username",name.getText().toString());
        myedit.putString("email",email.getText().toString());
        myedit.commit();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor myedit=userDetail.edit();
        super.onBackPressed();
        myedit.putString("username",name.getText().toString());
        myedit.putString("email",email.getText().toString());
        myedit.apply();
    }
}