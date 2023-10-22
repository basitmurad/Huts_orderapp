
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
import com.afaq.huts.databinding.ActivitySpeciaLofferBinding;
import com.afaq.huts.model.BreakfastClass;

import java.util.ArrayList;

public class SpeciaLOfferActivity extends AppCompatActivity {

    private ActivitySpeciaLofferBinding binding;
    private ArrayList<BreakfastClass> list;
    private ArrayList<BreakfastClass> filterList;
    private String hutName;
    private BreakFastAdapter breakFastAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpeciaLofferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hutName = getIntent().getStringExtra("hutname");

        list = new ArrayList<>();
        filterList = new ArrayList<>();
        list.clear();
        filterList.clear();

        list.add(new BreakfastClass("Momos", "350", R.drawable.momos));
        list.add(new BreakfastClass("Chowmein", "350", R.drawable.chowmin));
        list.add(new BreakfastClass("Chicken chilli rice", "350", R.drawable.chickenchilli));
        list.add(new BreakfastClass("manchurian", "350", R.drawable.manuc));
        list.add(new BreakfastClass("Chicken shashlik rice", "380", R.drawable.chirice));




        filterList.addAll(list);

        breakFastAdapter = new BreakFastAdapter(this, filterList , hutName);

        binding.recyclerLunch.setAdapter(breakFastAdapter);

        binding.recyclerLunch.setLayoutManager(new LinearLayoutManager(this));

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
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SpeciaLOfferActivity.this, CartsActivity.class));
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