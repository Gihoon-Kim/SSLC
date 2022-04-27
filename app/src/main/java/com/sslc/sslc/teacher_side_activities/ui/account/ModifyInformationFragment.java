package com.sslc.sslc.teacher_side_activities.ui.account;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sslc.sslc.R;
import com.sslc.sslc.data.Teacher;
import com.sslc.sslc.databinding.FragmentModifyInformationBinding;
import com.sslc.sslc.requests.UpdateMeRequest;
import com.sslc.sslc.teacher_side_activities.TeacherMainViewModel;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModifyInformationFragment extends Fragment {

    private TeacherMainViewModel mainViewModel;
    private FragmentModifyInformationBinding binding;
    private ActivityResultLauncher<Intent> getImageActivityResultLauncher;

    private static final String TAG = ModifyInformationFragment.class.getSimpleName();

    private Uri selectedImageUri;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mainViewModel =
                new ViewModelProvider(requireActivity()).get(TeacherMainViewModel.class);

        binding = FragmentModifyInformationBinding.inflate(inflater, container, false);

        getImageActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::initActivityResultLauncher
        );

        setViews();
        return binding.getRoot();
    }

    public void setViews() {

        final TextView tv_Name = binding.tvName;
        tv_Name.setText(Objects.requireNonNull(mainViewModel.getTeacherInformation().getValue()).getName());

        final TextView tv_DOB = binding.tvDOB;
        tv_DOB.setText(mainViewModel.getTeacherInformation().getValue().getDob());

        tv_DOB.setOnClickListener(view -> onStudentDOBClicked());

        final EditText et_Introduce = binding.etIntroduce;
        et_Introduce.setText(mainViewModel.getTeacherInformation().getValue().getAboutMe());

        final CircleImageView iv_ProfileImage = binding.ivProfileImage;
        if (mainViewModel.getTeacherInformation().getValue().hasProfileImage()) {

            selectedImageUri = mainViewModel.getTeacherInformation().getValue().getProfileImage();
            Glide.with(requireContext())
                    .load(mainViewModel.getTeacherInformation().getValue().getProfileImage())
                    .into(iv_ProfileImage);

        } else {

            Glide.with(requireContext())
                    .load(R.drawable.ic_baseline_person_24)
                    .into(iv_ProfileImage);
        }
        final CircleImageView iv_ProfileImageFab = binding.ivProfileImageFab;
        iv_ProfileImage.setOnClickListener(view -> onProfileImageClicked());
        iv_ProfileImageFab.setOnClickListener(view -> onProfileImageClicked());

        final Button btn_Update = binding.btnUpdate;
        btn_Update.setOnClickListener(view -> onBtnUpdateClicked());
    }

    private void onBtnUpdateClicked() {

        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle(requireContext().getString(R.string.updating));
        progressDialog.setMessage(requireContext().getString(R.string.update_in_progress));
        progressDialog.show();

        Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, response);
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean(requireContext().getString(R.string.success));

                if (success) {

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageReference = storage.getReference();

                    String fileName = "profile_teacher_".concat(Objects.requireNonNull(mainViewModel.getTeacherInformation().getValue()).getName()).concat(".jpg");
                    StorageReference riversRef = storageReference.child("profile_img/" + fileName);
                    riversRef.delete()
                            .addOnSuccessListener(unused -> { })
                            .addOnFailureListener(e -> { });

                    UploadTask uploadTask = riversRef.putFile(selectedImageUri);

                    // Save New Profile Image
                    uploadTask.addOnFailureListener(
                            e -> Toast.makeText(requireContext(), "Profile Image Upload Failed", Toast.LENGTH_SHORT).show())
                            .addOnSuccessListener(
                                    taskSnapshot -> Toast.makeText(requireContext(), "Profile Image Uploaded", Toast.LENGTH_SHORT).show()
                            );

                    Objects.requireNonNull(mainViewModel.getTeacherInformation().getValue()).setAboutMe(
                            binding.etIntroduce.getText().toString()
                    );
                    mainViewModel.getTeacherInformation().getValue().setDob(
                            binding.tvDOB.getText().toString()
                    );

                    Teacher teacher = new Teacher(
                            Objects.requireNonNull(mainViewModel.getTeacherInformation().getValue()).getName(),
                            mainViewModel.getTeacherInformation().getValue().getDob(),
                            mainViewModel.getTeacherInformation().getValue().getMyClass(),
                            mainViewModel.getTeacherInformation().getValue().getAboutMe(),
                            mainViewModel.getTeacherInformation().getValue().getId(),
                            mainViewModel.getTeacherInformation().getValue().getPassword(),
                            mainViewModel.getTeacherInformation().getValue().hasProfileImage(),
                            true,
                            selectedImageUri
                    );
                    mainViewModel.setTeacherInformation(teacher);

                    Toast.makeText(requireContext(), getString(R.string.update_complete), Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireView()).navigate(R.id.action_nav_modify_info_to_nav_home);
                } else {

                    Toast.makeText(requireContext(), getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            } catch (Exception e) {

                e.printStackTrace();
            }
        };

        UpdateMeRequest updateMeRequest = new UpdateMeRequest(
                Objects.requireNonNull(mainViewModel.getTeacherInformation().getValue()).getId(),
                binding.tvDOB.getText().toString(),
                binding.etIntroduce.getText().toString(),
                "1",
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(updateMeRequest);
    }

    private void onStudentDOBClicked() {

        Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);

            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.date_format));
            binding.tvDOB.setText(simpleDateFormat.format(myCalendar.getTime()));
        };

        new DatePickerDialog(
                requireContext(),
                dateSetListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void onProfileImageClicked() {

        // Get permission to access gallery
        if (isPermissionGranted()) {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            getImageActivityResultLauncher.launch(intent);
        }
    }

    private void initActivityResultLauncher(@NonNull ActivityResult result) {

        if (result.getResultCode() == Activity.RESULT_CANCELED) {

            Toast.makeText(requireContext(), "Get image cancelled", Toast.LENGTH_SHORT).show();
        } else {

            selectedImageUri = Objects.requireNonNull(result.getData()).getData();

            Glide.with(requireActivity())
                    .load(selectedImageUri)
                    .into(binding.ivProfileImage);
        }
    }

    private boolean isPermissionGranted() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    requireContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                Log.d(TAG, "Authorization setting complete");
                return true;
            } else {

                Log.d(TAG, "Authorization setting Request");
                ActivityCompat.requestPermissions(
                        requireActivity(),
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        },
                        1
                );
                return false;
            }
        }
        return true;
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