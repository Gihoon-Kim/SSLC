package com.sslc.sslc.common_fragment_activities.ClassNews;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sslc.sslc.R;
import com.sslc.sslc.requests.AddClassNewsRequest;
import com.sslc.sslc.common_fragment_activities.ui.myClassMain.MyClassDetailViewModel;

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
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_Cancel)
    Button btn_Cancel;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_AddNews)
    Button btn_AddNews;

    MyClassDetailViewModel mainViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainViewModel =
                new ViewModelProvider(requireActivity()).get(MyClassDetailViewModel.class);

        View view = inflater.inflate(
                R.layout.fragment_class_news_add,
                container,
                false
        );
        ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_Cancel)
    public void onBtnCancelClicked() {

        Navigation
                .findNavController(btn_Cancel)
                .navigate(R.id.action_addClassNewsFragment_to_fragment_class_news_list);
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

        Response.Listener<String> responseListener = this::addClassNews;
        AddClassNewsRequest addClassNewsRequest = new AddClassNewsRequest(
                et_ClassNewsTitle.getText().toString().trim(),
                et_ClassNewsDescription.getText().toString().trim(),
                mainViewModel.getClassTitle().getValue(),
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(addClassNewsRequest);
    }

    private void addClassNews(String response) {
        try {

            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean(getString(R.string.success));

            if (success) {

                Toast.makeText(requireContext(), response, Toast.LENGTH_SHORT).show();
                // Get back to ClassNewsFragment.
                Navigation
                        .findNavController(btn_AddNews)
                        .navigate(R.id.action_addClassNewsFragment_to_fragment_class_news_list);
            } else {

                Toast.makeText(requireContext(), response + "Failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}