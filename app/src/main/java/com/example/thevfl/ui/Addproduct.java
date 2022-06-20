package com.example.thevfl.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.thevfl.R;
import com.example.thevfl.Registration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.Manifest;

public class Addproduct extends AppCompatActivity {
     ImageView productImg;
     LinearLayout layout;
     Button button;
     Uri seletedimage=null;
     EditText name,category,description,price;
     ProgressDialog dialog;
     SharedPreferences preferences;
     float v=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        layout=findViewById(R.id.linear1);
        productImg=findViewById(R.id.imagepickerforaddingproduct);
        button=findViewById(R.id.addButton);
        name=findViewById(R.id.nameOfProduct);
        category=findViewById(R.id.catOfProduct);
        description=findViewById(R.id.descriptionOfProduct);
        price=findViewById(R.id.priceOfProduct);
        preferences= getSharedPreferences(Registration.PREFERENCE_DETAIL,0);

        layout.setAlpha(v);
        productImg.setAlpha(v);
        button.setAlpha(v);
        name.setAlpha(v);
        category.setAlpha(v);
        description.setAlpha(v);
        price.setAlpha(v);

        layout.setTranslationY(1000);
        button.setTranslationY(1000);
        productImg.setTranslationY(-1000);
        name.setTranslationX(-1000);
        category.setTranslationX(1000);
        description.setTranslationX(-1000);
        price.setTranslationX(1000);


        layout.animate().translationY(0).alpha(1).setStartDelay(700).setDuration(1000).start();
        productImg.animate().translationY(0).alpha(1).setStartDelay(700).setDuration(1000).start();
        button.animate().translationY(0).alpha(1).setStartDelay(1700).setDuration(1000).start();
        name.animate().translationX(0).alpha(1).setStartDelay(1700).setDuration(1000).start();
        category.animate().translationX(0).alpha(1).setStartDelay(1700).setDuration(1000).start();
        description.animate().translationX(0).alpha(1).setStartDelay(1700).setDuration(1000).start();
        price.animate().translationX(0).alpha(1).setStartDelay(1700).setDuration(1000).start();




        productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogToChooseOption();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seletedimage!=null && name.getText().toString().length()!=0 && category.getText().toString().length()!=0 && description.getText().toString().length()!=0 && price.getText().toString().length()!=0){
                    dialog=new ProgressDialog(Addproduct.this);
                    dialog.setMessage("Please wait....");
                    dialog.setCancelable(false);
                    dialog.setTitle("Adding Product");
                    dialog.show();
                    addAProduct(name.getText().toString(),category.getText().toString(),description.getText().toString(),price.getText().toString());
                }
                else{
                    Toast.makeText(Addproduct.this, "Product detail incomplete", Toast.LENGTH_SHORT).show();
                }
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
//    private boolean checkAndRequestPermission(){
//        if(Build.VERSION.SDK_INT >=23){
//              int pr= ActivityCompat.checkSelfPermission(Addproduct.this, Manifest.permission.CAMERA);
//
//        }
//        return true;
//    }
     public void addAProduct(String name,String cat, String desc,String price){

         JSONObject jsonObject=new JSONObject();
         try {
             jsonObject.put("name",name);
             jsonObject.put("image",seletedimage.toString());
             jsonObject.put("seller",preferences.getString("username","xyz"));
             jsonObject.put("price",price);
             jsonObject.put("sellerId",preferences.getString("sellerId","abcd"));
         } catch (JSONException e) {
             e.printStackTrace();
         }
         System.out.println(jsonObject);

         JsonRequest request=new JsonObjectRequest(Request.Method.POST,"http://192.168.168.162:8000/api/product/add/"+preferences.getString("sellerId","hbjsd"), jsonObject, new Response.Listener<JSONObject>() {
             @Override
             public void onResponse(JSONObject response) {
                 System.out.println(response);
                 dialog.dismiss();
                 alertDialog(response);
                 resetField();
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 System.out.println(error.toString());
                 dialog.dismiss();
                 alertDialog(null);
                 resetField();
             }
         });
         RequestQueue queue = Volley.newRequestQueue(this);
         queue.add(request);
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    seletedimage=data.getData();
                    productImg.setImageURI(seletedimage);
                }
        }
    }


    public void alertDialog(JSONObject r){
        AlertDialog.Builder builder=new AlertDialog.Builder(Addproduct.this);

        try {
            if(r!=null) {
                builder.setTitle("Success");
                builder.setIcon(R.drawable.rightcheck);
                builder.setMessage(r.getString("message"));
            }
            else {
                builder.setTitle("Error");
                builder.setIcon(R.drawable.cancelicon);
                builder.setMessage("Some error occured");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setCancelable(false);
        AlertDialog dialog1=builder.create();
        dialog1.show();
    }

    public void resetField(){
        name.setText("");
        category.setText("");
        description.setText("");
        price.setText("");
    }
}