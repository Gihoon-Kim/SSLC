package com.example.sslc.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sslc.AdminAddClassActivity;
import com.example.sslc.R;
import com.example.sslc.adapters.ClassFragmentAdapter;
import com.example.sslc.data.Programs;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassFragment extends Fragment {

    @BindView(R.id.rv_Class)
    RecyclerView rv_Class;

    ClassFragmentAdapter classFragmentAdapter;
    ArrayList<Programs> programDataList = new ArrayList<>();

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

        // Create RecyclerView
        rv_Class.setHasFixedSize(true);
        rv_Class.setLayoutManager(new LinearLayoutManager(requireContext()));
        classFragmentAdapter = new ClassFragmentAdapter(requireContext(), programDataList);
        rv_Class.setAdapter(classFragmentAdapter);

        return view;
    }

    @OnClick(R.id.fab_Class)
    public void onFabClassClicked() {

        Intent intent = new Intent(requireContext(), AdminAddClassActivity.class);
        startActivity(intent);
    }
}