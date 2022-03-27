package com.example.sslc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sslc.databinding.ActivityAdminClassDetailBinding;
import com.example.sslc.dialog.ChangeNewsTitleDialog;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class AdminClassDetailActivity extends AppCompatActivity implements ChangeNewsTitleDialog.ChangeNewsTitleDialogListener {

    private ActivityAdminClassDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminClassDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getDataAndSetUI();

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    private void getDataAndSetUI() {

        // Get Data from Intent
        Intent intent = getIntent();
        String classTitle = intent.getStringExtra("classTitle");
        String classTeacher = intent.getStringExtra("classTeacher");
        String classDescription = intent.getStringExtra("classDescription");
        String classStartTime = intent.getStringExtra("classStartTime");
        String classEndTime = intent.getStringExtra("classEndTime");
        String classRoom = intent.getStringExtra("classRoom");

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(classTitle);
        toolBarLayout.setOnClickListener(view -> changeTitle(toolBarLayout));
        setSpinner();

        Objects.requireNonNull(binding.include.etClassTeacher).setText(classTeacher);
        Objects.requireNonNull(binding.include.etClassDescription).setText(classDescription);
        Objects.requireNonNull(binding.include.etClassRoom).setText(classRoom);

        for (int i = 0; i < Objects.requireNonNull(binding.include.spinnerStartTime).getCount(); i++) {

            if (classStartTime.equals(String.valueOf(binding.include.spinnerStartTime.getItemAtPosition(i)))) {

                binding.include.spinnerStartTime.setSelection(i);
            } else if (classEndTime.equals(String.valueOf(Objects.requireNonNull(binding.include.spinnerEndTime).getItemAtPosition(i)))) {

                binding.include.spinnerEndTime.setSelection(i);
                break;
            }
        }
    }

    private void changeTitle(@NonNull CollapsingToolbarLayout toolBarLayout) {
        ChangeNewsTitleDialog changeNewsTitleDialog = new ChangeNewsTitleDialog(Objects.requireNonNull(toolBarLayout.getTitle()).toString());
        changeNewsTitleDialog.show(
                getSupportFragmentManager(),
                "ChangeTitleDialog"
        );
    }

    private void setSpinner() {

        String[] times = getResources().getStringArray(R.array.time);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                times
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        Objects.requireNonNull(binding.include.spinnerStartTime).setAdapter(spinnerAdapter);
        Objects.requireNonNull(binding.include.spinnerEndTime).setAdapter(spinnerAdapter);
    }

    @Override
    public void applyNewTitle(String newTitle) {

        binding.toolbarLayout.setTitle(newTitle);
    }
}