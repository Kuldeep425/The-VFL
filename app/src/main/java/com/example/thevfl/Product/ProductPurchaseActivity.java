package com.example.thevfl.Product;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thevfl.Adapter.ProductFeaturesAdapter;
import com.example.thevfl.R;

import java.util.ArrayList;
import java.util.List;

public class ProductPurchaseActivity extends AppCompatActivity {

    private static final String TAG = "ProductPurchaseActivity";
    private RecyclerView revFeatures;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_purchase);
        Log.d(TAG, "onCreate: Hrere 1");



       List<String> categories = getData();
        revFeatures = findViewById(R.id.rev_features);
        Log.d(TAG, "onCreate: Hrer 2");
        revFeatures.setLayoutManager(new LinearLayoutManager(ProductPurchaseActivity.this));
        Log.d(TAG, "onCreate: here 3");
        revFeatures.setAdapter(new ProductFeaturesAdapter(ProductPurchaseActivity.this, categories));
        Log.d(TAG, "onCreate: Here 4");
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();

        list.add("Feature 1");
        list.add("Feature 2");
        list.add("Feature 3");
        list.add("Feature 4");
        list.add("Feature 5");
        list.add("Feature 6");
        list.add("Feature 7");
        list.add("Feature 8");

        return list;
    }
}