package com.sslc.sslc.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sslc.sslc.fragments.ClassFragment;
import com.sslc.sslc.fragments.NewsFragment;
import com.sslc.sslc.fragments.StudentFragment;
import com.sslc.sslc.fragments.TeacherFragment;

public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 1:
                return new TeacherFragment();
            case 2:
                return new StudentFragment();
            case 3:
                return new ClassFragment();
            default:
                return new NewsFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
