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
import com.example.sslc.data.AppData;
import com.example.sslc.databinding.ActivityAdminAddTeacherBinding;
import com.example.sslc.dialog.TeacherClassesDialog;
import com.example.sslc.fragments.TeacherFragment;
import com.example.sslc.interfaces.ApplyClassListListener;
import com.example.sslc.requests.AddTeacherRequest;
import com.example.sslc.requests.UploadImageRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/*
 * To admin add a teacher with teacher's Name, DOB, Classes, Introduce, and profile Image.
 * Also provide ID and Password to login the application.
 */
public class AdminAddTeacherActivity extends AppCompatActivity implements ApplyClassListListener {

    private static final String TAG = AdminAddTeacherActivity.class.getSimpleName();

    ActivityResultLauncher<Intent> addTeacherImageFromGalleryActivityResultLauncher;
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

        binding.ivTeacherProfileImage.setOnClickListener(view -> getTeacherProfileImageFromGallery());
        Objects.requireNonNull(binding.teacherInclude.tvTeacherClass).setOnClickListener(view -> getTeacherClasses());

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

    private void getTeacherClasses() {

        TeacherClassesDialog teacherClassesDialog = new TeacherClassesDialog(
                this,
                ((AppData)getApplication()).getClassList()
        );
        teacherClassesDialog.callDialog();
    }

    private void getTeacherProfileImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        addTeacherImageFromGalleryActivityResultLauncher.launch(intent);
    }

    private void updateLabel() {

        String dateFormat = getString(R.string.date_format);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Objects.requireNonNull(binding.teacherInclude.tvTeacherDOB).setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    private void initActivityResultLauncher() {

        addTeacherImageFromGalleryActivityResultLauncher = registerForActivityResult(
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

        progressDialog = new ProgressDialog(AdminAddTeacherActivity.this);
        progressDialog.setTitle(getString(R.string.updating));
        progressDialog.setMessage(getString(R.string.update_in_progress));
        progressDialog.show();

        if (!binding.etTeacherName.getText().toString().trim().equals("") &&
                !Objects.requireNonNull(binding.teacherInclude.tvTeacherDOB).getText().toString().trim().equals(getString(R.string.date_format)) &&
                !Objects.requireNonNull(binding.etTeacherIntroduce).getText().toString().trim().equals("") &&
                !Objects.requireNonNull(binding.teacherInclude.etTeacherID).getText().toString().trim().equals("") &&
                !Objects.requireNonNull(binding.teacherInclude.etTeacherPassword).getText().toString().trim().equals("")) {

            String teacherName = binding.etTeacherName.getText().toString().trim();
            String teacherDOB = String.valueOf(binding.teacherInclude.tvTeacherDOB.getText());
            String teacherIntroduce = binding.etTeacherIntroduce.getText().toString();
            String teacherImage = "";
            String teacherClass = Objects.requireNonNull(binding.teacherInclude.tvTeacherClass).getText().toString();
            String teacherID = Objects.requireNonNull(binding.teacherInclude.etTeacherID).getText().toString().trim();
            String teacherPassword = Objects.requireNonNull(binding.teacherInclude.etTeacherPassword).getText().toString().trim();

            if (!binding.ivTeacherProfileImage.getDrawable().toString().contains(getString(R.string.vector_drawable))) {

                BitmapDrawable drawable = (BitmapDrawable) binding.ivTeacherProfileImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                teacherImage = bitmapToString(bitmap);

                Response.Listener<String> uploadImageListener = this::uploadImageRequest;
                UploadImageRequest uploadImageRequest = new UploadImageRequest(
                        teacherName,
                        true,
                        teacherImage,
                        uploadImageListener
                );
                RequestQueue uploadImageQueue = Volley.newRequestQueue(AdminAddTeacherActivity.this);
                uploadImageQueue.add(uploadImageRequest);
            }

            String finalTeacherImage = teacherImage;
            Response.Listener<String> responseListener = response -> {

                progressDialog = new ProgressDialog(AdminAddTeacherActivity.this);
                progressDialog.setTitle(getString(R.string.creating));
                progressDialog.setMessage(getString(R.string.create_in_progress));
                progressDialog.show();

                addTeacherRequest(
                        teacherName,
                        teacherDOB,
                        teacherIntroduce,
                        finalTeacherImage,
                        teacherClass,
                        teacherID,
                        teacherPassword,
                        response
                );
            };
//            Add Teacher in database and return teacher data
            AddTeacherRequest addTeacherRequest = new AddTeacherRequest(
                    teacherName,
                    teacherDOB,
                    teacherClass,
                    teacherID,
                    teacherPassword,
                    teacherImage,
                    teacherIntroduce,
                    responseListener
            );
            RequestQueue queue = Volley.newRequestQueue(AdminAddTeacherActivity.this);
            queue.add(addTeacherRequest);
        } else {

            Toast.makeText(this, getString(R.string.fields_not_filled), Toast.LENGTH_SHORT).show();
        }
    }

    private void addTeacherRequest(String teacherName, String teacherDOB, String teacherIntroduce, String teacherImage, String teacherClass, String teacherID, String teacherPassword, String response) {

        try {

            Log.i(TAG, response);
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean(getString(R.string.success));

            if (success) {

                Intent intent = new Intent(getApplicationContext(), TeacherFragment.class);
                intent.putExtra(getString(R.string.teacher_name), teacherName);
                intent.putExtra(getString(R.string.teacher_dob), teacherDOB);
                intent.putExtra(getString(R.string.teacher_class), teacherClass);
                intent.putExtra(getString(R.string.teacher_id), teacherID);
                intent.putExtra(getString(R.string.teacher_password), teacherPassword);
                intent.putExtra(getString(R.string.teacher_introduce), teacherIntroduce);
                intent.putExtra(getString(R.string.teacher_image), teacherImage);
                setResult(9003, intent);
                finish();
            } else {

                Toast.makeText(this, getString(R.string.create_failed), Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadImageRequest(String response) {

        try {

            Log.i(TAG, response);
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean(getString(R.string.success));

            if (success) {

                Toast.makeText(this, getString(R.string.image_uploaded), Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, getString(R.string.image_upload_failed), Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String bitmapToString(@NonNull Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        byte[] byteArrayVar = outputStream.toByteArray();
        return Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
    }

    @Override
    public void applyClassList(@NonNull ArrayList<String> classList) {

        Objects.requireNonNull(binding.teacherInclude.tvTeacherClass).setText(classList.toString());
    }
}