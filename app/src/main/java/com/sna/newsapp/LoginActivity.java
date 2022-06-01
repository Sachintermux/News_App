package com.sna.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sna.newsapp.adapters.ViewPager_Adapter;
import com.sna.newsapp.fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginData {
private ViewPager2 viewPager2;
private ViewPager_Adapter adapter;
private TabLayout tabLayout;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout_loginA);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ViewPager_Adapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected( TabLayout.Tab tab ) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected( TabLayout.Tab tab ) {
            }

            @Override
            public void onTabReselected( TabLayout.Tab tab ) {
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected( int position ) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

    @Override
    public void Success() {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }
}