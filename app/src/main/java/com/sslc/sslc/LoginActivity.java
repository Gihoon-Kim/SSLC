package com.sslc.sslc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sslc.sslc.admin_side_activities.AdminMainActivity;
import com.sslc.sslc.requests.LoginRequest;
import com.sslc.sslc.teacher_side_activities.TeacherMainActivity;

import org.json.JSONObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * People who not admin ( Student, Teacher ) cannot register.
 * Only admin can create new user for students and teachers.
 * After admin create new user and provide login info (userID and Password), teachers and students can login
 * with provided info.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_LoginID)
    EditText et_LoginID;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_LoginPassword)
    EditText et_LoginPassword;

    private AlertDialog dialog;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);
    }

    // Login Process
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_Login)
    void onLoginButtonClicked() {

        final String userID = et_LoginID.getText().toString().trim();
        String userPassword = et_LoginPassword.getText().toString().trim();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle(getString(R.string.login));
        progressDialog.setMessage(getString(R.string.validate_in_progress));
        progressDialog.show();

        if (userID.equals(getString(R.string.admin_id)) && userPassword.equals(getString(R.string.admin_pwd))) {

            startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
            progressDialog.dismiss();
        } else {

            Response.Listener<String> responseListener = this::loginRequest;
            LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (dialog != null) {

            dialog.dismiss();
            dialog = null;
        }
    }

    private void loginRequest(String response) {

        try {

            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean(getString(R.string.success));
            progressDialog.dismiss();

            if (success) {

                Log.i(TAG, response);
                int isTeacher = jsonResponse.getInt("isTeacher");

                if (isTeacher != 0) {

                    Intent intent = new Intent(LoginActivity.this, TeacherMainActivity.class);
                    intent.putExtra("teacherName", jsonResponse.getString("userName"));
                    intent.putExtra("teacherID", jsonResponse.getString("userID"));
                    intent.putExtra("teacherDOB", jsonResponse.getString("userDOB"));
                    intent.putExtra("teacherClass", jsonResponse.getString("userClass"));
                    intent.putExtra("teacherIntroduce", jsonResponse.getString("userIntroduce"));
                    intent.putExtra("teacherPassword", jsonResponse.getString("userPassword"));
                    intent.putExtra("hasProfileImage", jsonResponse.getBoolean("hasProfileImage"));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Student Main Activity", Toast.LENGTH_SHORT).show();
                }
            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                dialog = builder.setMessage("Check your ID and password")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            et_LoginID.setText("");
                            et_LoginPassword.setText("");
                        })
                        .create();
                dialog.show();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}