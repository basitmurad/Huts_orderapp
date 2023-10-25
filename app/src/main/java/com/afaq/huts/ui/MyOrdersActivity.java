package com.afaq.huts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.afaq.huts.adapters.MyFragmentStateAdapter;
import com.afaq.huts.databinding.ActivityMyOrdersBinding;
import com.google.android.material.tabs.TabLayout;

public class MyOrdersActivity extends AppCompatActivity {

    private ActivityMyOrdersBinding binding;

    private MyFragmentStateAdapter fragmentStateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        fragmentStateAdapter = new MyFragmentStateAdapter(MyOrdersActivity.this);
        binding.viewPager.setAdapter(fragmentStateAdapter);


        binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                binding.viewPager.setCurrentItem(tab.getPosition(), true);

            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tablayout.selectTab(binding.tablayout.getTabAt(position));
            }
        });


    }
}