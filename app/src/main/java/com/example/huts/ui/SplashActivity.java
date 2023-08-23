package com.example.huts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

        // Get a reference to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference userRef = database.getReference("usersDetail").child("randomUUId");
//
//        // Retrieve user data
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    // Retrieve data and display it
//                    String email = snapshot.child("email").getValue(String.class);
//                    String name = snapshot.child("name").getValue(String.class);
//                    String password = snapshot.child("password").getValue(String.class);
//                    String number = snapshot.child("number").getValue(String.class);
//
//                    String message = "Name: " + name + "\n" +
//                            "Email: " + email + "\n" +
//                            "Password: " + password + "\n" +
//                            "Number: " + number;
//
//                    Toast.makeText(SplashActivity.this, message, Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(SplashActivity.this, "No data found", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(SplashActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });



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