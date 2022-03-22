package com.example.sslc.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sslc.AdminTeacherDetailActivity;
import com.example.sslc.R;
import com.example.sslc.data.Teacher;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherFragmentAdapter extends RecyclerView.Adapter<TeacherFragmentAdapter.TeacherFragmentViewHolder> {

    Context context;
    ArrayList<Teacher> teacherList;

    public TeacherFragmentAdapter(Context context, ArrayList<Teacher> teacherList) {

        this.context = context;
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public TeacherFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_teacher,
                        parent,
                        false
                );

        return new TeacherFragmentAdapter.TeacherFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherFragmentViewHolder holder, int position) {

        holder.onBind(
                context,
                teacherList.get(position).getImage(),
                teacherList.get(position).getName(),
                teacherList.get(position).getDob(),
                teacherList.get(position).getMyClass(),
                teacherList.get(position).getAboutMe()
        );

        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(context, AdminTeacherDetailActivity.class);
            intent.putExtra("teacherName", teacherList.get(holder.getAdapterPosition()).getName());
            intent.putExtra("teacherClass", teacherList.get(holder.getAdapterPosition()).getMyClass());
            intent.putExtra("teacherDOB", teacherList.get(holder.getAdapterPosition()).getDob());
            intent.putExtra("teacherIntroduce", teacherList.get(holder.getAdapterPosition()).getAboutMe());

            if (teacherList.get(holder.getAdapterPosition()).getImage() == null) {

                intent.putExtra("isThereImage", false);
                intent.putExtra("teacherProfileImage", "null");
            } else {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                teacherList.get(holder.getAdapterPosition()).getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("isThereImage", true);
                intent.putExtra("teacherProfileImage", byteArray);
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public static class TeacherFragmentViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.iv_TeacherProfileImage)
        CircleImageView iv_TeacherProfileImage;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_TeacherName)
        TextView tv_TeacherName;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_TeacherDOB)
        TextView tv_TeacherDOB;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_TeacherClass)
        TextView tv_TeacherClass;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_TeacherIntroduce)
        TextView tv_TeacherIntroduce;

        public TeacherFragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void onBind(
                Context context,
                Bitmap teacherProfileImage,
                String teacherName,
                String teacherDOB,
                String teacherClass,
                String teacherIntroduce
        ) {

            Log.i("TeacherFragmentAdapter", "image : " + teacherProfileImage);

            if (teacherProfileImage == null || teacherProfileImage.equals("")) {

                iv_TeacherProfileImage.setImageResource(R.drawable.ic_baseline_person_24);
            } else {

                Glide.with(context)
                        .load(teacherProfileImage)
                        .into(iv_TeacherProfileImage);
            }

            tv_TeacherName.setText(teacherName);
            tv_TeacherDOB.setText(teacherDOB);
            tv_TeacherClass.setText(teacherClass);
            tv_TeacherIntroduce.setText(teacherIntroduce);
        }
    }
}
