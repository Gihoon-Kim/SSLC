package com.example.sslc.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sslc.R;
import com.example.sslc.data.ClassHomework;
import com.example.sslc.teacher_side_activities.ui.myClassMain.TeacherMyClassDetailViewModel;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherClassHomeworkAdapter extends RecyclerView.Adapter<TeacherClassHomeworkAdapter.TeacherClassHomeworkViewHolder> {

    ArrayList<ClassHomework> classHomeworkList;
    Context context;
    TeacherMyClassDetailViewModel mainViewModel;

    public TeacherClassHomeworkAdapter(
            Context context,
            ArrayList<ClassHomework> classHomeworkList,
            TeacherMyClassDetailViewModel mainViewModel
    ) {

        this.context = context;
        this.classHomeworkList = classHomeworkList;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public TeacherClassHomeworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_class_homework,
                        parent,
                        false
                );

        return new TeacherClassHomeworkAdapter.TeacherClassHomeworkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherClassHomeworkViewHolder holder, int position) {

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
                classHomeworkList.get(position).getTitle(),
                classHomeworkList.get(position).getScript(),
                classHomeworkList.get(position).getDeadline()
        );
    }

    @Override
    public int getItemCount() {
        return classHomeworkList.size();
    }

    public static class TeacherClassHomeworkViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_homeworkTitle)
        TextView tv_homeworkTitle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_homeworkScript)
        TextView tv_homeworkScript;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_homeworkDeadline)
        TextView tv_homeworkDeadline;

        public TeacherClassHomeworkViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        void onBind(String title, String script, String deadline) {

            tv_homeworkTitle.setText(title);
            if (script.length() > 30) {
                tv_homeworkScript.setText(script.substring(0, 30) + "...");
            } else {
                tv_homeworkScript.setText(script);
            }
            tv_homeworkDeadline.setText(deadline);
        }
    }
}
