package com.example.sslc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewerActivity extends AppCompatActivity {

    private static final String TAG = ImageViewerActivity.class.getSimpleName();
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_ProfileImage)
    ImageView iv_ProfileImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_image);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        byte[] encodeByte = Base64.decode(intent.getStringExtra("profileImage"), Base64.DEFAULT);
        Bitmap profileBitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

        Glide.with(this)
                .load(profileBitmap)
                .into(iv_ProfileImage);
    }
}
