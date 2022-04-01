package com.example.sslc.admin_side_activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.R;
import com.example.sslc.data.AppData;
import com.example.sslc.fragments.StudentFragment;
import com.example.sslc.requests.DeleteStudentRequest;
import com.example.sslc.requests.UpdateStudentClassRequest;

import org.json.JSONObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * When an admin clicks a student recycler view item.
 * Admin can check teacher's information (Name, Class),
 * and also update student's class.
 * Other information of a student should be changed by student themselves.
 */
public class AdminStudentDetailActivity extends AppCompatActivity {

    private static final String TAG = AdminStudentDetailActivity.class.getSimpleName();

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_StudentName)
    TextView tv_StudentName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_CurrentClass)
    TextView tv_CurrentClass;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spinner_StudentClass)
    Spinner spinner_StudentClass;

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
        tv_StudentName.setText(intent.getStringExtra(getString(R.string.student_name)));
        tv_CurrentClass.setText(intent.getStringExtra(getString(R.string.student_class)));

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
    @OnClick(R.id.btn_UpdateStudent)
    public void onBtnUpdateStudentClicked() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.updating));
        progressDialog.setMessage(getString(R.string.update_in_progress));

        Response.Listener<String> responseListener = this::updateStudentClassRequest;
        UpdateStudentClassRequest updateStudentClassRequest = new UpdateStudentClassRequest(
                studentNumber,
                spinner_StudentClass.getSelectedItem().toString(),
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(updateStudentClassRequest);
    }

    private void updateStudentClassRequest(String response) {

        try {

            Log.i(TAG, response);
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean(getString(R.string.success));

            if (success) {

                Intent intent = new Intent(this, StudentFragment.class);
                intent.putExtra(getString(R.string.student_number), studentNumber);
                intent.putExtra(getString(R.string.student_class), spinner_StudentClass.getSelectedItem().toString());
                setResult(9006, intent);
                finish();
            } else {

                Toast.makeText(this, getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_DeleteStudent)
    public void onBtnDeleteStudentClicked() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to delete student " + tv_StudentName.getText() + "?")
                .setTitle("Deleting a student")
                .setPositiveButton("Yes", (dialogInterface, i) -> deleteStudent())
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteStudent() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.deleting));
        progressDialog.setMessage(getString(R.string.delete_in_progress));

        Response.Listener<String> responseListener = this::deleteStudentRequest;
        DeleteStudentRequest deleteStudentRequest = new DeleteStudentRequest(
                studentNumber,
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(deleteStudentRequest);
    }

    private void deleteStudentRequest(String response) {

        try {

            progressDialog.show();
            Log.i(TAG, response);
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean(getString(R.string.success));

            if (success) {

                Toast.makeText(this, getString(R.string.delete_complete), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, StudentFragment.class);
                intent.putExtra(getString(R.string.student_number), studentNumber);
                setResult(9007, intent);

                finish();
            } else {

                Toast.makeText(this, getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}