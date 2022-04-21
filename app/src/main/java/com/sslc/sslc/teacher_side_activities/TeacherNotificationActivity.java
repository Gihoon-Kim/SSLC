package com.sslc.sslc.teacher_side_activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sslc.sslc.R;
import com.sslc.sslc.adapters.NewsFragmentAdapter;
import com.sslc.sslc.data.NewsData;
import com.sslc.sslc.requests.GetNewsRequest;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherNotificationActivity extends AppCompatActivity {

    private static final String TAG = TeacherNotificationActivity.class.getSimpleName();
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_News)
    RecyclerView rv_News;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmer_Layout)
    ShimmerFrameLayout shimmerFrameLayout;

    NewsFragmentAdapter newsFragmentAdapter;
    ArrayList<NewsData> newsDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_notification);

        ButterKnife.bind(this);

        newsDataList = new ArrayList<>();

        getNews();

        shimmerFrameLayout.startShimmer();

        // Create RecyclerView
        rv_News.setHasFixedSize(true);
        rv_News.setLayoutManager(new LinearLayoutManager(this));
        newsFragmentAdapter = new NewsFragmentAdapter(
                this,
                newsDataList
        );
        rv_News.setAdapter(newsFragmentAdapter);
    }

    private void getNews() {

        // Get News
        newsDataList.clear();

        @SuppressLint("NotifyDataSetChanged")
        Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, "response : " + response);
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray(getString(R.string.news));

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject newsItem = jsonArray.getJSONObject(i);
                    boolean success = newsItem.getBoolean(getString(R.string.success));

                    if (success) {

                        int newsNumber = newsItem.getInt(getString(R.string.news_number));
                        String newsTitle = newsItem.getString(getString(R.string.news_title));
                        String newsDescription = newsItem.getString(getString(R.string.news_description));
                        String newsCreatedAt = newsItem.getString(getString(R.string.news_createdAt));
                        NewsData newsData = new NewsData(
                                newsNumber,
                                newsTitle,
                                newsDescription,
                                newsCreatedAt
                        );
                        newsDataList.add(newsData);
                        newsFragmentAdapter.notifyDataSetChanged();

                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        GetNewsRequest getNewsRequest = new GetNewsRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getNewsRequest);
    }
}