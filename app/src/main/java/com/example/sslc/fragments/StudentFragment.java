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
import com.example.sslc.admin_side_activities.AdminAddStudentActivity;
import com.example.sslc.R;
import com.example.sslc.adapters.StudentFragmentAdapter;
import com.example.sslc.data.Student;
import com.example.sslc.requests.GetStudentRequest;
import com.facebook.shimmer.ShimmerFrameLayout;

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
    @BindView(R.id.shimmer_Layout)
    ShimmerFrameLayout shimmerFrameLayout;
    StudentFragmentAdapter studentFragmentAdapter;

    ArrayList<Student> studentList = new ArrayList<>();

    ActivityResultLauncher<Intent> addStudentActivityResultLauncher;
    ActivityResultLauncher<Intent> updateStudentActivityResultLauncher;

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

        shimmerFrameLayout.startShimmer();

        // Get Students from database
        getStudents();

        // Initialize activityResult Launchers
        initActivityResultLaunchers();

        studentFragmentAdapter = new StudentFragmentAdapter(
                requireContext(),
                studentList,
                updateStudentActivityResultLauncher
        );
        rv_Student.setAdapter(studentFragmentAdapter);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.fab_Student)
    public void onFabStudentClicked() {

        Intent intent = new Intent(requireContext(), AdminAddStudentActivity.class);
        addStudentActivityResultLauncher.launch(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initActivityResultLaunchers() {

        addStudentActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == 9005) {

                        Intent intent = result.getData();
                        int studentNumber = Objects.requireNonNull(intent).getIntExtra(getString(R.string.student_number), 0);
                        String studentName = intent.getStringExtra(getString(R.string.student_name));
                        String studentClass = intent.getStringExtra(getString(R.string.student_class));
                        String studentDOB = intent.getStringExtra(getString(R.string.student_dob));
                        String studentCountry = intent.getStringExtra(getString(R.string.student_country));

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

        updateStudentActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    // Student Class Update
                    if (result.getResultCode() == 9006) {

                        Intent intent = result.getData();
                        int studentNumber = Objects.requireNonNull(intent).getIntExtra(getString(R.string.student_number), 0);

                        String studentClass = intent.getStringExtra(getString(R.string.student_class));

                        for (int i = 0; i < studentList.size(); i++) {

                            if (studentList.get(i).getStudentNumber() == studentNumber) {

                                studentList.get(i).setMyClass(studentClass);
                                studentFragmentAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }

                    // Student Delete
                    else if (result.getResultCode() == 9007) {

                        Intent intent = result.getData();
                        int studentNumber = Objects.requireNonNull(intent).getIntExtra("studentNumber", 0);

                        for (int i = 0; i < studentList.size(); i++) {

                            if (studentList.get(i).getStudentNumber() == studentNumber) {

                                studentList.remove(i);
                                studentFragmentAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }
                }
        );
    }

    private void getStudents() {

        studentList.clear();

        Response.Listener<String> responseListener = this::getStudentRequest;
        GetStudentRequest getStudentRequest = new GetStudentRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(getStudentRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getStudentRequest(String response) {

        try {

            Log.i(TAG, "response : " + response);
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArray = jsonResponse.getJSONArray(getString(R.string.student));

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject studentItem = jsonArray.getJSONObject(i);
                boolean success = studentItem.getBoolean(getString(R.string.success));

                if (success) {

                    int studentNumber = studentItem.getInt(getString(R.string.student_number));
                    String studentName = studentItem.getString(getString(R.string.student_name));
                    String studentDOB = studentItem.getString(getString(R.string.student_dob));
                    String studentClass = studentItem.getString(getString(R.string.student_class));
                    String studentCountry = studentItem.getString(getString(R.string.student_country));
                    String studentIntroduce = studentItem.getString(getString(R.string.student_introduce));

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
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}