package com.example.sslc.teacher_side_activities.ui.myClassMain.ClassHomeWork;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.R;
import com.example.sslc.databinding.FragmentClassHomeworkDetailBinding;
import com.example.sslc.requests.UpdateClassHomeworkRequest;
import com.example.sslc.teacher_side_activities.ui.myClassMain.TeacherMyClassDetailViewModel;

import org.json.JSONObject;

import java.util.Objects;

public class DetailClassHomeworkFragment extends Fragment {

    private FragmentClassHomeworkDetailBinding binding;
    private TeacherMyClassDetailViewModel mainViewModel;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentClassHomeworkDetailBinding.inflate(
                inflater,
                container,
                false
        );
        mainViewModel = new
                ViewModelProvider(requireActivity()).get(TeacherMyClassDetailViewModel.class);
        progressDialog = new ProgressDialog(requireContext());

        initUI();

        return binding.getRoot();
    }

    private void initUI() {

        binding.etHomeworkTitle.setText(Objects.requireNonNull(mainViewModel.getClassHomeworkLiveData().getValue()).getTitle());
        binding.etHomeworkScript.setText(mainViewModel.getClassHomeworkLiveData().getValue().getScript());
        binding.tvHomeworkDeadline.setText(mainViewModel.getClassHomeworkLiveData().getValue().getDeadline());

        binding.ivBack.setOnClickListener(this::backToMain);
        binding.fabUpdate.setOnClickListener(this::updateHomework);
    }

    private void backToMain(View view) {

        Navigation.findNavController(view)
                .navigate(R.id.action_detailClassHomeworkFragment_to_classHomeWorkListFragment);
    }

    private void updateHomework(View view) {

        if (binding.etHomeworkScript.getText().toString().trim().equals("") ||
        binding.etHomeworkTitle.getText().toString().trim().equals("")) {

            Toast.makeText(requireContext(), getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
        } else {

            progressDialog.setTitle(getString(R.string.updating));
            progressDialog.setMessage(getString(R.string.update_in_progress));
            progressDialog.show();

            Response.Listener<String> responseListener = response -> {

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean(getString(R.string.success));

                    if (success) {

                        Toast.makeText(requireContext(), getString(R.string.update_complete), Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view)
                                .navigate(R.id.action_detailClassHomeworkFragment_to_classHomeWorkListFragment);
                    } else {

                        Toast.makeText(requireContext(), getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            UpdateClassHomeworkRequest updateClassHomeworkRequest = new UpdateClassHomeworkRequest(
                    Objects.requireNonNull(mainViewModel.getClassHomeworkLiveData().getValue()).getTitle(),
                    binding.etHomeworkTitle.getText().toString().trim(),
                    binding.etHomeworkScript.getText().toString().trim(),
                    binding.tvHomeworkDeadline.getText().toString(),
                    mainViewModel.getClassTitle().getValue(),
                    responseListener
            );
            RequestQueue queue = Volley.newRequestQueue(requireContext());
            queue.add(updateClassHomeworkRequest);
        }
    }
}