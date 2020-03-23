package com.jann_luellmann.thekenapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ThekenappFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public ThekenappFragmentPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ListFragment();
            case 1:
                return new SettingsFragment();
            default:
                return new ListFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.list_heading);
            case 1:
                return context.getString(R.string.settings_heading);
            default:
                return null;
        }
    }
}
