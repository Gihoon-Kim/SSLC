package com.example.sslc.teacher_side_activities.ui.myClassMain.ClassNews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sslc.R;
import com.example.sslc.adapters.TeacherClassNewsAdapter;
import com.example.sslc.data.ClassNews;
import com.example.sslc.databinding.FragmentTeacherMyClassNewsBinding;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ClassNewsFragment extends Fragment {

    private FragmentTeacherMyClassNewsBinding binding;
    private TeacherClassNewsAdapter adapter;

    private ArrayList<ClassNews> classNewsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        classNewsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTeacherMyClassNewsBinding.inflate(
                inflater,
                container,
                false
        );
        RecyclerView rv_NewsList = binding.rvClassNewsList;
        adapter = new TeacherClassNewsAdapter();
        rv_NewsList.setHasFixedSize(true);
        rv_NewsList.setAdapter(adapter);
        rv_NewsList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv_NewsList.addItemDecoration(new DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
        ));

        binding.fab.setOnClickListener(view -> addNews());

        return binding.getRoot();
    }

    private void addNews() {

        AddClassNewsFragment addClassNewsFragment = new AddClassNewsFragment();
        getParentFragmentManager().beginTransaction().replace(R.id.constraintLayout, addClassNewsFragment).commit();
        binding.fab.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}