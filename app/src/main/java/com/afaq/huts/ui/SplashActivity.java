package com.afaq.huts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.DecelerateInterpolator;

import com.afaq.huts.databinding.ActivitySplashBinding;
import com.afaq.utils.InternetChecker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private InternetChecker internetChecker;
    private   FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String userUid = "YOUR_USER_ID";

        internetChecker = new InternetChecker(this);

        if (!internetChecker.isConnected())
        {
            internetChecker.showInternetDialog();
        }
        else {


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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!internetChecker.isConnected())
        {
            internetChecker.showInternetDialog();
        }
        else {


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
    }
}