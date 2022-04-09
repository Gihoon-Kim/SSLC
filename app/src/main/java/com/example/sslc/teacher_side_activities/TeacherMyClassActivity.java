package com.example.sslc.teacher_side_activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.R;
import com.example.sslc.adapters.MyClassListAdapter;
import com.example.sslc.data.Programs;
import com.example.sslc.requests.GetMyClassRequest;
import com.ramotion.foldingcell.FoldingCell;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherMyClassActivity extends AppCompatActivity {

    private static final String TAG = TeacherMyClassActivity.class.getSimpleName();

    @BindView(R.id.rv_MyClassList)
    RecyclerView rv_MyClassList;

    MyClassListAdapter myClassListAdapter;

    private ArrayList<Programs> myClassList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_myclass);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        getMyClassList();

        rv_MyClassList.setHasFixedSize(true);
        rv_MyClassList.setLayoutManager(new LinearLayoutManager(this));
        rv_MyClassList.addItemDecoration(
                new DividerItemDecoration(
                        this,
                        DividerItemDecoration.VERTICAL
                )
        );

        myClassListAdapter = new MyClassListAdapter(this, myClassList);
        rv_MyClassList.setAdapter(myClassListAdapter);
    }

    private void getMyClassList() {

        Response.Listener<String> responseListener = response -> {

            try {

                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray(getString(R.string.program));

                Log.i(TAG, response);

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
                                classTitle,
                                classDescription,
                                classStartTime,
                                classEndTime,
                                classRoom
                        );
                        myClassList.add(programs);
                        myClassListAdapter.notifyDataSetChanged();
                    } else {

                        Toast.makeText(this, getString(R.string.get_data_fail), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        GetMyClassRequest getMyClassRequest = new GetMyClassRequest(getIntent().getStringExtra("teacherID"), responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getMyClassRequest);
    }
}