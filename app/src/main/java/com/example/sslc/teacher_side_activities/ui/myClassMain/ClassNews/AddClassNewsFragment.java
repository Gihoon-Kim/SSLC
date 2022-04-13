package com.example.sslc.teacher_side_activities.ui.myClassMain.ClassNews;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.R;
import com.example.sslc.requests.AddClassNewsRequest;
import com.example.sslc.teacher_side_activities.ui.myClassMain.TeacherMyClassDetailViewModel;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddClassNewsFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_ClassNewsTitle)
    EditText et_ClassNewsTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_ClassNewsDescription)
    EditText et_ClassNewsDescription;

    TeacherMyClassDetailViewModel mainViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainViewModel =
                new ViewModelProvider(requireActivity()).get(TeacherMyClassDetailViewModel.class);

        View view = inflater.inflate(R.layout.fragment_add_class_news, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_Cancel)
    public void onBtnCancelClicked() {

        ClassNewsFragment classNewsFragment = new ClassNewsFragment();
        getParentFragmentManager().beginTransaction().replace(R.id.constraintLayout, classNewsFragment).commit();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_AddNews)
    public void onBtnAddNewsClicked() {

        if (et_ClassNewsDescription.getText().toString().trim().equals("") ||
                et_ClassNewsTitle.getText().toString().trim().equals("")) {

            Toast.makeText(requireContext(), getString(R.string.fields_not_filled), Toast.LENGTH_SHORT).show();
        } else {

            addClassNews();
        }
    }

    private void addClassNews() {

        Response.Listener<String> responseListener = response -> {

            try {

                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean(getString(R.string.success));

                if (success) {

                    Toast.makeText(requireContext(), response, Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(requireContext(), response + "Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        AddClassNewsRequest addClassNewsRequest = new AddClassNewsRequest(
                et_ClassNewsTitle.getText().toString().trim(),
                et_ClassNewsDescription.getText().toString().trim(),
                mainViewModel.getClassTitle().getValue(),
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(addClassNewsRequest);
    }
}