package com.example.huts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.huts.adapters.MyFragmentStateAdapter;
import com.example.huts.databinding.ActivityMyOrdersBinding;
import com.google.android.material.tabs.TabLayout;

public class MyOrdersActivity extends AppCompatActivity {

    private ActivityMyOrdersBinding binding;
    private MyFragmentStateAdapter myFragmentStateAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        myFragmentStateAdapter = new MyFragmentStateAdapter(MyOrdersActivity.this);
        binding.viewPager.setAdapter(myFragmentStateAdapter);



        binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                binding.viewPager.setCurrentItem(tab.getPosition(),true);
//                switch (tab.getPosition())
//                {
//                    case 0 :
//                        new ActiveOrdersFragment();
//                        break;
//                    case 1 :
//                        new DeliveredOrdersFragment();
//                        break;
//                    case 2 :
//                        new CancelOrdersFragment();
//                        break;
//
//
//                }
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