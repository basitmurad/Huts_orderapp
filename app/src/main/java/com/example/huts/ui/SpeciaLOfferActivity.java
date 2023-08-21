
package com.example.huts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.huts.R;
import com.example.huts.databinding.ActivitySpeciaLofferBinding;

public class SpeciaLOfferActivity extends AppCompatActivity {

    private ActivitySpeciaLofferBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpeciaLofferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}