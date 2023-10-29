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



        list.add(new BreakfastClass("Single Kabuli pulao", "250", R.drawable.kabulipulao));
        list.add(new BreakfastClass("Full Kabuli pulao", "280", R.drawable.kabulipulao));
        list.add(new BreakfastClass("Bannu beef pulao", "230", R.drawable.beefpulao));
        list.add(new BreakfastClass("Bannu beef pulao 0.5 kg", "330", R.drawable.beefpulao));
        list.add(new BreakfastClass("Bannu beef pulao 1 kg", "630", R.drawable.beefpulao));
        list.add(new BreakfastClass("Chicken jalfrezi", "180", R.drawable.chickenjalferzi));
        list.add(new BreakfastClass("Alfredo pasta", "350", R.drawable.chickenpasta));
        list.add(new BreakfastClass("Cajun pasta", "350", R.drawable.canjupasta));
        list.add(new BreakfastClass("Manchurian w rice", "400", R.drawable.manchurianrice));
        list.add(new BreakfastClass("Chilli dry w rice", "400", R.drawable.chillirice));
        list.add(new BreakfastClass("Shashlik w rice", "400", R.drawable.shashlikrice));
        list.add(new BreakfastClass("Chinese Briyani", "260", R.drawable.chinessbriyani));
        list.add(new BreakfastClass("Zinger burger", "350", R.drawable.zingerburger));
        list.add(new BreakfastClass("Chicken burger", "350", R.drawable.chickenburger));
        list.add(new BreakfastClass("Club sandwich", "250", R.drawable.clubsandwich));
        list.add(new BreakfastClass("Egg fried rice", "250", R.drawable.eggfiedrice));
        list.add(new BreakfastClass("Chicken chowmein", "320", R.drawable.chickenchowmein));
        list.add(new BreakfastClass("Qourma", "180", R.drawable.qourma));
        list.add(new BreakfastClass("Grilled chicken burger", "350", R.drawable.chickenburger));
        list.add(new BreakfastClass("Mayo fries", "220", R.drawable.mayofries));
        list.add(new BreakfastClass("BBQ chi wings", "420", R.drawable.bbqwings));
        list.add(new BreakfastClass("Tandoori chi plater", "1050", R.drawable.tandoori));
        list.add(new BreakfastClass("Mali Boti 0.5 kg", "620", R.drawable.maliboti));
        list.add(new BreakfastClass("Spicy wings 8 pcs", "420", R.drawable.wings));
        list.add(new BreakfastClass("Chicken roll paratha", "200", R.drawable.chickenrollparatha));
        list.add(new BreakfastClass("Chicken shawarma", "200", R.drawable.chickenshawarma));
        list.add(new BreakfastClass("Roti", "20", R.drawable.roti));
        list.add(new BreakfastClass("Hot & sour soup", "180", R.drawable.hotandsoursoup));
        list.add(new BreakfastClass("Chi corn soup", "180", R.drawable.chicornsoup));
        list.add(new BreakfastClass("American steak", "750", R.drawable.americansteak));
        list.add(new BreakfastClass("Mexican steak", "750", R.drawable.mexicansteak));
        list.add(new BreakfastClass("Terragon steak", "750", R.drawable.terragonsteak));
        list.add(new BreakfastClass("Chicken lasagna", "700", R.drawable.chickenlasagna));
        list.add(new BreakfastClass("Stuffed chicken", "700", R.drawable.stuffedchicken));
        list.add(new BreakfastClass("Italian chicken", "700", R.drawable.italianchicken));
        list.add(new BreakfastClass("American pasta", "350", R.drawable.americanpasta));
        list.add(new BreakfastClass("Moroccan pasta", "350", R.drawable.moroccanpasta));
        list.add(new BreakfastClass("Spicy pasta", "350", R.drawable.spicypasta));
        list.add(new BreakfastClass("Crunch chi burger", "350", R.drawable.crunchchiburger));
        list.add(new BreakfastClass("Mushroom swiss burger", "350", R.drawable.mushroomswissburger));
        list.add(new BreakfastClass("Mexican burger", "350", R.drawable.mexicanburger));
        list.add(new BreakfastClass("Royal burger", "350", R.drawable.royalburger));
        list.add(new BreakfastClass("BBQ chi burger", "350", R.drawable.bbqchiburger));
        list.add(new BreakfastClass("Cheese burger", "350", R.drawable.cheeseburger));
        list.add(new BreakfastClass("Classic cheese sandwich", "300", R.drawable.classiccheesesandwich));
        list.add(new BreakfastClass("Zinger sandwich", "260", R.drawable.zingersandwich));
        list.add(new BreakfastClass("Chi nuggets 10pcs", "530", R.drawable.chinuggets));
        list.add(new BreakfastClass("Chi Madrassi", "260", R.drawable.chimadrassi));
        list.add(new BreakfastClass("Chicken handi", "260", R.drawable.chickenhandi));
        list.add(new BreakfastClass("Banana shake", "120", R.drawable.bananashake));
        list.add(new BreakfastClass("Oreo shake", "120", R.drawable.oreashake));
        list.add(new BreakfastClass("Apple shake", "120", R.drawable.appleshake));
        list.add(new BreakfastClass("Grapes juice", "130", R.drawable.graphjuice));
        list.add(new BreakfastClass("Fruit chat", "120", R.drawable.fruitchat));
        list.add(new BreakfastClass("Fruit chat special", "150", R.drawable.fruitchatspecial));
        list.add(new BreakfastClass("Masammi juice", "120", R.drawable.massamijuice));
        list.add(new BreakfastClass("Orange juice", "120", R.drawable.orangejuice));
        list.add(new BreakfastClass("Falsa juice", "130", R.drawable.falsajuice));
        list.add(new BreakfastClass("Mineral water S", "60", R.drawable.water));
        list.add(new BreakfastClass("Mineral water L", "100", R.drawable.water));
        list.add(new BreakfastClass("Pepsi 200ml", "70", R.drawable.pepsi));
        list.add(new BreakfastClass("Pepsi 1 litre", "160", R.drawable.pepsi));
        list.add(new BreakfastClass("Coke 1 litre", "160", R.drawable.coke));
        list.add(new BreakfastClass("Pepsi 500ml", "120", R.drawable.pepsi));
        list.add(new BreakfastClass("Pepsi 1.5 litre", "190", R.drawable.pepsi));
        list.add(new BreakfastClass("Coke 200ml", "70", R.drawable.coke));
        list.add(new BreakfastClass("Coke 500ml", "120", R.drawable.coke));
        list.add(new BreakfastClass("Coke 1.5 litre", "190", R.drawable.coke));
        list.add(new BreakfastClass("Disposable glass", "5", R.drawable.glasss));



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