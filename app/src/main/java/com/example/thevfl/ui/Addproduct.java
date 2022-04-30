package com.example.thevfl.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.thevfl.R;

import java.util.jar.Manifest;

public class Addproduct extends AppCompatActivity {
     ImageView productImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        productImg=findViewById(R.id.imagepickerforaddingproduct);
        productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogToChooseOption();
            }
        });
    }
    private void showDialogToChooseOption(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Addproduct.this);
        LayoutInflater inflater=getLayoutInflater();
        View dialogview=inflater.inflate(R.layout.alerttochooseimage,null);
        alertDialog.setCancelable(false);
        alertDialog.setView(dialogview);
        AlertDialog dialog=alertDialog.create();
        dialog.show();
        ImageView camera=dialog.findViewById(R.id.dialog_camera);
        ImageView gallery=dialog.findViewById(R.id.dialog_gallery);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageFromCamera();
                dialog.cancel();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageFromGallery();
                dialog.cancel();
            }
        });
    }
    private void uploadImageFromCamera(){

    }
    private void uploadImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }
    private boolean checkAndRequestPermission(){
        if(Build.VERSION.SDK_INT >=23){
              int pr= ActivityCompat.checkSelfPermission(Addproduct.this, Manifest.permission.CAMERA);

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    Uri seletedimage=data.getData();
                    productImg.setImageURI(seletedimage);
                }
        }
    }
}