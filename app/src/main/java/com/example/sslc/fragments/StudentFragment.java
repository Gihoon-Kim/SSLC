package com.example.sslc.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sslc.AdminAddStudentActivity;
import com.example.sslc.R;
import com.example.sslc.adapters.StudentFragmentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_Student)
    RecyclerView rv_Student;
    StudentFragmentAdapter studentFragmentAdapter;

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

        studentFragmentAdapter = new StudentFragmentAdapter();
        rv_Student.setAdapter(studentFragmentAdapter);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.fab_Student)
    public void onFabStudentClicked() {

        Intent intent = new Intent(requireContext(), AdminAddStudentActivity.class);
        startActivity(intent);
    }
}