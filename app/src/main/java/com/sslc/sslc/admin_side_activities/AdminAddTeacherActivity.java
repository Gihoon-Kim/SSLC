package com.sslc.sslc.admin_side_activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sslc.sslc.R;
import com.sslc.sslc.data.AppData;
import com.sslc.sslc.databinding.ActivityAdminAddTeacherBinding;
import com.sslc.sslc.dialog.TeacherClassesDialog;
import com.sslc.sslc.fragments.TeacherFragment;
import com.sslc.sslc.interfaces.ApplyClassListListener;
import com.sslc.sslc.requests.AddTeacherRequest;

import org.json.JSONObject;

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

    // Uri for image
    private Uri selectedImageUri;

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

        // Get permission to access gallery
        if (isPermissionGranted()) {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            addTeacherImageFromGalleryActivityResultLauncher.launch(intent);
        }
    }

    private boolean isPermissionGranted() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                Log.d(TAG, "Authorization setting complete");
                return true;
            } else {

                Log.d(TAG, "Authorization setting Request");
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        },
                        1
                );
                return false;
            }
        }
        return true;
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
        progressDialog.setTitle(getString(R.string.creating));
        progressDialog.setMessage(getString(R.string.create_in_progress));
        progressDialog.show();

        if (!binding.etTeacherName.getText().toString().trim().equals("") &&
                !Objects.requireNonNull(binding.teacherInclude.tvTeacherDOB).getText().toString().trim().equals(getString(R.string.date_format)) &&
                !Objects.requireNonNull(binding.etTeacherIntroduce).getText().toString().trim().equals("") &&
                !Objects.requireNonNull(binding.teacherInclude.etTeacherID).getText().toString().trim().equals("") &&
                !Objects.requireNonNull(binding.teacherInclude.etTeacherPassword).getText().toString().trim().equals("")) {

            String teacherName = binding.etTeacherName.getText().toString().trim();
            String teacherDOB = String.valueOf(binding.teacherInclude.tvTeacherDOB.getText());
            String teacherIntroduce = binding.etTeacherIntroduce.getText().toString();
            String teacherClass = Objects.requireNonNull(binding.teacherInclude.tvTeacherClass).getText().toString();
            String teacherID = Objects.requireNonNull(binding.teacherInclude.etTeacherID).getText().toString().trim();
            String teacherPassword = Objects.requireNonNull(binding.teacherInclude.etTeacherPassword).getText().toString().trim();
            int hasProfileImage = 0;

            if (!binding.ivTeacherProfileImage.getDrawable().toString().contains(getString(R.string.vector_drawable))) {

                // TODO : UPLOAD PROFILE IMAGE TO FIREBASE
                hasProfileImage = 1;

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();

                String fileName = "profile_teacher_".concat(teacherName).concat(".jpg");
                Log.d(TAG, "Uri = ".concat(String.valueOf(selectedImageUri)));
                StorageReference riversRef = storageReference.child("profile_img/" + fileName);
                UploadTask uploadTask = riversRef.putFile(selectedImageUri);

                // Save New Profile Image
                uploadTask.addOnFailureListener(
                        e -> Toast.makeText(AdminAddTeacherActivity.this, "Profile Image Upload Failed", Toast.LENGTH_SHORT).show())
                        .addOnSuccessListener(
                                taskSnapshot -> Toast.makeText(AdminAddTeacherActivity.this, "Profile Image Uploaded", Toast.LENGTH_SHORT).show()
                        );
            }

            int finalHasProfileImage = hasProfileImage;
            Response.Listener<String> responseListener = response -> {

                addTeacherRequest(
                        teacherName,
                        teacherDOB,
                        teacherIntroduce,
                        teacherClass,
                        teacherID,
                        teacherPassword,
                        finalHasProfileImage,
                        response
                );

                progressDialog.dismiss();
            };
//            Add Teacher in database and return teacher data
            AddTeacherRequest addTeacherRequest = new AddTeacherRequest(
                    teacherName,
                    teacherDOB,
                    teacherClass,
                    teacherID,
                    teacherPassword,
                    teacherIntroduce,
                    hasProfileImage,
                    responseListener
            );
            RequestQueue queue = Volley.newRequestQueue(AdminAddTeacherActivity.this);
            queue.add(addTeacherRequest);
        } else {

            Toast.makeText(this, getString(R.string.fields_not_filled), Toast.LENGTH_SHORT).show();
        }
    }

    private void addTeacherRequest(String teacherName, String teacherDOB, String teacherIntroduce, String teacherClass, String teacherID, String teacherPassword, int hasProfileImage, String response) {

        try {

            Log.i(TAG, response);
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean(getString(R.string.success));
            boolean validate = jsonResponse.getBoolean(getString(R.string.validation));

            if (!validate) {

                Toast.makeText(this, getString(R.string.validation_failed), Toast.LENGTH_SHORT).show();
            } else {
                if (success) {

                    Intent intent = new Intent(getApplicationContext(), TeacherFragment.class);
                    intent.putExtra(getString(R.string.teacher_name), teacherName);
                    intent.putExtra(getString(R.string.teacher_dob), teacherDOB);
                    intent.putExtra(getString(R.string.teacher_class), teacherClass);
                    intent.putExtra(getString(R.string.teacher_id), teacherID);
                    intent.putExtra(getString(R.string.teacher_password), teacherPassword);
                    intent.putExtra(getString(R.string.teacher_introduce), teacherIntroduce);
                    intent.putExtra(getString(R.string.teacher_number), jsonResponse.getInt("rowCount") + 1);
                    intent.putExtra("hasProfileImage", hasProfileImage);
                    setResult(9003, intent);
                    finish();
                } else {

                    Toast.makeText(this, getString(R.string.create_failed), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void applyClassList(@NonNull ArrayList<String> classList) {

        Objects.requireNonNull(binding.teacherInclude.tvTeacherClass).setText(classList.toString());
    }
}