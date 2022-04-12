package com.example.sslc.teacher_side_activities.ui.myClassMain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sslc.databinding.ClassStudentListFragmentBinding;

public class ClassStudentListFragment extends Fragment {

    private ClassStudentListViewModel studentListViewModel;
    private ClassStudentListFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentListViewModel = new ViewModelProvider(this).get(ClassStudentListViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState
    ) {

        binding = ClassStudentListFragmentBinding.inflate(
                inflater,
                container,
                false
        );

        final TextView textView = binding.tvClassStudentsTextView;
        studentListViewModel.getText()
                .observe(
                        getViewLifecycleOwner(),
                        textView::setText
                );
        studentListViewModel.setText("This is Second Fragment");

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}