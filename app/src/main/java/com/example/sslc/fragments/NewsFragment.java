package com.example.sslc.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sslc.R;
import com.example.sslc.adapters.NewsFragmentAdapter;
import com.example.sslc.data.NewsData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_News)
    RecyclerView rv_News;

    NewsFragmentAdapter newsFragmentAdapter;
    ArrayList<NewsData> newsDataList = new ArrayList<>();

    public NewsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        // Create RecyclerView
        rv_News.setHasFixedSize(true);
        rv_News.setLayoutManager(new LinearLayoutManager(this.getContext()));
        newsFragmentAdapter = new NewsFragmentAdapter(newsDataList);
        rv_News.setAdapter(newsFragmentAdapter);

        return view;
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @OnClick(R.id.fab_News)
    public void onFabNewsClicked() {

        NewsData exampleData = new NewsData("Title", "Desc", "22/03/16");
        newsDataList.add(exampleData);
        newsFragmentAdapter.notifyDataSetChanged();
    }
}