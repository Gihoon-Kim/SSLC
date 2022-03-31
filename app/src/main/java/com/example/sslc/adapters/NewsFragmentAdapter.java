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
import com.example.sslc.AdminNewsDetailActivity;
import com.example.sslc.R;
import com.example.sslc.data.NewsData;
import com.example.sslc.requests.DeleteNewsRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFragmentAdapter extends RecyclerView.Adapter<NewsFragmentAdapter.NewsFragmentViewHolder> {

    private static final String TAG = "NewsFragmentAdapter";

    ArrayList<NewsData> newsDataList;
    Context context;
    ActivityResultLauncher<Intent> updateNewsActivityResultLauncher;
    ProgressDialog progressDialog;

    public NewsFragmentAdapter(Context context, ArrayList<NewsData> newsDataList, ActivityResultLauncher<Intent> updateNewsActivityResultLauncher) {

        this.newsDataList = newsDataList;
        this.context = context;
        this.updateNewsActivityResultLauncher = updateNewsActivityResultLauncher;
    }

    @NonNull
    @Override
    public NewsFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                R.layout.item_news,
                parent,
                false
        );

        return new NewsFragmentAdapter.NewsFragmentViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull NewsFragmentViewHolder holder, int position) {

        holder.onBind(
                newsDataList.get(position).getTitle(),
                newsDataList.get(position).getDescription(),
                newsDataList.get(position).getCreatedAt()
        );

        holder.cv_Item.setOnClickListener(view -> {

            Intent intent = new Intent(context, AdminNewsDetailActivity.class);
            intent.putExtra("newsNumber", newsDataList.get(position).getNewsID());
            intent.putExtra("newsTitle", newsDataList.get(position).getTitle());
            intent.putExtra("newsDescription", newsDataList.get(position).getDescription());
            updateNewsActivityResultLauncher.launch(intent);
        });

        holder.cv_Item.setOnLongClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you really want to remove this News?")
                    .setTitle("Delete News : " + newsDataList.get(position).getTitle())
                    .setPositiveButton("Delete", (dialogInterface, i) ->
                            deleteNewsFromListAndDatabase(position, newsDataList.get(position).getNewsID()))
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });
    }

    private void deleteNewsFromListAndDatabase(int position, int newsID) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Delete News");
        progressDialog.setMessage("Please Wait.\nDeleting in progress");
        progressDialog.show();

        @SuppressLint("NotifyDataSetChanged")
        Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, response);
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                progressDialog.dismiss();

                if (success) {

                    newsDataList.remove(position);
                    notifyDataSetChanged();
                } else {

                    Toast.makeText(context, "Delete News Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        DeleteNewsRequest deleteNewsRequest = new DeleteNewsRequest(newsID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(deleteNewsRequest);
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
