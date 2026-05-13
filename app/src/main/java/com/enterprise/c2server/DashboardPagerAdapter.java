package com.enterprise.c2server;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DashboardPagerAdapter extends FragmentStateAdapter {

    public DashboardPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new DeviceListFragment();
            case 1: return new CommandFragment();
            case 2: return new FileExplorerFragment();
            case 3: return new LiveStreamFragment();
            default: return new DeviceListFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
