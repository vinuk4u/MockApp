package com.iv.mockapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iv.mockapp.fragments.MAViewPagerFragment;

/**
 * Created by vineeth on 09/09/16
 */
public class MAViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGE_COUNT = 4;

    public MAViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return MAViewPagerFragment.init(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
