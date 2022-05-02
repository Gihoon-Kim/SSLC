package com.sslc.sslc.student_side_activities.ui.account;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.sslc.sslc.R;
import com.sslc.sslc.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);

        final TextView tv_Error = binding.tvError;
        accountViewModel.getText().observe(
                getViewLifecycleOwner(),
                tv_Error::setText
        );

        final TextView tv_ChangePassword = binding.tvChangePassword;
        tv_ChangePassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_ChangePassword.setOnClickListener(view -> {
            Navigation
                    .findNavController(requireView())
                    .navigate(R.id.action_nav_account_to_changePasswordFragment);
        });

        final Button btn_Processing = binding.btnProcess;
        btn_Processing.setOnClickListener(view -> {

            String password = binding.etPassword.getText().toString();

            if (password.trim().equals("")) {

                accountViewModel.setText("Please enter your password");
            } else {

                Navigation.findNavController(requireView())
                        .navigate(R.id.action_nav_account_to_modifyInformationFragment);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}