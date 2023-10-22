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
import com.afaq.huts.databinding.ActivityFastFoodAndOtherBinding;
import com.afaq.huts.model.BreakfastClass;

import java.util.ArrayList;

public class FastFoodAndOtherActivity extends AppCompatActivity {

    private ActivityFastFoodAndOtherBinding binding;

    private ArrayList<BreakfastClass> list;
    private ArrayList<BreakfastClass> filterList;
    private BreakFastAdapter breakFastAdapter;
    private String hutName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFastFoodAndOtherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        hutName  = getIntent()
                .getStringExtra("hutname");


       list = new ArrayList<>();
       filterList = new ArrayList<>();
       list.clear();
       filterList.clear();




        list.add(new BreakfastClass("Zinger Roll", "250", R.drawable.zingerroll));
        list.add(new BreakfastClass("Samosa Chat", "100", R.drawable.samosachat));
        list.add(new BreakfastClass("Dahi Bhaley", "100", R.drawable.dahibaley));
        list.add(new BreakfastClass("Chaney Chat", "100", R.drawable.chaneychat));
        list.add(new BreakfastClass("Chicken Shawarma", "180", R.drawable.chickenshawarma));
        list.add(new BreakfastClass("Zinger Burger", "250", R.drawable.zingerburger));
        list.add(new BreakfastClass("Zinger Burger(2X)", "350", R.drawable.zingerburger));
        list.add(new BreakfastClass("Chicken Burger", "250", R.drawable.chickenburger));
        list.add(new BreakfastClass("Anda Burger", "120", R.drawable.andaburger));
        list.add(new BreakfastClass("Zinger Shawarma", "250", R.drawable.zingershawarma));
        list.add(new BreakfastClass("Strawberry Shake", "150", R.drawable.stawberyshake));
        list.add(new BreakfastClass("Chicken Soup", "150", R.drawable.chickensoap));
        list.add(new BreakfastClass("Lemon Soda", "100", R.drawable.lamonsoda));
        list.add(new BreakfastClass("Peach Shake", "110", R.drawable.peechshake));
        list.add(new BreakfastClass("Banana Shake", "110", R.drawable.bananashake));
        list.add(new BreakfastClass("Mango Shake", "110", R.drawable.mangoshake));
        list.add(new BreakfastClass("Grapes Juice", "110", R.drawable.graphsjuice));
        list.add(new BreakfastClass("Fruit Chart", "100", R.drawable.fruitchat));
        list.add(new BreakfastClass("Orange Juice", "110", R.drawable.orangeshake));
        list.add(new BreakfastClass("Apple Shake", "110", R.drawable.appleshake));
        list.add(new BreakfastClass("Falsa Juice", "110", R.drawable.falsajuice));




        filterList.addAll(list);
       breakFastAdapter = new BreakFastAdapter(this , filterList , hutName);

       binding.recyclerFastFood.setAdapter(breakFastAdapter);
       binding.recyclerFastFood.setLayoutManager(new LinearLayoutManager(this));
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

//       binding.btnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//           @Override
//           public boolean onQueryTextSubmit(String query) {
//               return false;
//           }
//
//           @Override
//           public boolean onQueryTextChange(String newText) {
//               Filter(newText);
//               return false;
//           }
//       });


       binding.btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });

       binding.btncart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(FastFoodAndOtherActivity.this, CartsActivity.class));
           }
       });


    }

    private void filter(String newText) {

        filterList.clear();
        for (BreakfastClass item :  list)
        {
            if (item.getName().toLowerCase().contains(newText.toLowerCase()))
            {
                filterList.add(item);
            }
        }

        if (filterList.isEmpty())
        {
            Toast.makeText(this, "No matching items found.", Toast.LENGTH_SHORT).show();
        }
        breakFastAdapter.notifyDataSetChanged();
    }
}