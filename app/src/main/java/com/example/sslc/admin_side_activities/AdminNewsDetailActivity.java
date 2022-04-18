package com.example.sslc.admin_side_activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.R;
import com.example.sslc.databinding.ActivityNewsDetailBinding;
import com.example.sslc.dialog.ChangeNewsTitleDialog;
import com.example.sslc.fragments.NewsFragment;
import com.example.sslc.interfaces.ChangeNewsTitleDialogListener;
import com.example.sslc.requests.UpdateNewsRequest;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.Objects;

/*
 * When an admin clicks a News recycler view item.
 * Admin can check News's data (Title, Script),
 * and also update News's data.
 */
public class AdminNewsDetailActivity
        extends AppCompatActivity
        implements ChangeNewsTitleDialogListener
{

    private static final String TAG = AdminNewsDetailActivity.class.getSimpleName();

    private ActivityNewsDetailBinding binding;

    int newsNumber;
    EditText et_NewsContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get Data through Intent
        Intent intent = getIntent();
        newsNumber = intent.getIntExtra(getString(R.string.news_number), 0);
        String newsTitle = intent.getStringExtra(getString(R.string.news_title));
        String newsDescription = intent.getStringExtra(getString(R.string.news_description));

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(newsTitle);

        // If toolbarLayout is clicked, new dialog shows up to change title of the news
        toolBarLayout.setOnClickListener(view -> changeNewsTitle());

        FloatingActionButton fab = binding.fabUpdate;
        fab.setOnClickListener(view -> updateNews());

        et_NewsContent = binding.etNewsDescription;
        et_NewsContent.setText(newsDescription);
    }

    private void changeNewsTitle() {

        // New Dialog to let admin change news title
        ChangeNewsTitleDialog changeNewsTitleDialog = new ChangeNewsTitleDialog(Objects.requireNonNull(binding.toolbarLayout.getTitle()).toString());
        changeNewsTitleDialog.show(
                getSupportFragmentManager(),
                TAG + ChangeNewsTitleDialog.class.getSimpleName()
        );
    }

    @Override
    public void applyNewTitle(String newTitle) {
        binding.toolbarLayout.setTitle(newTitle);
    }

    private void updateNews() {

        ProgressDialog progressDialog = new ProgressDialog(AdminNewsDetailActivity.this);
        progressDialog.setTitle(getString(R.string.updating));
        progressDialog.setMessage(getString(R.string.update_in_progress));
        progressDialog.show();

        Response.Listener<String> responseListener = response -> updateNewsRequest(progressDialog, response);
        UpdateNewsRequest updateNewsRequest = new UpdateNewsRequest(
                newsNumber,
                Objects.requireNonNull(binding.toolbarLayout.getTitle()).toString(),
                et_NewsContent.getText().toString(),
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(AdminNewsDetailActivity.this);
        queue.add(updateNewsRequest);
    }

    private void updateNewsRequest(ProgressDialog progressDialog, String response) {

        try {

            Log.i(TAG, response);
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean(getString(R.string.success));

            if (success) {

                Intent intent = new Intent(getApplicationContext(), NewsFragment.class);
                intent.putExtra(getString(R.string.news_number), newsNumber);
                intent.putExtra(getString(R.string.news_title), Objects.requireNonNull(binding.toolbarLayout.getTitle()).toString());
                intent.putExtra(getString(R.string.news_description), et_NewsContent.getText().toString());
                setResult(9002, intent);
                finish();
            } else {

                Toast.makeText(this, getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}