package com.example.sslc.fragments;

import static com.android.volley.VolleyLog.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.AdminAddStudentActivity;
import com.example.sslc.R;
import com.example.sslc.adapters.StudentFragmentAdapter;
import com.example.sslc.data.Student;
import com.example.sslc.requests.GetStudentRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_Student)
    RecyclerView rv_Student;
    StudentFragmentAdapter studentFragmentAdapter;

    ArrayList<Student> studentList = new ArrayList<>();

    ActivityResultLauncher<Intent> addStudentActivityResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_student,
                container,
                false
        );
        ButterKnife.bind(this, view);

        rv_Student.setHasFixedSize(true);
        rv_Student.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv_Student.addItemDecoration(
                new DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                )
        );

        // Get Students from database
        getStudents();

        // Initialize activityResult Launchers
        activityResultLauncherInit();

        studentFragmentAdapter = new StudentFragmentAdapter(requireContext(), studentList);
        rv_Student.setAdapter(studentFragmentAdapter);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.fab_Student)
    public void onFabStudentClicked() {

        Intent intent = new Intent(requireContext(), AdminAddStudentActivity.class);
        addStudentActivityResult.launch(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void activityResultLauncherInit() {

        addStudentActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == 9005) {

                        Intent intent = result.getData();
                        int studentNumber = Objects.requireNonNull(intent).getIntExtra("studentNumber", 0);
                        String studentName = intent.getStringExtra("studentName");
                        String studentClass = intent.getStringExtra("studentClass");
                        String studentDOB = intent.getStringExtra("studentDOB");
                        String studentCountry = intent.getStringExtra("studentCountry");

                        Student student = new Student(
                                studentNumber,
                                studentName,
                                studentDOB,
                                studentClass,
                                false,
                                studentCountry
                        );

                        studentList.add(student);
                        studentFragmentAdapter.notifyDataSetChanged();
                    }
                }
        );
    }

    private void getStudents() {

        studentList.clear();

        @SuppressLint("NotifyDataSetChanged") Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, "response : " + response);
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("Student");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject studentItem = jsonArray.getJSONObject(i);
                    boolean success = studentItem.getBoolean("success");

                    if (success) {

                        int studentNumber = studentItem.getInt("StudentNumber");
                        String studentName = studentItem.getString("StudentName");
                        String studentDOB = studentItem.getString("StudentDOB");
                        String studentClass = studentItem.getString("StudentClass");
                        String studentCountry = studentItem.getString("Country");
                        String studentIntroduce = studentItem.getString("StudentIntroduce");

                        Student student = new Student(
                                studentNumber,
                                studentName,
                                studentDOB,
                                studentClass,
                                false,
                                studentIntroduce,
                                studentCountry
                        );
                        studentList.add(student);
                        studentFragmentAdapter.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
        };

        GetStudentRequest getStudentRequest = new GetStudentRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(getStudentRequest);
    }
}