package com.example.sslc;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sslc.databinding.ActivityAdminAddTeacherBinding;
import com.example.sslc.fragments.TeacherFragment;
import com.example.sslc.requests.AddTeacherRequest;
import com.example.sslc.requests.UploadImageRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

        Objects.requireNonNull(binding.teacherInclude.tvTeacherDOB).setOnClickListener(view -> new DatePickerDialog(
                AdminAddTeacherActivity.this,
                dateSetListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show());
    }

    private void updateLabel() {
        String dateFormat = "dd/MM/yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Objects.requireNonNull(binding.teacherInclude.tvTeacherDOB).setText(simpleDateFormat.format(myCalendar.getTime()));
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onFabAddTeacherClicked() {

        if (!binding.etTeacherName.getText().toString().trim().equals("") &&
                !Objects.requireNonNull(binding.teacherInclude.tvTeacherDOB).getText().toString().trim().equals("dd/mm/yyyy") &&
                !Objects.requireNonNull(binding.teacherInclude.etTeacherIntroduce).getText().toString().trim().equals("")) {

            String teacherName = binding.etTeacherName.getText().toString().trim();
            String teacherDOB = String.valueOf(binding.teacherInclude.tvTeacherDOB.getText());
            String teacherIntroduce = binding.teacherInclude.etTeacherIntroduce.getText().toString();
            String teacherImage = "";
            String teacherClass = Objects.requireNonNull(binding.teacherInclude.etTeacherClass).getText().toString();

            if (binding.ivTeacherProfileImage.getDrawable().toString().contains("VectorDrawable")) {

                Response.Listener<String> responseListener = addTeacherListenerInit(teacherName, teacherDOB, teacherIntroduce, teacherImage, teacherClass);
//            Add Teacher in database and return teacher data
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

                BitmapDrawable drawable = (BitmapDrawable) binding.ivTeacherProfileImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                teacherImage = bitmapToString(bitmap);

                progressDialog = new ProgressDialog(AdminAddTeacherActivity.this);
                progressDialog.setTitle("Upload the profile image");
                progressDialog.setMessage("Uploading photo is in progress");
                progressDialog.show();

                Response.Listener<String> uploadImageListener = uploadTeacherImageListenerInit();
                String isTeacher = "teacher";
                UploadImageRequest uploadImageRequest = new UploadImageRequest(
                        teacherName,
                        isTeacher,
                        teacherImage,
                        uploadImageListener
                );
                RequestQueue uploadImageQueue = Volley.newRequestQueue(AdminAddTeacherActivity.this);
                uploadImageQueue.add(uploadImageRequest);

                Response.Listener<String> responseListener = addTeacherListenerInit(teacherName, teacherDOB, teacherIntroduce, teacherImage, teacherClass);
                AddTeacherRequest addTeacherRequest = new AddTeacherRequest(
                        teacherName,
                        teacherDOB,
                        teacherClass,
                        teacherImage,
                        teacherIntroduce,
                        responseListener
                );
                RequestQueue addTeacherQueue = Volley.newRequestQueue(AdminAddTeacherActivity.this);
                addTeacherQueue.add(addTeacherRequest);
            }
        } else {

            Toast.makeText(this, "Required Fields should be filled", Toast.LENGTH_SHORT).show();
        }
    }

    private String bitmapToString(Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        byte[] byteArrayVar = outputStream.toByteArray();
        return Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
    }

    @NonNull
    private Response.Listener<String> uploadTeacherImageListenerInit() {
        return response -> {

            try {

                Log.i(TAG, response);
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");

                if (success) {

                    Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(this, "Image Not Uploaded", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @NonNull
    private Response.Listener<String> addTeacherListenerInit(String teacherName, String teacherDOB, String teacherIntroduce, String teacherImage, String teacherClass) {
        return response -> {

            progressDialog = new ProgressDialog(AdminAddTeacherActivity.this);
            progressDialog.setTitle("Adding Teacher");
            progressDialog.setMessage("Adding Teacher is in progress");
            progressDialog.show();

            try {

                Log.i(TAG, response);
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

                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}