package com.sslc.sslc.common_fragment_activities.ClassNews;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.sslc.sslc.R;
import com.sslc.sslc.adapters.TeacherClassNewsAdapter;
import com.sslc.sslc.data.ClassNews;
import com.sslc.sslc.databinding.FragmentClassNewsListBinding;
import com.sslc.sslc.requests.GetAllClassNewsRequest;
import com.sslc.sslc.common_fragment_activities.ui.myClassMain.MyClassDetailViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class ClassNewsListFragment extends Fragment {

    private static final String TAG = ClassNewsListFragment.class.getSimpleName();
    private TeacherClassNewsAdapter adapter;
    private MyClassDetailViewModel mainViewModel;

    private ArrayList<ClassNews> classNewsList;

    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        classNewsList = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();

        classNewsList.clear();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        FragmentClassNewsListBinding binding = FragmentClassNewsListBinding.inflate(
                inflater,
                container,
                false
        );
        mainViewModel =
                new ViewModelProvider(requireActivity()).get(MyClassDetailViewModel.class);

        adapter = new TeacherClassNewsAdapter(
                requireContext(),
                classNewsList,
                mainViewModel
        );

        RecyclerView rv_ClassNewsList = binding.rvClassNewsList;
        rv_ClassNewsList.setHasFixedSize(true);
        rv_ClassNewsList.setAdapter(adapter);
        rv_ClassNewsList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv_ClassNewsList.addItemDecoration(new DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
        ));

        shimmerFrameLayout = binding.shimmerLayout;
        shimmerFrameLayout.startShimmer();
        // get class news from database and set it to recycler view
        getAllClassNews();

        binding.fab.setOnClickListener(view -> Navigation
                .findNavController(view)
                .navigate(R.id.action_fragment_class_news_list_to_addClassNewsFragment)
        );

        return binding.getRoot();
    }


    private void getAllClassNews() {

        Response.Listener<String> responseListener = this::getAllClassNews;
        GetAllClassNewsRequest getAllClassNewsRequest = new GetAllClassNewsRequest(
                mainViewModel.getClassTitle().getValue(),
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(getAllClassNewsRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getAllClassNews(String response) {

        try {

            Log.i(TAG, response);
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArray = jsonResponse.getJSONArray("ClassNews");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject classItem = jsonArray.getJSONObject(i);
                boolean success = classItem.getBoolean(getString(R.string.success));

                if (success) {

                    ClassNews classNews = new ClassNews(
                            classItem.getString("newsTitle"),
                            classItem.getString("newsDescription"),
                            classItem.getString("newsCreatedAt")
                    );
                    classNewsList.add(classNews);
                    adapter.notifyDataSetChanged();
                } else {

                    Log.i(TAG, response);
                }
            }

            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}