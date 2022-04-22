package com.sslc.sslc.teacher_side_activities.ui.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sslc.sslc.R;
import com.sslc.sslc.databinding.FragmentChangePasswordBinding;
import com.sslc.sslc.requests.UpdatePasswordRequest;
import com.sslc.sslc.teacher_side_activities.TeacherMainViewModel;

import org.json.JSONObject;

public class ChangePasswordFragment extends Fragment {

    private static final String TAG = ChangePasswordFragment.class.getSimpleName();
    private FragmentChangePasswordBinding binding;
    private ChangePasswordViewModel viewModel;

    private TeacherMainViewModel mainViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewModel =
                new ViewModelProvider(this).get(ChangePasswordViewModel.class);
        mainViewModel =
                new ViewModelProvider(requireActivity()).get(TeacherMainViewModel.class);

        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);

        final TextView tv_Error = binding.tvError;
        // Define Observer - Handlers to handle when data changes events occur.
        Observer<String> errorObserver = tv_Error::setText;
        // Attach the observer into ViewModel
        viewModel.getError().observe(getViewLifecycleOwner(), errorObserver);

        final Button btn_Update = binding.btnChangePassword;
        btn_Update.setOnClickListener(view -> {

            if (binding.etCurrentPassword.getText().toString().trim().equals("") ||
                    binding.etNewPassword.getText().toString().trim().equals("")) {

                viewModel.setError("Please fill all fields out");
            } else if (!binding.etCurrentPassword.getText().toString().equals(
                    mainViewModel.getPassword().getValue())) {

                viewModel.setError("Current Password is not right");
            } else if (!binding.etNewPassword.getText().toString().equals(
                    binding.etNewPasswordChecker.getText().toString())) {

                viewModel.setError("New Password and new password checker is not matched");
            } else {

                updatePassword();
            }
        });

        return binding.getRoot();
    }

    private void updatePassword() {

        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle(getString(R.string.updating));
        progressDialog.setMessage(getString(R.string.update_in_progress));
        progressDialog.show();

        Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, response);
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean(getString(R.string.success));

                if (success) {

                    mainViewModel.setPassword(binding.etNewPassword.getText().toString());
                    Navigation.findNavController(requireView()).navigate(R.id.action_nav_changePassword_to_nav_home);
                } else {

                    Toast.makeText(requireContext(), getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(
                mainViewModel.getId().getValue(),
                binding.etNewPassword.getText().toString(),
                "1",
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(updatePasswordRequest);
    }
}