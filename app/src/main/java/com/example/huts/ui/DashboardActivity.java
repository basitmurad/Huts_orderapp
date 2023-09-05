package com.example.huts.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
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
import com.example.utils.InternetChecker;
import com.example.utils.NetworkChanger;
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
    private String userEmail, userName , userNumber , userFcmToken;
    private BroadcastReceiver broadcastReceiver;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        broadcastReceiver = new NetworkChanger();
        registerNetworkChangeReceiver();
        firebaseAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(this);

        getToken();





        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("UsersDetail").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve user data using the User class
                    Users user = dataSnapshot.getValue(Users.class);

                    if (user != null) {
                        userEmail = user.getEmail();
                        userName = user.getName();
                        userNumber = user.getNumber();
                        userFcmToken = user.getUserFcmToken();
                        String pass = user.getPassword();


                        sessionManager.saveCredentials(userName,pass,userEmail,userNumber,userFcmToken);



                        // ... other fields
                        //   Toast.makeText(DashboardActivity.this, " user" + userName + userEmail, Toast.LENGTH_SHORT).show();
                        NavigationView navigationView = findViewById(R.id.navView);
                        View headerView = navigationView.getHeaderView(0); // Get the header layout
                        TextView nameHeaderTextView = headerView.findViewById(R.id.nameHeader);
                        TextView emailHeaderTextView = headerView.findViewById(R.id.emailHeader);


                        sessionManager.saveEmailAndPassword(userName, userEmail);

                        nameHeaderTextView.setText(userName);
                        emailHeaderTextView.setText(userEmail);

                    } else {
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


        binding.logout12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();

                startActivity(new Intent(DashboardActivity.this, SignUpActivity.class));
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
            } else if (itemId == R.id.nav_invites) {

                // Create an invitation message with an Instagram profile link
                String inviteMessage = "Join us on our awesome app!\n" + "Follow us on Instagram: https://www.instagram.com/mr__bushoo/";

                // Create an intent to send the message
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, inviteMessage);

                // Create a chooser dialog to let the user choose an app
                Intent chooser = Intent.createChooser(intent, "Invite Friends");

                // Check if there are apps that can handle the intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                } else {
                    // No apps can handle the intent
                    Toast.makeText(DashboardActivity.this, "No apps available", Toast.LENGTH_SHORT).show();
                }

            }
            else if (itemId == R.id.nav_chats) {

                startActivity(new Intent(DashboardActivity.this, ChatsActivity.class));
            } else if (itemId == R.id.nav_terms) {
                {
                    Toast.makeText(this, "terms and conditions", Toast.LENGTH_SHORT).show();
                }

            }


            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
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

    private void getToken() {

        DatabaseReference adminDetailRef;

        adminDetailRef = FirebaseDatabase.getInstance().getReference("AdminDetail");
        adminDetailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Admin admin = dataSnapshot.getValue(Admin.class);



                        sessionManager.setAdminFcmToken(admin.getFcmToken());
                        sessionManager.setAdminUerId(admin.getUserId());



                    }


                } else {

                    Toast.makeText(DashboardActivity.this, "No user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


                Toast.makeText(DashboardActivity.this, "database error" + error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        InternetChecker internetChecker = new InternetChecker(DashboardActivity.this);
        if (!internetChecker.isConnected()) {

            internetChecker.showInternetDialog();
        }
    }

    private void registerNetworkChangeReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, filter);
    }

    private void unregisterNetworkChangeReceiver() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChangeReceiver();
    }
}