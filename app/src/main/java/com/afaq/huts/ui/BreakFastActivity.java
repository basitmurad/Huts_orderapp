package com.afaq.huts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.afaq.huts.R;
import com.afaq.huts.SessionManager;
import com.afaq.huts.adapters.BreakFastAdapter;
import com.afaq.huts.databinding.ActivityBreakFastBinding;
import com.afaq.huts.model.BreakfastClass;

import java.util.ArrayList;

public class BreakFastActivity extends AppCompatActivity {

    private ArrayList<BreakfastClass> list;
    private ArrayList<BreakfastClass> filteredList;
    private BreakFastAdapter adapter;
    private ActivityBreakFastBinding binding;
    private String hutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBreakFastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hutName = getIntent().getStringExtra("hutname");

        list = new ArrayList<>();
        filteredList = new ArrayList<>();
        list.clear();
        filteredList.clear();

        SessionManager sessionManager = new SessionManager(this);

//        Toast.makeText(this, "admin userId " +sessionManager.getAdminUserId(), Toast.LENGTH_SHORT).show();

        list.add(new BreakfastClass("Sandwitch", "100", R.drawable.sandwitch));
        list.add(new BreakfastClass("Anda Burji", "50", R.drawable.andaburji));
        list.add(new BreakfastClass("Jam Malai", "60", R.drawable.jammalai));
        list.add(new BreakfastClass("Roghni Naan", "60", R.drawable.roghnnaan));

        list.add(new BreakfastClass("Omlete", "50", R.drawable.omlete));
        list.add(new BreakfastClass("Chaye", "50", R.drawable.chaye));
        list.add(new BreakfastClass("Naan", "30", R.drawable.naan));
        list.add(new BreakfastClass("Lahori chaney", "100", R.drawable.lahorichaneyingle));

        list.add(new BreakfastClass("Paratha", "50", R.drawable.paratha));
        list.add(new BreakfastClass("Aloo Paratha", "100", R.drawable.alooparatha));
        list.add(new BreakfastClass("Anda Fri", "50", R.drawable.andafri));


        filteredList.addAll(list);

        adapter = new BreakFastAdapter(this, filteredList, hutName);

        binding.recyclerBreakfast.setAdapter(adapter);
        binding.recyclerBreakfast.setLayoutManager(new LinearLayoutManager(this));

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BreakFastActivity.this, CartsActivity.class));
            }
        });
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
//                filter(newText);
//                return true;
//            }
//        });

    }

    private void filter(String query) {
        filteredList.clear();
        for (BreakfastClass item : list) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }


        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No matching items found.", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }
}


















