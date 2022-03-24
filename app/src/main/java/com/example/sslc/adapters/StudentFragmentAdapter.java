package com.example.sslc.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sslc.R;
import com.example.sslc.data.Student;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentFragmentAdapter extends RecyclerView.Adapter<StudentFragmentAdapter.StudentFragmentViewHolder> {

    Context context;
    ArrayList<Student> studentList;

    public StudentFragmentAdapter(Context context, ArrayList<Student> studentList) {

        this.context = context;
        this.studentList = studentList;
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

        holder.onBind(
                studentList.get(position).getName(),
                studentList.get(position).getMyClass(),
                studentList.get(position).getDob(),
                studentList.get(position).getStudentCountry()
        );
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
        @BindView(R.id.cv_Item)
        CardView cv_Item;

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
