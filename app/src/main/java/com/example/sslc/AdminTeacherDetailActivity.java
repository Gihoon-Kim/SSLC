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

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/*
 * When an admin clicks a teacher recycler view item.
 * Admin can check teacher's information (Name, Class, DOB, Introduce, Profile Image),
 * and also update teacher's profile.
 */
public class AdminTeacherDetailActivity
        extends AppCompatActivity
        implements ChangeNewsTitleDialogListener, ApplyClassListListener
{

    private static final String TAG = AdminTeacherDetailActivity.class.getSimpleName();

    private ActivityAdminTeacherDetailBinding binding;
    private ActivityResultLauncher<Intent> updateTeacherProfileImageFromGalleryResultLauncher;

    int teacherNumber;

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
        binding.fab.setOnClickListener(view -> updateTeacher());
    }

    public void setBasicUI() {

        // Get Data and set basic UI
        Intent intent = getIntent();
        teacherNumber = intent.getIntExtra("teacherNumber", 0);
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

        updateTeacherProfileImageFromGalleryResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == RESULT_CANCELED) {

                        Toast.makeText(this, R.string.get_image_cancelled, Toast.LENGTH_SHORT).show();
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
        changeTeacherNameDialog.show(
                getSupportFragmentManager(),
                TAG + ChangeNewsTitleDialog.class.getSimpleName()
        );
    }

    private void changeProfileImage() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        updateTeacherProfileImageFromGalleryResultLauncher.launch(intent);
    }

    private void changeDOB() {

        String teacherDOB = Objects.requireNonNull(binding.teacherDetailContents.tvTeacherDOB).getText().toString();
        String[] dayMonthYear = teacherDOB.split("/");

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

            String dateFormat = getString(R.string.date_format);
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
        progressDialog.setTitle(getString(R.string.updating));
        progressDialog.setMessage(getString(R.string.update_in_progress));
        progressDialog.show();

        // Image to String
        // If the profile image is not selected ( it is based image ),
        // teacherImage has no value ("")
        String teacherImage;
        if (binding.ivTeacherProfileImage.getDrawable().toString().contains(getString(R.string.vector_drawable))) {

            teacherImage = "";
        } else {

            BitmapDrawable drawable = (BitmapDrawable) binding.ivTeacherProfileImage.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            teacherImage = bitmapToString(bitmap);
        }

        Response.Listener<String> responseListener = response -> updateTeacherRequest(
                progressDialog,
                teacherImage,
                response
        );
        UpdateTeacherRequest updateTeacherRequest = new UpdateTeacherRequest(
                teacherNumber,
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

    private void updateTeacherRequest(ProgressDialog progressDialog, String teacherImage, String response) {

        try {

            Log.i(TAG, response);
            JSONObject jsonObject = new JSONObject(response);
            boolean success = jsonObject.getBoolean(getString(R.string.success));
            progressDialog.dismiss();

            if(success) {

                Intent intent = new Intent(this, TeacherFragment.class);
                intent.putExtra(getString(R.string.teacher_number), teacherNumber);
                intent.putExtra(getString(R.string.teacher_name), binding.tvTeacherName.getText().toString());
                intent.putExtra(getString(R.string.teacher_class), Objects.requireNonNull(binding.teacherDetailContents.tvTeacherClass).getText().toString());
                intent.putExtra(getString(R.string.teacher_introduce), Objects.requireNonNull(binding.teacherDetailContents.etTeacherIntroduce).getText().toString());
                intent.putExtra(getString(R.string.teacher_dob), Objects.requireNonNull(binding.teacherDetailContents.tvTeacherDOB).getText());
                intent.putExtra(getString(R.string.teacher_image), teacherImage);
                setResult(9004, intent);
                finish();
            } else {

                Toast.makeText(this, getString(R.string.teacher_update_failed), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String bitmapToString(Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        byte[] byteArrayVar = outputStream.toByteArray();
        return Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
    }

    @Override
    public void applyNewTitle(String newTitle) {

    binding.tvTeacherName.setText(newTitle);
}

    @Override
    public void applyClassList(ArrayList<String> classList) {

        Objects.requireNonNull(binding.teacherDetailContents.tvTeacherClass).setText(classList.toString());
    }
}