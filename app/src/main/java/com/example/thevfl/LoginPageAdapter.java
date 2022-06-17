package com.example.thevfl;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginPageAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;

    public LoginPageAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                loginFragment loginFragment=new loginFragment();
                return loginFragment;
            case 1:
                SignupFragment signInFragment=new SignupFragment();
                return signInFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }

}
