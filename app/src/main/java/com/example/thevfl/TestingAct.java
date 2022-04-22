package com.example.thevfl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thevfl.Category.AddCategoryActivity;
import com.example.thevfl.Category.ListCategoriesActivity;
import com.example.thevfl.Home.HomeActivity;
import com.example.thevfl.Product.AddProductActivity;
import com.example.thevfl.Product.ListProductsActivity;

public class TestingAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing_act);

    }

    public void viewMyProducts(View view) {
        startActivity(new Intent(TestingAct.this, ListProductsActivity.class));
    }

    public void addProduct(View view) {
        startActivity(new Intent(TestingAct.this, AddProductActivity.class));
    }

    public void pastOrders(View view) {
        startActivity(new Intent(TestingAct.this, AddCategoryActivity.class));
    }

    public void myCart(View view) {
        startActivity(new Intent(TestingAct.this, ListCategoriesActivity.class));
    }

    public void back2Home(View view) {
        startActivity(new Intent(TestingAct.this, HomeActivity.class));
    }
}