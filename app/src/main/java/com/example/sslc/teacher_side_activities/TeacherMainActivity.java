package com.example.sslc.teacher_side_activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.sslc.ImageViewerActivity;
import com.example.sslc.R;
import com.example.sslc.databinding.ActivityTeacherMainBinding;
import com.google.android.material.navigation.NavigationView;


public class TeacherMainActivity extends AppCompatActivity {

    private static final String TAG = TeacherMainActivity.class.getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityTeacherMainBinding binding;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTeacherMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarTeacherMain.toolbar);
        intent = getIntent();

        Log.i(TAG, "Teacher Name " + intent.getStringExtra("teacherName") +
                "\nTeacher DOB " + intent.getStringExtra("teacherDOB") +
                "\nTeacher Class " + intent.getStringExtra("teacherClass") +
                "\nTeacher Introduce " + intent.getStringExtra("teacherIntroduce") +
                "\nTeacher Password " + intent.getStringExtra("teacherPassword") +
                "\nTeacher Image " + intent.getStringExtra("teacherProfileImage"));

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
        tv_TeacherName.setText(intent.getStringExtra("teacherName"));
        TextView tv_Logout = binding.navView.getHeaderView(0).findViewById(R.id.tv_LogOut);
        tv_Logout.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        tv_Logout.setOnClickListener(view -> {

            // TODO : after build keep login functionality.
            tv_Logout.setTextColor(getResources().getColor(R.color.red));
        });

        if (!intent.getStringExtra("teacherProfileImage").equals("")) {

            // Teacher profile image decode and set profile image up
            byte[] encodeByte = Base64.decode(intent.getStringExtra("teacherProfileImage"), Base64.DEFAULT);
            Bitmap profileBitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            ImageView iv_TeacherProfileImage = binding.navView.getHeaderView(0).findViewById(R.id.iv_TeacherProfileImage);

            Glide.with(this)
                    .load(profileBitmap)
                    .into(iv_TeacherProfileImage);

            iv_TeacherProfileImage.setOnClickListener(view -> {

                Intent imageIntent = new Intent(TeacherMainActivity.this, ImageViewerActivity.class);
                imageIntent.putExtra("profileImage", intent.getStringExtra("teacherProfileImage"));
                startActivity(imageIntent);
            });
        }
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