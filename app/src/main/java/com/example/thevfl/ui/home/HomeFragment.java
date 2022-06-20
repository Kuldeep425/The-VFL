package com.example.thevfl.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thevfl.Adapter.ProductAdapter;
import com.example.thevfl.Model.CategoryShowingData;
import com.example.thevfl.MyMenu;
import com.example.thevfl.R;
import com.example.thevfl.databinding.CategoryItemBinding;
import com.example.thevfl.databinding.FragmentHomeBinding;
import com.example.thevfl.ui.Addproduct;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private RecyclerView recyclerView;
    private ArrayList<CategoryShowingData> recyclerDataArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        recyclerView=root.findViewById(R.id.category_recyclerview);

        // created new array list..
        recyclerDataArrayList=new ArrayList<>();

        // added data to array list
        recyclerDataArrayList.add(new CategoryShowingData("Electronics",R.drawable.my_logo));
        recyclerDataArrayList.add(new CategoryShowingData("Daily use",R.drawable.my_logo));
        recyclerDataArrayList.add(new CategoryShowingData("Food",R.drawable.my_logo));
        recyclerDataArrayList.add(new CategoryShowingData("Clothes",R.drawable.my_logo));
        recyclerDataArrayList.add(new CategoryShowingData("Education",R.drawable.education));
        ProductAdapter adapter=new ProductAdapter(recyclerDataArrayList,getActivity());

        // setting grid layout manager to implement grid view.
        // in this method '2' represents number of columns to be displayed in grid view.
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),2);

        // at last set adapter to recycler view.
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}