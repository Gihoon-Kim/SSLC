package com.sslc.sslc.common_fragment_activities.ui.myClassMain;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sslc.sslc.R;
import com.sslc.sslc.common_fragment_activities.SectionsPagerAdapter;
import com.sslc.sslc.databinding.ActivityMyClassDetailBinding;

public class MyClassDetail extends AppCompatActivity {

    private ActivityMyClassDetailBinding binding;
    private MyClassDetailViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainViewModel =
                new ViewModelProvider(this).get(MyClassDetailViewModel.class);

        binding = ActivityMyClassDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        initViews();
    }

    private void initViews() {

        binding.title.setText(getIntent().getStringExtra(getString(R.string.class_title)));
        mainViewModel.setClassTitle(getIntent().getStringExtra(getString(R.string.class_title)));
    }
}