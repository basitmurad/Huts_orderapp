package com.afaq.huts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.afaq.huts.databinding.ActivitySettingBinding;


public class SettingActivity extends AppCompatActivity {

     private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}