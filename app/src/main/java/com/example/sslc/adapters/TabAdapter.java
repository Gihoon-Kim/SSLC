package com.example.sslc.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sslc.fragments.ClassFragment;
import com.example.sslc.fragments.NewsFragment;
import com.example.sslc.fragments.StudentFragment;
import com.example.sslc.fragments.TeacherFragment;

public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                NewsFragment notificationFragment = new NewsFragment();
                return notificationFragment;
            case 1:
                TeacherFragment teacherFragment = new TeacherFragment();
                return teacherFragment;
            case 2:
                StudentFragment studentFragment = new StudentFragment();
                return studentFragment;
            case 3:
                ClassFragment classFragment = new ClassFragment();
                return classFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
