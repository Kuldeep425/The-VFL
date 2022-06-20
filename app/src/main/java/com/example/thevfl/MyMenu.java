package com.example.thevfl;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.textclassifier.TextLinks;
import android.widget.TextView;

import com.example.thevfl.ui.Addproduct;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thevfl.databinding.ActivityMenuBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyMenu extends AppCompatActivity {
    TextView nav_username,nav_emialId;
    CircleImageView nav_image;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;



    public void uploadNewImage(View v){
        changeImage();
    }

    private void logoutUser(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MyMenu.this,R.style.AlertDialogStyle);
        builder.setMessage("Do you want to logout ?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        SharedPreferences sharedPreferences=getSharedPreferences(Registration.PREFERENCE_DETAIL,0);
                        sharedPreferences.edit().putBoolean("hasLoggedIn",false).commit();
                        startActivity(new Intent(MyMenu.this,Registration.class));
                        finish();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog=builder.create();
      //  alertDialog.setView(R.style.AlertDialogStyle);
        alertDialog.show();
    }

    private void changeImage(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MyMenu.this);
        builder.setMessage("Do you want to change profile image?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        builder.setNegativeButton(
                "No",
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

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        View navView=binding.getRoot();
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMenu.toolbar);
        binding.appBarMenu.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(MyMenu.this, Addproduct.class));
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerView=navigationView.getHeaderView(0);
        SharedPreferences sharedPreferences= getSharedPreferences(Registration.PREFERENCE_DETAIL,0);
        nav_username=headerView.findViewById(R.id.nav_username);
        nav_username.setText(sharedPreferences.getString("name","Username"));
        nav_emialId=headerView.findViewById(R.id.nav_emailId);
        nav_emialId.setText(sharedPreferences.getString("email","user12@gmail.com"));
        nav_image=headerView.findViewById(R.id.nav_imageView);
        Picasso.get().load("https://telugu.cdn.zeenews.com/telugu/sites/default/files/RashmikaMandannaHot.jpg").into(nav_image);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        setProductOnFronted();
    }

    private void setProductOnFronted() {




            //@kuldeep , you need to write the ip address of desktop(server) instead of localhost in the url
            //connect your computer with your mobile hotspot then see the ip address of your computer (by typing ipconfig command in windows cmd)

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://192.168.168.162:8000/api/product/get-all-category-sorted", null,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //@Kuldeep response is a jsonObject, see how it appears in terminal and act accordingly
                    // flag=true;
                    System.out.println(response);
                    System.out.println("response " + response.toString());
                   // SignupFragment.class.progressDialog.dismiss();
                    onPause();
                   // SignupFragment.class.showAlertDialogmessageOnResponse();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //@kuldeep, if status code is 400 i.e., error simply display error page on ui
                    //SignupFragment.class.progressDialog.dismiss();
                   // SignupFragment.class.showAlertDialogmessageOnErrorResponse();
                    System.out.println("this is error page...." + error.toString());
                }
            });
            RequestQueue queue = Volley.newRequestQueue(MyMenu.this);
            queue.add(request);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void logout_user(MenuItem item) {
        logoutUser();
    }
}