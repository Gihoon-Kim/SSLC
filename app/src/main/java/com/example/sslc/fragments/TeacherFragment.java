package com.example.sslc.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sslc.AdminAddTeacherActivity;
import com.example.sslc.R;
import com.example.sslc.adapters.TeacherFragmentAdapter;
import com.example.sslc.data.Teacher;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeacherFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_Teacher)
    RecyclerView rv_Teacher;

    TeacherFragmentAdapter teacherFragmentAdapter;
    ArrayList<Teacher> teacherList = new ArrayList<>();

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

        teacherFragmentAdapter = new TeacherFragmentAdapter(teacherList);
        rv_Teacher.setAdapter(teacherFragmentAdapter);

        return view;
    }

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @OnClick(R.id.fab_Teacher)
    public void onFabTeacherClicked() {

        Intent intent = new Intent(getContext(), AdminAddTeacherActivity.class);
        startActivity(intent);
    }
}