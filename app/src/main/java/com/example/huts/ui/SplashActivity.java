package com.example.huts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.huts.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

   private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentUser != null) {
                    // User is authenticated, go to the dashboard
                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                    finish(); // Finish the current activity
                } else {
                    // User is not authenticated, go to the signup activity
                    startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
                    finish(); // Finish the current activity
                }
            }
        },700);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}