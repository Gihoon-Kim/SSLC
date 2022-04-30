package com.sslc.sslc.student_side_activities;

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
import com.sslc.sslc.data.Student;
import com.sslc.sslc.databinding.ActivityStudentMainBinding;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityStudentMainBinding binding;

    private StudentMainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStudentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainViewModel = new ViewModelProvider(this, new StudentMainViewModelFactory())
                .get(StudentMainViewModel.class);

        Student student = new Student(
                getIntent().getStringExtra("studentName"),
                getIntent().getStringExtra("studentDOB"),
                getIntent().getStringExtra("studentClass"),
                getIntent().getStringExtra("studentIntroduce"),
                getIntent().getStringExtra("studentID"),
                getIntent().getStringExtra("studentPassword"),
                getIntent().getIntExtra("hasProfileImage", 0) == 1,
                getIntent().getStringExtra("country"),
                false,
                null
        );

        mainViewModel.setStudentInformation(student);

        setSupportActionBar(binding.appBarStudentMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_account, R.id.nav_devInfo)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_student_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        initUI_NavHeaderView();
    }

    private void initUI_NavHeaderView() {

        TextView tv_Logout = binding.navView.getHeaderView(0).findViewById(R.id.tv_LogOut);
        tv_Logout.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        TextView tv_StudentName = binding.navView.getHeaderView(0).findViewById(R.id.tv_StudentName);
        tv_StudentName.setText(Objects.requireNonNull(mainViewModel.getStudentInformation().getValue()).getName());
        CircleImageView iv_studentProfileImage = binding.navView.getHeaderView(0).findViewById(R.id.iv_StudentProfileImage);

        final Observer<Student> studentObserver = student -> {

            if (student.getProfileImage() != null) {

                iv_studentProfileImage.setImageURI(student.getProfileImage());
            } else {

                iv_studentProfileImage.setImageURI(null);
            }
        };

        mainViewModel.getStudentInformation().observe(
                this,
                studentObserver
        );

        if (mainViewModel.getStudentInformation().getValue().hasProfileImage()) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            storageReference.child("profile_img/".concat("profile_student_").concat(mainViewModel.getStudentInformation().getValue().getName()).concat(".jpg"))
                    .getDownloadUrl()
                    .addOnSuccessListener(uri -> {

                        mainViewModel.getStudentInformation().getValue().setProfileImage(uri);

                        Glide.with(StudentMainActivity.this)
                                .load(mainViewModel.getStudentInformation().getValue().getProfileImage())
                                .into(iv_studentProfileImage);
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Download Failed", Toast.LENGTH_SHORT).show());
        } else {

            mainViewModel.getStudentInformation().getValue().setProfileImage(null);

            Glide.with(StudentMainActivity.this)
                    .load(R.drawable.ic_baseline_person_24)
                    .into(iv_studentProfileImage);
        }

        tv_Logout.setOnClickListener(view -> tv_Logout.setTextColor(getResources().getColor(R.color.red)));

        iv_studentProfileImage.setOnClickListener(view -> {

            Intent imageIntent = new Intent(StudentMainActivity.this, ImageViewerActivity.class);
            imageIntent.putExtra("profileImage", mainViewModel.getStudentInformation().getValue().getProfileImage());
            startActivity(imageIntent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_student_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}