package com.example.huts.ui;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huts.Admin;
import com.example.huts.DashboardClass;
import com.example.huts.R;
import com.example.huts.SessionManager;
import com.example.huts.adapters.DashboardAdapter;
import com.example.huts.databinding.ActivityDashboardBinding;
import com.example.huts.model.Users;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private DashboardAdapter dashboardAdapter;
    private SessionManager sessionManager;
    private ArrayList<DashboardClass> list;
    private FirebaseAuth firebaseAuth;
    private String userEmail ,userName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(this);



        DatabaseReference adminDetailRef = FirebaseDatabase.getInstance().getReference("AdminDetail");
        DatabaseReference userRef = adminDetailRef.child("aylalHdctZWghIdg9UCvGLdFZzw2"); // Replace with the actual userId

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String fcmToken = dataSnapshot.child("fcmToken").getValue(String.class);

                    sessionManager.setAdminFcmToken(fcmToken);
          //          Toast.makeText(DashboardActivity.this, ""+fcmToken, Toast.LENGTH_SHORT).show();
                    // Use the fcmToken as needed (e.g., sending notifications)
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });




        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        Toast.makeText(this, "" + userID
//                , Toast.LENGTH_SHORT).show();


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("UsersDetail").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve user data using the User class
                    Users user = dataSnapshot.getValue(Users.class);

                    if (user != null) {
                        userEmail = user.getEmail();
                        userName= user.getName();

                        // ... other fields
                     //   Toast.makeText(DashboardActivity.this, " user" + userName + userEmail, Toast.LENGTH_SHORT).show();
                        NavigationView navigationView = findViewById(R.id.navView);
                        View headerView = navigationView.getHeaderView(0); // Get the header layout
                        TextView nameHeaderTextView = headerView.findViewById(R.id.nameHeader);
                        TextView emailHeaderTextView = headerView.findViewById(R.id.emailHeader);


                       sessionManager.saveEmailAndPassword(userName, userEmail);

                        nameHeaderTextView.setText(userName);
                        emailHeaderTextView.setText(userEmail);

                    }
                    else {
                        Toast.makeText(DashboardActivity.this, "No user", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DashboardActivity.this, "database error" + databaseError
                        .getMessage(), Toast.LENGTH_SHORT).show();

                // Handle error
            }
        });





        list = new ArrayList<>();
        list.add(new DashboardClass("BreakFast", "Oder from your favorite\n" +
                "restaurants and home \n" +
                "chefs", R.drawable.breakfast));

        list.add(new DashboardClass("Lunch & Dinner", "Oder from your favorite\n" +
                "restaurants and home \n" +
                "chefs", R.drawable.lunch));
        list.add(new DashboardClass("BreakFast", "Oder from your favorite\n" +
                "restaurants and home \n" +
                "chefs"));

        list.add(new DashboardClass("Fast Food & Others", "Oder from your favorite\n" +
                "restaurants and home \n" +
                "chefs", R.drawable.huts));

        list.add(new DashboardClass("Huts Special", "Oder from your favorite\n" +
                "restaurants and home \n" +
                "chefs", R.drawable.hutspecial));


        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();

                startActivity(new Intent(DashboardActivity.this,SignUpActivity.class));
                finish();
            }
        });

        binding.btnBreakfast.setOnClickListener(v -> startActivity(new Intent(
                DashboardActivity.this, BreakFastActivity.class
        )));
//
        binding.btnLunchAndDinner.setOnClickListener(v -> startActivity(new Intent(
                DashboardActivity.this, LunchAndDinnerActivity.class
        )));

        binding.btnfastFood.setOnClickListener(v -> startActivity(new Intent(
                DashboardActivity.this, FastFoodAndOtherActivity.class
        )));

        binding.btnHutsSpecial.setOnClickListener(v -> startActivity(new Intent(
                DashboardActivity.this, SpeciaLOfferActivity.class
        )));

        dashboardAdapter = new DashboardAdapter(this, list);
        sessionManager = new SessionManager(this);




        binding.navView.setNavigationItemSelectedListener(item -> {
            // Handle navigation item clicks here
            int itemId = item.getItemId();

            if (itemId == R.id.nav_myordders) {

                startActivity(new Intent(DashboardActivity.this, MyOrdersActivity.class));
            } else if (itemId == R.id.nav_profile) {
                // Navigate to ProfileFragment
                Toast.makeText(this, "Profile here", Toast.LENGTH_SHORT).show();
            }


            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        binding.btnLogout.setOnClickListener(v -> {

            firebaseAuth.signOut();
        });


        View appbarVIew = findViewById(R.id.include);


        ImageView imageView = findViewById(R.id.navDrawer);


        imageView.setOnClickListener(v -> {
            if (binding.drawerLayout.isOpen())

                binding.drawerLayout.closeDrawer(GravityCompat.START);

            else binding.drawerLayout.openDrawer(GravityCompat.START);
        });


        ImageView imageView1 = findViewById(R.id.imageView3);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, CartsActivity.class));
            }
        });


//        Toast.makeText(this, ""+sessionManager.getNaame(), Toast.LENGTH_SHORT).show();



    }
    private void getUserDetail() {


        String uerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("UsersDetail").child(uerID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            userName = snapshot.child("username").getValue(String.class);
                            Toast.makeText(DashboardActivity.this, "Exist" + userName, Toast.LENGTH_SHORT).show();
                            String userEmail = snapshot.child("email").getValue(String.class);

                        } else {
                            Toast.makeText(DashboardActivity.this, "no data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DashboardActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
        DbHelper dbHelper = new DbHelper(this);
        dbHelper.deleteAllOrders();
    }
}