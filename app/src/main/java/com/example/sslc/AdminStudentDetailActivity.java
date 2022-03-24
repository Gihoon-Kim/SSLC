package com.example.sslc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminStudentDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_StudentName)
    TextView tv_StudentName;
    @BindView(R.id.tv_CurrentClass)
    TextView tv_CurrentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_detail);

        ButterKnife.bind(this);
    }
}