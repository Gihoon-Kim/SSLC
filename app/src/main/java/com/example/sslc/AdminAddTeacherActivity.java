package com.example.sslc;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sslc.databinding.ActivityAdminAddTeacherBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class AdminAddTeacherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.sslc.databinding.ActivityAdminAddTeacherBinding binding = ActivityAdminAddTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FloatingActionButton fab = binding.fabAddTeacher;
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }
}