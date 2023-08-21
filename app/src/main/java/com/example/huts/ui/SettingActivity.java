package com.example.huts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.huts.R;
import com.example.huts.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}