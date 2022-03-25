package com.example.sslc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.example.sslc.requests.UpdateStudentClassRequest;

import org.json.JSONObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminStudentDetailActivity extends AppCompatActivity {

    private static final String TAG = "StudentDetailActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_StudentName)
    TextView tv_StudentName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_CurrentClass)
    TextView tv_CurrentClass;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_NewClass)
    EditText et_NewClass;

    int studentNumber;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_detail);

        ButterKnife.bind(this);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setBasicUI();
    }

    private void setBasicUI() {

        Intent intent = getIntent();
        studentNumber = intent.getIntExtra("studentNumber", 0);
        tv_StudentName.setText(intent.getStringExtra("studentName"));
        tv_CurrentClass.setText(intent.getStringExtra("studentClass"));
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_UpdateStudent)
    public void onBtnUpdateStudentClicked() {

        if (et_NewClass.getText().toString().trim().equals("")) {

            Toast.makeText(this, "New Class cannot be empty", Toast.LENGTH_SHORT).show();
        } else {

            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Updating");
            progressDialog.setMessage("Please Wait..\nUpdating is in progress");
            Response.Listener<String> responseListener = response -> {

                try {

                    Log.i(TAG, response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        Intent intent = new Intent(this, StudentFragment.class);
                        intent.putExtra("studentNumber", studentNumber);
                        intent.putExtra("studentClass", et_NewClass.getText().toString().trim());
                        setResult(9006, intent);
                        finish();
                    } else {

                        Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }

                    progressDialog.dismiss();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            };

            UpdateStudentClassRequest updateStudentClassRequest = new UpdateStudentClassRequest(
                    studentNumber,
                    et_NewClass.getText().toString().trim(),
                    responseListener
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(updateStudentClassRequest);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_DeleteStudent)
    public void onBtnDeleteStudentClicked() {


    }
}