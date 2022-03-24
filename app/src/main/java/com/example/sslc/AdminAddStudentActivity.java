package com.example.sslc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.fragments.StudentFragment;
import com.example.sslc.requests.AddStudentRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminAddStudentActivity extends AppCompatActivity {

    private static final String TAG = "AddStudentActivity";

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
    @BindView(R.id.et_StudentClass)
    EditText et_StudentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_student);

        ButterKnife.bind(this);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tv_StudentDOB)
    public void onStudentDOBClicked() {

        Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
        progressDialog.setTitle("Adding Student");
        progressDialog.setMessage("Please Wait...\nAdding Student in progress");
        progressDialog.show();

        if (et_StudentName.getText().toString().trim().equals("") ||
                et_StudentID.getText().toString().trim().equals("") ||
                et_StudentPassword.getText().toString().trim().equals("") ||
                tv_StudentDOB.getText().toString().trim().equals("Click to set") ||
                et_StudentCountry.getText().toString().trim().equals("")) {

            Toast.makeText(this, "Please fill all information correctly", Toast.LENGTH_SHORT).show();
        } else {

            addStudentOnDatabase();
        }
        progressDialog.dismiss();
    }

    private void addStudentOnDatabase() {

        Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, response);
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                boolean validate = jsonResponse.getBoolean("validation");

                if (!validate) {

                    Toast.makeText(this, "Validation Failed", Toast.LENGTH_SHORT).show();
                } else {

                    if (success) {

                        Intent intent = new Intent(getApplicationContext(), StudentFragment.class);
                        intent.putExtra("studentNumber", jsonResponse.getInt("rowCount") + 1);
                        intent.putExtra("studentName", et_StudentName.getText().toString().trim());
                        intent.putExtra("studentClass", et_StudentClass.getText().toString().trim());
                        intent.putExtra("studentDOB", tv_StudentDOB.getText().toString());
                        intent.putExtra("studentCountry", et_StudentCountry.getText().toString().trim());
                        setResult(9005, intent);

                        Toast.makeText(this, "Student Created Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {

                        Toast.makeText(this, "Fail create student", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        AddStudentRequest addStudentRequest = new AddStudentRequest(
                et_StudentName.getText().toString().trim(),
                tv_StudentDOB.getText().toString().trim(),
                et_StudentClass.getText().toString().trim(),
                et_StudentCountry.getText().toString().trim(),
                et_StudentID.getText().toString().trim(),
                et_StudentPassword.getText().toString().trim(),
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(addStudentRequest);
    }
}