package com.sslc.sslc.admin_side_activities;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sslc.sslc.R;
import com.sslc.sslc.fragments.ClassFragment;
import com.sslc.sslc.fragments.NewsFragment;
import com.sslc.sslc.fragments.StudentFragment;
import com.sslc.sslc.fragments.TeacherFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.news, R.string.teacher, R.string.student, R.string.program};

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
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
        return TAB_TITLES.length;
    }
}
