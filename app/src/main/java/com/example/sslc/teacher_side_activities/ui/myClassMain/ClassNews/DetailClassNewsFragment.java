package com.example.sslc.teacher_side_activities.ui.myClassMain.ClassNews;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.R;
import com.example.sslc.databinding.FragmentClassNewsDetailBinding;
import com.example.sslc.requests.DeleteClassNewsRequest;
import com.example.sslc.requests.UpdateClassNewsRequest;
import com.example.sslc.teacher_side_activities.ui.myClassMain.TeacherMyClassDetailViewModel;

import org.json.JSONObject;

import java.util.Objects;

public class DetailClassNewsFragment extends Fragment {

    private static final String TAG = DetailClassNewsFragment.class.getSimpleName();
    private FragmentClassNewsDetailBinding binding;
    private TeacherMyClassDetailViewModel mainViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentClassNewsDetailBinding.inflate(
                inflater,
                container,
                false
        );

        mainViewModel =
                new ViewModelProvider(requireActivity()).get(TeacherMyClassDetailViewModel.class);

        initUI();

        return binding.getRoot();
    }

    private void initUI() {

        binding.etNewsTitle.setText(
                Objects.requireNonNull(mainViewModel.getClassNewsLiveData().getValue()).getNewsTitle()
        );
        binding.etNewsDescription.setText(
                mainViewModel.getClassNewsLiveData().getValue().getDescription()
        );
        binding.ivBack.setOnClickListener(view -> {
                    Log.i(TAG, "Back Button Clicked");
                    Navigation
                            .findNavController(view)
                            .navigate(R.id.action_detailClassNewsFragment_to_fragment_class_news_list);
                }
        );

        binding.fabUpdate.setOnClickListener(this::updateClassNews);

        binding.ivDelete.setOnClickListener(this::deleteClassNews);
    }

    private void updateClassNews(View view) {

        Response.Listener<String> responseListener = response -> {

            ProgressDialog progressDialog = new ProgressDialog(requireContext());
            progressDialog.setTitle(getString(R.string.updating));
            progressDialog.setMessage(getString(R.string.update_in_progress));
            progressDialog.show();

            try {

                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean(getString(R.string.success));

                if (success) {

                    Toast.makeText(getContext(), getString(R.string.update_complete), Toast.LENGTH_SHORT).show();
                    Navigation
                            .findNavController(view)
                            .navigate(R.id.action_detailClassNewsFragment_to_fragment_class_news_list);
                } else {

                    Toast.makeText(getContext(), getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Log.i(TAG, Objects.requireNonNull(mainViewModel.getClassNewsLiveData().getValue()).getNewsTitle());
        UpdateClassNewsRequest updateClassNewsRequest = new UpdateClassNewsRequest(
                mainViewModel.getClassNewsLiveData().getValue().getNewsTitle(),
                binding.etNewsTitle.getText().toString(),
                binding.etNewsDescription.getText().toString(),
                mainViewModel.getClassTitle().getValue(),
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(updateClassNewsRequest);
    }

    private void deleteClassNews(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(getString(R.string.delete))
                .setMessage("Do you really want to delete News " + Objects.requireNonNull(mainViewModel.getClassNewsLiveData().getValue()).getClassTitle() + "?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {

                    ProgressDialog progressDialog = new ProgressDialog(requireContext());
                    progressDialog.setTitle(getString(R.string.delete));
                    progressDialog.setMessage(getString(R.string.delete_in_progress));
                    progressDialog.show();

                    Response.Listener<String> responseListener = response -> {

                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean(getString(R.string.success));

                            if (success) {

                                Toast.makeText(requireContext(), getString(R.string.delete_complete), Toast.LENGTH_SHORT).show();
                                Navigation
                                        .findNavController(view)
                                        .navigate(R.id.action_detailClassNewsFragment_to_fragment_class_news_list);
                            } else {

                                Toast.makeText(requireContext(), getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    };

                    DeleteClassNewsRequest deleteClassNewsRequest = new DeleteClassNewsRequest(
                            Objects.requireNonNull(mainViewModel.getClassNewsLiveData().getValue()).getNewsTitle(),
                            mainViewModel.getClassTitle().getValue(),
                            responseListener
                    );
                    RequestQueue queue = Volley.newRequestQueue(requireContext());
                    queue.add(deleteClassNewsRequest);
                })
                .setNegativeButton("No", null)
                .show();
    }
}