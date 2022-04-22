package com.sslc.sslc.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sslc.sslc.R;
import com.sslc.sslc.admin_side_activities.AdminTeacherDetailActivity;
import com.sslc.sslc.data.Teacher;
import com.sslc.sslc.requests.DeleteTeacherRequest;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherFragmentAdapter extends RecyclerView.Adapter<TeacherFragmentAdapter.TeacherFragmentViewHolder> {

    Context context;
    ArrayList<Teacher> teacherList;
    ActivityResultLauncher<Intent> updateTeacherActivityResultLauncher;

    public TeacherFragmentAdapter(
            Context context,
            ArrayList<Teacher> teacherList,
            ActivityResultLauncher<Intent> updateTeacherActivityResultLauncher
    ) {

        this.context = context;
        this.teacherList = teacherList;
        this.updateTeacherActivityResultLauncher = updateTeacherActivityResultLauncher;
    }

    @NonNull
    @Override
    public TeacherFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_teacher,
                        parent,
                        false
                );

        return new TeacherFragmentAdapter.TeacherFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherFragmentViewHolder holder, int position) {

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
                teacherList.get(position).getName(),
                teacherList.get(position).getDob(),
                teacherList.get(position).getMyClass(),
                teacherList.get(position).getAboutMe(),
                teacherList.get(position).hasProfileImage()
        );

        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(context, AdminTeacherDetailActivity.class);
            intent.putExtra(context.getString(R.string.teacher_number), teacherList.get(holder.getAdapterPosition()).getTeacherNumber());
            intent.putExtra(context.getString(R.string.teacher_name), teacherList.get(holder.getAdapterPosition()).getName());
            intent.putExtra(context.getString(R.string.teacher_class), teacherList.get(holder.getAdapterPosition()).getMyClass());
            intent.putExtra(context.getString(R.string.teacher_dob), teacherList.get(holder.getAdapterPosition()).getDob());
            intent.putExtra(context.getString(R.string.teacher_introduce), teacherList.get(holder.getAdapterPosition()).getAboutMe());
            intent.putExtra(context.getString(R.string.has_profile_image), teacherList.get(holder.getAdapterPosition()).hasProfileImage());

            updateTeacherActivityResultLauncher.launch(intent);
        });

        holder.itemView.setOnLongClickListener(view -> {
            deleteTeacherFromListAndDatabase(position);
            return true;
        });
    }

    private void deleteTeacherFromListAndDatabase(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you really want to remove ".concat(teacherList.get(position).getName()))
                .setTitle("Delete Teacher : " + teacherList.get(position).getName())
                .setPositiveButton(R.string.delete, (dialogInterface, i) -> {

                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle(context.getString(R.string.deleting));
                    progressDialog.setMessage(context.getString(R.string.delete_in_progress));
                    progressDialog.show();

                    Response.Listener<String> responseListener = response -> deleteTeacherRequest(
                            position,
                            progressDialog,
                            response
                    );
                    DeleteTeacherRequest deleteTeacherRequest = new DeleteTeacherRequest(
                            teacherList.get(position).getTeacherNumber(),
                            responseListener
                    );
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(deleteTeacherRequest);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteTeacherRequest(int position, ProgressDialog progressDialog, String response) {

        try {

            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean(context.getString(R.string.success));

            if (success) {

                teacherList.remove(position);
                notifyDataSetChanged();
            } else {

                Toast.makeText(context, context.getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public static class TeacherFragmentViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.iv_TeacherProfileImage)
        CircleImageView iv_TeacherProfileImage;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_TeacherName)
        TextView tv_TeacherName;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_TeacherDOB)
        TextView tv_TeacherDOB;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_TeacherClass)
        TextView tv_TeacherClass;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_TeacherIntroduce)
        TextView tv_TeacherIntroduce;

        public TeacherFragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void onBind(
                Context context,
                String teacherName,
                String teacherDOB,
                String teacherClass,
                String teacherIntroduce,
                boolean hasProfileImage
        ) {

            if (!hasProfileImage) {

                iv_TeacherProfileImage.setImageResource(R.drawable.ic_baseline_person_24);
            } else {

                File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/profile_img");

                if (!file.isDirectory()) {

                    file.mkdir();
                }

                downloadImg(context, teacherName);
            }

            tv_TeacherName.setText(teacherName);
            tv_TeacherDOB.setText(teacherDOB);
            tv_TeacherClass.setText(teacherClass);
            if (teacherIntroduce.length() > 15) {

                tv_TeacherIntroduce.setText(teacherIntroduce.substring(0, 15).concat(".."));
            } else {

                tv_TeacherIntroduce.setText(teacherIntroduce);
            }
        }

        private void downloadImg(Context context, String teacherName) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            storageReference.child("profile_img/".concat("profile_teacher_").concat(teacherName).concat(".jpg"))
                    .getDownloadUrl()
                    .addOnSuccessListener(uri ->
                            Glide.with(context)
                                    .load(uri)
                                    .into(iv_TeacherProfileImage))
                    .addOnFailureListener(e -> Toast.makeText(context, "Download Image Failed", Toast.LENGTH_SHORT).show());
        }
    }
}
