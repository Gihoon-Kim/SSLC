package com.sslc.sslc;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewerActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_ProfileImage)
    ImageView iv_ProfileImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_image);

        ButterKnife.bind(this);

        try {

            Uri profileImage = getIntent().getParcelableExtra("profileImage");

            Glide.with(this)
                    .load(profileImage)
                    .into(iv_ProfileImage);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
