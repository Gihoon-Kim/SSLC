package com.sslc.sslc.common_fragment_activities.ClassNews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.sslc.sslc.R;
import com.sslc.sslc.common_fragment_activities.ui.myClassMain.MyClassDetailViewModel;
import com.sslc.sslc.databinding.FragmentClassNewsDetailStudentBinding;

import java.util.Objects;

/*
 * This fragment is for student.
 */
public class DetailClassNewsFragmentS extends Fragment {

    private FragmentClassNewsDetailStudentBinding binding;
    private MyClassDetailViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        binding = FragmentClassNewsDetailStudentBinding.inflate(
                inflater,
                container,
                false
        );

        mainViewModel =
                new ViewModelProvider(requireActivity()).get(MyClassDetailViewModel.class);

        initUI();

        return binding.getRoot();
    }

    private void initUI() {

        binding.tvNewsTitle.setText(
                Objects.requireNonNull(mainViewModel.getClassNewsLiveData().getValue()).getNewsTitle()
        );
        binding.tvNewsDescription.setText(
                mainViewModel.getClassNewsLiveData().getValue().getDescription()
        );
        binding.ivBack.setOnClickListener(view -> Navigation
                .findNavController(view)
                .navigate(R.id.action_detailClassNewsFragmentS_to_fragment_class_news_list)
        );
    }
}
