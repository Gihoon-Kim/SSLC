package com.example.sslc;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sslc.databinding.ActivityAdminAddTeacherBinding;
import com.example.sslc.fragments.NewsFragment;
import com.example.sslc.fragments.TeacherFragment;
import com.example.sslc.requests.AddTeacherRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class AdminAddTeacherActivity extends AppCompatActivity {

    private static final String TAG = "AdminAddTeacher";

    ActivityResultLauncher<Intent> addTeacherActivityResultLauncher;
    final Calendar myCalendar = Calendar.getInstance();

    private ActivityAdminAddTeacherBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminAddTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FloatingActionButton fab = binding.fabAddTeacher;
        fab.setOnClickListener(view -> onFabAddTeacherClicked());

        initActivityResultLauncher();

        binding.ivTeacherProfileImage.setOnClickListener(view -> {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            addTeacherActivityResultLauncher.launch(intent);
        });

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        };

        binding.teacherInclude.tvTeacherDOB.setOnClickListener(view -> {

            new DatePickerDialog(
                    AdminAddTeacherActivity.this,
                    dateSetListener,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    private void updateLabel() {
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        binding.teacherInclude.tvTeacherDOB.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    private void initActivityResultLauncher() {

        addTeacherActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_CANCELED) {

                        Toast.makeText(this, "Get image cancelled", Toast.LENGTH_SHORT).show();
                    } else {
                        Uri selectedImageUri;
                        selectedImageUri = Objects.requireNonNull(result.getData()).getData();
                        Glide.with(getApplicationContext())
                                .load(selectedImageUri)
                                .into(binding.ivTeacherProfileImage);
                    }
                }
        );
    }

    private void onFabAddTeacherClicked() {

        progressDialog = new ProgressDialog(AdminAddTeacherActivity.this);
        progressDialog.setTitle("Add a Teacher");
        progressDialog.setMessage("Adding teacher is in progress");
        progressDialog.show();

        if (!binding.etTeacherName.getText().toString().trim().equals("") ||
                !Objects.requireNonNull(binding.teacherInclude.tvTeacherDOB).getText().toString().trim().equals("dd/mm/yyyy") ||
                !Objects.requireNonNull(binding.teacherInclude.etTeacherIntroduce).getText().toString().trim().equals("")) {

            String teacherName = binding.etTeacherName.getText().toString().trim();
            String teacherDOB = String.valueOf(binding.teacherInclude.tvTeacherDOB.getText());
            String teacherIntroduce = binding.teacherInclude.etTeacherIntroduce.getText().toString();
            String teacherImage = "";
            String teacherClass = "";

            Response.Listener<String> responseListener = response -> {

                try {

                    Log.i(TAG, response);
                    progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        Intent intent = new Intent(getApplicationContext(), TeacherFragment.class);
                        intent.putExtra("teacherName", teacherName);
                        intent.putExtra("teacherDOB", teacherDOB);
                        intent.putExtra("teacherIntroduce", teacherIntroduce);
                        intent.putExtra("teacherImage", teacherImage);
                        intent.putExtra("teacherClass", teacherClass);
                        setResult(9003, intent);
                        finish();
                    } else {

                        Toast.makeText(this, "Adding Teacher failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            AddTeacherRequest addTeacherRequest = new AddTeacherRequest(
                    teacherName,
                    teacherDOB,
                    teacherClass,
                    teacherImage,
                    teacherIntroduce,
                    responseListener
            );
            RequestQueue queue = Volley.newRequestQueue(AdminAddTeacherActivity.this);
            queue.add(addTeacherRequest);
        } else {

            Toast.makeText(this, "Required Fields should be filled", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
}