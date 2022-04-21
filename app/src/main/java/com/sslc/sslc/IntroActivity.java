package com.sslc.sslc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sslc.sslc.data.AppData;
import com.sslc.sslc.requests.GetAllClassRequest;
import com.sslc.sslc.requests.GetAllTeacherRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * Splash Screen
 * While splashed animation is working, processing that getting all class titles and teacher names are being worked.
 */
public class IntroActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_IntroLogo)
    ImageView iv_IntroLogo;

    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ButterKnife.bind(this);

        // Hide ActionBar to set intro activity up to fill all display.
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Set animation
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.side_slide); // Create the animation.
        anim.setAnimationListener(animationListener());

        iv_IntroLogo.startAnimation(anim);
    }

    @NonNull
    private Animation.AnimationListener animationListener() {

        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                // When app started, All class title and all teacher names are listed into
                // classList, teacherList ( static variable )
                getAllClass();
                getAllTeachers();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
    }

    public void getAllClass() {

        ((AppData) getApplication()).getClassList().clear();
        Response.Listener<String> responseListener = this::getAllClassRequest;
        GetAllClassRequest getAllClassRequest = new GetAllClassRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getAllClassRequest);
    }

    private void getAllClassRequest(String response) {
        try {

            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArray = jsonResponse.getJSONArray("allClass");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject classItem = jsonArray.getJSONObject(i);
                boolean success = classItem.getBoolean("success");

                if (success) {

                    ((AppData) getApplication()).getClassList().add(classItem.getString("classTitle"));
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void getAllTeachers() {

        ((AppData) getApplication()).getTeacherList().clear();
        Response.Listener<String> responseListener = this::getAllTeachersRequest;
        GetAllTeacherRequest getAllTeacherRequest = new GetAllTeacherRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getAllTeacherRequest);
    }

    private void getAllTeachersRequest(String response) {

        try {

            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArray = jsonResponse.getJSONArray("allTeacher");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject teacherItem = jsonArray.getJSONObject(i);
                boolean success = teacherItem.getBoolean("success");

                if (success) {

                    ((AppData) getApplication()).getTeacherList().add(teacherItem.getString("teacherName"));
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}