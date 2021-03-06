package com.sslc.sslc.common_fragment_activities;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sslc.sslc.R;
import com.sslc.sslc.common_fragment_activities.ClassHomeWork.ClassHomeWorkFragment;
import com.sslc.sslc.common_fragment_activities.ClassNews.ClassNewsFragment;
import com.sslc.sslc.common_fragment_activities.ClassStudent.ClassStudentFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_class_notification, R.string.tab_class_students, R.string.tab_class_homework};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);

        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 1:
                return new ClassStudentFragment();
            case 2:
                return new ClassHomeWorkFragment();
            default:
                return new ClassNewsFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}