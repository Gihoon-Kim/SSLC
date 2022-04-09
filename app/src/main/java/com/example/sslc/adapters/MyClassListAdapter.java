package com.example.sslc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sslc.R;
import com.example.sslc.data.Programs;
import com.example.sslc.teacher_side_activities.TeacherMyClassActivity;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyClassListAdapter extends RecyclerView.Adapter<MyClassListAdapter.MyClassListViewHolder> {

    private Context context;
    private ArrayList<Programs> arrayList;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.foldingCell.toggle(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyClassListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.folding_cell)
        FoldingCell foldingCell;
        @BindView(R.id.tv_Simple_ClassTitle)
        TextView tv_Simple_ClassTitle;
        @BindView(R.id.tv_ClassTime)
        TextView tv_ClassTime;
        @BindView(R.id.tv_ClassDescription)
        TextView tv_ClassDescription;
        @BindView(R.id.tv_Detail_ClassTitle)
        TextView tv_Detail_ClassTitle;
        @BindView(R.id.tv_ClassRoom)
        TextView tv_ClassRoom;

        public MyClassListViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
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
