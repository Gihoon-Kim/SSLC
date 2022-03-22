package com.example.sslc;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.sslc.databinding.ActivityAdminTeacherDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class AdminTeacherDetailActivity extends AppCompatActivity {

    private ActivityAdminTeacherDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminTeacherDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setBasicUI();

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    public void setBasicUI() {

        Intent intent = getIntent();
        String teacherName = intent.getStringExtra("teacherName");
        binding.tvTeacherName.setText(teacherName);
        String teacherClass = intent.getStringExtra("teacherClass");
        Objects.requireNonNull(binding.teacherDetailContents.etTeacherClass).setText(teacherClass);
        String teacherIntroduce = intent.getStringExtra("teacherIntroduce");
        Objects.requireNonNull(binding.teacherDetailContents.etTeacherIntroduce).setText(teacherIntroduce);
        String teacherDOB = intent.getStringExtra("teacherDOB");
        Objects.requireNonNull(binding.teacherDetailContents.tvTeacherDOB).setText(teacherDOB);

        if (intent.getBooleanExtra("isThereImage", true)) {

            byte[] teacherProfileImage = intent.getByteArrayExtra("teacherProfileImage");

            Glide.with(getApplicationContext())
                    .load(
                            BitmapFactory.decodeByteArray(
                                    intent.getByteArrayExtra("teacherProfileImage"),
                                    0,
                                    teacherProfileImage.length
                            )
                    )
                    .into(binding.ivTeacherProfileImage);
        }

    }
}