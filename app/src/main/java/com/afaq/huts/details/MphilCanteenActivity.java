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
import com.afaq.huts.databinding.ActivityMphilCanteenBinding;
import com.afaq.huts.model.BreakfastClass;
import com.afaq.huts.ui.CartsActivity;

import java.util.ArrayList;

public class MphilCanteenActivity extends AppCompatActivity {

    private ActivityMphilCanteenBinding binding;
    private ArrayList<BreakfastClass> list;
    private ArrayList<BreakfastClass> filteredList;
    private BreakFastAdapter adapter;
    private String hutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMphilCanteenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hutName= getIntent().getStringExtra("hutname");


        list = new ArrayList<>();
        filteredList = new ArrayList<>();
        list.clear();
        filteredList.clear();



        list.add(new BreakfastClass("Chicken Haleem" ,"150", R.drawable.haleem));

        list.clear(); // Clear the existing list

        list.add(new BreakfastClass("Chicken Haleem", "150", R.drawable.haleem));
        list.add(new BreakfastClass("Chi steam roast", "250", R.drawable.roast));
//        list.add(new BreakfastClass("Laziza", "180", R.drawable.la));
        list.add(new BreakfastClass("Aalu gosht", "200", R.drawable.aloghost));
        list.add(new BreakfastClass("Kaliji fri", "150", R.drawable.kalijifri));
        list.add(new BreakfastClass("Aalu special", "120", R.drawable.aluspecial));
        list.add(new BreakfastClass("Sabzi", "120", R.drawable.sabzimix));
        list.add(new BreakfastClass("Dam pokht", "200", R.drawable.dumpkht));
        list.add(new BreakfastClass("Qourma", "200", R.drawable.qourma));
        list.add(new BreakfastClass("Daal chawal", "150", R.drawable.daalchawal));
        list.add(new BreakfastClass("Lobiya fri", "150", R.drawable.lobia));
        list.add(new BreakfastClass("Chaney", "100", R.drawable.dalchaney));
        list.add(new BreakfastClass("Omlete", "50", R.drawable.omlete));
        list.add(new BreakfastClass("Jam malai", "70", R.drawable.jammalai));
        list.add(new BreakfastClass("Roti", "20", R.drawable.roti));
        list.add(new BreakfastClass("Paratha", "50", R.drawable.paratha));
        list.add(new BreakfastClass("Chai", "50", R.drawable.chaye));
        list.add(new BreakfastClass("Egg fri", "50", R.drawable.eggfri));





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
                startActivity(new Intent(MphilCanteenActivity.this, CartsActivity.class));
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