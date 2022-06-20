package com.example.thevfl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thevfl.Model.CategoryShowingData;
import com.example.thevfl.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.holder> {

    private ArrayList<CategoryShowingData> categoryDataArrayList;
    private Context mcontext;

    public ProductAdapter(ArrayList<CategoryShowingData> recyclerDataArrayList, Context mcontext) {
        this.categoryDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.list_category,parent,false);
        return new holder(view);
    }



    @Override
    public void onBindViewHolder(holder holder, int position) {
        // Set the data to textview and imageview.
        CategoryShowingData recyclerData = categoryDataArrayList.get(position);
        holder.courseTV.setText(recyclerData.getCategory());
        holder.courseIV.setImageResource(recyclerData.getImageUrl());
    }




    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return categoryDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    class holder extends RecyclerView.ViewHolder {

        private TextView courseTV;
        private ImageView courseIV;

        public holder(@NonNull View itemView) {
            super(itemView);
            courseTV = itemView.findViewById(R.id.idTVCourse);
            courseIV = itemView.findViewById(R.id.idIVcourseIV);
        }
    }
}
