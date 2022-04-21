package com.sslc.sslc.teacher_side_activities.ui.myClassMain.ClassHomeWork;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sslc.sslc.R;
import com.sslc.sslc.databinding.FragmentClassHomeworkDetailBinding;
import com.sslc.sslc.requests.DeleteClassHomeworkRequest;
import com.sslc.sslc.requests.UpdateClassHomeworkRequest;
import com.sslc.sslc.teacher_side_activities.ui.myClassMain.TeacherMyClassDetailViewModel;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class DetailClassHomeworkFragment extends Fragment {

    private FragmentClassHomeworkDetailBinding binding;
    private TeacherMyClassDetailViewModel mainViewModel;

    private ProgressDialog progressDialog;

    final Calendar myCalendar = Calendar.getInstance();

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

        binding.tvHomeworkDeadline.setOnClickListener(view -> changeDeadline());

        binding.ivBack.setOnClickListener(this::backToMain);
        binding.fabUpdate.setOnClickListener(this::updateHomework);
        binding.ivDelete.setOnClickListener(this::deleteHomework);
    }

    private void deleteHomework(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(getString(R.string.delete))
                .setMessage("Do you really want to delete Homework " + Objects.requireNonNull(mainViewModel.getClassHomeworkLiveData().getValue()).getTitle() + "?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {

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
                                        .navigate(R.id.action_detailClassHomeworkFragment_to_classHomeWorkListFragment);
                            } else {

                                Toast.makeText(requireContext(), getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    };

                    DeleteClassHomeworkRequest deleteClassHomeworkRequest = new DeleteClassHomeworkRequest(
                            Objects.requireNonNull(mainViewModel.getClassHomeworkLiveData().getValue()).getTitle(),
                            mainViewModel.getClassTitle().getValue(),
                            responseListener
                    );
                    RequestQueue queue = Volley.newRequestQueue(requireContext());
                    queue.add(deleteClassHomeworkRequest);
                })
                .setNegativeButton("No", null)
                .show();
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

    private void changeDeadline() {

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        };

        new DatePickerDialog(
                requireContext(),
                dateSetListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void updateLabel() {

        String dateFormat = getString(R.string.date_format);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        binding.tvHomeworkDeadline.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}