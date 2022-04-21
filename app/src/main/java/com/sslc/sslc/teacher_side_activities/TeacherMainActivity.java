package com.sslc.sslc.teacher_side_activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.sslc.sslc.ImageViewerActivity;
import com.sslc.sslc.R;
import com.sslc.sslc.databinding.ActivityTeacherMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class TeacherMainActivity extends AppCompatActivity {

    private static final String TAG = TeacherMainActivity.class.getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityTeacherMainBinding binding;
    private Intent intent;

    private TeacherMainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTeacherMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarTeacherMain.toolbar);
        intent = getIntent();

        Log.i(TAG, "Teacher Name " + intent.getStringExtra("teacherName") +
                "\nTeacher DOB " + intent.getStringExtra("teacherDOB") +
                "\nTeacher ID " + intent.getStringExtra("teacherID") +
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
        CircleImageView iv_TeacherProfileImage = binding.navView.getHeaderView(0).findViewById(R.id.iv_TeacherProfileImage);

        // ViewModel
        mainViewModel = new ViewModelProvider(this, new TeacherMainViewModelFactory())
                .get(TeacherMainViewModel.class);

        // Initialize ViewModel
        mainViewModel.setId(getIntent().getStringExtra("teacherID"));
        mainViewModel.setDob(getIntent().getStringExtra("teacherDOB"));
        mainViewModel.setIntroduce(getIntent().getStringExtra("teacherIntroduce"));
        mainViewModel.setPassword(getIntent().getStringExtra("teacherPassword"));

        // Define Observer - Handlers to handle when data changes events occur.
        Observer<Bitmap> imageObserver = iv_TeacherProfileImage::setImageBitmap;

        // Attach the observer into ViewModel
        mainViewModel.getImage().observe(this, imageObserver);

        // Set Profile Image
        if (intent.getIntExtra("hasProfileImage", 0) == 1) {

            // Teacher profile image decode and set profile image up
            byte[] encodeByte = Base64.decode(intent.getStringExtra("teacherProfileImage"), Base64.DEFAULT);
            Log.i(TAG, Arrays.toString(encodeByte));
            Bitmap profileBitmap = BitmapFactory.decodeByteArray(encodeByte, 40, encodeByte.length);

            mainViewModel.setImage(profileBitmap);
        } else {

            Bitmap bitmap = getBitmapFromVectorDrawable(R.drawable.ic_baseline_person_24);

            mainViewModel.setImage(bitmap);
        }

        tv_Logout.setOnClickListener(view -> {

            // TODO : after build keep login functionality.
            tv_Logout.setTextColor(getResources().getColor(R.color.red));
        });

        iv_TeacherProfileImage.setOnClickListener(view -> {

            //Convert to byte array
            Bitmap bmp = mainViewModel.getImage().getValue();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Objects.requireNonNull(bmp).compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Intent imageIntent = new Intent(TeacherMainActivity.this, ImageViewerActivity.class);
            imageIntent.putExtra("profileImage", byteArray);
            startActivity(imageIntent);
        });
    }

    private Bitmap getBitmapFromVectorDrawable(int resId) {

        Drawable drawable = ContextCompat.getDrawable(
                this,
                resId
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            drawable = (DrawableCompat.wrap(Objects.requireNonNull(drawable))).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(
                Objects.requireNonNull(drawable).getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(
                0,
                0,
                canvas.getWidth(),
                canvas.getHeight()
        );
        drawable.draw(canvas);
        return bitmap;
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