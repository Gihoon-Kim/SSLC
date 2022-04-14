package com.example.sslc.teacher_side_activities.ui.myClassMain;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sslc.R;

public class ClassHomeWorkFragment extends Fragment {

    private ClassHomeWorkViewModel homeWorkViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeWorkViewModel = new ViewModelProvider(this).get(ClassHomeWorkViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_class_homework, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}