package com.example.sslc.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sslc.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherClassDialogAdapter extends RecyclerView.Adapter<TeacherClassDialogAdapter.TeacherClassDialogViewHolder> {

    ArrayList<String> classTitleList;
    Context context;
    ArrayList<String> addedClassList = new ArrayList<>();

    public TeacherClassDialogAdapter(
            Context context,
            ArrayList<String> classTitleList
    ) {

        this.context = context;
        this.classTitleList = classTitleList;
    }

    @NonNull
    @Override
    public TeacherClassDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.teacher_class_dialog_item,
                        parent,
                        false
                );

        return new TeacherClassDialogAdapter.TeacherClassDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherClassDialogViewHolder holder, int position) {

        holder.onBind(classTitleList.get(position));

        holder.tv_ClassTitle.setOnClickListener(view -> holder.checkBox.setChecked(!holder.checkBox.isChecked()));

        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> checkedChangeListener(holder, b));
    }

    private void checkedChangeListener(@NonNull TeacherClassDialogViewHolder holder, boolean b) {

        if (b) {

            addedClassList.add(classTitleList.get(holder.getAdapterPosition()));
        } else {

            for (int i = 0; i < addedClassList.size(); i++) {

                if (addedClassList.get(i).equals(classTitleList.get(holder.getAdapterPosition()))) {

                    addedClassList.remove(i);
                    break;
                }
            }
        }
    }

    public ArrayList<String> getAddedClassList() {
        return addedClassList;
    }

    @Override
    public int getItemCount() {
        return classTitleList.size();
    }

    public static class TeacherClassDialogViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.checkbox)
        CheckBox checkBox;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_ClassTitle)
        TextView tv_ClassTitle;

        public TeacherClassDialogViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void onBind(String classTitle) {

            tv_ClassTitle.setText(classTitle);
        }
    }
}
