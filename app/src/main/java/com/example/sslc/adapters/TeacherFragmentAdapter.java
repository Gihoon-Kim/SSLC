package com.example.sslc.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sslc.R;
import com.example.sslc.data.Teacher;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherFragmentAdapter extends RecyclerView.Adapter<TeacherFragmentAdapter.TeacherFragmentViewHolder> {

    ArrayList<Teacher> teacherList;

    public TeacherFragmentAdapter(ArrayList<Teacher> teacherList) {

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
                teacherList.get(position).getImage(),
                teacherList.get(position).getName(),
                teacherList.get(position).getDob(),
                teacherList.get(position).getMyClass(),
                teacherList.get(position).getAboutMe()
        );
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
                String teacherProfileImage,
                String teacherName,
                String teacherDOB,
                String teacherClass,
                String teacherIntroduce
        ) {

            if (teacherProfileImage != null) {

                // TODO: set teacher profile image if teacherProfileImage is not null
            } else {

                iv_TeacherProfileImage.setImageResource(R.drawable.ic_baseline_person_24);
            }

            tv_TeacherName.setText(teacherName);
            tv_TeacherDOB.setText(teacherDOB);
            tv_TeacherClass.setText(teacherClass);
            tv_TeacherIntroduce.setText(teacherIntroduce);
        }
    }
}
