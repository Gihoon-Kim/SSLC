package com.sslc.sslc.admin_side_activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sslc.sslc.R;
import com.sslc.sslc.data.AppData;
import com.sslc.sslc.databinding.ActivityAdminClassDetailBinding;
import com.sslc.sslc.dialog.ChangeNewsTitleDialog;
import com.sslc.sslc.fragments.ClassFragment;
import com.sslc.sslc.interfaces.ChangeNewsTitleDialogListener;
import com.sslc.sslc.requests.UpdateClassRequest;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.Objects;

/*
 * When an admin clicks a Class recycler view item.
 * Admin can check class's data (Name, Teacher, Time, introduce, and class room),
 * and also update class's data.
 */
public class AdminClassDetailActivity extends AppCompatActivity implements ChangeNewsTitleDialogListener {

    private static final String TAG = AdminClassDetailActivity.class.getSimpleName();
    private ActivityAdminClassDetailBinding binding;

    int classNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminClassDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getDataAndSetUI();

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> updateClass());
    }

    private void getDataAndSetUI() {

        // Get Data from Intent
        Intent intent = getIntent();
        classNumber = intent.getIntExtra(getString(R.string.class_number), 0);
        String classTitle = intent.getStringExtra(getString(R.string.class_title));
        String classTeacher = intent.getStringExtra(getString(R.string.class_teacher));
        String classDescription = intent.getStringExtra(getString(R.string.class_description));
        String classStartTime = intent.getStringExtra(getString(R.string.class_start_time));
        String classEndTime = intent.getStringExtra(getString(R.string.class_end_time));
        String classRoom = intent.getStringExtra(getString(R.string.class_room));

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(classTitle);
        toolBarLayout.setOnClickListener(view -> changeTitle(toolBarLayout));

        setSpinner();

        for (int i = 0; i < ((AppData)getApplication()).getTeacherList().size(); i++) {

            if (((AppData)getApplication()).getTeacherList().get(i).equals(classTeacher)) {

                Objects.requireNonNull(binding.include.spinnerClassTeacher).setSelection(i);
                break;
            }
        }
        Objects.requireNonNull(binding.etClassDescription).setText(classDescription);
        Objects.requireNonNull(binding.include.etClassRoom).setText(classRoom);

        for (int i = 0; i < Objects.requireNonNull(binding.include.spinnerStartTime).getCount(); i++) {

            if (classStartTime.equals(String.valueOf(binding.include.spinnerStartTime.getItemAtPosition(i)))) {

                binding.include.spinnerStartTime.setSelection(i);
            } else if (classEndTime.equals(String.valueOf(Objects.requireNonNull(binding.include.spinnerEndTime).getItemAtPosition(i)))) {

                binding.include.spinnerEndTime.setSelection(i);
                break;
            }
        }
    }

    private void changeTitle(@NonNull CollapsingToolbarLayout toolBarLayout) {
        ChangeNewsTitleDialog changeNewsTitleDialog = new ChangeNewsTitleDialog(Objects.requireNonNull(toolBarLayout.getTitle()).toString());
        changeNewsTitleDialog.show(
                getSupportFragmentManager(),
                TAG + ChangeNewsTitleDialog.class.getSimpleName()
        );
    }

    private void setSpinner() {

        // Spinners for Start time and End time
        String[] times = getResources().getStringArray(R.array.time);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                times
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        Objects.requireNonNull(binding.include.spinnerStartTime).setAdapter(spinnerAdapter);
        Objects.requireNonNull(binding.include.spinnerEndTime).setAdapter(spinnerAdapter);

        // Spinner for Teacher Name
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

    private void updateClass() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.updating));
        progressDialog.setMessage(getString(R.string.update_in_progress));
        progressDialog.show();

        Response.Listener<String> responseListener = response -> updateClassRequest(progressDialog, response);
        UpdateClassRequest updateClassRequest = new UpdateClassRequest(
                classNumber,
                Objects.requireNonNull(binding.toolbarLayout.getTitle()).toString(),
                Objects.requireNonNull(binding.include.spinnerClassTeacher).getSelectedItem().toString(),
                Objects.requireNonNull(binding.etClassDescription).getText().toString(),
                Objects.requireNonNull(binding.include.spinnerStartTime).getSelectedItem().toString(),
                Objects.requireNonNull(binding.include.spinnerEndTime).getSelectedItem().toString(),
                Objects.requireNonNull(binding.include.etClassRoom).getText().toString(),
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(updateClassRequest);
    }

    private void updateClassRequest(ProgressDialog progressDialog, String response) {

        try {

            Log.i(TAG, response);
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean(getString(R.string.success));

            if (success) {

                Intent intent = new Intent(this, ClassFragment.class);
                intent.putExtra(getString(R.string.class_number), classNumber);
                intent.putExtra(getString(R.string.class_title), Objects.requireNonNull(binding.toolbarLayout.getTitle()).toString());
                intent.putExtra(getString(R.string.class_teacher), Objects.requireNonNull(binding.include.spinnerClassTeacher).getSelectedItem().toString());
                intent.putExtra(getString(R.string.class_description), Objects.requireNonNull(binding.etClassDescription).getText().toString());
                intent.putExtra(getString(R.string.class_start_time), Objects.requireNonNull(binding.include.spinnerStartTime).getSelectedItem().toString());
                intent.putExtra(getString(R.string.class_end_time), Objects.requireNonNull(binding.include.spinnerEndTime).getSelectedItem().toString());
                intent.putExtra(getString(R.string.class_room), Objects.requireNonNull(binding.include.etClassRoom).getText().toString());
                setResult(9009, intent);

                Toast.makeText(this, getString(R.string.update_complete), Toast.LENGTH_SHORT).show();
                finish();
            } else {

                Toast.makeText(this, getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void applyNewTitle(String newTitle) {

        binding.toolbarLayout.setTitle(newTitle);
    }
}