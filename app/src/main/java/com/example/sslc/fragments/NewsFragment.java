package com.example.sslc.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

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

    ActivityResultLauncher<Intent> addNewsActivityResultLauncher;
    ActivityResultLauncher<Intent> updateNewsActivityResultLauncher;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        // Get News from database
        GetNews();

        // Initialize activityResult Launchers
        activityResultLauncherInit();

        // Create RecyclerView
        rv_News.setHasFixedSize(true);
        rv_News.setLayoutManager(new LinearLayoutManager(this.getContext()));
        newsFragmentAdapter = new NewsFragmentAdapter(getContext(), newsDataList, updateNewsActivityResultLauncher);
        rv_News.setAdapter(newsFragmentAdapter);

        return view;
    }

    // To add, to update a news, it requires open new activity.
    // the launchers are to get newly added / updated result of NewsData.
    @SuppressLint("NotifyDataSetChanged")
    private void activityResultLauncherInit() {

        // addActivityResultLauncher Initialize
        addNewsActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

            if (result.getResultCode() == 9001) {

                Intent intent = result.getData();
                String newsDataTitle = Objects.requireNonNull(intent).getStringExtra("newsDataTitle");
                String newsDataDesc = intent.getStringExtra("newsDataDesc");
                NewsData newsData = new NewsData(newsDataTitle, newsDataDesc);
                newsDataList.add(0, newsData);
                newsFragmentAdapter.notifyDataSetChanged();
            }
        });

        // updateActivityResultLauncher Initialize
        updateNewsActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

            if (result.getResultCode() == 9002) {

                Intent intent = result.getData();
                int newsID = Objects.requireNonNull(intent).getIntExtra("newsID", 0);
                String newTitle = Objects.requireNonNull(intent).getStringExtra("newTitle");
                String newDesc = intent.getStringExtra("newDesc");

                Log.i(TAG, "News ID : " + newsID + "\nNew Title : " + newTitle + "\nNew Content" + newDesc);

                // find Index of newsDataList
                for (int i = 0; i < newsDataList.size(); i++) {

                    if (newsDataList.get(i).getNewsID() == newsID) {

                        newsDataList.get(i).setTitle(newTitle);
                        newsDataList.get(i).setDescription(newDesc);

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        newsDataList.get(i).setCreatedAt(dateFormat.format(date));
                        newsFragmentAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
    }

    // This method is to get news from database.
    // When Activity is resumed, it is executed.
    private void GetNews() {
        // Get News
        newsDataList.clear();

        @SuppressLint("NotifyDataSetChanged") Response.Listener<String> responseListener = response -> {

            try {

                Log.i(TAG, "response : " + response);
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("News");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject newsItem = jsonArray.getJSONObject(i);
                    boolean success = newsItem.getBoolean("success");

                    if (success) {

                        int newsID = newsItem.getInt("newsNumber");
                        String newsTitle = newsItem.getString("newsTitle");
                        String newsDescription = newsItem.getString("newsDescription");
                        String newsCreatedAt = newsItem.getString("newsCreatedAt");
                        NewsData newsData = new NewsData(
                                newsID,
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
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(getNewsRequest);
    }

    @SuppressLint({"NonConstantResourceId"})
    @OnClick(R.id.fab_News)
    public void onFabNewsClicked() {

        Intent intent = new Intent(getContext(), AdminAddNewsActivity.class);
        addNewsActivityResultLauncher.launch(intent);
    }
}