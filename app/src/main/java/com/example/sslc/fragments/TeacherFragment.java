package com.example.sslc.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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
import java.util.Objects;

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
    ActivityResultLauncher<Intent> updateTeacherActivityResultLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_teacher,
                container,
                false
        );
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

        teacherFragmentAdapter = new TeacherFragmentAdapter(requireContext(), teacherList, updateTeacherActivityResultLauncher);
        rv_Teacher.setAdapter(teacherFragmentAdapter);

        return view;
    }

    private void GetTeachers() {

        teacherList.clear();

        @SuppressLint("NotifyDataSetChanged") Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, "response : " + response);
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("Teacher");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject teacherItem = jsonArray.getJSONObject(i);
                    boolean success = teacherItem.getBoolean("success");

                    if (success) {

                        int teacherNumber = teacherItem.getInt("teacherNumber");
                        String teacherName = teacherItem.getString("teacherName");
                        String teacherClass = teacherItem.getString("teacherClass");
                        String teacherDOB = teacherItem.getString("teacherDOB");
                        String teacherIntroduce = teacherItem.getString("teacherIntroduce");
                        String teacherImage = teacherItem.getString("teacherImage");

                        byte [] encodeByte = Base64.decode(teacherImage, Base64.DEFAULT);
                        Bitmap profileBitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                        Teacher teacher = new Teacher(
                                teacherNumber,
                                teacherName,
                                profileBitmap,
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

    @SuppressLint("NotifyDataSetChanged")
    private void activityResultLauncherInit() {

        addTeacherActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == 9003) {

                        Intent intent = result.getData();
                        String teacherName = Objects.requireNonNull(intent).getStringExtra("teacherName");
                        String teacherClass = intent.getStringExtra("teacherClass");
                        String teacherImage = intent.getStringExtra("teacherImage");
                        String teacherDOB = intent.getStringExtra("teacherDOB");
                        String teacherIntroduce = intent.getStringExtra("teacherIntroduce");

                        byte [] encodeByte = Base64.decode(teacherImage, Base64.DEFAULT);
                        Bitmap profileBitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                        Teacher teacher = new Teacher(
                                teacherName,
                                profileBitmap,
                                teacherDOB,
                                teacherClass,
                                teacherIntroduce,
                                true
                        );
                        teacherList.add(teacher);
                        teacherFragmentAdapter.notifyDataSetChanged();
                    }
        });

        updateTeacherActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == 9004) {

                        Intent intent = result.getData();
                        int teacherID = Objects.requireNonNull(intent).getIntExtra("teacherID", 0);
                        String teacherName = intent.getStringExtra("teacherName");
                        String teacherClass = intent.getStringExtra("teacherClass");
                        String teacherIntroduce = intent.getStringExtra("teacherIntroduce");
                        String teacherImageBLOB = intent.getStringExtra("teacherImage");
                        String teacherDOB = intent.getStringExtra("teacherDOB");

                        Log.i(TAG, "teacherID = " + teacherID + "teacherName = " + teacherName);
                        byte [] encodeByte = Base64.decode(teacherImageBLOB, Base64.DEFAULT);
                        Bitmap profileBitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                        for (int i = 0; i < teacherList.size(); i++) {

                            if (teacherList.get(i).getTeacherId() == teacherID) {

                                teacherList.get(i).setName(teacherName);
                                teacherList.get(i).setDob(teacherDOB);
                                teacherList.get(i).setMyClass(teacherClass);
                                teacherList.get(i).setAboutMe(teacherIntroduce);
                                teacherList.get(i).setImage(profileBitmap);
                                teacherFragmentAdapter.notifyItemChanged(i);
                                break;
                            }
                        }
                    }
                }
        );
    }

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @OnClick(R.id.fab_Teacher)
    public void onFabTeacherClicked() {

        Intent intent = new Intent(getContext(), AdminAddTeacherActivity.class);
        addTeacherActivityResultLauncher.launch(intent);
    }
}