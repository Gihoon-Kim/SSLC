package com.sslc.sslc.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.sslc.sslc.R;
import com.sslc.sslc.adapters.TeacherFragmentAdapter;
import com.sslc.sslc.admin_side_activities.AdminAddTeacherActivity;
import com.sslc.sslc.data.Teacher;
import com.sslc.sslc.requests.GetTeacherRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * This Fragment is for showing all teachers in the database.
 */
public class TeacherFragment extends Fragment {

    private static final String TAG = TeacherFragment.class.getSimpleName();

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_Teacher)
    RecyclerView rv_Teacher;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmer_Layout)
    ShimmerFrameLayout shimmerFrameLayout;

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

        shimmerFrameLayout.startShimmer();

        rv_Teacher.setHasFixedSize(true);
        rv_Teacher.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv_Teacher.addItemDecoration(new DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL)
        );

        // Get Teachers from database
        getTeachers();

        // Initialize activityResult Launchers
        initActivityResultLaunchers();

        teacherFragmentAdapter = new TeacherFragmentAdapter(
                requireContext(),
                teacherList,
                updateTeacherActivityResultLauncher
        );
        rv_Teacher.setAdapter(teacherFragmentAdapter);

        return view;
    }

    private void getTeachers() {

        teacherList.clear();

        Response.Listener<String> responseListener = this::getTeacherRequest;
        GetTeacherRequest getTeacherRequest = new GetTeacherRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(getTeacherRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getTeacherRequest(String response) {

        try {

            Log.i(TAG, response);
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArray = jsonResponse.getJSONArray(getString(R.string.teacher));

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject teacherItem = jsonArray.getJSONObject(i);
                boolean success = teacherItem.getBoolean(getString(R.string.success));

                if (success) {

                    Log.i(TAG, teacherItem.toString());
                    int teacherNumber = teacherItem.getInt(getString(R.string.teacher_number));
                    String teacherName = teacherItem.getString(getString(R.string.teacher_name));
                    String teacherDOB = teacherItem.getString(getString(R.string.teacher_dob));
                    String teacherClass = teacherItem.getString(getString(R.string.teacher_class));
                    String teacherIntroduce = teacherItem.getString(getString(R.string.teacher_introduce));
                    boolean hasProfileImage = teacherItem.getString(getString(R.string.has_profile_image)).equals("1");
                    Log.i(TAG, String.valueOf(hasProfileImage));

                    Teacher teacher = new Teacher(
                            teacherNumber,
                            teacherName,
                            teacherDOB,
                            teacherClass,
                            teacherIntroduce,
                            hasProfileImage,
                            true,
                            null
                    );

                    teacherList.add(teacher);
                    teacherFragmentAdapter.notifyDataSetChanged();
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initActivityResultLaunchers() {

        addTeacherActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == 9003) {

                        Intent intent = result.getData();
                        int teacherNumber = Objects.requireNonNull(intent).getIntExtra(getString(R.string.teacher_number), 0);
                        String teacherName = Objects.requireNonNull(intent).getStringExtra(getString(R.string.teacher_name));
                        String teacherClass = intent.getStringExtra(getString(R.string.teacher_class));
                        String teacherDOB = intent.getStringExtra(getString(R.string.teacher_dob));
                        String teacherIntroduce = intent.getStringExtra(getString(R.string.teacher_introduce));
                        boolean hasProfileImage = intent.getIntExtra("hasProfileImage", 0) == 1;
                        Uri profileImageUri = intent.getParcelableExtra("imageUri");

                        Teacher teacher = new Teacher(
                                teacherNumber,
                                teacherName,
                                teacherDOB,
                                teacherClass,
                                teacherIntroduce,
                                hasProfileImage,
                                true,
                                profileImageUri
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
                        int teacherNumber = Objects.requireNonNull(intent).getIntExtra(getString(R.string.teacher_number), 0);
                        String teacherName = Objects.requireNonNull(intent).getStringExtra(getString(R.string.teacher_name));
                        String teacherClass = intent.getStringExtra(getString(R.string.teacher_class));
                        String teacherDOB = intent.getStringExtra(getString(R.string.teacher_dob));
                        String teacherIntroduce = intent.getStringExtra(getString(R.string.teacher_introduce));
                        boolean hasProfileImage = intent.getIntExtra("hasProfileImage", 0) == 1;
                        Uri profileImageUri = intent.getParcelableExtra("imageUri");
                        Log.i(TAG, "After Update " + hasProfileImage);

                        for (int i = 0; i < teacherList.size(); i++) {

                            if (teacherList.get(i).getTeacherNumber() == teacherNumber) {

                                teacherList.get(i).setName(teacherName);
                                teacherList.get(i).setDob(teacherDOB);
                                teacherList.get(i).setMyClass(teacherClass);
                                teacherList.get(i).setAboutMe(teacherIntroduce);
                                teacherList.get(i).setHasProfileImage(hasProfileImage);
                                teacherList.get(i).setProfileImage(profileImageUri);
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