package com.example.sslc.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeacherClassNewsAdapter extends RecyclerView.Adapter<TeacherClassNewsAdapter.TeacherClassNewsViewHolder> {

    @NonNull
    @Override
    public TeacherClassNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherClassNewsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class TeacherClassNewsViewHolder extends RecyclerView.ViewHolder {

        public TeacherClassNewsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
