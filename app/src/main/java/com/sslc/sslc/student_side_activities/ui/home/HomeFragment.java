package com.sslc.sslc.student_side_activities.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.sslc.sslc.databinding.FragmentHomeBinding;
import com.sslc.sslc.NotificationActivity;
import com.sslc.sslc.student_side_activities.StudentMainViewModel;
import com.sslc.sslc.student_side_activities.ui.myClassMain.StudentMyClassMainActivity;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private StudentMainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mainViewModel =
                new ViewModelProvider(requireActivity()).get(StudentMainViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        LinearLayout ll_myClass = binding.llMyClass;
        ll_myClass.setOnClickListener(view -> {

            Intent intent = new Intent(getActivity(), StudentMyClassMainActivity.class);
            intent.putExtra("classTitle", Objects.requireNonNull(mainViewModel.getStudentInformation().getValue()).getMyClass());
            startActivity(intent);
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