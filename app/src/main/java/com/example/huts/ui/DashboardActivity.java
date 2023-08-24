package com.example.huts.ui;

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

import com.example.huts.DashboardClass;
import com.example.huts.R;
import com.example.huts.SessionManager;
import com.example.huts.adapters.DashboardAdapter;
import com.example.huts.databinding.ActivityDashboardBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private DashboardAdapter dashboardAdapter;
    private SessionManager sessionManager;
    private ArrayList<DashboardClass> list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        sessionManager = new SessionManager(this);


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




        binding.navigationView.setNavigationItemSelectedListener(item -> {
            // Handle navigation item clicks here
            int itemId = item.getItemId();

            if (itemId == R.id.nav_myordders) {

                startActivity(new Intent(DashboardActivity.this, MyOrdersActivity.class));
            } else if (itemId == R.id.nav_profile) {
                // Navigate to ProfileFragment
                Toast.makeText(this, "Profile here", Toast.LENGTH_SHORT).show();
            }
            // Add similar cases for other menu items...

            // Close the drawer after handling the click
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

        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0); // Get the header view

        TextView emailHeaderTextView = headerView.findViewById(R.id.emailHeader);
        TextView nameHeaderTextView = headerView.findViewById(R.id.nameHeader);

        emailHeaderTextView.setText(sessionManager.getEmail());
        nameHeaderTextView.setText(sessionManager.getNaame());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
        DbHelper dbHelper = new DbHelper(this);
        dbHelper.deleteAllOrders();
    }
}