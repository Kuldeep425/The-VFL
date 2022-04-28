package com.example.thevfl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

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
                        sharedPreferences.edit().clear().commit();
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
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerView=navigationView.getHeaderView(0);
        SharedPreferences sharedPreferences= getSharedPreferences(Registration.PREFERENCE_DETAIL,0);
        nav_username=headerView.findViewById(R.id.nav_username);
        nav_username.setText(sharedPreferences.getString("username","Username"));
        nav_emialId=headerView.findViewById(R.id.nav_emailId);
        nav_emialId.setText(sharedPreferences.getString("email","user12@gmail.com"));
        nav_image=headerView.findViewById(R.id.nav_imageView);
        Picasso.get().load("https://st1.bollywoodlife.com/wp-content/uploads/2021/08/Rashmika-Mandanna.jpg").into(nav_image);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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