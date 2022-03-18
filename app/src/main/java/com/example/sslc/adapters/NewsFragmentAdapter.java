package com.example.sslc.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sslc.AdminNewsDetailActivity;
import com.example.sslc.R;
import com.example.sslc.data.NewsData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFragmentAdapter extends RecyclerView.Adapter<NewsFragmentAdapter.NewsFragmentViewHolder> {

    ArrayList<NewsData> newsDataList;
    Context context;
    ActivityResultLauncher<Intent> updateNewsActivityResultLauncher;

    public NewsFragmentAdapter(Context context, ArrayList<NewsData> newsDataList, ActivityResultLauncher<Intent> updateNewsActivityResultLauncher) {

        this.newsDataList = newsDataList;
        this.context = context;
        this.updateNewsActivityResultLauncher = updateNewsActivityResultLauncher;
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
        holder.cv_Item.setOnClickListener(view -> {

            Intent intent = new Intent(context, AdminNewsDetailActivity.class);
            intent.putExtra("NewsID", newsDataList.get(position).getNewsID());
            intent.putExtra("NewsTitle", newsDataList.get(position).getTitle());
            intent.putExtra("NewsDescription", newsDataList.get(position).getDescription());
            updateNewsActivityResultLauncher.launch(intent);
        });
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
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.cv_Item)
        CardView cv_Item;

        public NewsFragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        void onBind(String title, String description, String createdAt) {

            tv_NewsTitle.setText(title);
            if (description.length() > 30) {
                tv_NewsSummary.setText(description.substring(0, 30) + "...");
            } else {
                tv_NewsSummary.setText(description);
            }
            tv_NewsDate.setText(createdAt);
        }
    }
}
