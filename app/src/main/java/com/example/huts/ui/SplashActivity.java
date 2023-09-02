package com.example.huts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.huts.databinding.ActivitySplashBinding;
import com.example.huts.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String userUid = "YOUR_USER_ID";


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentUser != null) {
                    // User is authenticated, go to the dashboard
//                    applyBlurEffect(SplashActivity.this,binding.imageView,20);
//                    binding.imageView.animate().alpha(0f).setDuration(1000).start();
//                    binding.imageView.animate()
//                            .scaleX(0f)
//                            .scaleY(0f)
//                            .translationXBy(100f) // Move horizontally
//                            .translationYBy(100f) // Move vertically
//                            .setDuration(500)
//                            .start();
                    ObjectAnimator zoomOut = ObjectAnimator.ofFloat(binding.imageView, "scaleX", 1.0f, 0.5f);
                    zoomOut.setDuration(1000); // Animation duration in milliseconds
                    zoomOut.setInterpolator(new DecelerateInterpolator()); // Optional: Add an interpolator for smoothness

                    zoomOut.start();
                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                    //   applyBlurEffect(SplashActivity.this, binding.imageView, 25f); // 25f is the radius of the blur

                    finish(); // Finish the current activity
                } else {

                    binding.imageView.animate().alpha(0f).setDuration(1000).start();
                    binding.imageView.animate()
                            .scaleX(0f)
                            .scaleY(0f)
                            .translationXBy(100f) // Move horizontally
                            .translationYBy(100f) // Move vertically
                            .setDuration(500)
                            .start();
                    // User is not authenticated, go to the signup activity
                    startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
                    finish(); // Finish the current activity
                }
            }
        }, 700);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }





}