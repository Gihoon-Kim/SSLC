package com.sslc.sslc.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.sslc.sslc.R;
import com.sslc.sslc.data.Student;
import com.sslc.sslc.teacher_side_activities.ui.myClassMain.TeacherMyClassDetailViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherClassStudentAdapter extends RecyclerView.Adapter<TeacherClassStudentAdapter.TeacherClassStudentViewHolder> {

    ArrayList<Student> classStudentArrayList;
    Context context;
    TeacherMyClassDetailViewModel mainViewModel;

    public TeacherClassStudentAdapter(
            Context context,
            ArrayList<Student> classStudentList,
            TeacherMyClassDetailViewModel mainViewModel
    ) {

        this.context = context;
        this.classStudentArrayList = classStudentList;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public TeacherClassStudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_class_student,
                        parent,
                        false
                );

        return new TeacherClassStudentAdapter.TeacherClassStudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherClassStudentViewHolder holder, int position) {

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
                context,
                classStudentArrayList.get(position)
        );

        holder.itemView.setOnClickListener(view -> {

            mainViewModel.setClassStudentLiveData(
                    classStudentArrayList.get(holder.getAdapterPosition())
            );
            Navigation
                    .findNavController(view)
                    .navigate(R.id.action_classStudentListFragment_to_classStudentDetailFragment);
        });
    }

    @Override
    public int getItemCount() {
        return classStudentArrayList.size();
    }

    public static class TeacherClassStudentViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.iv_ProfileImage)
        CircleImageView iv_profileImage;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_name)
        TextView tv_name;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_DOB)
        TextView tv_DOB;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_introduce)
        TextView tv_introduce;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_country)
        TextView tv_country;

        public TeacherClassStudentViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void onBind(Context context, Student student) {

            tv_name.setText(student.getName());
            tv_DOB.setText(student.getDob());
            tv_introduce.setText(student.getAboutMe());
            tv_country.setText(student.getStudentCountry());

            if (student.hasProfileImage()) {

                Glide.with(context)
                        .load(student.getProfileImage())
                        .into(iv_profileImage);
            } else {

                Glide.with(context)
                        .load(R.drawable.ic_baseline_person_24)
                        .into(iv_profileImage);
            }
        }
    }
}
