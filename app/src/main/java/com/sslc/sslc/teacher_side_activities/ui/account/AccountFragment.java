package com.sslc.sslc.teacher_side_activities.ui.account;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.sslc.sslc.R;
import com.sslc.sslc.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {

    private static final String TAG = AccountFragment.class.getSimpleName();
    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        Log.i(TAG, requireActivity().getIntent().getStringExtra("teacherPassword"));

        final TextView tv_Error = binding.tvError;
        accountViewModel.getText().observe(getViewLifecycleOwner(), tv_Error::setText);

        final TextView tv_ChangePassword = binding.tvChangePassword;
        tv_ChangePassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_ChangePassword.setOnClickListener(view -> Navigation
                .findNavController(requireView())
                .navigate(R.id.action_nav_account_to_nav_changePassword)
        );

        final Button btn_Processing = binding.btnProcess;
        btn_Processing.setOnClickListener(view -> {

            String password = binding.etPassword.getText().toString();

            if (password.trim().equals("")) {

                accountViewModel.setText("Please enter your password");
            } else if (!password.trim().equals(requireActivity().getIntent().getStringExtra("teacherPassword"))) {

                accountViewModel.setText("Password you entered is not correct");
            } else {

                Navigation.findNavController(requireView()).navigate(R.id.action_nav_account_to_nav_modify_info);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}