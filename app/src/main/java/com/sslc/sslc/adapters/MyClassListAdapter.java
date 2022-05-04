package com.sslc.sslc.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sslc.sslc.R;
import com.sslc.sslc.data.Programs;
import com.sslc.sslc.common_fragment_activities.ui.myClassMain.MyClassDetail;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyClassListAdapter extends RecyclerView.Adapter<MyClassListAdapter.MyClassListViewHolder> {

    private final Context context;
    private final ArrayList<Programs> arrayList;

    public MyClassListAdapter(Context context, ArrayList<Programs> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyClassListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_myclass_list,
                        parent,
                        false
                );

        return new MyClassListAdapter.MyClassListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyClassListViewHolder holder, int position) {

        holder.onBind(
                arrayList.get(position).getProgramTitle(),
                arrayList.get(position).getProgramDescription(),
                arrayList.get(position).getProgramStartTime(),
                arrayList.get(position).getProgramEndTime(),
                arrayList.get(position).getProgramClassRoom()
        );

        holder.itemView.setOnClickListener(view -> holder.foldingCell.toggle(false));
        
        holder.tv_ViewDetail.setOnClickListener(view -> {

            Intent intent = new Intent(context, MyClassDetail.class);
            intent.putExtra(context.getString(R.string.class_title), arrayList.get(holder.getAdapterPosition()).getProgramTitle());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyClassListViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.folding_cell)
        FoldingCell foldingCell;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_Simple_ClassTitle)
        TextView tv_Simple_ClassTitle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_ClassTime)
        TextView tv_ClassTime;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_ClassDescription)
        TextView tv_ClassDescription;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_Detail_ClassTitle)
        TextView tv_Detail_ClassTitle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_ClassRoom)
        TextView tv_ClassRoom;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_ViewDetail)
        TextView tv_ViewDetail;

        public MyClassListViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            tv_ViewDetail.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        }

        void onBind(String classTitle, String classDescription, String classStartAt, String classEndAt, String classRoom) {

            tv_Simple_ClassTitle.setText(classTitle);
            tv_Detail_ClassTitle.setText(classTitle);
            tv_ClassDescription.setText(classDescription);
            tv_ClassTime.setText(classStartAt.concat("~").concat(classEndAt));
            tv_ClassRoom.setText(classRoom);
        }
    }
}
