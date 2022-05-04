package com.sslc.sslc.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.sslc.sslc.R;
import com.sslc.sslc.adapters.NewsFragmentAdapter;
import com.sslc.sslc.admin_side_activities.AdminAddNewsActivity;
import com.sslc.sslc.data.NewsData;
import com.sslc.sslc.requests.GetNewsRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * This Fragment is for showing all the news (College side news)
 */
public class NewsFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_News)
    RecyclerView rv_News;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmer_Layout)
    ShimmerFrameLayout shimmerFrameLayout;

    NewsFragmentAdapter newsFragmentAdapter;
    ArrayList<NewsData> newsDataList = new ArrayList<>();

    ActivityResultLauncher<Intent> addNewsActivityResultLauncher;
    ActivityResultLauncher<Intent> updateNewsActivityResultLauncher;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_news,
                container,
                false
        );
        ButterKnife.bind(this, view);

        // Get News from database
        getNews();

        shimmerFrameLayout.startShimmer();

        // Initialize activityResult Launchers
        activityResultLauncherInit();

        // Create RecyclerView
        rv_News.setHasFixedSize(true);
        rv_News.setLayoutManager(new LinearLayoutManager(this.requireContext()));
        newsFragmentAdapter = new NewsFragmentAdapter(
                requireContext(),
                newsDataList,
                updateNewsActivityResultLauncher
        );
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
                        String newsDataTitle = Objects.requireNonNull(intent).getStringExtra(getString(R.string.news_title));
                        String newsDataDesc = intent.getStringExtra(getString(R.string.news_description));
                        NewsData newsData = new NewsData(
                                newsDataTitle,
                                newsDataDesc
                        );
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
                        int newsNumber = Objects.requireNonNull(intent).getIntExtra(getString(R.string.news_number), 0);
                        String newTitle = Objects.requireNonNull(intent).getStringExtra(getString(R.string.news_title));
                        String newDesc = intent.getStringExtra(getString(R.string.news_description));

                        // find Index of newsDataList
                        for (int i = 0; i < newsDataList.size(); i++) {

                            if (newsDataList.get(i).getNewsID() == newsNumber) {

                                newsDataList.get(i).setTitle(newTitle);
                                newsDataList.get(i).setDescription(newDesc);

                                @SuppressLint("SimpleDateFormat")
                                SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format));
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
    private void getNews() {

        // Get News
        newsDataList.clear();

        @SuppressLint("NotifyDataSetChanged")
        Response.Listener<String> responseListener = this::getNewsRequest;
        GetNewsRequest getNewsRequest = new GetNewsRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(getNewsRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getNewsRequest(String response) {

        try {

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
    }

    @SuppressLint({"NonConstantResourceId"})
    @OnClick(R.id.fab_News)
    public void onFabNewsClicked() {

        Intent intent = new Intent(
                requireContext(),
                AdminAddNewsActivity.class
        );
        addNewsActivityResultLauncher.launch(intent);
    }
}