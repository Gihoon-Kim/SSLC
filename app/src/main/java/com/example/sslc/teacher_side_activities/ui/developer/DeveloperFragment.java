package com.example.sslc.teacher_side_activities.ui.developer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sslc.R;
import com.example.sslc.databinding.FragmentDeveloperBinding;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeveloperFragment extends Fragment {

    private FragmentDeveloperBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DeveloperViewModel developerViewModel =
                new ViewModelProvider(this).get(DeveloperViewModel.class);

        binding = FragmentDeveloperBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}