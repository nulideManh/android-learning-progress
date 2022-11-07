package com.karl.c9_weather;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragList; //Collection of different fragments (pages)

    public MyFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragList) {
        super(fragmentActivity);
        this.fragList = fragList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragList.size();
    }
}
