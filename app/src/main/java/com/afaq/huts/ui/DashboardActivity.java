package com.afaq.huts.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afaq.huts.Admin;
import com.afaq.huts.DashboardClass;

import com.afaq.huts.R;
import com.afaq.huts.SessionManager;
import com.afaq.huts.adapters.DashboardAdapter;
import com.afaq.huts.databinding.ActivityDashboardBinding;
import com.afaq.huts.model.Senders;
import com.afaq.huts.model.Users;
import com.afaq.utils.GetDateTime;
import com.afaq.utils.InternetChecker;
import com.afaq.utils.NetworkChanger;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private DashboardAdapter dashboardAdapter;
    private SessionManager sessionManager;
    private ArrayList<DashboardClass> list;
    private FirebaseAuth firebaseAuth;
    private String userEmail, userName, userNumber, userFcmToken;
    private BroadcastReceiver broadcastReceiver;

    private DatabaseReference databaseReference;

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
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("SenderAdmin");


        getToken();


//        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                binding.notif.setVisibility(View.INVISIBLE);
//                Intent intent = new Intent(DashboardActivity.this, ChatsActivity.class);
//
//
//                intent.putExtra("id", sessionManager.getAdminUserId());
//                startActivity(intent);
//
//
//                databaseReference.child(sessionManager.getAdminUserId()).child("read").setValue(false);
//
//                finish();
//            }
//        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.notif.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(DashboardActivity.this, ChatsActivity.class);

                String adminUserId = sessionManager.getAdminUserId();
                if (adminUserId != null) {
                    intent.putExtra("id", adminUserId);
                    startActivity(intent);

                    // Update the 'read' status in the database
                    databaseReference.child(adminUserId).child("read").setValue(false);


                } else {

                    Toast.makeText(DashboardActivity.this, "Database error", Toast.LENGTH_SHORT).show();
                    // Handle the case where adminUserId is null
                    // You can show a message or take appropriate action here.
                }
            }
        });


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


                        sessionManager.saveCredentials(userName, pass, userEmail, userNumber, userFcmToken);


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

        list.add(new DashboardClass("Lunch  ", "Oder from your favorite\n" +
                "restaurants and home \n" +
                "chefs", R.drawable.lunch));
        list.add(new DashboardClass("Dinner", "Oder from your favorite\n" +
                "restaurants and home \n" +
                "chefs"));


        binding.logout12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();

                startActivity(new Intent(DashboardActivity.this, SignUpActivity.class));
                finish();
            }
        });

        binding.btnBreakfast.setOnClickListener(v -> startActivity(new Intent(
                DashboardActivity.this, HutsActivity.class
        )));
//
        binding.btnLunchAndDinner.setOnClickListener(v -> startActivity(new Intent(
                DashboardActivity.this, HutsActivity.class
        )));

        binding.btnfastFood.setOnClickListener(v -> startActivity(new Intent(
                DashboardActivity.this, HutsActivity.class
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
                String inviteMessage = "Join us on our awesome app!\n" + "Download the app from : https://play.google.com/store/apps/details?id=com.afaq.huts/";

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

            } else if (itemId == R.id.nav_chats) {

                startActivity(new Intent(DashboardActivity.this, ChatsActivity.class));

                binding.notif.setVisibility(View.INVISIBLE);
            } else if (itemId == R.id.nav_terms) {
                {
                    String privacyPolicyUrl = "https://doc-hosting.flycricket.io/huts-privacy-policy/6e630c34-22d9-48ab-a097-505d4f0c3921/privacy"; // Replace with your privacy policy URL
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl));
                    startActivity(intent);

                    return true;

                }
                // https://doc-hosting.flycricket.io/huts-privacy-policy/6e630c34-22d9-48ab-a097-505d4f0c3921/privacy
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

    private void adminDetail() {
        databaseReference.child(sessionManager.getAdminUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Senders senders = dataSnapshot.getValue(Senders.class);
//                    Toast.makeText(DashboardActivity.this, ""+senders.getUserId(), Toast.LENGTH_SHORT).show();

                    if (senders.isRead()) {
                        binding.notif.setVisibility(View.VISIBLE);
                    }


                } else {

                    binding.notif.setVisibility(View.INVISIBLE);
                    // User data does not exist

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here
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


    @Override
    protected void onResume() {
        super.onResume();

        InternetChecker internetChecker = new InternetChecker(DashboardActivity.this);
        if (!internetChecker.isConnected()) {

            internetChecker.showInternetDialog();
        }

        String adminUserId = sessionManager.getAdminUserId();
        if (adminUserId != null) {
            databaseReference.child(adminUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Senders senders = dataSnapshot.getValue(Senders.class);
                        if (senders.isRead()) {
                            binding.notif.setVisibility(View.VISIBLE);
                        }
                    } else {
                        binding.notif.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle potential errors here

                    Toast.makeText(DashboardActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
//        else {
//            getToken();
//        }
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

    @Override
    protected void onPause() {
        super.onPause();
        String adminUserId = sessionManager.getAdminUserId();
        if (adminUserId != null) {
            databaseReference.child(adminUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Senders senders = dataSnapshot.getValue(Senders.class);
                        if (senders.isRead()) {
                            binding.notif.setVisibility(View.VISIBLE);
                        }
                    } else {
                        binding.notif.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle potential errors here

                    Toast.makeText(DashboardActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
//        else {
//            getToken();
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sessionManager.getAdminFcmToken() == null && sessionManager.getAdminUserId() == null) {
            getToken();
        }


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

                        if (admin != null) {
                            sessionManager.setAdminFcmToken(admin.getFcmToken());
                            sessionManager.setAdminUerId(admin.getUserId());
                        } else {
                            Toast.makeText(DashboardActivity.this, "No Admin ", Toast.LENGTH_SHORT).show();
                        }


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
}