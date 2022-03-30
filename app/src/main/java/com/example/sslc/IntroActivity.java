package com.example.sslc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.data.AppData;
import com.example.sslc.requests.GetAllClassRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * Splash Screen
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
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                getAllClass();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        iv_IntroLogo.startAnimation(anim);
    }

    public void getAllClass() {

        ((AppData) getApplication()).getClassList().clear();
        Response.Listener<String> responseListener = response -> {

            try {

                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("allClass");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject teacherItem = jsonArray.getJSONObject(i);
                    boolean success = teacherItem.getBoolean("success");

                    if (success) {

                        ((AppData) getApplication()).getClassList().add(teacherItem.getString("classTitle"));
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
        };

        GetAllClassRequest getAllClassRequest = new GetAllClassRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getAllClassRequest);
    }
}