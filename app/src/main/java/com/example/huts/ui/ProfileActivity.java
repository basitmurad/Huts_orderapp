package com.example.huts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.huts.R;
import com.example.huts.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





    }
}