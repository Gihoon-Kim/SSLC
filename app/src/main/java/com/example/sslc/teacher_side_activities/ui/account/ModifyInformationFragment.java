package com.example.sslc.teacher_side_activities.ui.account;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.example.sslc.R;
import com.example.sslc.databinding.FragmentModifyInformationBinding;
import com.example.sslc.requests.UpdateMeRequest;
import com.example.sslc.teacher_side_activities.TeacherMainViewModel;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModifyInformationFragment extends Fragment {

    private ModifyInformationViewModel modifyInformationViewModel;
    private TeacherMainViewModel mainViewModel;
    private FragmentModifyInformationBinding binding;
    private ActivityResultLauncher<Intent> getImageActivityResultLauncher;

    private static final String TAG = ModifyInformationFragment.class.getSimpleName();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        modifyInformationViewModel =
                new ViewModelProvider(this).get(ModifyInformationViewModel.class);
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

        modifyInformationViewModel.setName(requireActivity().getIntent().getStringExtra("teacherName"));
        modifyInformationViewModel.setDob(requireActivity().getIntent().getStringExtra("teacherDOB"));
        modifyInformationViewModel.setIntroduce(requireActivity().getIntent().getStringExtra("teacherIntroduce"));

        final TextView tv_Name = binding.tvName;
        modifyInformationViewModel.getName().observe(
                getViewLifecycleOwner(),
                tv_Name::setText
        );

        final TextView tv_DOB = binding.tvDOB;
        modifyInformationViewModel.getDOB().observe(
                getViewLifecycleOwner(),
                tv_DOB::setText
        );
        tv_DOB.setOnClickListener(view -> onStudentDOBClicked());

        final EditText et_Introduce = binding.etIntroduce;
        modifyInformationViewModel.getIntroduce().observe(
                getViewLifecycleOwner(),
                et_Introduce::setText
        );

        final CircleImageView iv_ProfileImage = binding.ivProfileImage;
        if (!requireActivity().getIntent().getStringExtra("teacherProfileImage").equals("")) {

            byte[] encodeByte = Base64.decode(requireActivity().getIntent().getStringExtra("teacherProfileImage"), Base64.DEFAULT);
            Bitmap profileBitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            modifyInformationViewModel.setImage(profileBitmap);
            modifyInformationViewModel.getImage().observe(
                    getViewLifecycleOwner(),
                    iv_ProfileImage::setImageBitmap
            );
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

        modifyInformationViewModel.setIntroduce(
                binding.etIntroduce.getText().toString()
        );
        modifyInformationViewModel.setDob(
                binding.tvDOB.getText().toString()
        );

        Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, response);
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean(requireContext().getString(R.string.success));

                if (success) {

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

        String profileImage;
        if (modifyInformationViewModel.getImage().getValue() == null) {

            profileImage = "";
        } else {

            BitmapDrawable drawable = (BitmapDrawable) binding.ivProfileImage.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            profileImage = bitmapToString(bitmap);
            modifyInformationViewModel.setImage(bitmap);
            mainViewModel.setImage(bitmap);
        }
        UpdateMeRequest updateMeRequest = new UpdateMeRequest(
                requireActivity().getIntent().getStringExtra("teacherID"),
                modifyInformationViewModel.getDOB().getValue(),
                modifyInformationViewModel.getIntroduce().getValue(),
                profileImage,
                "1",
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(updateMeRequest);
    }

    private String bitmapToString(@NonNull Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, outputStream);
        byte[] byteArrayVar = outputStream.toByteArray();
        return Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
    }

    private void onStudentDOBClicked() {

        Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);

            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.date_format));
            modifyInformationViewModel.setDob(simpleDateFormat.format(myCalendar.getTime()));
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

            Uri selectedImageUri = Objects.requireNonNull(result.getData()).getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().getContentResolver(),
                        selectedImageUri
                );
                modifyInformationViewModel.setImage(bitmap);
                modifyInformationViewModel.getImage().observe(
                        getViewLifecycleOwner(),
                        binding.ivProfileImage::setImageBitmap
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
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