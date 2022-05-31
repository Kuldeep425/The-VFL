package com.example.thevfl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class OnBoardingFragment3 extends Fragment implements View.OnClickListener {
    ImageView arrowimageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.onboardingfragment3,container,false);
        arrowimageView=root.findViewById(R.id.arrowimageView);
        arrowimageView.setOnClickListener(this);
        return root;

    }


    @Override
    public void onClick(View v) {
     switch(v.getId()){
         case R.id.arrowimageView:
             arrowimageView.animate().translationX(1000).setDuration(4000).setStartDelay(1000);
             Intent i=new Intent(getActivity(),Registration.class);
             startActivity(i);
             getActivity().finish();
             ((Activity) getActivity()).overridePendingTransition(1, 1);
     }
    }
}
