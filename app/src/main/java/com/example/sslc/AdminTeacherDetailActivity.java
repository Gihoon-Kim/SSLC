package com.example.sslc;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sslc.data.AppData;
import com.example.sslc.databinding.ActivityAdminTeacherDetailBinding;
import com.example.sslc.dialog.ChangeNewsTitleDialog;
import com.example.sslc.dialog.TeacherClassesDialog;
import com.example.sslc.fragments.TeacherFragment;
import com.example.sslc.interfaces.ApplyClassListListener;
import com.example.sslc.interfaces.ChangeNewsTitleDialogListener;
import com.example.sslc.requests.UpdateTeacherRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AdminTeacherDetailActivity extends AppCompatActivity implements ChangeNewsTitleDialogListener, ApplyClassListListener {

    private static final String TAG = "TeacherDetailActivity";

    private ActivityAdminTeacherDetailBinding binding;
    private ActivityResultLauncher<Intent> updateTeacherProfileImageResultLauncher;

    int teacherID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminTeacherDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setBasicUI();

        // Click Listeners
        binding.ivTeacherProfileImage.setOnClickListener(view -> changeProfileImage());
        Objects.requireNonNull(binding.teacherDetailContents.tvTeacherDOB).setOnClickListener(view -> changeDOB());
        binding.tvTeacherName.setOnClickListener(view -> changeTeacherName());
        Objects.requireNonNull(binding.teacherDetailContents.tvTeacherClass).setOnClickListener(view -> {

            TeacherClassesDialog teacherClassesDialog = new TeacherClassesDialog(
                    this,
                    ((AppData)getApplication()).getClassList()
            );
            teacherClassesDialog.callDialog();
        });

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> updateTeacher());
    }

    public void setBasicUI() {

        // Get Data and set basic UI
        Intent intent = getIntent();
        teacherID = intent.getIntExtra("teacherID", 0);
        String teacherName = intent.getStringExtra("teacherName");
        binding.tvTeacherName.setText(teacherName);
        String teacherClass = intent.getStringExtra("teacherClass");
        Objects.requireNonNull(binding.teacherDetailContents.tvTeacherClass).setText(teacherClass);
        String teacherIntroduce = intent.getStringExtra("teacherIntroduce");
        Objects.requireNonNull(binding.teacherDetailContents.etTeacherIntroduce).setText(teacherIntroduce);
        String teacherDOB = intent.getStringExtra("teacherDOB");
        Objects.requireNonNull(binding.teacherDetailContents.tvTeacherDOB).setText(teacherDOB);

        if (intent.getBooleanExtra("isThereImage", true)) {

            byte[] teacherProfileImage = intent.getByteArrayExtra("teacherProfileImage");

            Glide.with(getApplicationContext())
                    .load(
                            BitmapFactory.decodeByteArray(
                                    intent.getByteArrayExtra("teacherProfileImage"),
                                    0,
                                    teacherProfileImage.length
                            )
                    )
                    .into(binding.ivTeacherProfileImage);
        }

        initActivityResultLauncher();
    }

    public void initActivityResultLauncher() {

        updateTeacherProfileImageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == RESULT_CANCELED) {

                        Toast.makeText(this, "Get Image cancelled", Toast.LENGTH_SHORT).show();
                    } else {

                        Uri selectedImageUri;
                        selectedImageUri = Objects.requireNonNull(result.getData())
                                .getData();
                        Glide.with(getApplicationContext())
                                .load(selectedImageUri)
                                .into(binding.ivTeacherProfileImage);
                    }
                }
        );
    }

    private void changeTeacherName() {

        ChangeNewsTitleDialog changeTeacherNameDialog = new ChangeNewsTitleDialog(binding.tvTeacherName.getText().toString());
        changeTeacherNameDialog.show(getSupportFragmentManager(), "ChangeTeacherName");
    }

    @Override
    public void applyNewTitle(String newTitle) {

        binding.tvTeacherName.setText(newTitle);
    }

    private void changeProfileImage() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        updateTeacherProfileImageResultLauncher.launch(intent);
    }

    private void changeDOB() {

        String teacherDOB = Objects.requireNonNull(binding.teacherDetailContents.tvTeacherDOB).getText().toString();

        String[] dayMonthYear = teacherDOB.split("/");

        Log.i(TAG, "year = " + dayMonthYear[2] + "month = " + dayMonthYear[1] + "day = " + dayMonthYear[0]);
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dataSetListener = (
                datePicker,
                year,
                month,
                day
        ) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);

            String dateFormat = "dd/MM/yyyy";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            Objects.requireNonNull(binding.teacherDetailContents.tvTeacherDOB).setText(simpleDateFormat.format(myCalendar.getTime()));
        };

        new DatePickerDialog(
                AdminTeacherDetailActivity.this,
                dataSetListener,
                Integer.parseInt(dayMonthYear[2]),
                Integer.parseInt(dayMonthYear[1]) - 1,
                Integer.parseInt(dayMonthYear[0])
                ).show();
    }

    private void updateTeacher() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating");
        progressDialog.setMessage("Please Wait..\nUpdating in progress");
        progressDialog.show();

        // Image to String
        String teacherImage;
        if (binding.ivTeacherProfileImage.getDrawable().toString().contains("VectorDrawable")) {

            teacherImage = "";
        } else {

            BitmapDrawable drawable = (BitmapDrawable) binding.ivTeacherProfileImage.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            teacherImage = bitmapToString(bitmap);
        }

        Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, response);
                JSONObject jsonObject = new JSONObject(response);
                boolean success = jsonObject.getBoolean("success");
                progressDialog.dismiss();

                if(success) {

                    Intent intent = new Intent(this, TeacherFragment.class);
                    intent.putExtra("teacherID", teacherID);
                    intent.putExtra("teacherName", binding.tvTeacherName.getText().toString());
                    intent.putExtra("teacherClass", Objects.requireNonNull(binding.teacherDetailContents.tvTeacherClass).getText().toString());
                    intent.putExtra("teacherIntroduce", Objects.requireNonNull(binding.teacherDetailContents.etTeacherIntroduce).getText().toString());
                    intent.putExtra("teacherDOB", Objects.requireNonNull(binding.teacherDetailContents.tvTeacherDOB).getText());
                    intent.putExtra("teacherImage", teacherImage);
                    setResult(9004, intent);
                    finish();
                } else {

                    Toast.makeText(this, "Teacher Update Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        UpdateTeacherRequest updateTeacherRequest = new UpdateTeacherRequest(
                teacherID,
                binding.tvTeacherName.getText().toString(),
                Objects.requireNonNull(binding.teacherDetailContents.tvTeacherClass).getText().toString(),
                Objects.requireNonNull(binding.teacherDetailContents.etTeacherIntroduce).getText().toString(),
                Objects.requireNonNull(binding.teacherDetailContents.tvTeacherDOB).getText().toString(),
                teacherImage,
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(updateTeacherRequest);
    }

    private String bitmapToString(Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        byte[] byteArrayVar = outputStream.toByteArray();
        return Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
    }

    @Override
    public void applyClassList(ArrayList<String> classList) {

        Objects.requireNonNull(binding.teacherDetailContents.tvTeacherClass).setText(classList.toString());
    }
}