package com.example.sslc.teacher_side_activities.ui.myClassMain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.sslc.databinding.FragmentTeacherMyClassNewsBinding;

/**
 * A placeholder fragment containing a simple view.
 */
public class ClassNewsFragment extends Fragment {

    private ClassNewsViewModel classNewsViewModel;
    private FragmentTeacherMyClassNewsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classNewsViewModel = new ViewModelProvider(this).get(ClassNewsViewModel.class);
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

        final TextView textView = binding.tvClassNewsTextView;
        classNewsViewModel.getText()
                .observe(
                        getViewLifecycleOwner(),
                        textView::setText
                );
        classNewsViewModel.setText("This is First Fragment");

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}