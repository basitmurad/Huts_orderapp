package com.afaq.huts.ui;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.afaq.huts.databinding.ActivitySplashBinding;
import com.afaq.utils.GetDateTime;
import com.afaq.utils.InternetChecker;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private InternetChecker internetChecker;
    private   FirebaseUser currentUser;

    private static final int APP_UPDATE_REQUEST_CODE = 123;

    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String userUid = "YOUR_USER_ID";
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Check for app updates
        checkForAppUpdate();





    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void checkForAppUpdate() {
        // Create a flexible app update request
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                // An update is available, and it's a flexible update (can be deferred).
                // Display a dialog to prompt the user to update.
                showUpdateDialog(appUpdateInfo);
//                Toast.makeText(this, " update available", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "no update available", Toast.LENGTH_SHORT).show();

                // No update is available or it's not a flexible update.
                // Proceed with your normal logic (e.g., checking for internet connectivity).
                checkInternetConnection();
            }
        });

        appUpdateInfoTask.addOnFailureListener(e -> {
            // An error occurred while checking for updates.
            // Proceed with your normal logic (e.g., checking for internet connectivity).
            checkInternetConnection();
        });
    }

    private void showUpdateDialog(AppUpdateInfo appUpdateInfo) {
        // Display a dialog to prompt the user to update the app.
        // Customize the dialog's appearance and content as needed.
        // Example: Use an AlertDialog with a message and buttons.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Available");
        builder.setMessage("A new version of the app is available. Do you want to update now?");
        builder.setPositiveButton("Update", (dialog, which) -> {
            // User clicked "Update," start the update.
            startFlexibleUpdate(appUpdateInfo);
        });
        builder.setNegativeButton("Later", (dialog, which) -> {
            // User clicked "Later," proceed with your normal logic (e.g., checking for internet connectivity).
            checkInternetConnection();
        });
        builder.setCancelable(false); // Prevent canceling the dialog.
        builder.show();
    }

    private void startFlexibleUpdate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    this,
                    APP_UPDATE_REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            // Handle the exception if necessary.
            // Proceed with your normal logic (e.g., checking for internet connectivity).
            checkInternetConnection();
        }
    }

    // Method to check internet connectivity and proceed accordingly
    private void checkInternetConnection() {
        InternetChecker internetChecker = new InternetChecker(this);
        if (!internetChecker.isConnected()) {
            internetChecker.showInternetDialog();
        } else {
            // Proceed with your normal logic, such as checking user authentication and navigating to activities.
            checkUserAuthentication();
        }
    }

    private void checkUserAuthentication() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // User is authenticated, navigate to DashboardActivity.
            navigateToDashboard();
        } else {
            // User is not authenticated, navigate to SignUpActivity.
            navigateToSignUp();
        }
    }

    private void navigateToDashboard() {
        // Start DashboardActivity and finish the current activity.
        startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
        finish();
    }

    private void navigateToSignUp() {
        // Start SignUpActivity and finish the current activity.
        startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
        finish();
    }

}