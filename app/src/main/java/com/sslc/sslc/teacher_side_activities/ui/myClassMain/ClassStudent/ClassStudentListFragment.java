package com.sslc.sslc.teacher_side_activities.ui.myClassMain.ClassStudent;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sslc.sslc.R;
import com.sslc.sslc.adapters.TeacherClassStudentAdapter;
import com.sslc.sslc.data.Student;
import com.sslc.sslc.databinding.FragmentClassStudentListBinding;
import com.sslc.sslc.requests.GetClassStudentRequest;
import com.sslc.sslc.teacher_side_activities.ui.myClassMain.TeacherMyClassDetailViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassStudentListFragment extends Fragment {

    private static final String TAG = ClassStudentListFragment.class.getSimpleName();

    private FragmentClassStudentListBinding binding;
    private TeacherMyClassDetailViewModel mainViewModel;
    private TeacherClassStudentAdapter adapter;
    private ArrayList<Student> classStudentList;
    private ShimmerFrameLayout shimmerFrameLayout;

    private StorageReference storageReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        classStudentList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState
    ) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        binding = FragmentClassStudentListBinding.inflate(
                inflater,
                container,
                false
        );
        mainViewModel =
                new ViewModelProvider(requireActivity())
                        .get(TeacherMyClassDetailViewModel.class);

        adapter = new TeacherClassStudentAdapter(
                requireContext(),
                classStudentList,
                mainViewModel
        );

        RecyclerView rv_ClassStudentList = binding.rvClassStudentList;
        rv_ClassStudentList.setHasFixedSize(true);
        rv_ClassStudentList.setAdapter(adapter);
        rv_ClassStudentList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv_ClassStudentList.addItemDecoration(new DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
        ));

        shimmerFrameLayout = binding.shimmerLayout;
        shimmerFrameLayout.startShimmer();

        getClassStudents();
        return binding.getRoot();
    }

    private void getClassStudents() {

        @SuppressLint("NotifyDataSetChanged")
        Response.Listener<String> responseListener = response -> {

            try {

                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("ClassStudent");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject classStudentItem = jsonArray.getJSONObject(i);
                    boolean success = classStudentItem.getBoolean(getString(R.string.success));

                    if (success) {

                        Log.i(TAG, classStudentItem.toString());

                        if (classStudentItem.getString("hasProfileImage").equals("1")) {

                            storageReference.child("profile_img/".concat("profile_student_")
                                    .concat(classStudentItem.getString(getString(R.string.student_name))).concat(".jpg"))
                                    .getDownloadUrl()
                                    .addOnSuccessListener(uri -> {

                                        try {
                                            Student student = new Student(
                                                    classStudentItem.getString(getString(R.string.student_name)),
                                                    classStudentItem.getString(getString(R.string.student_dob)),
                                                    classStudentItem.getString(getString(R.string.student_introduce)),
                                                    classStudentItem.getString(getString(R.string.student_country)),
                                                    classStudentItem.getString("hasProfileImage").equals("1"),
                                                    uri
                                            );
                                            classStudentList.add(student);

                                            adapter.notifyDataSetChanged();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    });
                        } else {

                            Student student = new Student(
                                    classStudentItem.getString(getString(R.string.student_name)),
                                    classStudentItem.getString(getString(R.string.student_dob)),
                                    classStudentItem.getString(getString(R.string.student_introduce)),
                                    classStudentItem.getString(getString(R.string.student_country)),
                                    classStudentItem.getString("hasProfileImage").equals("1"),
                                    null
                            );

                            classStudentList.add(student);

                            adapter.notifyDataSetChanged();
                        }

                    } else {

                        Log.i(TAG, response);
                    }
                }

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        GetClassStudentRequest getClassStudentRequest = new GetClassStudentRequest(
                mainViewModel.getClassTitle().getValue(),
                responseListener
        );
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(getClassStudentRequest);
    }

    @Override
    public void onResume() {
        super.onResume();

        classStudentList.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}