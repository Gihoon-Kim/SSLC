package com.example.sslc.adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.AdminClassDetailActivity;
import com.example.sslc.R;
import com.example.sslc.data.Programs;
import com.example.sslc.requests.DeleteClassRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassFragmentAdapter extends RecyclerView.Adapter<ClassFragmentAdapter.ClassFragmentViewHolder> {

    private static final String TAG = "ClassFragmentAdapter";
    ArrayList<Programs> programDataList;
    Context context;
    ActivityResultLauncher<Intent> updateClassResultLauncher;

    public ClassFragmentAdapter(
            Context context,
            ArrayList<Programs> programDataList,
            ActivityResultLauncher<Intent> updateClassResultLauncher
    ) {
        this.programDataList = programDataList;
        this.context = context;
        this.updateClassResultLauncher = updateClassResultLauncher;
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
                programDataList.get(position).getProgramStartTime() + " to " + programDataList.get(position).getProgramEndTime(),
                programDataList.get(position).getProgramClassRoom()
        );

        holder.cv_Item.setOnClickListener(view -> {

            Intent intent = new Intent(context, AdminClassDetailActivity.class);
            intent.putExtra("classNumber", programDataList.get(holder.getAdapterPosition()).getProgramNumber());
            intent.putExtra("classTitle", programDataList.get(holder.getAdapterPosition()).getProgramTitle());
            intent.putExtra("classTeacher", programDataList.get(holder.getAdapterPosition()).getProgramTeacher());
            intent.putExtra("classDescription", programDataList.get(holder.getAdapterPosition()).getProgramDescription());
            intent.putExtra("classStartTime", programDataList.get(holder.getAdapterPosition()).getProgramStartTime());
            intent.putExtra("classEndTime", programDataList.get(holder.getAdapterPosition()).getProgramEndTime());
            intent.putExtra("classRoom", programDataList.get(holder.getAdapterPosition()).getProgramClassRoom());

            updateClassResultLauncher.launch(intent);
        });

        holder.cv_Item.setOnLongClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you really want to remove this News?")
                    .setTitle("Delete News : " + programDataList.get(holder.getAdapterPosition()).getProgramTitle())
                    .setPositiveButton("Delete", (dialogInterface, i) ->
                            deleteClassFromListAndDatabase(holder.getAdapterPosition(), programDataList.get(holder.getAdapterPosition()).getProgramNumber()))
                    .setNegativeButton("Cancel", null)
                    .show();
            return false;
        });
    }

    private void deleteClassFromListAndDatabase(int position, int classNumber) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Delete Class");
        progressDialog.setMessage("Please Wait.\nDeleting in progress");
        progressDialog.show();

        @SuppressLint("NotifyDataSetChanged") Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, response);
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");

                if (success) {

                    programDataList.remove(position);
                    notifyDataSetChanged();
                } else {

                    Toast.makeText(context, "Delete Class Failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            } catch (Exception e) {

                e.printStackTrace();
            }
        };

        DeleteClassRequest deleteClassRequest = new DeleteClassRequest(
                classNumber,
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(deleteClassRequest);
    }

    @Override
    public int getItemCount() {
        return programDataList.size();
    }

    public static class ClassFragmentViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.cv_Item)
        CardView cv_Item;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_ProgramTitle)
        TextView tv_ProgramTitle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_ProgramTeacher)
        TextView tv_ProgramTeacher;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_ProgramDescription)
        TextView tv_ProgramDescription;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_ProgramTime)
        TextView tv_ProgramTime;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_ProgramClassRoom)
        TextView tv_ProgramClassRoom;

        public ClassFragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        void onBind(String programTitle, String programTeacher, String programDescription, String programTime, String classRoom) {

            tv_ProgramTitle.setText(programTitle);
            tv_ProgramTeacher.setText(programTeacher);
            if (programDescription.length() > 30) {
                tv_ProgramDescription.setText(programDescription.substring(0, 30) + "...");
            } else {
                tv_ProgramDescription.setText(programDescription);
            }
            tv_ProgramTime.setText(programTime);
            tv_ProgramClassRoom.setText(classRoom);
        }
    }
}
