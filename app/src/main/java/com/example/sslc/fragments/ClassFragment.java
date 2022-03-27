package com.example.sslc.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.AdminAddClassActivity;
import com.example.sslc.R;
import com.example.sslc.adapters.ClassFragmentAdapter;
import com.example.sslc.data.Programs;
import com.example.sslc.requests.GetClassRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassFragment extends Fragment {

    private static final String TAG = "ClassFragment";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_Class)
    RecyclerView rv_Class;

    ClassFragmentAdapter classFragmentAdapter;
    ArrayList<Programs> programDataList = new ArrayList<>();

    ActivityResultLauncher<Intent> addClassResultLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_class,
                container,
                false
        );
        ButterKnife.bind(this, view);

        // Get Classes from database
        getPrograms();
        // Initialize activityResultLaunchers
        activityResultLauncherInit();

        // Create RecyclerView
        rv_Class.setHasFixedSize(true);
        rv_Class.setLayoutManager(new LinearLayoutManager(requireContext()));
        classFragmentAdapter = new ClassFragmentAdapter(requireContext(), programDataList);
        rv_Class.setAdapter(classFragmentAdapter);

        return view;
    }

    private void getPrograms() {

        programDataList.clear();

        @SuppressLint("NotifyDataSetChanged") Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, response);
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("Class");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject classItem = jsonArray.getJSONObject(i);
                    boolean success = classItem.getBoolean("success");

                    if (success) {

                        int classNumber = classItem.getInt("classNumber");
                        String classTitle = classItem.getString("classTitle");
                        String classTeacher = classItem.getString("classTeacher");
                        String classDescription = classItem.getString("classDescription");
                        String classStartTime = classItem.getString("classStartTime");
                        String classEndTime = classItem.getString("classEndTime");
                        String classRoom = classItem.getString("classRoom");

                        Programs programs = new Programs(
                                classNumber,
                                classTitle,
                                classTeacher,
                                classDescription,
                                classStartTime,
                                classEndTime,
                                classRoom
                        );
                        programDataList.add(programs);
                        classFragmentAdapter.notifyDataSetChanged();
                    } else {

                        Toast.makeText(requireContext(), "Get Data Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        GetClassRequest getClassRequest = new GetClassRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(getClassRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void activityResultLauncherInit() {

        addClassResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == 9008) {

                        Intent intent = result.getData();
                        int classNumber = Objects.requireNonNull(intent).getIntExtra("classNumber", 0);
                        String classTitle = intent.getStringExtra("classTitle");
                        String classTeacher = intent.getStringExtra("classTeacher");
                        String classDescription = intent.getStringExtra("classDescription");
                        String classStartTime = intent.getStringExtra("classStartTime");
                        String classEndTime = intent.getStringExtra("classEndTime");
                        String classRoom = intent.getStringExtra("classRoom");

                        Programs programs = new Programs(
                                classNumber,
                                classTitle,
                                classTeacher,
                                classDescription,
                                classStartTime,
                                classEndTime,
                                classRoom
                        );
                        programDataList.add(programs);
                        classFragmentAdapter.notifyDataSetChanged();
                    }
                }
        );
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.fab_Class)
    public void onFabClassClicked() {

        Intent intent = new Intent(requireContext(), AdminAddClassActivity.class);
        addClassResultLauncher.launch(intent);
    }
}