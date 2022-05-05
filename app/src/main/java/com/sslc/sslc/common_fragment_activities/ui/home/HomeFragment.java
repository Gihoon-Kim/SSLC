package com.sslc.sslc.common_fragment_activities.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sslc.sslc.NotificationActivity;
import com.sslc.sslc.common_fragment_activities.ui.myClassMain.MyClassDetail;
import com.sslc.sslc.databinding.FragmentHomeBinding;
import com.sslc.sslc.student_side_activities.StudentMainViewModel;
import com.sslc.sslc.teacher_side_activities.TeacherMyClassActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        StudentMainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(StudentMainViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        Log.i(TAG, requireActivity().toString());
        LinearLayout ll_myClass = binding.llMyClass;
        ll_myClass.setOnClickListener(view -> {

            if (requireActivity().toString().contains("TeacherMainActivity")) {

                Log.i(TAG, requireActivity().getIntent().getStringExtra("teacherID"));
                Intent intent = new Intent(getActivity(), TeacherMyClassActivity.class);
                intent.putExtra("teacherID", requireActivity().getIntent().getStringExtra("teacherID"));
                intent.putExtra("isTeacher", true);
                startActivity(intent);

            } else {

                Intent intent = new Intent(getActivity(), MyClassDetail.class);
                intent.putExtra("classTitle", Objects.requireNonNull(mainViewModel.getStudentInformation().getValue()).getMyClass());
                intent.putExtra("isTeacher", false);
                startActivity(intent);
            }
        });

        LinearLayout ll_notification = binding.llNotification;
        ll_notification.setOnClickListener(view ->
                startActivity(new Intent(getActivity(), NotificationActivity.class))
        );

        LinearLayout ll_event = binding.llEvent;
        ll_event.setOnClickListener(view -> Snackbar.make(view, "Not in service", Snackbar.LENGTH_SHORT).show());

        LinearLayout ll_help = binding.llHelp;
        ll_help.setOnClickListener(view -> Snackbar.make(view, "Not in service", Snackbar.LENGTH_SHORT).show());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}