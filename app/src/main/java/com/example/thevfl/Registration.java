package com.example.thevfl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ReactiveGuide;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

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
import com.example.thevfl.ui.dashboard.DashboardFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
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
    public static SharedPreferences userDetail;
    public static  String PREFERENCE_DETAIL="MyPrefsFile";
    SharedPreferences.Editor myedit;
    TabLayout layout;
    ViewPager viewPager;
    FloatingActionButton google,fb,twitter;
    float v=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
       userDetail =getSharedPreferences(Registration.PREFERENCE_DETAIL,0);
        if(userDetail.getBoolean("hasLoggedIn",false)){
            startActivity(new Intent(Registration.this, MyMenu.class));
        }
        layout=findViewById(R.id.tabLayout);
        viewPager =findViewById(R.id.viewPager);
        google=findViewById(R.id.google);
        fb=findViewById(R.id.fb);
        twitter=findViewById(R.id.twitter);
        layout.addTab(layout.newTab().setText("Login"));
        layout.addTab(layout.newTab().setText("SignUp"));
        layout.setTabGravity(layout.GRAVITY_FILL);

        //shared preferences

        System.out.println(layout.getSelectedTabPosition());
        final LoginPageAdapter adapter=new LoginPageAdapter(getSupportFragmentManager(),this,layout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(layout));



        fb.setTranslationY(300);
        google.setTranslationY(300);
        twitter.setTranslationY(300);

        fb.setAlpha(v);
        google.setAlpha(v);
        twitter.setAlpha(v);

        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
    }



}
