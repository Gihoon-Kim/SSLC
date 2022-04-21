package com.sslc.sslc.admin_side_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sslc.sslc.R;
import com.sslc.sslc.data.AppData;
import com.sslc.sslc.fragments.StudentFragment;
import com.sslc.sslc.requests.AddStudentRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * To admin add a student with student's Name, DOB, Class.
 * Also provide ID and Password to login the application.
 * Student's profile image and introduce will be uploaded by student themselves.
 */

public class AdminAddStudentActivity extends AppCompatActivity {

    private static final String TAG = AdminAddStudentActivity.class.getSimpleName();

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_StudentName)
    EditText et_StudentName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_StudentID)
    EditText et_StudentID;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_StudentPassword)
    EditText et_StudentPassword;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_StudentDOB)
    TextView tv_StudentDOB;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_StudentCountry)
    EditText et_StudentCountry;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spinner_StudentClass)
    Spinner spinner_StudentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_student);

        ButterKnife.bind(this);

        initSpinner();
    }

    void initSpinner() {

        String[] allClass = new String[((AppData)getApplication()).getClassList().size()];
        allClass = ((AppData)getApplication()).getClassList().toArray(allClass);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                allClass
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_StudentClass.setAdapter(spinnerAdapter);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tv_StudentDOB)
    public void onStudentDOBClicked() {

        Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.date_format));
            Objects.requireNonNull(tv_StudentDOB).setText(simpleDateFormat.format(myCalendar.getTime()));
        };

        new DatePickerDialog(
                this,
                dateSetListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_StudentRegister)
    public void onRegisterButtonClicked() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.creating));
        progressDialog.setMessage(getString(R.string.create_in_progress));
        progressDialog.show();

        if (et_StudentName.getText().toString().trim().equals("") ||
                et_StudentID.getText().toString().trim().equals("") ||
                et_StudentPassword.getText().toString().trim().equals("") ||
                tv_StudentDOB.getText().toString().trim().equals(getString(R.string.click_to_set)) ||
                et_StudentCountry.getText().toString().trim().equals("")) {

            Toast.makeText(this, getString(R.string.fields_not_filled), Toast.LENGTH_SHORT).show();
        } else {

            addStudentOnDatabase();
        }
        progressDialog.dismiss();
    }

    private void addStudentOnDatabase() {

        Response.Listener<String> responseListener = this::addStudentRequest;

        AddStudentRequest addStudentRequest = new AddStudentRequest(
                et_StudentName.getText().toString().trim(),
                tv_StudentDOB.getText().toString().trim(),
                spinner_StudentClass.getSelectedItem().toString(),
                et_StudentCountry.getText().toString().trim(),
                et_StudentID.getText().toString().trim(),
                et_StudentPassword.getText().toString().trim(),
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(addStudentRequest);
    }

    private void addStudentRequest(String response) {

        try {

            Log.i(TAG, response);
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean(getString(R.string.success));
            boolean validate = jsonResponse.getBoolean(getString(R.string.validation));

            if (!validate) {

                Toast.makeText(this, getString(R.string.validation_failed), Toast.LENGTH_SHORT).show();
            } else {

                if (success) {

                    Intent intent = new Intent(getApplicationContext(), StudentFragment.class);
                    intent.putExtra(getString(R.string.student_number), jsonResponse.getInt("rowCount") + 1);
                    intent.putExtra(getString(R.string.student_name), et_StudentName.getText().toString().trim());
                    intent.putExtra(getString(R.string.student_class), spinner_StudentClass.getSelectedItem().toString().trim());
                    intent.putExtra(getString(R.string.student_dob), tv_StudentDOB.getText().toString());
                    intent.putExtra(getString(R.string.student_country), et_StudentCountry.getText().toString().trim());
                    setResult(9005, intent);

                    Toast.makeText(this, getString(R.string.create_complete), Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                    Toast.makeText(this, getString(R.string.create_failed), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}