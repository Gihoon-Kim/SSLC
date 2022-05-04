package com.sslc.sslc.common_fragment_activities.ClassHomeWork;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.sslc.sslc.R;
import com.sslc.sslc.adapters.TeacherClassHomeworkAdapter;
import com.sslc.sslc.data.ClassHomework;
import com.sslc.sslc.databinding.FragmentClassHomeworkListBinding;
import com.sslc.sslc.requests.GetClassHomeworkRequest;
import com.sslc.sslc.common_fragment_activities.ui.myClassMain.MyClassDetailViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassHomeWorkListFragment extends Fragment {

    private static final String TAG = ClassHomeWorkListFragment.class.getSimpleName();

    private MyClassDetailViewModel mainViewModel;

    private TeacherClassHomeworkAdapter adapter;
    private ArrayList<ClassHomework> classHomeworkList;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        classHomeworkList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentClassHomeworkListBinding binding = FragmentClassHomeworkListBinding.inflate(
                inflater,
                container,
                false
        );

        mainViewModel =
                new ViewModelProvider(requireActivity()).get(MyClassDetailViewModel.class);

        adapter = new TeacherClassHomeworkAdapter(
                requireContext(),
                classHomeworkList,
                mainViewModel
        );

        RecyclerView rv_ClassHomeworkList = binding.rvClassHomeworkList;
        rv_ClassHomeworkList.setHasFixedSize(true);
        rv_ClassHomeworkList.setAdapter(adapter);
        rv_ClassHomeworkList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv_ClassHomeworkList.addItemDecoration(new DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
        ));

        binding.fab.setOnClickListener(view -> Navigation
                .findNavController(view)
                .navigate(R.id.action_classHomeWorkListFragment_to_addClassHomeworkFragment)
        );

        shimmerFrameLayout = binding.shimmerLayout;
        shimmerFrameLayout.startShimmer();

        getClassHomework();

        return binding.getRoot();
    }

    private void getClassHomework() {

        @SuppressLint("NotifyDataSetChanged")
        Response.Listener<String> responseListener = response -> {

            try {

                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("ClassHomework");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject classHomeworkItem = jsonArray.getJSONObject(i);
                    boolean success = classHomeworkItem.getBoolean(getString(R.string.success));

                    if (success) {

                        Log.i(TAG, classHomeworkItem.toString());
                        ClassHomework homework = new ClassHomework(
                                classHomeworkItem.getString(getString(R.string.homework_title)),
                                classHomeworkItem.getString(getString(R.string.homework_script)),
                                classHomeworkItem.getString(getString(R.string.homework_deadline))
                        );
                        classHomeworkList.add(homework);
                        adapter.notifyDataSetChanged();
                    }
                }

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        GetClassHomeworkRequest getClassHomeworkRequest = new GetClassHomeworkRequest(
                mainViewModel.getClassTitle().getValue(),
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(getClassHomeworkRequest);
    }

    @Override
    public void onResume() {
        super.onResume();

        classHomeworkList.clear();
    }
}