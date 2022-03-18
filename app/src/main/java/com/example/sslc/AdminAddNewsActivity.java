package com.example.sslc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.databinding.ActivityAdminAddNewsBinding;
import com.example.sslc.fragments.NewsFragment;
import com.example.sslc.requests.AddNewsRequest;

import org.json.JSONObject;

public class AdminAddNewsActivity extends AppCompatActivity {

    private static final String TAG = "AdminAddNewsActivity";
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

                Toast.makeText(AdminAddNewsActivity.this, "News Title or Description is empty", Toast.LENGTH_SHORT).show();
            } else {

                progressDialog = new ProgressDialog(AdminAddNewsActivity.this);
                progressDialog.setTitle("Add News");
                progressDialog.setMessage("Adding news is in progress");
                progressDialog.show();

                String newsTitle = binding.etNewsTitle.getText().toString().trim();
                String newsDescription = binding.etNewsDescription.getText().toString().trim();

                Response.Listener<String> responseListener = response -> {

                    try {

                        Log.i(TAG, response);
                        progressDialog.dismiss();
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {

                            Intent intent = new Intent(getApplicationContext(), NewsFragment.class);
                            intent.putExtra("newsDataTitle", newsTitle);
                            intent.putExtra("newsDataDesc", newsDescription);
                            setResult(9001, intent);
                            finish();
                        } else {

                            Toast.makeText(AdminAddNewsActivity.this, "Fail" + response, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };

                AddNewsRequest addNewsRequest = new AddNewsRequest(newsTitle, newsDescription, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AdminAddNewsActivity.this);
                queue.add(addNewsRequest);
            }
        });
    }
}