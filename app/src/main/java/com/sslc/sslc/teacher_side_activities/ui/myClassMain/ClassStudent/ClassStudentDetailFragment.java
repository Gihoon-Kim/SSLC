package com.sslc.sslc.teacher_side_activities.ui.myClassMain.ClassStudent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.sslc.sslc.ImageViewerActivity;
import com.sslc.sslc.R;
import com.sslc.sslc.databinding.FragmentClassStudentDetailBinding;
import com.sslc.sslc.teacher_side_activities.ui.myClassMain.TeacherMyClassDetailViewModel;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class ClassStudentDetailFragment extends Fragment {

    private FragmentClassStudentDetailBinding binding;
    private TeacherMyClassDetailViewModel mainViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentClassStudentDetailBinding.inflate(
                inflater,
                container,
                false
        );

        mainViewModel =
                new ViewModelProvider(requireActivity())
                    .get(TeacherMyClassDetailViewModel.class);

        initUI();
        return binding.getRoot();
    }

    private void initUI() {

        binding.tvName.setText(Objects.requireNonNull(mainViewModel.getClassStudentLiveData().getValue()).getName());
        binding.tvCountry.setText(mainViewModel.getClassStudentLiveData().getValue().getStudentCountry());
        binding.tvDOB.setText(mainViewModel.getClassStudentLiveData().getValue().getDob());
        binding.tvIntroduce.setText(mainViewModel.getClassStudentLiveData().getValue().getAboutMe());

        if (mainViewModel.getClassStudentLiveData().getValue().hasProfileImage()) {

            // TODO : GET PROFILE IMAGE FROM FIREBASE
            /*
            Glide.with(requireContext())
                    .load(mainViewModel.getClassStudentLiveData().getValue().getImage())
                    .into(binding.ivDeveloper);
             */
        }

        binding.ivDeveloper.setOnClickListener(view -> seeProfileImage());

        binding.btnBack.setOnClickListener(this::backToMain);
    }

    private void seeProfileImage() {

        if (Objects.requireNonNull(mainViewModel.getClassStudentLiveData().getValue()).hasProfileImage()) {

            // TODO : GET PROFILE IMAGE FROM FIREBASE
            /*
            Intent imageIntent = new Intent(requireContext(), ImageViewerActivity.class);
            imageIntent.putExtra("profileImage", byteArray);
            startActivity(imageIntent);
             */
        } else {

            Toast.makeText(requireContext(), "No Profile Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void backToMain(View view) {

        Navigation
                .findNavController(view)
                .navigate(R.id.action_classStudentDetailFragment_to_classStudentListFragment);
    }
}