package com.sslc.sslc.admin_side_activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sslc.sslc.R;
import com.sslc.sslc.databinding.ActivityAdminAddNewsBinding;
import com.sslc.sslc.fragments.NewsFragment;
import com.sslc.sslc.requests.AddNewsRequest;

import org.json.JSONObject;

/*
 * To admin add a new with News Title and script.
 * When it is added, created date also added
 */
public class AdminAddNewsActivity extends AppCompatActivity {

    private static final String TAG = AdminAddNewsActivity.class.getSimpleName();
    private ActivityAdminAddNewsBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminAddNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAddNews.setOnClickListener(view -> {

            if(binding.etNewsTitle.getText().toString().trim().equals("") ||
            binding.etNewsDescription.getText().toString().trim().equals("")) {

                Toast.makeText(AdminAddNewsActivity.this, getString(R.string.fields_not_filled), Toast.LENGTH_SHORT).show();
            } else {

                progressDialog = new ProgressDialog(AdminAddNewsActivity.this);
                progressDialog.setTitle(getString(R.string.creating));
                progressDialog.setMessage(getString(R.string.create_in_progress));
                progressDialog.show();

                String newsTitle = binding.etNewsTitle.getText().toString().trim();
                String newsDescription = binding.etNewsDescription.getText().toString().trim();

                Response.Listener<String> responseListener = response -> addNewsRequest(newsTitle, newsDescription, response);
                AddNewsRequest addNewsRequest = new AddNewsRequest(newsTitle, newsDescription, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AdminAddNewsActivity.this);
                queue.add(addNewsRequest);
            }
        });
    }

    private void addNewsRequest(String newsTitle, String newsDescription, String response) {

        try {

            Log.i(TAG, response);
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean(getString(R.string.success));

            if (success) {

                Intent intent = new Intent(getApplicationContext(), NewsFragment.class);
                intent.putExtra(getString(R.string.news_title), newsTitle);
                intent.putExtra(getString(R.string.news_description), newsDescription);
                setResult(9001, intent);
                finish();
            } else {

                Toast.makeText(AdminAddNewsActivity.this, getString(R.string.create_failed), Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}