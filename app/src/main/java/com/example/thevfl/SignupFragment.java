package com.example.thevfl;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupFragment extends Fragment {
    SharedPreferences userDetail;
    EditText username,password,phone,email;
    Button signBtn;
    ProgressDialog progressDialog;
    float v=0;
    public void progressDialogOpen(){
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
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

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://192.168.168.162:8000/api/auth/register", details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //@Kuldeep response is a jsonObject, see how it appears in terminal and act accordingly
               // flag=true;
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
        builder.setMessage("We have sent verification link on "+username.getText().toString()+"\n\nplease verify for further process..😊😊\nPlease verify and login");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        userDetail.edit().putString("name",username.getText().toString())
                         .putString("email",email.getText().toString())
                .putBoolean("hasLoggedIn",false).apply();

        username.setText("");
        password.setText("");
        email.setText("");
        phone.setText("");
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view =(ViewGroup) inflater.inflate(R.layout.signupfragment,container,false);

        userDetail = getActivity().getSharedPreferences(Registration.PREFERENCE_DETAIL, Context.MODE_PRIVATE);

        username=view.findViewById(R.id.username_signup);
        password=view.findViewById(R.id.signup_password);
        phone=view.findViewById(R.id.phoneTxt);
        email=view.findViewById(R.id.signup_email);
        signBtn=view.findViewById(R.id.signBtn);

        username.setTranslationX(800);
        password.setTranslationX(800);
        phone.setTranslationX(800);
        email.setTranslationX(800);
        signBtn.setTranslationX(800);


        username.setAlpha(v);
        password.setAlpha(v);
        signBtn.setAlpha(v);
        phone.setAlpha(v);
        email.setAlpha(v);

        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        phone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        signBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().toString().length()!=0 && email.getText().toString().length()!=0 && password.getText().toString().length()!=0 && phone.getText().toString().length()!=0){
                    progressDialogOpen();
                    apiCallForVerification(username.getText().toString().trim(),email.getText().toString().trim(),password.getText().toString());
                }

                else
                    Toast.makeText(getActivity(), "Please complete profile", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

}
