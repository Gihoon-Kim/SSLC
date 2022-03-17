package com.example.sslc.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sslc.R;
import com.example.sslc.data.NewsData;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFragmentAdapter extends RecyclerView.Adapter<NewsFragmentAdapter.NewsFragmentViewHolder> {

    ArrayList<NewsData> newsDataList;

    public NewsFragmentAdapter(ArrayList<NewsData> newsDataList) {

        this.newsDataList = newsDataList;
    }

    @NonNull
    @Override
    public NewsFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_news,
                parent,
                false
        );

        return new NewsFragmentAdapter.NewsFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFragmentViewHolder holder, int position) {

        holder.onBind(
                newsDataList.get(position).getTitle(),
                newsDataList.get(position).getDescription(),
                newsDataList.get(position).getCreatedAt()
        );
    }

    @Override
    public int getItemCount() {
        return newsDataList.size();
    }

    public static class NewsFragmentViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_NewsDate)
        TextView tv_NewsDate;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_NewsTitle)
        TextView tv_NewsTitle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_NewsSummary)
        TextView tv_NewsSummary;

        public NewsFragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void onBind(String title, String description, Date createdAt) {

            tv_NewsTitle.setText(title);
            tv_NewsSummary.setText(description);
            tv_NewsDate.setText(createdAt.toString());
        }
    }
}
