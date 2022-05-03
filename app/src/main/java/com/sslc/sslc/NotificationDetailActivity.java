package com.sslc.sslc;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sslc.sslc.databinding.ActivityNotificationDetailBinding;

import java.util.Objects;

public class NotificationDetailActivity extends AppCompatActivity {

    private ActivityNotificationDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotificationDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {

        binding.tvNewsTitle.setText(getIntent().getStringExtra(getString(R.string.news_title)));
        Objects.requireNonNull(binding.content.tvNewsDescription).setText(getIntent().getStringExtra(getString(R.string.news_description)));
        Objects.requireNonNull(binding.content.tvNewsDate).setText(getIntent().getStringExtra(getString(R.string.news_createdAt)));
    }
}