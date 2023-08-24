package com.example.huts.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.huts.fragments.ActiveOrdersFragment;
import com.example.huts.fragments.CancelOrdersFragment;
import com.example.huts.fragments.DeliverFragment;

public class MyFragmentStateAdapter extends FragmentStateAdapter {

    public MyFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ActiveOrdersFragment();
            case 1:
                return new CancelOrdersFragment();
            case 2:
                return new DeliverFragment();
            default:
                throw new IllegalArgumentException("Invalid tab position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs
    }
}
