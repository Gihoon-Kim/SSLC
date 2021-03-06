package com.sslc.sslc.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sslc.sslc.R;
import com.sslc.sslc.admin_side_activities.AdminStudentDetailActivity;
import com.sslc.sslc.data.Student;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentFragmentAdapter extends RecyclerView.Adapter<StudentFragmentAdapter.StudentFragmentViewHolder> {

    Context context;
    ArrayList<Student> studentList;
    ActivityResultLauncher<Intent> updateStudentActivityResultLauncher;

    public StudentFragmentAdapter(
            Context context,
            ArrayList<Student> studentList,
            ActivityResultLauncher<Intent> updateStudentActivityResultLauncher
    ) {

        this.context = context;
        this.studentList = studentList;
        this.updateStudentActivityResultLauncher = updateStudentActivityResultLauncher;
    }

    @NonNull
    @Override
    public StudentFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_student,
                        parent,
                        false
                );

        return new StudentFragmentAdapter.StudentFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentFragmentViewHolder holder, int position) {

        Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
                .setBaseColor(Color.parseColor("#f3f3f3"))
                .setBaseAlpha(1)
                .setHighlightColor(Color.parseColor("#E7E7E7"))
                .setHighlightAlpha(1)
                .setDropoff(50)
                .build();

        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);

        holder.onBind(
                studentList.get(position).getName(),
                studentList.get(position).getMyClass(),
                studentList.get(position).getDob(),
                studentList.get(position).getStudentCountry()
        );

        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(
                    context,
                    AdminStudentDetailActivity.class
            );
            intent.putExtra("studentNumber", studentList.get(holder.getAdapterPosition()).getStudentNumber());
            intent.putExtra("studentName", studentList.get(holder.getAdapterPosition()).getName());
            intent.putExtra("studentClass", studentList.get(holder.getAdapterPosition()).getMyClass());
            updateStudentActivityResultLauncher.launch(intent);
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class StudentFragmentViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_StudentName)
        TextView tv_StudentName;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_StudentDOB)
        TextView tv_StudentDOB;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_StudentClass)
        TextView tv_StudentClass;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_StudentCountry)
        TextView tv_StudentCountry;
        @SuppressLint("NonConstantResourceId")

        public StudentFragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void onBind(String studentName, String studentClass, String studentDOB, String studentCountry) {

            tv_StudentName.setText(studentName);
            tv_StudentDOB.setText(studentDOB);
            tv_StudentClass.setText(studentClass);
            tv_StudentCountry.setText(studentCountry);
        }
    }
}
