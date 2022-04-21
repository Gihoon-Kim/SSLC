package com.example.sslc.teacher_side_activities.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sslc.databinding.FragmentHomeBinding;
import com.example.sslc.teacher_side_activities.TeacherMyClassActivity;
import com.example.sslc.teacher_side_activities.TeacherNotificationActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        LinearLayout ll_myClass = binding.llMyClass;
        ll_myClass.setOnClickListener(view -> {

            Intent intent = new Intent(getActivity(), TeacherMyClassActivity.class);
            intent.putExtra("teacherID", requireActivity().getIntent().getStringExtra("teacherID"));
            startActivity(intent);
        });

        LinearLayout ll_notification = binding.llNotification;
        ll_notification.setOnClickListener(view ->
                startActivity(new Intent(getActivity(), TeacherNotificationActivity.class))
        );
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}