package com.sslc.sslc.common_fragment_activities.ClassHomeWork;

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
import com.sslc.sslc.databinding.FragmentClassHomeworkDetailStudentBinding;

import java.util.Objects;

/*
 * This fragment is for students.
 */
public class DetailClassHomeworkFragmentS extends Fragment {

    private FragmentClassHomeworkDetailStudentBinding binding;
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

        binding = FragmentClassHomeworkDetailStudentBinding.inflate(
                inflater,
                container,
                false
        );
        mainViewModel = new
                ViewModelProvider(requireActivity()).get(MyClassDetailViewModel.class);

        init();

        return binding.getRoot();
    }

    private void init() {

        binding.tvHomeworkTitle.setText(Objects.requireNonNull(mainViewModel.getClassHomeworkLiveData().getValue()).getTitle());
        binding.tvHomeworkScript.setText(mainViewModel.getClassHomeworkLiveData().getValue().getScript());
        binding.tvHomeworkDeadline.setText(mainViewModel.getClassHomeworkLiveData().getValue().getDeadline());

        binding.ivBack.setOnClickListener(this::backToMain);
    }

    private void backToMain(View view) {

        Navigation.findNavController(view)
                .navigate(R.id.action_detailClassHomeworkFragmentS_to_classHomeWorkListFragment);
    }
}
