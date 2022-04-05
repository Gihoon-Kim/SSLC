package com.example.sslc.teacher_side_activities.ui.account;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sslc.R;
import com.example.sslc.databinding.FragmentModifyInformationBinding;

public class ModifyInformationFragment extends Fragment {

    private ModifyInformationViewModel mViewModel;
    private FragmentModifyInformationBinding binding;

    public static ModifyInformationFragment newInstance() {
        return new ModifyInformationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ModifyInformationViewModel modifyInformationViewModel =
                new ViewModelProvider(this).get(ModifyInformationViewModel.class);

        binding = FragmentModifyInformationBinding.inflate(inflater, container, false);
        return inflater.inflate(R.layout.fragment_modify_information, container, false);
    }
}