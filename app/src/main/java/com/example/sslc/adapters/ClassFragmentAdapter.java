package com.example.sslc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sslc.R;
import com.example.sslc.data.Programs;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassFragmentAdapter extends RecyclerView.Adapter<ClassFragmentAdapter.ClassFragmentViewHolder> {

    ArrayList<Programs> programDataList;
    Context context;

    public ClassFragmentAdapter(Context context, ArrayList<Programs> programDataList) {
        this.programDataList = programDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClassFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_class,
                        parent,
                        false
                );

        return new ClassFragmentAdapter.ClassFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassFragmentViewHolder holder, int position) {

        holder.onBind(
                programDataList.get(position).getProgramTitle(),
                programDataList.get(position).getProgramTeacher(),
                programDataList.get(position).getProgramDescription(),
                programDataList.get(position).getProgramStartTime() + " to " + programDataList.get(position).getProgramEndTime()
        );
    }

    @Override
    public int getItemCount() {
        return programDataList.size();
    }

    public static class ClassFragmentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_Item)
        CardView cv_Item;
        @BindView(R.id.tv_ProgramTitle)
        TextView tv_ProgramTitle;
        @BindView(R.id.tv_ProgramTeacher)
        TextView tv_ProgramTeacher;
        @BindView(R.id.tv_ProgramDescription)
        TextView tv_ProgramDescription;
        @BindView(R.id.tv_ProgramTime)
        TextView tv_ProgramTime;

        public ClassFragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void onBind(String programTitle, String programTeacher, String programDescription, String programTime) {

            tv_ProgramTitle.setText(programTitle);
            tv_ProgramTeacher.setText(programTeacher);
            if (programDescription.length() > 30) {
                tv_ProgramDescription.setText(programDescription.substring(0, 30) + "...");
            } else {
                tv_ProgramDescription.setText(programDescription);
            }
            tv_ProgramTime.setText(programTime);

        }
    }
}
