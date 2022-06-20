package com.example.thevfl.ui.gallery;

import android.app.AuthenticationRequiredException;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.thevfl.MainActivity;
import com.example.thevfl.Model.MyProductData;
import com.example.thevfl.MyMenu;
import com.example.thevfl.R;
import com.example.thevfl.Registration;
import com.example.thevfl.databinding.FragmentGalleryBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    RecyclerView recyclerView;
    private ArrayList<MyProductData>myProductData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        recyclerView=root.findViewById(R.id.myproduct_recyclerview);
        setMyProduct();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void setMyProduct() {
        SharedPreferences sharedPreferences  = getActivity().getSharedPreferences(Registration.PREFERENCE_DETAIL, Context.MODE_PRIVATE);
        String user_id= sharedPreferences.getString("sellerId",null);
        String url="http://192.168.168.162:8000/api/product/view/"+user_id;
        System.out.println(user_id);
        System.out.println(url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                addAdapter(response);
                System.out.println(response);
                System.out.println("response " + response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //@kuldeep, if status code is 400 i.e., error simply display error page on ui
                //showAlertDialogmessageOnErrorResponse();
                System.out.println("this is error page...." + error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }
    private void addAdapter(JSONObject jsonObject){
       myProductData=new ArrayList<>();


    }
}