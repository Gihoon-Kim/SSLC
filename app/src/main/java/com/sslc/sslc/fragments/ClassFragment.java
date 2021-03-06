package com.sslc.sslc.fragments;

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
import com.sslc.sslc.admin_side_activities.AdminAddClassActivity;
import com.sslc.sslc.R;
import com.sslc.sslc.adapters.ClassFragmentAdapter;
import com.sslc.sslc.data.Programs;
import com.sslc.sslc.requests.GetClassRequest;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * Fragment for showing all Classes in database.
 */
public class ClassFragment extends Fragment {

    private static final String TAG = ClassFragment.class.getSimpleName();

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmer_Layout)
    ShimmerFrameLayout shimmerFrameLayout;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_Class)
    RecyclerView rv_Class;

    private ClassFragmentAdapter classFragmentAdapter;
    private final ArrayList<Programs> programDataList = new ArrayList<>();

    private ActivityResultLauncher<Intent> addClassResultLauncher;
    private ActivityResultLauncher<Intent> updateClassResultLauncher;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_class,
                container,
                false
        );
        ButterKnife.bind(this, view);

        // ShimmerFrameLayout for looking nicer while get classes.
        shimmerFrameLayout.startShimmer();

        // Get Classes from database
        getPrograms();
        // Initialize activityResultLaunchers
        initActivityResultLauncher();

        // Create RecyclerView
        rv_Class.setHasFixedSize(true);
        rv_Class.setLayoutManager(new LinearLayoutManager(requireContext()));
        classFragmentAdapter = new ClassFragmentAdapter(
                requireContext(),
                programDataList,
                updateClassResultLauncher
        );
        rv_Class.setAdapter(classFragmentAdapter);

        return view;
    }

    /*
     * Get classes (programs) from database
     */
    private void getPrograms() {

        programDataList.clear();

        @SuppressLint("NotifyDataSetChanged")
        Response.Listener<String> responseListener = this::getClassRequest;
        GetClassRequest getClassRequest = new GetClassRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(getClassRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getClassRequest(String response) {

        try {

            Log.i(TAG, response);
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArray = jsonResponse.getJSONArray(getString(R.string.program));

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject classItem = jsonArray.getJSONObject(i);
                boolean success = classItem.getBoolean(getString(R.string.success));

                if (success) {

                    int classNumber = classItem.getInt(getString(R.string.class_number));
                    String classTitle = classItem.getString(getString(R.string.class_title));
                    String classTeacher = classItem.getString(getString(R.string.class_teacher));
                    String classDescription = classItem.getString(getString(R.string.class_description));
                    String classStartTime = classItem.getString(getString(R.string.class_start_time));
                    String classEndTime = classItem.getString(getString(R.string.class_end_time));
                    String classRoom = classItem.getString(getString(R.string.class_room));

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
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                } else {

                    Toast.makeText(requireContext(), getString(R.string.get_data_fail), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Initialize all activity result launchers.
     */
    @SuppressLint("NotifyDataSetChanged")
    private void initActivityResultLauncher() {

        addClassResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == 9008) {

                        Intent intent = result.getData();
                        int classNumber = Objects.requireNonNull(intent).getIntExtra(getString(R.string.class_number), 0);
                        String classTitle = intent.getStringExtra(getString(R.string.class_title));
                        String classTeacher = intent.getStringExtra(getString(R.string.class_teacher));
                        String classDescription = intent.getStringExtra(getString(R.string.class_description));
                        String classStartTime = intent.getStringExtra(getString(R.string.class_start_time));
                        String classEndTime = intent.getStringExtra(getString(R.string.class_end_time));
                        String classRoom = intent.getStringExtra(getString(R.string.class_room));

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

        updateClassResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == 9009) {

                        Intent intent = result.getData();
                        int classNumber = Objects.requireNonNull(intent).getIntExtra(getString(R.string.class_number), 0);
                        String classTitle = intent.getStringExtra(getString(R.string.class_title));
                        String classTeacher = intent.getStringExtra(getString(R.string.class_teacher));
                        String classDescription = intent.getStringExtra(getString(R.string.class_description));
                        String classStartTime = intent.getStringExtra(getString(R.string.class_start_time));
                        String classEndTime = intent.getStringExtra(getString(R.string.class_end_time));
                        String classRoom = intent.getStringExtra(getString(R.string.class_room));

                        for (int i = 0; i < programDataList.size(); i++) {

                            if (programDataList.get(i).getProgramNumber() == classNumber) {

                                programDataList.get(i).setProgramTitle(classTitle);
                                programDataList.get(i).setProgramTeacher(classTeacher);
                                programDataList.get(i).setProgramDescription(classDescription);
                                programDataList.get(i).setProgramStartTime(classStartTime);
                                programDataList.get(i).setProgramEndTime(classEndTime);
                                programDataList.get(i).setProgramClassRoom(classRoom);
                                classFragmentAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
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