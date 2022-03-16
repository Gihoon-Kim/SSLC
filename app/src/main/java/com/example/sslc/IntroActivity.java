package com.example.sslc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        iv_IntroLogo.startAnimation(anim);
    }
}