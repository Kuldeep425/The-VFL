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
import com.example.thevfl.Model.MyProductData;
import com.example.thevfl.R;

import java.util.ArrayList;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.holder> {
    private ArrayList<MyProductData> MyProductDataArrayList;
    private Context mcontext;

    public MyProductAdapter(ArrayList<MyProductData> recyclerDataArrayList, Context mcontext) {
        this.MyProductDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }
    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.my_product,parent,false);
        return new MyProductAdapter.holder(view);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {

        MyProductData myProductData=MyProductDataArrayList.get(position);
        holder.productName.setText(myProductData.getProductName());
        holder.categoryName.setText(myProductData.getCategoryName());
        holder.price.setText(myProductData.getPrice());
        holder.description.setText(myProductData.getDescription());

    }

    @Override
    public int getItemCount() {
        return MyProductDataArrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName,categoryName,price,description;
        public holder(View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.image_myproduct);
            productName=itemView.findViewById(R.id.productname_myproduct);
            categoryName=itemView.findViewById(R.id.categoryname_myproduct);
            price=itemView.findViewById(R.id.price_myproduct);
            description=itemView.findViewById(R.id.description_myproduct);
        }
    }
}
