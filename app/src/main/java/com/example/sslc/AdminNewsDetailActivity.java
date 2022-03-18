package com.example.sslc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.databinding.ActivityNewsDetailBinding;
import com.example.sslc.dialog.ChangeNewsTitleDialog;
import com.example.sslc.fragments.NewsFragment;
import com.example.sslc.requests.UpdateNewsRequest;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.Objects;

public class AdminNewsDetailActivity extends AppCompatActivity implements ChangeNewsTitleDialog.ChangeNewsTitleDialogListener {

    private static final String TAG = "NewsDetailActivity";

    private ActivityNewsDetailBinding binding;

    int newsID;
    EditText et_NewsContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get Data through Intent
        Intent intent = getIntent();
        newsID = intent.getIntExtra("NewsID", 0);
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

        FloatingActionButton fab = binding.fabUpdate;
        fab.setOnClickListener(view -> updateNews());

        et_NewsContent = binding.includeView.getRoot().findViewById(R.id.et_NewsContent);
        et_NewsContent.setText(newsDescription);
    }

    @Override
    public void applyNewTitle(String newTitle) {
        binding.toolbarLayout.setTitle(newTitle);
    }

    private void updateNews() {

        ProgressDialog progressDialog = new ProgressDialog(AdminNewsDetailActivity.this);
        progressDialog.setTitle("Updating");
        progressDialog.setMessage("Please Wait.\nUpdating in progress");
        progressDialog.show();

        Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, response);
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                progressDialog.dismiss();

                if (success) {

                    Intent intent = new Intent(getApplicationContext(), NewsFragment.class);
                    intent.putExtra("newsID", newsID);
                    intent.putExtra("newTitle", Objects.requireNonNull(binding.toolbarLayout.getTitle()).toString());
                    intent.putExtra("newDesc", et_NewsContent.getText().toString());
                    setResult(9002, intent);
                    finish();
                } else {

                    Toast.makeText(this, "Update Failure", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        UpdateNewsRequest updateNewsRequest = new UpdateNewsRequest(
                newsID,
                Objects.requireNonNull(binding.toolbarLayout.getTitle()).toString(),
                et_NewsContent.getText().toString(),
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(AdminNewsDetailActivity.this);
        queue.add(updateNewsRequest);
    }
}