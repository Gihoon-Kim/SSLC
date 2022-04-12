package com.example.sslc.teacher_side_activities.ui.myClassMain;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sslc.R;
import com.example.sslc.databinding.ClassHomeWorkFragmentBinding;

public class ClassHomeWorkFragment extends Fragment {

    private ClassHomeWorkViewModel homeWorkViewModel;
    private ClassHomeWorkFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeWorkViewModel = new ViewModelProvider(this).get(ClassHomeWorkViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = ClassHomeWorkFragmentBinding.inflate(
                inflater,
                container,
                false
        );

        final TextView textView = binding.tvClassHomeWorkTextView;
        homeWorkViewModel.getText().observe(
                getViewLifecycleOwner(),
                textView::setText
        );
        homeWorkViewModel.setText("This is Third Fragment");
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
}