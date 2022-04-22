package com.sslc.sslc.admin_side_activities;

import android.content.Context;
import android.util.Log;

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

    private static final String TAG = SectionsPagerAdapter.class.getSimpleName();

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.news, R.string.teacher, R.string.student, R.string.program};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);

        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Log.i(TAG, String.valueOf(position));
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

    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}
