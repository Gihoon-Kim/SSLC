package com.example.sslc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sslc.databinding.ActivityNewsDetailBinding;
import com.example.sslc.dialog.ChangeNewsTitleDialog;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class NewsDetailActivity extends AppCompatActivity implements ChangeNewsTitleDialog.ChangeNewsTitleDialogListener {

    private ActivityNewsDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get Data through Intent
        Intent intent = getIntent();
        String newsTitle = intent.getStringExtra("NewsTitle");
        String newsDescription = intent.getStringExtra("NewsDescription");

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(newsTitle);

        // If toolbarLayout is clicked, new dialog shows up to change title of the news
        toolBarLayout.setOnClickListener(view -> {

            // New Dialog to let admin change news title
            ChangeNewsTitleDialog changeNewsTitleDialog = new ChangeNewsTitleDialog(Objects.requireNonNull(toolBarLayout.getTitle()).toString());
            changeNewsTitleDialog.show(getSupportFragmentManager(), "ChangeTitleDialog");
        });

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        EditText et_NewsContent = binding.includeView.getRoot().findViewById(R.id.et_NewsContent);
        et_NewsContent.setText(newsDescription);
    }

    @Override
    public void applyNewTitle(String newTitle) {
        binding.toolbarLayout.setTitle(newTitle);
    }
}