package com.sslc.sslc.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sslc.sslc.R;
import com.sslc.sslc.data.ClassHomework;
import com.sslc.sslc.common_fragment_activities.ui.myClassMain.MyClassDetailViewModel;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherClassHomeworkAdapter extends RecyclerView.Adapter<TeacherClassHomeworkAdapter.TeacherClassHomeworkViewHolder> {

    ArrayList<ClassHomework> classHomeworkList;
    Context context;
    MyClassDetailViewModel mainViewModel;
    Boolean isTeacher;

    public TeacherClassHomeworkAdapter(
            Context context,
            ArrayList<ClassHomework> classHomeworkList,
            MyClassDetailViewModel mainViewModel,
            boolean isTeacher) {

        this.context = context;
        this.classHomeworkList = classHomeworkList;
        this.mainViewModel = mainViewModel;
        this.isTeacher = isTeacher;
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

        holder.cv_Item.setOnClickListener(view -> {

                    if (isTeacher) {
                        Navigation
                                .findNavController(view)
                                .navigate(R.id.action_classHomeWorkListFragment_to_detailClassHomeworkFragment);
                    } else {

                        Navigation
                                .findNavController(view)
                                .navigate(R.id.action_classHomeWorkListFragment_to_detailClassHomeworkFragmentS);
                    }
                    mainViewModel.setClassHomeworkLiveData(classHomeworkList.get(position));
                }
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
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.cv_Item)
        CardView cv_Item;

        public TeacherClassHomeworkViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        void onBind(String title, String script, String deadline) {

            tv_homeworkTitle.setText(title);
            if (script.length() > 30) {
                tv_homeworkScript.setText(script.substring(0, 30).concat("..."));
            } else {
                tv_homeworkScript.setText(script);
            }
            tv_homeworkDeadline.setText("Deadline : ".concat(deadline));
        }
    }
}
