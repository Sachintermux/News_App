package com.sna.newsapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sna.newsapp.fragments.LoginFragment;
import com.sna.newsapp.fragments.SignUpFragment;

public class ViewPager_Adapter extends FragmentStateAdapter {

    public ViewPager_Adapter( @NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

    }

    @NonNull
    @Override
    public Fragment createFragment( int position ) {
        switch (position){
            case 1:
                return new SignUpFragment();



        }
        return new LoginFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}