package com.afaq.huts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.afaq.huts.R;
import com.afaq.huts.adapters.HutAdapter;

import com.afaq.huts.databinding.ActivityHutsBinding;
import com.afaq.huts.model.HutsClass;


import java.util.ArrayList;

public class HutsActivity extends AppCompatActivity {

    private ActivityHutsBinding binding;
    private ArrayList<HutsClass> list ;
    private ArrayList<HutsClass> filterList;
    private HutAdapter hutAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHutsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        list = new ArrayList<>();
        filterList = new ArrayList<>();

        list.clear();
        filterList.clear();


        list.add(new HutsClass("Qau Cafe", "10:00  to 22:00 ", R.drawable.farward));
        list.add(new HutsClass("Majeed Hut", "10:00  to 22:00 ", R.drawable.farward));
        list.add(new HutsClass("Social Hut", "10:00  to 18:00 ", R.drawable.farward));
        list.add(new HutsClass("Paradise Hut", "10:00  to 22:00 ", R.drawable.farward));
        list.add(new HutsClass("Mphil Canteen", "10:00  to 22:00 ", R.drawable.farward));
        list.add(new HutsClass("Quetta Cafe", "10:00  to 23:00 ", R.drawable.farward));
        list.add(new HutsClass("Faizan Hut", "10:00  to 16:00 ", R.drawable.farward));
        list.add(new HutsClass("Hikmat Hut", "10:00  to 22:00 ", R.drawable.farward));
        list.add(new HutsClass("Shabbir Hut", "10:00  to 22:00 ", R.drawable.farward));
        list.add(new HutsClass("Daniyal Hut", "10:00  to 16:00 ", R.drawable.farward));

        list.add(new HutsClass("Bio Hut", "10:00  to 16:00 ", R.drawable.farward));

        list.add(new HutsClass("H9 Canteen", "4:00  to 23:00 ", R.drawable.farward));









        filterList.addAll(list);
        hutAdapter =new HutAdapter(this, filterList);

        binding.hutRecycler.setAdapter(hutAdapter);
        binding.hutRecycler.setLayoutManager(new LinearLayoutManager(this));



        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        filterList.clear();
        for (HutsClass item : list) {
            if (item.getHutsName().toLowerCase().contains(query.toLowerCase())) {
                filterList.add(item);
            }
        }


        if (filterList.isEmpty()) {
            Toast.makeText(this, "No matching items found.", Toast.LENGTH_SHORT).show();
        }
        hutAdapter.notifyDataSetChanged();
    }

}