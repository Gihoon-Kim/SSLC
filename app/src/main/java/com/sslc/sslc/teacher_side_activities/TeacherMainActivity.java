package com.sslc.sslc.teacher_side_activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sslc.sslc.ImageViewerActivity;
import com.sslc.sslc.R;
import com.sslc.sslc.data.Teacher;
import com.sslc.sslc.databinding.ActivityTeacherMainBinding;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class TeacherMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityTeacherMainBinding binding;
    private TeacherMainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTeacherMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarTeacherMain.toolbar);

        // ViewModel
        mainViewModel = new ViewModelProvider(this, new TeacherMainViewModelFactory())
                .get(TeacherMainViewModel.class);

        Teacher teacher = new Teacher(
                getIntent().getStringExtra("teacherName"),
                getIntent().getStringExtra("teacherDOB"),
                getIntent().getStringExtra("teacherClass"),
                getIntent().getStringExtra("teacherIntroduce"),
                getIntent().getStringExtra("teacherID"),
                getIntent().getStringExtra("password"),
                getIntent().getIntExtra("hasProfileImage", 0) == 1,
                true,
                null
        );

        mainViewModel.setTeacherInformation(teacher);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_account, R.id.nav_devInfo)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_teacher_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // HeaderView UI
        setNavHeaderView();
    }

    private void setNavHeaderView() {

        TextView tv_TeacherName = binding.navView.getHeaderView(0).findViewById(R.id.tv_TeacherName);
        tv_TeacherName.setText(Objects.requireNonNull(mainViewModel.getTeacherInformation().getValue()).getName());
        TextView tv_Logout = binding.navView.getHeaderView(0).findViewById(R.id.tv_LogOut);
        tv_Logout.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        CircleImageView iv_TeacherProfileImage = binding.navView.getHeaderView(0).findViewById(R.id.iv_TeacherProfileImage);

        final Observer<Teacher> teacherObserver = teacher -> {
            // Update the UI, in this case, a TextView.
            iv_TeacherProfileImage.setImageURI(teacher.getProfileImage());
        };

        mainViewModel.getTeacherInformation().observe(
                this,
                teacherObserver
        );

        // Set Profile Image
        if (mainViewModel.getTeacherInformation().getValue().hasProfileImage()) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            storageReference.child("profile_img/".concat("profile_teacher_").concat(mainViewModel.getTeacherInformation().getValue().getName()).concat(".jpg"))
                    .getDownloadUrl()
                    .addOnSuccessListener(uri -> {

                        mainViewModel.getTeacherInformation().getValue().setProfileImage(uri);

                        Glide.with(TeacherMainActivity.this)
                                .load(mainViewModel.getTeacherInformation().getValue().getProfileImage())
                                .into(iv_TeacherProfileImage);
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Download Image Failed", Toast.LENGTH_SHORT).show());
        } else {

            mainViewModel.getTeacherInformation().getValue().setProfileImage(null);
        }

        tv_Logout.setOnClickListener(view -> {

            // TODO : after build keep login functionality.
            tv_Logout.setTextColor(getResources().getColor(R.color.red));
        });

        iv_TeacherProfileImage.setOnClickListener(view -> {

            Intent imageIntent = new Intent(TeacherMainActivity.this, ImageViewerActivity.class);
            imageIntent.putExtra("profileImage", mainViewModel.getTeacherInformation().getValue().getProfileImage());
            startActivity(imageIntent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teacher_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_teacher_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}