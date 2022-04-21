package com.example.sslc.teacher_side_activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sslc.R;
import com.example.sslc.databinding.ActivityTeacherNotificationDetailBinding;

import java.util.Objects;

public class TeacherNotificationDetailActivity extends AppCompatActivity {

    private ActivityTeacherNotificationDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTeacherNotificationDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {

        binding.tvNewsTitle.setText(getIntent().getStringExtra(getString(R.string.news_title)));
        Objects.requireNonNull(binding.content.tvNewsDescription).setText(getIntent().getStringExtra(getString(R.string.news_description)));
        Objects.requireNonNull(binding.content.tvNewsDate).setText(getIntent().getStringExtra(getString(R.string.news_createdAt)));
    }
}