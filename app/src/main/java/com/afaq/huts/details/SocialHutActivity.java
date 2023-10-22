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

import com.afaq.huts.databinding.ActivitySocialHutBinding;
import com.afaq.huts.model.BreakfastClass;
import com.afaq.huts.ui.CartsActivity;

import java.util.ArrayList;

public class SocialHutActivity extends AppCompatActivity {

    private ActivitySocialHutBinding binding;
    private ArrayList<BreakfastClass> list;
    private ArrayList<BreakfastClass> filteredList;
    private BreakFastAdapter adapter;
    private String hutName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySocialHutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        hutName= getIntent().getStringExtra("hutname");


        list = new ArrayList<>();
        filteredList = new ArrayList<>();
        list.clear();
        filteredList.clear();

        list.add(new BreakfastClass("Samosa chat", "120", R.drawable.samosachat));
        list.add(new BreakfastClass("Dahi bhaley", "120", R.drawable.dahibaley));
        list.add(new BreakfastClass("Chaney chat", "120", R.drawable.chaneychat));
        list.add(new BreakfastClass("Zinger burger", "280", R.drawable.zingerburger));
        list.add(new BreakfastClass("Shawarma", "180", R.drawable.sharma));
        list.add(new BreakfastClass("Zinger roll paratha", "250", R.drawable.zingerroll));
        list.add(new BreakfastClass("Anda shami burger", "150", R.drawable.andaburger));
        list.add(new BreakfastClass("Fries", "100", R.drawable.fries));
        list.add(new BreakfastClass("Chowmein", "300", R.drawable.chowin));
        list.add(new BreakfastClass("Biryani", "220", R.drawable.briyani));
        list.add(new BreakfastClass("Chaney", "120", R.drawable.dalchaney));
        list.add(new BreakfastClass("Aalu paratha", "90", R.drawable.alooparatha));
        list.add(new BreakfastClass("Paratha", "50", R.drawable.paratha));
        list.add(new BreakfastClass("Egg fri", "50", R.drawable.eggfiedrice)); // This item was missing in your original list
        list.add(new BreakfastClass("Omlete", "50", R.drawable.omlete)); // This item was missing in your original list
        list.add(new BreakfastClass("Chai", "50", R.drawable.chaye)); // This item was missing in your original list
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
                startActivity(new Intent(SocialHutActivity.this, CartsActivity.class));
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