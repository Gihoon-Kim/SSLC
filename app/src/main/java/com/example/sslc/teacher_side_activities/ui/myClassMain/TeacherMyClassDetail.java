package com.example.sslc.teacher_side_activities.ui.myClassMain;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.sslc.R;
import com.example.sslc.databinding.ActivityTeacherMyClassDetailBinding;
import com.google.android.material.tabs.TabLayout;

public class TeacherMyClassDetail extends AppCompatActivity {

    private ActivityTeacherMyClassDetailBinding binding;
    private TeacherMyClassDetailViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainViewModel =
                new ViewModelProvider(this).get(TeacherMyClassDetailViewModel.class);

        binding = ActivityTeacherMyClassDetailBinding.inflate(getLayoutInflater());
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