package com.example.huts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.huts.R;
import com.example.huts.adapters.MyFragmentStateAdapter;
import com.example.huts.databinding.ActivityMyOrdersBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyOrdersActivity extends AppCompatActivity {

    private ActivityMyOrdersBinding binding;
    private MyFragmentStateAdapter myFragmentStateAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myFragmentStateAdapter = new MyFragmentStateAdapter(this);
        binding.viewPager.setAdapter(myFragmentStateAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tablayout, binding.viewPager,
                (tab, position) -> {
                    // Set tab text based on position
                    switch (position) {
                        case 0:
                            tab.setText("Active Orders");
                            break;
                        case 1:
                            tab.setText("Deliver Orders");
                            break;
                        case 2:
                            tab.setText("Cancel Orders");
                            break;
                    }
                });
        tabLayoutMediator.attach();
    }
}