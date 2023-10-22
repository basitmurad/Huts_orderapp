package com.afaq.huts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.afaq.huts.R;
import com.afaq.huts.adapters.BreakFastAdapter;
import com.afaq.huts.databinding.ActivityLunchAndDinnerBinding;
import com.afaq.huts.model.BreakfastClass;

import java.util.ArrayList;

public class LunchAndDinnerActivity extends AppCompatActivity {

    private ActivityLunchAndDinnerBinding binding;

    private ArrayList<BreakfastClass> list ;
    private ArrayList<BreakfastClass> filterList;
    private BreakFastAdapter breakFastAdapter;
    private String hutName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLunchAndDinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hutName = getIntent().getStringExtra("hutname");

        list = new ArrayList<>();
        filterList = new ArrayList<>();

        list.clear();
        filterList.clear();




        list.add(new BreakfastClass("Chowmein", "350", R.drawable.chowmin));
        list.add(new BreakfastClass("Egg fried rice", "250", R.drawable.eggfiedrice));
        list.add(new BreakfastClass("Manchurian Rice", "350", R.drawable.manchurianrice));
        list.add(new BreakfastClass("Seekh kabab", "80", R.drawable.seekhkabab));
        list.add(new BreakfastClass("Seekh kabab fri", "150", R.drawable.seekhkabab));
        list.add(new BreakfastClass("Aalu anda", "100", R.drawable.aluanda));
        list.add(new BreakfastClass("Lobya anda", "120", R.drawable.lobiasada));
        list.add(new BreakfastClass("Lobya ", "100", R.drawable.lobia));
        list.add(new BreakfastClass("Chicken laziza", "150", R.drawable.chickenlaziza));
        list.add(new BreakfastClass("Chicken BBQ", "180", R.drawable.chickenbbqpieces));
        list.add(new BreakfastClass("Crisis", "150", R.drawable.crisis));
        list.add(new BreakfastClass("chicken steam rost ", "250", R.drawable.rost));
        list.add(new BreakfastClass("Daal mash", "100", R.drawable.dalmash));
        list.add(new BreakfastClass("Chicken jalfrezi", "180", R.drawable.chickenjalferzi));
        list.add(new BreakfastClass("Kari pakora", "130", R.drawable.karilakora));
        list.add(new BreakfastClass("Lahori kofta", "140", R.drawable.lahorikoftachaney));
        list.add(new BreakfastClass("Sabzi", "120", R.drawable.sabzimix));
        list.add(new BreakfastClass("Daal chaney", "100", R.drawable.dalchaney));
        list.add(new BreakfastClass("Kabuli pulao", "250", R.drawable.kabulipulao));
        list.add(new BreakfastClass("Chicken Qourma", "180", R.drawable.chickenqorma));
        list.add(new BreakfastClass("Haleem", "120", R.drawable.chickenhaleem));
        list.add(new BreakfastClass("Chicken biryani", "200", R.drawable.chickenbiryani));
        list.add(new BreakfastClass("Mutton pulao", "250", R.drawable.muttonpulao));
        list.add(new BreakfastClass("Beef pulao", "250", R.drawable.beefpulao));
        list.add(new BreakfastClass("Chicken dum pokht", "200", R.drawable.salan));
















        filterList.addAll(list);

        breakFastAdapter = new BreakFastAdapter(this, filterList , hutName);

        binding.recyclerLunch.setAdapter(breakFastAdapter);

        binding.recyclerLunch.setLayoutManager(new LinearLayoutManager(this));
        SearchView btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set focus on the SearchView
                btnSearch.setIconified(false);

                // Show the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(btnSearch, InputMethodManager.SHOW_IMPLICIT);
            }
        });

// Set the query listener as you have in your code
        binding.btnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle query text changes
                filter(newText);
                return true;
            }
        });
//        binding.btnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Filter(newText);
//                return false;
//            }
//        });


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LunchAndDinnerActivity.this, CartsActivity.class));
            }
        });

    }

    private void filter(String newText) {

        filterList.clear();

        for (BreakfastClass breakfastClass : list)
        {
            if (breakfastClass.getName().toLowerCase().contains(newText.toLowerCase()))
            {
                filterList.add(breakfastClass);
            }
        }

        if (filterList.isEmpty()) {
            Toast.makeText(this, "No matching items found.", Toast.LENGTH_SHORT).show();
        }
        breakFastAdapter.notifyDataSetChanged();
    }
}