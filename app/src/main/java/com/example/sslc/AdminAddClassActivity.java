package com.example.sslc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.data.AppData;
import com.example.sslc.databinding.ActivityAdminAddClassBinding;
import com.example.sslc.fragments.ClassFragment;
import com.example.sslc.requests.AddClassRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.Objects;

public class AdminAddClassActivity extends AppCompatActivity {

    private static final String TAG = "AddClassActivity";

    private ActivityAdminAddClassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminAddClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> addNewClass());

        // Spinner Value Setting
        setSpinner();
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
    private void addNewClass() {

        String classTitle = binding.etClassTitle.getText().toString().trim();
        String classTeacher = Objects.requireNonNull(binding.include.etClassTeacher).getText().toString().trim();
        String classStartTime = Objects.requireNonNull(binding.include.spinnerStartTime).getSelectedItem().toString();
        String classEndTime = Objects.requireNonNull(binding.include.spinnerEndTime).getSelectedItem().toString();
        String classDescription = Objects.requireNonNull(binding.include.etClassDescription).getText().toString().trim();
        String classRoom = Objects.requireNonNull(binding.include.etClassRoom).getText().toString().trim();

        if (classTitle.equals("") || classTeacher.equals("") || classDescription.equals("")) {

            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else if (binding.include.spinnerEndTime.getSelectedItemPosition() <= binding.include.spinnerStartTime.getSelectedItemPosition()) {

            Toast.makeText(this, "End time must after than start time", Toast.LENGTH_SHORT).show();
        } else {

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Create Class");
            progressDialog.setMessage("Please wait..\nCreate class in progress");
            progressDialog.show();

            Response.Listener<String> responseListener = response -> {

                try {

                    Log.i(TAG, response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        Intent intent = new Intent(this, ClassFragment.class);
                        intent.putExtra("classNumber", jsonResponse.getInt("rowCount") + 1);
                        intent.putExtra("classTitle", classTitle);
                        intent.putExtra("classTeacher", classTeacher);
                        intent.putExtra("classDescription", classDescription);
                        intent.putExtra("classStartTime", classStartTime);
                        intent.putExtra("classEndTime", classEndTime);
                        intent.putExtra("classRoom", classRoom);
                        setResult(9008, intent);

                        ((AppData)getApplication()).getClassList().add(classTitle);

                        Toast.makeText(this, "New class created", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {

                        Toast.makeText(this, "Create new class failed", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            };

            AddClassRequest addClassRequest = new AddClassRequest(
                    classTitle,
                    classTeacher,
                    classDescription,
                    classStartTime,
                    classEndTime,
                    classRoom,
                    responseListener
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(addClassRequest);
        }
    }
}