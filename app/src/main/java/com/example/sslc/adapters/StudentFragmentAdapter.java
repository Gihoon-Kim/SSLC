package com.example.sslc.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentFragmentAdapter extends RecyclerView.Adapter<StudentFragmentAdapter.StudentFragmentViewHolder> {

    @NonNull
    @Override
    public StudentFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentFragmentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class StudentFragmentViewHolder extends RecyclerView.ViewHolder {

        public StudentFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
