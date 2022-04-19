package com.example.sslc.teacher_side_activities.ui.myClassMain.ClassHomeWork;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.sslc.requests.AddHomeworkRequest;
import com.example.sslc.teacher_side_activities.ui.myClassMain.TeacherMyClassDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddClassHomeworkFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_homeworkTitle)
    EditText et_homeworkTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_homeworkDeadline)
    TextView tv_homeworkDeadLine;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_homeworkScript)
    EditText et_homeworkScript;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fab_AddHomework)
    FloatingActionButton fab_addHomework;

    final Calendar myCalendar = Calendar.getInstance();

    private TeacherMyClassDetailViewModel mainViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_class_homework_add,
                container,
                false
        );
        ButterKnife.bind(this, view);

        mainViewModel = new
                ViewModelProvider(requireActivity()).get(TeacherMyClassDetailViewModel.class);

        return view;
    }

    private void updateLabel() {

        String dateFormat = getString(R.string.date_format);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        tv_homeworkDeadLine.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tv_homeworkDeadline)
    public void onDeadlineClicked() {

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

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.fab_AddHomework)
    public void onAddHomeworkClicked() {

        if (et_homeworkTitle.getText().toString().trim().equals("") ||
                tv_homeworkDeadLine.getText().toString().equals("dd/MM/yyyy") ||
                et_homeworkScript.getText().toString().trim().equals("")) {

            Toast.makeText(requireContext(), getString(R.string.fields_not_filled), Toast.LENGTH_SHORT).show();
        } else {

            ProgressDialog progressDialog = new ProgressDialog(requireContext());
            progressDialog.setTitle(getString(R.string.creating));
            progressDialog.setMessage(getString(R.string.create_in_progress));
            progressDialog.show();

            Response.Listener<String> responseListener = response -> {

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean(getString(R.string.success));

                    if (success) {

                        Toast.makeText(requireContext(), getString(R.string.create_complete), Toast.LENGTH_SHORT).show();
                        Navigation
                                .findNavController(fab_addHomework)
                                .navigate(R.id.action_addClassHomeworkFragment_to_classHomeWorkListFragment);
                    } else {

                        Toast.makeText(requireContext(), getString(R.string.create_failed), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            AddHomeworkRequest addHomeworkRequest = new AddHomeworkRequest(
                    et_homeworkTitle.getText().toString().trim(),
                    et_homeworkScript.getText().toString().trim(),
                    tv_homeworkDeadLine.getText().toString(),
                    mainViewModel.getClassTitle().getValue(),
                    responseListener
            );
            RequestQueue queue = Volley.newRequestQueue(requireContext());
            queue.add(addHomeworkRequest);
        }
    }
}