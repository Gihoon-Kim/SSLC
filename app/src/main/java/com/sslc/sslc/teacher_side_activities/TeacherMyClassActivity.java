package com.sslc.sslc.teacher_side_activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sslc.sslc.R;
import com.sslc.sslc.adapters.MyClassListAdapter;
import com.sslc.sslc.data.Programs;
import com.sslc.sslc.requests.GetMyClassRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherMyClassActivity extends AppCompatActivity {

    private static final String TAG = TeacherMyClassActivity.class.getSimpleName();

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_MyClassList)
    RecyclerView rv_MyClassList;

    MyClassListAdapter myClassListAdapter;

    private final ArrayList<Programs> myClassList = new ArrayList<>();

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

        Response.Listener<String> responseListener = this::getMyClassList;

        GetMyClassRequest getMyClassRequest = new GetMyClassRequest(getIntent().getStringExtra(getString(R.string.teacher_id)), responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getMyClassRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getMyClassList(String response) {
        try {

            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArray = jsonResponse.getJSONArray(getString(R.string.program));

            Log.i(TAG, response);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject classItem = jsonArray.getJSONObject(i);
                boolean success = classItem.getBoolean(getString(R.string.success));

                if (success) {

                    String classTitle = classItem.getString(getString(R.string.class_title));
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
    }
}