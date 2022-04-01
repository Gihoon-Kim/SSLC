package com.example.sslc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.adapters.TeacherClassDialogAdapter;
import com.example.sslc.data.AppData;
import com.example.sslc.databinding.ActivityAdminAddClassBinding;
import com.example.sslc.fragments.ClassFragment;
import com.example.sslc.requests.AddClassRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.Objects;

/*
 * To admin add a class with class name, teacher, description, start time and end time.
 * end time should be later than start time.
 */
public class AdminAddClassActivity extends AppCompatActivity {

    private static final String TAG = AdminAddClassActivity.class.getSimpleName();

    private ActivityAdminAddClassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminAddClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> addNewClass());

        // Spinner Value Setting
        setSpinner();
    }

    private void setSpinner() {

        // Spinner Times
        String[] times = getResources().getStringArray(R.array.time);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                times
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        Objects.requireNonNull(binding.include.spinnerStartTime).setAdapter(spinnerAdapter);
        Objects.requireNonNull(binding.include.spinnerEndTime).setAdapter(spinnerAdapter);

        // Spinner Teacher Name
        String[] allClass = new String[((AppData)getApplication()).getTeacherList().size()];
        allClass = ((AppData)getApplication()).getTeacherList().toArray(allClass);

        spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                allClass
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        Objects.requireNonNull(binding.include.spinnerClassTeacher).setAdapter(spinnerAdapter);
    }
    private void addNewClass() {

        String classTitle = binding.etClassTitle.getText().toString().trim();
        String classTeacher = Objects.requireNonNull(binding.include.spinnerClassTeacher).getSelectedItem().toString();
        String classStartTime = Objects.requireNonNull(binding.include.spinnerStartTime).getSelectedItem().toString();
        String classEndTime = Objects.requireNonNull(binding.include.spinnerEndTime).getSelectedItem().toString();
        String classDescription = Objects.requireNonNull(binding.etClassDescription).getText().toString().trim();
        String classRoom = Objects.requireNonNull(binding.include.etClassRoom).getText().toString().trim();

        if (classTitle.equals("") || classTeacher.equals("") || classDescription.equals("")) {

            Toast.makeText(this, getString(R.string.fields_not_filled), Toast.LENGTH_SHORT).show();
        } else if (binding.include.spinnerEndTime.getSelectedItemPosition() <= binding.include.spinnerStartTime.getSelectedItemPosition()) {

            Toast.makeText(this, R.string.end_time_start_time, Toast.LENGTH_SHORT).show();
        } else {

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getString(R.string.creating));
            progressDialog.setMessage(getString(R.string.create_in_progress));
            progressDialog.show();

            Response.Listener<String> responseListener = response -> addClassRequest(
                    classTitle,
                    classTeacher,
                    classStartTime,
                    classEndTime,
                    classDescription,
                    classRoom,
                    progressDialog,
                    response
            );
            AddClassRequest addClassRequest = new AddClassRequest(
                    classTitle,
                    classTeacher,
                    classDescription,
                    classStartTime,
                    classEndTime,
                    classRoom,
                    responseListener
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(addClassRequest);
        }
    }

    private void addClassRequest(
            String classTitle,
            String classTeacher,
            String classStartTime,
            String classEndTime,
            String classDescription,
            String classRoom,
            ProgressDialog progressDialog,
            String response
    ) {

        try {

            Log.i(TAG, response);
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean(getString(R.string.success));

            if (success) {

                Intent intent = new Intent(this, ClassFragment.class);
                intent.putExtra(getString(R.string.class_number), jsonResponse.getInt("rowCount") + 1);
                intent.putExtra(getString(R.string.class_title), classTitle);
                intent.putExtra(getString(R.string.class_teacher), classTeacher);
                intent.putExtra(getString(R.string.class_description), classDescription);
                intent.putExtra(getString(R.string.class_start_time), classStartTime);
                intent.putExtra(getString(R.string.class_end_time), classEndTime);
                intent.putExtra(getString(R.string.class_room), classRoom);
                setResult(9008, intent);

                ((AppData)getApplication()).getClassList().add(classTitle);

                Toast.makeText(this, getString(R.string.create_complete), Toast.LENGTH_SHORT).show();
                finish();
            } else {

                Toast.makeText(this, getString(R.string.create_failed), Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}