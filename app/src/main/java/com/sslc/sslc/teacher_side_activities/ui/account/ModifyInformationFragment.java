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
import androidx.lifecycle.ViewModel;
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
import com.sslc.sslc.data.Student;
import com.sslc.sslc.data.Teacher;
import com.sslc.sslc.databinding.FragmentModifyInformationBinding;
import com.sslc.sslc.requests.UpdateMeRequest;
import com.sslc.sslc.student_side_activities.StudentMainViewModel;
import com.sslc.sslc.teacher_side_activities.TeacherMainViewModel;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModifyInformationFragment extends Fragment {

    private ViewModel mainViewModel;
    private FragmentModifyInformationBinding binding;
    private ActivityResultLauncher<Intent> getImageActivityResultLauncher;

    private static final String TAG = ModifyInformationFragment.class.getSimpleName();

    private Uri selectedImageUri;
    private boolean isTeacher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (requireActivity().toString().contains("StudentMainActivity")) {

            isTeacher = false;
            mainViewModel =
                    new ViewModelProvider(requireActivity()).get(StudentMainViewModel.class);
        } else {

            isTeacher = true;
            mainViewModel =
                    new ViewModelProvider(requireActivity()).get(TeacherMainViewModel.class);
        }

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
        final TextView tv_DOB = binding.tvDOB;
        final EditText et_Introduce = binding.etIntroduce;
        final CircleImageView iv_ProfileImage = binding.ivProfileImage;

        if (isTeacher) {

            tv_Name.setText(Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).getName());
            tv_DOB.setText(Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).getDob());
            tv_DOB.setOnClickListener(this::onDOBClicked);
            et_Introduce.setText(Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).getAboutMe());

            if (Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).hasProfileImage()) {

                selectedImageUri = Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).getProfileImage();
                Glide.with(requireContext())
                        .load(selectedImageUri)
                        .into(iv_ProfileImage);

            } else {

                Glide.with(requireContext())
                        .load(R.drawable.ic_baseline_person_24)
                        .into(iv_ProfileImage);
            }
        } else {

            tv_Name.setText(Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).getName());
            tv_DOB.setText(Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).getDob());
            tv_DOB.setOnClickListener(this::onDOBClicked);
            et_Introduce.setText(Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).getAboutMe());

            if (Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).hasProfileImage()) {

                selectedImageUri = Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).getProfileImage();
                Glide.with(requireContext())
                        .load(selectedImageUri)
                        .into(iv_ProfileImage);

            } else {

                Glide.with(requireContext())
                        .load(R.drawable.ic_baseline_person_24)
                        .into(iv_ProfileImage);
            }
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

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        String fileName;
        if (isTeacher) {

            if (selectedImageUri != null) {

                fileName = "profile_teacher_".concat(Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).getName()).concat(".jpg");
                StorageReference riversRef = storageReference.child("profile_img/" + fileName);
                riversRef.delete()
                        .addOnSuccessListener(unused -> {
                        })
                        .addOnFailureListener(e -> {
                        });

                UploadTask uploadTask = riversRef.putFile(selectedImageUri);

                // Save New Profile Image
                uploadTask.addOnFailureListener(
                        e -> Toast.makeText(requireContext(), "Profile Image Upload Failed", Toast.LENGTH_SHORT).show())
                        .addOnSuccessListener(
                                taskSnapshot -> Toast.makeText(requireContext(), "Profile Image Uploaded", Toast.LENGTH_SHORT).show()
                        );
            }

            Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).setAboutMe(
                    binding.etIntroduce.getText().toString()
            );
            Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).setDob(
                    binding.tvDOB.getText().toString()
            );

            Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).setHasProfileImage(true);
        } else {

            if (selectedImageUri != null) {

                fileName = "profile_student_".concat(Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).getName()).concat(".jpg");
                StorageReference riversRef = storageReference.child("profile_img/" + fileName);
                riversRef.delete()
                        .addOnSuccessListener(unused -> {
                        })
                        .addOnFailureListener(e -> {
                        });

                UploadTask uploadTask = riversRef.putFile(selectedImageUri);

                // Save New Profile Image
                uploadTask.addOnFailureListener(
                        e -> Toast.makeText(requireContext(), "Profile Image Upload Failed", Toast.LENGTH_SHORT).show())
                        .addOnSuccessListener(
                                taskSnapshot -> Toast.makeText(requireContext(), "Profile Image Uploaded", Toast.LENGTH_SHORT).show()
                        );
            }

            Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).setAboutMe(
                    binding.etIntroduce.getText().toString()
            );
            Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).setDob(
                    binding.tvDOB.getText().toString()
            );

            Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).setHasProfileImage(true);
        }

        Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, response);
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean(requireContext().getString(R.string.success));

                if (success) {

                    if (isTeacher) {

                        Teacher teacher = new Teacher(
                                Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).getName(),
                                Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).getDob(),
                                Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).getMyClass(),
                                Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).getAboutMe(),
                                Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).getId(),
                                Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).getPassword(),
                                Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).hasProfileImage(),
                                true,
                                selectedImageUri
                        );
                        ((TeacherMainViewModel) mainViewModel).setTeacherInformation(teacher);

                        Navigation.findNavController(requireView()).navigate(R.id.action_nav_modify_info_to_nav_home);

                    } else {

                        Student student = new Student(
                                Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).getName(),
                                Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).getDob(),
                                Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).getMyClass(),
                                Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).getAboutMe(),
                                Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).getId(),
                                Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).getPassword(),
                                Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).hasProfileImage(),
                                Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).getStudentCountry(),
                                false,
                                selectedImageUri
                        );
                        ((StudentMainViewModel) mainViewModel).setStudentInformation(student);

                        Navigation.findNavController(requireView()).navigate(R.id.action_modifyInformationFragment_to_nav_home);
                    }
                    Toast.makeText(requireContext(), getString(R.string.update_complete), Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(requireContext(), getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            } catch (Exception e) {

                e.printStackTrace();
            }
        };

        UpdateMeRequest updateMeRequest;
        if (isTeacher) {

            updateMeRequest = new UpdateMeRequest(
                    Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).getId(),
                    binding.tvDOB.getText().toString(),
                    binding.etIntroduce.getText().toString(),
                    "1",
                    Objects.requireNonNull(((TeacherMainViewModel) mainViewModel).getTeacherInformation().getValue()).hasProfileImage(),
                    responseListener
            );
        } else {

            updateMeRequest = new UpdateMeRequest(
                    Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).getId(),
                    binding.tvDOB.getText().toString(),
                    binding.etIntroduce.getText().toString(),
                    "0",
                    Objects.requireNonNull(((StudentMainViewModel) mainViewModel).getStudentInformation().getValue()).hasProfileImage(),
                    responseListener
            );
        }

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(updateMeRequest);
    }

    private void onDOBClicked(View view) {

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