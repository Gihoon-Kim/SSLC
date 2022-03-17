package com.example.sslc.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sslc.AdminAddNewsActivity;
import com.example.sslc.R;
import com.example.sslc.adapters.NewsFragmentAdapter;
import com.example.sslc.data.NewsData;
import com.example.sslc.requests.GetNewsRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_News)
    RecyclerView rv_News;

    NewsFragmentAdapter newsFragmentAdapter;
    ArrayList<NewsData> newsDataList = new ArrayList<>();

    public NewsFragment() { }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        // Create RecyclerView
        rv_News.setHasFixedSize(true);
        rv_News.setLayoutManager(new LinearLayoutManager(this.getContext()));
        newsFragmentAdapter = new NewsFragmentAdapter(getContext(), newsDataList);
        rv_News.setAdapter(newsFragmentAdapter);

        return view;
    }

    // This method is to get news from database.
    // When Activity is resumed, it is executed.
    private void GetNews() {
        // Get News
        newsDataList.clear();

        Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, "response : " + response);
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("News");

                Log.i(TAG, jsonArray.toString());

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject newsItem = jsonArray.getJSONObject(i);
                    boolean success = newsItem.getBoolean("success");

                    if (success) {

                        String newsTitle = newsItem.getString("newsTitle");
                        String newsDescription = newsItem.getString("newsDescription");
                        String newsCreatedAt = newsItem.getString("newsCreatedAt");
                        NewsData newsData = new NewsData(
                                newsTitle,
                                newsDescription,
                                newsCreatedAt
                        );
                        newsDataList.add(newsData);
                        newsFragmentAdapter.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        GetNewsRequest getNewsRequest = new GetNewsRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getNewsRequest);
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @OnClick(R.id.fab_News)
    public void onFabNewsClicked() {

        startActivity(new Intent(getContext(), AdminAddNewsActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();

        GetNews();
    }
}