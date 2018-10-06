package com.example.shoumyo.ruinvolved.ui;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.shoumyo.ruinvolved.ui.fragments.AdminLandingFragment;
import com.example.shoumyo.ruinvolved.ui.fragments.StudentLandingFragment;

public class LandingPageAdapter extends FragmentStatePagerAdapter {

    public LandingPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new StudentLandingFragment();
            case 1:
                return new AdminLandingFragment();
        }
        return null;
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
                return "Student";
            case 1:
                return "Admin";
        }
        return "";
    }
}
