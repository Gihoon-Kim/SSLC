package com.example.sslc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.databinding.ActivityAdminClassDetailBinding;
import com.example.sslc.dialog.ChangeNewsTitleDialog;
import com.example.sslc.fragments.ClassFragment;
import com.example.sslc.requests.UpdateClassRequest;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.Objects;

public class AdminClassDetailActivity extends AppCompatActivity implements ChangeNewsTitleDialog.ChangeNewsTitleDialogListener {

    private static final String TAG = "ClassDetailActivity";
    private ActivityAdminClassDetailBinding binding;

    int classNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminClassDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getDataAndSetUI();

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> updateClass());
    }

    private void getDataAndSetUI() {

        // Get Data from Intent
        Intent intent = getIntent();
        classNumber = intent.getIntExtra("classNumber", 0);
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

    private void updateClass() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Class");
        progressDialog.setMessage("Please Wait...\nUpdating in progress");
        progressDialog.show();

        Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, response);
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");

                if (success) {

                    Intent intent = new Intent(this, ClassFragment.class);
                    intent.putExtra("classNumber", classNumber);
                    intent.putExtra("classTitle", Objects.requireNonNull(binding.toolbarLayout.getTitle()).toString());
                    intent.putExtra("classTeacher", Objects.requireNonNull(binding.include.etClassTeacher).getText().toString());
                    intent.putExtra("classDescription", Objects.requireNonNull(binding.include.etClassDescription).getText().toString());
                    intent.putExtra("classStartTime", Objects.requireNonNull(binding.include.spinnerStartTime).getSelectedItem().toString());
                    intent.putExtra("classEndTime", Objects.requireNonNull(binding.include.spinnerEndTime).getSelectedItem().toString());
                    intent.putExtra("classRoom", Objects.requireNonNull(binding.include.etClassRoom).getText().toString());
                    setResult(9009, intent);

                    Toast.makeText(this, "Update Complete", Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                    Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            } catch (Exception e) {

                e.printStackTrace();
            }
        };

        UpdateClassRequest updateClassRequest = new UpdateClassRequest(
                classNumber,
                Objects.requireNonNull(binding.toolbarLayout.getTitle()).toString(),
                Objects.requireNonNull(binding.include.etClassTeacher).getText().toString(),
                Objects.requireNonNull(binding.include.etClassDescription).getText().toString(),
                Objects.requireNonNull(binding.include.spinnerStartTime).getSelectedItem().toString(),
                Objects.requireNonNull(binding.include.spinnerEndTime).getSelectedItem().toString(),
                Objects.requireNonNull(binding.include.etClassRoom).getText().toString(),
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(updateClassRequest);
    }

    @Override
    public void applyNewTitle(String newTitle) {

        binding.toolbarLayout.setTitle(newTitle);
    }
}