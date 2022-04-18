package com.example.sslc.adapters;

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

import com.example.sslc.R;
import com.example.sslc.data.ClassNews;
import com.example.sslc.teacher_side_activities.ui.myClassMain.TeacherMyClassDetailViewModel;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherClassNewsAdapter extends RecyclerView.Adapter<TeacherClassNewsAdapter.TeacherClassNewsViewHolder> {

    ArrayList<ClassNews> classNewsArrayList;
    Context context;
    TeacherMyClassDetailViewModel mainViewModel;

    public TeacherClassNewsAdapter(
            Context context,
            ArrayList<ClassNews> classNewsArrayList,
            TeacherMyClassDetailViewModel mainViewModel
    ) {

        this.context = context;
        this.classNewsArrayList = classNewsArrayList;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public TeacherClassNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_news,
                        parent,
                        false
                );

        return new TeacherClassNewsAdapter.TeacherClassNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherClassNewsViewHolder holder, int position) {

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
                classNewsArrayList.get(position).getNewsTitle(),
                classNewsArrayList.get(position).getDescription(),
                classNewsArrayList.get(position).getCreatedAt()
        );

        holder.cv_Item.setOnClickListener(view -> {

            mainViewModel.setClassNewsLiveData(
                    classNewsArrayList.get(position)
            );
            Navigation
                    .findNavController(view)
                    .navigate(R.id.action_fragment_class_news_list_to_detailClassNewsFragment);
        });
    }

    @Override
    public int getItemCount() {
        return classNewsArrayList.size();
    }

    public static class TeacherClassNewsViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_NewsDate)
        TextView tv_NewsDate;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_NewsTitle)
        TextView tv_NewsTitle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_NewsSummary)
        TextView tv_NewsSummary;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.cv_Item)
        CardView cv_Item;

        public TeacherClassNewsViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        void onBind(String title, String description, String createdAt) {

            tv_NewsTitle.setText(title);
            if (description.length() > 30) {
                tv_NewsSummary.setText(description.substring(0, 30) + "...");
            } else {
                tv_NewsSummary.setText(description);
            }
            tv_NewsDate.setText(createdAt);
        }
    }
}
