package com.afaq.huts.details;

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
import com.afaq.huts.databinding.ActivityParadiseHutsBinding;
import com.afaq.huts.model.BreakfastClass;
import com.afaq.huts.ui.CartsActivity;

import java.util.ArrayList;

public class ParadiseHutsActivity extends AppCompatActivity {

    private ActivityParadiseHutsBinding binding;
    private ArrayList<BreakfastClass> list;
    private ArrayList<BreakfastClass> filteredList;
    private BreakFastAdapter adapter;
    private String hutName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityParadiseHutsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        hutName= getIntent().getStringExtra("hutname");


        list = new ArrayList<>();
        filteredList = new ArrayList<>();
        list.clear();
        filteredList.clear();

        list.add(new BreakfastClass("Kabuli pulao", "250", R.drawable.kabulipulao));
        list.add(new BreakfastClass("Bannu beef pulao", "250", R.drawable.beefpulao));
        list.add(new BreakfastClass("Chicken jalfrezi", "180", R.drawable.chickenjalferzi));
        list.add(new BreakfastClass("Alfredo pasta", "350", R.drawable.chickenpasta));
        list.add(new BreakfastClass("Cajun pasta", "350", R.drawable.canjupasta));
        list.add(new BreakfastClass("Manchurian w rice", "380", R.drawable.manchurianrice));
        list.add(new BreakfastClass("Chilli dry w rice", "380", R.drawable.chillirice));
        list.add(new BreakfastClass("Shashlik w rice", "380", R.drawable.shashlikrice));
        list.add(new BreakfastClass("Zinger burger", "350", R.drawable.zingerburger));
        list.add(new BreakfastClass("Chicken burger", "350", R.drawable.chickenburger));
        list.add(new BreakfastClass("Club sandwich", "250", R.drawable.clubsandwich));
        list.add(new BreakfastClass("Egg fried rice", "250", R.drawable.eggfiedrice));
        list.add(new BreakfastClass("Chicken chowmein", "350", R.drawable.chickenchowmein));
        list.add(new BreakfastClass("Qourma", "180", R.drawable.qourma));
        list.add(new BreakfastClass("Grilled chicken burger", "350", R.drawable.chickenburger));
        list.add(new BreakfastClass("Mayo fries", "200", R.drawable.mayofries));
        list.add(new BreakfastClass("Chicken roll paratha", "200", R.drawable.chickenrollparatha));
        list.add(new BreakfastClass("Chicken shawarma", "200", R.drawable.chickenshawarma));
        list.add(new BreakfastClass("Roti", "20", R.drawable.roti));
        filteredList.addAll(list);

        adapter = new BreakFastAdapter(this, filteredList, hutName);

        binding.MajRec.setAdapter(adapter);
        binding.MajRec.setLayoutManager(new LinearLayoutManager(this));

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParadiseHutsActivity.this, CartsActivity.class));
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