package com.example.sslc.teacher_side_activities.ui.myClassMain.ClassHomeWork;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sslc.R;
import com.example.sslc.adapters.TeacherClassHomeworkAdapter;
import com.example.sslc.data.ClassHomework;
import com.example.sslc.databinding.FragmentClassHomeworkListBinding;
import com.example.sslc.teacher_side_activities.ui.myClassMain.TeacherMyClassDetailViewModel;

import java.util.ArrayList;

public class ClassHomeWorkListFragment extends Fragment {

    private FragmentClassHomeworkListBinding binding;
    private TeacherMyClassDetailViewModel mainViewModel;

    private TeacherClassHomeworkAdapter adapter;
    private ArrayList<ClassHomework> classHomeworkList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentClassHomeworkListBinding.inflate(
                inflater,
                container,
                false
        );

        return inflater.inflate(R.layout.fragment_class_homework_list, container, false);
    }
}