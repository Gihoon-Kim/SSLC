package com.example.sslc.teacher_side_activities.ui.myClassMain.ClassHomeWork;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sslc.R;
import com.example.sslc.adapters.TeacherClassHomeworkAdapter;
import com.example.sslc.data.ClassHomework;
import com.example.sslc.databinding.FragmentClassHomeworkListBinding;
import com.example.sslc.teacher_side_activities.ui.myClassMain.TeacherMyClassDetailViewModel;

import java.util.ArrayList;

public class ClassHomeWorkListFragment extends Fragment {

    private FragmentClassHomeworkListBinding binding;
    private TeacherMyClassDetailViewModel mainViewModel;

    private TeacherClassHomeworkAdapter adapter;
    private ArrayList<ClassHomework> classHomeworkList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        classHomeworkList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentClassHomeworkListBinding.inflate(
                inflater,
                container,
                false
        );

        mainViewModel =
                new ViewModelProvider(requireActivity()).get(TeacherMyClassDetailViewModel.class);

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

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        classHomeworkList.clear();
    }
}