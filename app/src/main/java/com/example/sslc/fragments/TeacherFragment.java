package com.example.sslc.fragments;

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
import com.example.sslc.AdminAddTeacherActivity;
import com.example.sslc.R;
import com.example.sslc.adapters.TeacherFragmentAdapter;
import com.example.sslc.data.Teacher;
import com.example.sslc.requests.GetTeacherRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeacherFragment extends Fragment {

    private static final String TAG = "TeacherFragment";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_Teacher)
    RecyclerView rv_Teacher;

    TeacherFragmentAdapter teacherFragmentAdapter;
    ArrayList<Teacher> teacherList = new ArrayList<>();

    ActivityResultLauncher<Intent> addTeacherActivityResultLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher, container, false);
        ButterKnife.bind(this, view);

        rv_Teacher.setHasFixedSize(true);
        rv_Teacher.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv_Teacher.addItemDecoration(new DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL));

        // Get Teachers from database
        GetTeachers();

        // Initialize activityResult Launchers
        activityResultLauncherInit();

        teacherFragmentAdapter = new TeacherFragmentAdapter(teacherList);
        rv_Teacher.setAdapter(teacherFragmentAdapter);

        return view;
    }

    private void GetTeachers() {

        teacherList.clear();

        Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, "response : " + response);
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("Teacher");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject teacherItem = jsonArray.getJSONObject(i);
                    boolean success = teacherItem.getBoolean("success");

                    if (success) {

                        int teacherID = teacherItem.getInt("teacherID");
                        String teacherName = teacherItem.getString("teacherName");
                        String teacherClass = teacherItem.getString("teacherClass");
                        String teacherDOB = teacherItem.getString("teacherDOB");
                        String teacherIntroduce = teacherItem.getString("teacherIntroduce");
                        String teacherImage = teacherItem.getString("teacherImage");

                        Teacher teacher = new Teacher(
                                teacherID,
                                teacherName,
                                teacherImage,
                                teacherDOB,
                                teacherClass,
                                teacherIntroduce,
                                true
                        );
                        teacherList.add(teacher);
                        teacherFragmentAdapter.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        GetTeacherRequest getTeacherRequest = new GetTeacherRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(getTeacherRequest);
    }

    private void activityResultLauncherInit() {

        addTeacherActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == 9003) {

                        Intent intent = result.getData();
                        String teacherName = intent.getStringExtra("teacherName");
                        String teacherClass = intent.getStringExtra("teacherClass");
                        String teacherImage = intent.getStringExtra("teacherImage");
                        String teacherDOB = intent.getStringExtra("teacherDOB");
                        String teacherIntroduce = intent.getStringExtra("teacherIntroduce");

                        Teacher teacher = new Teacher(
                                teacherName,
                                teacherImage,
                                teacherDOB,
                                teacherClass,
                                teacherIntroduce,
                                true
                        );
                        teacherList.add(teacher);
                        teacherFragmentAdapter.notifyDataSetChanged();
                    }
        });
    }

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @OnClick(R.id.fab_Teacher)
    public void onFabTeacherClicked() {

        Intent intent = new Intent(getContext(), AdminAddTeacherActivity.class);
        addTeacherActivityResultLauncher.launch(intent);
    }
}