package com.example.thevfl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class loginFragment extends Fragment {
    EditText username,password;
    Button loginBtn;
    float v=0;
    public static SharedPreferences userDetail;
    SharedPreferences.Editor myedit;
    private void apiCallForLoginVerification(String email, String password) {

        JSONObject details = new JSONObject();
        try {
            details.put("email", email);
            details.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,"http://192.168.168.162:8000/api/auth/login", details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("response " + response.toString());
                userDetail = getActivity().getSharedPreferences(Registration.PREFERENCE_DETAIL, Context.MODE_PRIVATE);
                myedit= userDetail.edit();
                myedit.putBoolean("hasLoggedIn",true);
                try {
                    myedit.putString("sellerId",response.getString("_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                myedit.commit();
                startActivity(new Intent(getActivity(),MyMenu.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //@kuldeep, if status code is 400 i.e., error simply display error page on ui
                showAlertDialogmessageOnErrorResponse();
                System.out.println("this is error page...." + error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }
    private void showAlertDialogmessageOnErrorResponse() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
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
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    private void showAlertDialogmessageOnResponse() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Successfully sent");
        builder.setIcon(R.drawable.rightcheck);
        builder.setMessage("We have sent verification link on "+username.getText().toString()+"\n\nplease verify for further process..😊😊");
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
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
        ViewGroup view=(ViewGroup) inflater.inflate(R.layout.loginfragment,container,false);
        username=view.findViewById(R.id.email_id);
        password=view.findViewById(R.id.password);
        loginBtn=view.findViewById(R.id.loginBtn);
        username.setTranslationX(800);
        password.setTranslationX(800);
        loginBtn.setTranslationX(800);

        username.setAlpha(v);
        password.setAlpha(v);
        loginBtn.setAlpha(v);

        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        loginBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCallForLoginVerification(username.getText().toString().trim(),password.getText().toString().trim());
            }
        });
         return view;
    }
}
